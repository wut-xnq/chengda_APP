package com.jiaolong.cm.consu.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.core.util.RandomStringUtil;
import com.jiaolong.cm.consu.api.dto.OrderProductDto;
import com.jiaolong.cm.consu.api.entity.*;
import com.jiaolong.cm.consu.api.util.AlipayConfig;
import com.jiaolong.cm.consu.mapper.*;
import com.jiaolong.cm.consu.service.AlipayService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc: 支付宝支付服务实现
 * user: pan
 * date: 2024-11-01 12:37
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
	@Resource
	private AlipayConfig alipayConfig;
	@Resource
	private AlipayClient alipayClient;
	@Resource
	private AlipayTradeAppPayRequest appPayRequest;
	@Resource
	private AlipayTradeQueryRequest queryRequest;

	private static String QUICK_MSECURITY_PAY = "QUICK_MSECURITY_PAY";


	@Autowired
	private CmOrderPaymentMapper orderPaymentMapper;
	@Autowired
	private CmOrderShoppingMapper orderShoppingMapper;
	@Autowired
	private CmCustomerMapper customerMapper;
	@Autowired
	private CmOrderProductsMapper orderProductsMapper;
	@Autowired
	private CmProductStatisticsMapper statisticsMapper;
	@Autowired
	private CmProductSkuMapper skuMapper;


	@Override
	public R payForOrder(String orderNo) {
		/* 传参校验 */
		if (StringUtils.isBlank(orderNo)) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}
		CmOrderShopping shoppingOrder = orderShoppingMapper.getOrderByNo(orderNo);
		if (shoppingOrder == null) {
			return R.failed(BusinessErrorMessage.NONE_SHOPPING_ORDER);
		}

		/* 封装支付参数 */
		BigDecimal realPrice = shoppingOrder.getRealPrice();
		Long userId = shoppingOrder.getUserId();
		Long merchantId = shoppingOrder.getMerchantId();

		String paymentNo = uniqueOrderNo();
		String totalAmount = String.valueOf(realPrice);
		String subject = String.format("用户%s向商家%s支付金额%s元", userId, merchantId, totalAmount);

		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setOutTradeNo(paymentNo);
		model.setTotalAmount(totalAmount);
		model.setSubject(subject);
		model.setProductCode(QUICK_MSECURITY_PAY);

		// 设置订单绝对超时时间（15分钟后超时）
		String expireTime = LocalDateTimeUtil.format(LocalDateTimeUtil.offset(LocalDateTime.now(), 15L, ChronoUnit.MINUTES), DatePattern.NORM_DATETIME_PATTERN);
		model.setTimeExpire(expireTime);

		appPayRequest.setBizModel(model);
		// 第三方代调用模式下请设置app_auth_token
		// request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

		try {
			/* 发起支付 */
			log.info("支付宝请求：" + JSON.toJSONString(model));
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(appPayRequest);
			log.info("支付宝返回：" + JSON.toJSONString(response));
			String orderStr = response.getBody();

			if (response.isSuccess()) {
				log.info("调用支付宝支付接口成功。");
				/* 生成支付订单 */
				CmOrderPayment order = new CmOrderPayment();
				order.setCreateTime(LocalDateTime.now());
				order.setPaymentNo(paymentNo);
				order.setShoppingOrderNo(orderNo);
				order.setAmount(realPrice);
				order.setBuyerId(shoppingOrder.getUserId());
				order.setMerchantId(shoppingOrder.getMerchantId());
				order.setTradeState(BusinessConstants.ORDER_PREPAYMENT);
				order.setDeleted(BusinessConstants.NO);
				int result = orderPaymentMapper.insert(order);
				if (result <= 0) {
					log.error("新增支付订单失败：" + order);
				}

				Map<String, Object> map = new HashMap<>();
				map.put("appId", alipayConfig.getAppId());
				map.put("tradeNo", paymentNo);
				map.put("responseBody", orderStr);
				return R.ok(map);
			} else {
				log.error("调用支付宝支付失败！");
				return R.failed();
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return R.failed();
		}
	}

	@Override
	public R getPayResult(String orderNo) {
		/* 传参校验 */
		if (StringUtils.isBlank(orderNo)) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}
		CmOrderPayment paymentOrder = orderPaymentMapper.getOrderByNo(orderNo);
		if (paymentOrder == null) {
			return R.failed(BusinessErrorMessage.NONE_PAYMENT_ORDER);
		}

		/* 查询支付结果 */
		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo(orderNo);
		queryRequest.setBizModel(model);
		try {
			AlipayTradeQueryResponse response = alipayClient.execute(queryRequest);
			System.out.println(response.getBody());
			if (response.isSuccess()) {
				// 更新本地的支付订单
				paymentOrder.setTradeFlowNo(response.getTradeNo());
				paymentOrder.setPaymentType(BusinessConstants.PAYMENT_ALIPAY);
				paymentOrder.setTradeState(BusinessConstants.ORDER_PAYMENT_SUCCESS);
				int result = orderPaymentMapper.updateById(paymentOrder);
				if (result <= 0) {
					log.error("更新支付订单失败：" + paymentOrder);
				}

				// 更新本地的购物订单
				String shoppingOrderNo = paymentOrder.getShoppingOrderNo();
				CmOrderShopping shoppingOrder = orderShoppingMapper.getOrderByNo(shoppingOrderNo);
				shoppingOrder.setPaymentState(BusinessConstants.ORDER_PAYMENT_SUCCESS);
				shoppingOrder.setPaymentType(BusinessConstants.PAYMENT_ALIPAY);
				shoppingOrder.setUpdateTime(LocalDateTime.now());
				result = orderShoppingMapper.updateById(shoppingOrder);
				if (result <= 0) {
					log.error("更新购物订单失败：" + shoppingOrder);
				}

				// 更新用户积分（根据消费金额，四舍五入取整数）
				Long userId = shoppingOrder.getUserId();
				CmCustomer customer = customerMapper.selectById(userId);
				int score = customer.getUserScore();
				int addScore = shoppingOrder.getRealPrice().divide(BigDecimal.ONE, 0, RoundingMode.HALF_UP).intValue();
				int userScore = score + addScore;
				result = customerMapper.updateUserScore(userId, userScore);
				if (result <= 0) {
					log.error("更新用户积分失败：用户主键%s，原始积分%s，新增积分%s，更新后的积分%s", userId, score, addScore, userScore);
				}

				// 订单中的商品，增加购买量（ShoppingOrder -> OrderProduct.amount）
				Long shoppingOrderId = shoppingOrder.getId();
				List<OrderProductDto> orderProductList = orderProductsMapper.getOrderProductListByOrderId(shoppingOrderId);
				for (OrderProductDto dto : orderProductList) {
					Long skuId = dto.getSkuId();
					CmProductSku productSku = skuMapper.selectById(skuId);

					Long productId = productSku.getProductId();
					int amount = dto.getAmount();
					CmProductStatisticsTrend statisticsTrend = statisticsMapper.getByProductId(productId);
					if (statisticsTrend == null) {
						log.error("商品热度统计数据异常！");
						statisticsTrend = new CmProductStatisticsTrend();
						statisticsTrend.setProductId(productId);
						statisticsTrend.setViewAmount(1);
						statisticsTrend.setOrderAmount(amount);
						statisticsMapper.insert(statisticsTrend);
					} else {
						int orderAmount = statisticsTrend.getOrderAmount();
						statisticsTrend.setOrderAmount(orderAmount + amount);
						statisticsMapper.updateOrderAmount(statisticsTrend);
					}
				}
				return R.ok();
			} else {
				log.error("调用支付宝支付失败！");
				return R.failed();
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return R.failed();
		}
	}

	/**
	 * 生成支付订单编码
	 *
	 * @return
	 */
	private String uniqueOrderNo() {
		String orderNo = BusinessConstants.CHARGE_ORDER_PRE + RandomStringUtil.random18();
		int count = orderPaymentMapper.countOrderNo(orderNo);
		if (count == 0) {
			return orderNo;
		} else {
			return uniqueOrderNo();
		}
	}

}
