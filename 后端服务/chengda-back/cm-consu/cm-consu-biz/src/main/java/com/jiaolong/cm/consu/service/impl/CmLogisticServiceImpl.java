package com.jiaolong.cm.consu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.GoodsLogisticTraceDto;
import com.jiaolong.cm.consu.api.dto.LogisticAddressDto;
import com.jiaolong.cm.consu.api.dto.ProductNameDto;
import com.jiaolong.cm.consu.api.dto.UserAddressDto;
import com.jiaolong.cm.consu.api.entity.*;
import com.jiaolong.cm.consu.api.express.QueryTrackResp;
import com.jiaolong.cm.consu.api.param.LogisticTraceParam;
import com.jiaolong.cm.consu.api.param.QueryTrackReq;
import com.jiaolong.cm.consu.api.express.HttpResult;
import com.jiaolong.cm.consu.api.express.HttpUtils;
import com.jiaolong.cm.consu.api.param.TraceFeeVo;
import com.jiaolong.cm.consu.mapper.*;
import com.jiaolong.cm.consu.service.CmLogisticService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc: 物流跟踪服务实现
 * user: pan
 * date: 2024-10-17 14:47
 */
@Slf4j
@Service
public class CmLogisticServiceImpl implements CmLogisticService {

	//授权码
	@Value("${cm.kuaidi100.key}")
	private String key;

	//公司编号
	@Value("${cm.kuaidi100.customer}")
	private String customer;

	//获取物流轨迹url
	@Value("${cm.kuaidi100.queryUrl}")
	private String queryUrl;

	@Autowired
	private KdCompanyMapper kdCompanyMapper;
	@Autowired
	private CmOrderShoppingMapper cmOrderShoppingMapper;
	@Autowired
	private CmUserAddressMapper cmUserAddressMapper;
	@Autowired
	private CmOrderLogisticsMapper orderLogisticsMapper;
	@Autowired
	private CmProductMapper productMapper;
	@Autowired
	private CmProductSkuMapper productSkuMapper;
	@Autowired
	private CmMerchantLogisticsMapper logisticsMapper;
	@Autowired
	private CmOrderProductsMapper orderProductsMapper;

	@Override
	public R<GoodsLogisticTraceDto> traceLogistics(Long orderId) {
		if (orderId == null) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		CmOrderShopping cmOrderShopping = cmOrderShoppingMapper.selectById(orderId);
		if (cmOrderShopping == null) {
			return R.failed(BusinessErrorMessage.NONE_ORDER);
		}

		String orderNo = cmOrderShopping.getOrderNo();
		CmOrderLogistics cmOrderLogistics = orderLogisticsMapper.getByOrderNo(orderNo);
		if (cmOrderLogistics == null) {
			return R.failed(BusinessErrorMessage.NONE_ORDER_LOGISTICS);
		}

		GoodsLogisticTraceDto logisticTrace = new GoodsLogisticTraceDto();
		/* 填充商品信息 */
		List<ProductNameDto> productList = orderProductsMapper.getOrderProductNameList(orderId);
		logisticTrace.setProductList(productList);

		/* 填充物流单号 */
		String logisticsNo = cmOrderLogistics.getLogisticsNo();
		String logisticsName = cmOrderLogistics.getLogisticsName();
		logisticTrace.setLogisticsNo(logisticsNo);
		logisticTrace.setLogisticsName(logisticsName);

		/* 填充物流跟踪 */
		QueryTrackResp queryTrackResp = postForTraceDtoList(logisticsNo, logisticsName);
		logisticTrace.setQueryTrackResp(queryTrackResp);

		/* 填充收件地址 */
		LogisticAddressDto logisticAddressDto = new LogisticAddressDto();
		if (orderId != null) {
			UserAddressDto userAddressDto = cmUserAddressMapper.getUserAddressById(cmOrderShopping.getUserAddressId());
			logisticAddressDto.setConcatPerson(userAddressDto.getConcatPerson());
			logisticAddressDto.setContactPhone(userAddressDto.getContactPhone());
			logisticAddressDto.setAddress(userAddressDto.getAddress());
			logisticAddressDto.setFullAddress(userAddressDto.getFullAddress());
			logisticTrace.setLogisticAddressDto(logisticAddressDto);
		}
		log.info("traceLogistics is :" +JSON.toJSONString(logisticTrace));
		return R.ok(logisticTrace);
	}

	private QueryTrackResp postForTraceDtoList(String logisticsNo, String logisticsName) {
		String code = kdCompanyMapper.getCodeByName(logisticsName);
		/* 封装param */
		LogisticTraceParam traceParam = new LogisticTraceParam();
		traceParam.setCom(code);
		traceParam.setNum(logisticsNo);
		String param = new Gson().toJson(traceParam);

		/* 封装 QueryTrackReq */
		QueryTrackReq queryTrackReq = new QueryTrackReq();
		queryTrackReq.setParam(param);
		queryTrackReq.setCustomer(customer);
		queryTrackReq.setSign(DigestUtils.md5Hex(param + key + customer).toUpperCase());

		try {
			log.info("快递100请求：" + JSON.toJSONString(queryTrackReq));
			HttpResult httpResult = HttpUtils.doPost(queryUrl, queryTrackReq);
			log.info("快递100返回：" + JSON.toJSONString(httpResult));
			if (httpResult.getStatus() == 200 && StringUtils.isNotBlank(httpResult.getBody())) {
				QueryTrackResp queryTrackResp = JSON.parseObject(httpResult.getBody(), QueryTrackResp.class);
				return queryTrackResp;
			}
		} catch (Exception e) {
			log.info(BusinessErrorMessage.KUSIDI100_SERVICE_ERROR);
			e.printStackTrace();
		}
		return new QueryTrackResp();
	}

	@Override
	public R traceLogisticsFee(TraceFeeVo vo) {
		Long userAddressId = vo.getUserAddressId();
		Long skuId = vo.getSkuId();
		Integer amount = vo.getAmount();
		if (userAddressId == null || skuId == null || amount == null) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		/* 数据校验 */
		CmUserAddress cmUserAddress = cmUserAddressMapper.selectById(userAddressId);
		if (cmUserAddress == null) {
			return R.failed(BusinessErrorMessage.NONE_USER_ADDRESS);
		}

		CmProductSku productSku = productSkuMapper.selectById(skuId);
		if (productSku == null) {
			return R.failed(BusinessErrorMessage.NONE_SKU);
		}

		CmProduct product = productMapper.selectById(productSku.getProductId());
		if (product == null) {
			return R.failed(BusinessErrorMessage.NONE_PRODUCT);
		}

		CmMerchantLogistics logistics = logisticsMapper.selectById(product.getLogisticId());
		if (logistics == null) {
			return R.failed(BusinessErrorMessage.NONE_LOGISTIC);
		}

		/* 配送地址校验 */
		Integer countryCode = cmUserAddress.getCountryCode();
		String region = logistics.getRegion();
		boolean match = Arrays.stream(region.split(StrUtil.COMMA)).toList().stream().map(e -> Integer.valueOf(e)).anyMatch(e -> e.intValue() == countryCode);
		if (!match) {
			return R.failed(BusinessErrorMessage.UNABLE_TO_DELIVER);
		}

		/* 计算物流费用 */
		BigDecimal deliverPrice = BigDecimal.ZERO;
		String ifShipped = product.getIfShipped();
		if (BusinessConstants.NO.equals(ifShipped)) {
			// “首重价格”、“续重价格”
			BigDecimal price = logistics.getPrice();
			BigDecimal secondPrice = logistics.getSecondPrice();
			int weight = logistics.getWeight();
			int secondWeight = logistics.getSecondWeight();
			int multi = 0;
			if (amount.intValue() > weight) {
				int overCharge = amount.intValue() - weight;
				multi = (overCharge % secondWeight == 0) ? (overCharge / secondWeight) : (overCharge / secondWeight + 1);
			}
			deliverPrice = price.add(secondPrice.multiply(new BigDecimal(multi)));
		}

		Map<String, String> map = new HashMap<>();
		map.put("deliverPrice", String.valueOf(deliverPrice));
		return R.ok(map);
	}
}
