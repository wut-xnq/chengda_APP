package com.jiaolong.cm.consu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.core.util.RandomStringUtil;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.consu.api.dto.*;
import com.jiaolong.cm.consu.api.entity.*;
import com.jiaolong.cm.consu.api.param.ShoppingOrderParam;
import com.jiaolong.cm.consu.api.param.ShoppingOrderVo;
import com.jiaolong.cm.consu.convert.CmOrderProductConvert;
import com.jiaolong.cm.consu.convert.CmOrderShoppingConvert;
import com.jiaolong.cm.consu.mapper.*;
import com.jiaolong.cm.consu.service.CmOrderShoppingService;
import com.jiaolong.cm.consu.service.CmSmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * desc: 购物订单服务实现类
 * user: pan
 * date: 2024-08-22 10:21
 */
@Slf4j
@Service
public class CmOrderShoppingServiceImpl extends ServiceImpl<CmOrderShoppingMapper, CmOrderShopping> implements CmOrderShoppingService {

	@Autowired
	private CmOrderShoppingMapper orderMapper;
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;
	@Autowired
	private CmUserAddressMapper userAddressMapper;
	@Autowired
	private CmOrderLogisticsMapper logisticsMapper;
	@Autowired
	private CmOrderProductsMapper orderProductsMapper;
	@Autowired
	private CmProductSkuMapper productSkuMapper;
	@Autowired
	private CmProductMapper productMapper;

	@Autowired
	private CmSmsService cmSmsService;

	@Override
	public R<IPage<ShoppingOrderDto>> getUserOrderPage(Page page, ShoppingOrderParam param) {
		CmUser user = SecurityUtils.getUser();
		if (user == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long userId = param.getUserId();
		if (userId == null) {
			userId = user.getId();
			param.setUserId(userId);
		}

		/* 查询分页结果 */
		IPage<ShoppingOrderDto> pageResult = orderMapper.selectOrderPage(page, param);

		/* 可能喜欢的商品列表（暂时推送商品浏览量前10的商品） */
		List<RelationProductDto> relationProductDtoList = productMapper.getRelationProductDtoList();
		for (RelationProductDto dto : relationProductDtoList){
			fillingProductSkuList(dto);
		}

		/* 组装返回结果 */
		List<ShoppingOrderDto> records = pageResult.getRecords();
		for (ShoppingOrderDto orderDto : records) {
			// 添加订单商品列表
			List<OrderProductDto> productDtoList = orderProductsMapper.getOrderProductListByOrderId(orderDto.getId());
			if (!CollectionUtils.isEmpty(productDtoList)) {
				orderDto.setProductDtoList(productDtoList);
			}

			// 填充关联商品列表
			orderDto.setRelationProductDtoList(relationProductDtoList);
		}
		return R.ok(pageResult);
	}

	@Override
	public R<ShoppingOrderDto> getDetails(Long id) {
		CmOrderShopping order = orderMapper.selectById(id);
		// 校验订单
		if (order == null) {
			return R.failed(BusinessErrorMessage.NONE_ORDER);
		}
		ShoppingOrderDto orderDto = CmOrderShoppingConvert.INSTANCE.covertToDto(order);

		// 添加订单商品列表
		List<OrderProductDto> productDtoList = orderProductsMapper.getOrderProductListByOrderId(id);
		if (!CollectionUtils.isEmpty(productDtoList)) {
			orderDto.setProductDtoList(productDtoList);
		}

		// 填充商户名称、LOGO
		Long merchantId = orderDto.getMerchantId();
		if (merchantId != null) {
			CmMerchantInfo merchant = merchantInfoMapper.selectById(merchantId);
			orderDto.setMerchantName(merchant.getMerchantName());
			orderDto.setLogo(merchant.getLogo());
		}

		// 填充用户收件地址
		Long userAddressId = orderDto.getUserAddressId();
		if (userAddressId != null) {
			UserAddressDto userAddressDto = userAddressMapper.getUserAddressById(userAddressId);
			orderDto.setConcatPerson(userAddressDto.getConcatPerson());
			orderDto.setContactPhone(userAddressDto.getContactPhone());
			orderDto.setAddress(userAddressDto.getAddress());
			orderDto.setFullAddress(userAddressDto.getFullAddress());
		}

		// 填充物流信息
		LambdaQueryWrapper<CmOrderLogistics> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CmOrderLogistics::getShoppingOrderNo, order.getOrderNo());
		CmOrderLogistics logistics = logisticsMapper.selectOne(wrapper);
		if (logistics != null) {
			orderDto.setLogisticsName(logistics.getLogisticsName());
			orderDto.setLogisticsNo(logistics.getLogisticsNo());
		}

		/* 可能喜欢的商品列表（暂时推送商品浏览量前10的商品） */
		List<RelationProductDto> relationProductDtoList = productMapper.getRelationProductDtoList();
		for (RelationProductDto dto : relationProductDtoList){
			fillingProductSkuList(dto);
		}
		orderDto.setRelationProductDtoList(relationProductDtoList);

		return R.ok(orderDto);
	}

	@Override
	public R receiveOrder(Long id) {
		CmOrderShopping cmOrderShopping = orderMapper.selectById(id);
		if (cmOrderShopping == null) {
			return R.failed(BusinessErrorMessage.NONE_ORDER);
		}

		Integer state = cmOrderShopping.getState();
		Integer paymentState = cmOrderShopping.getPaymentState();
		if (state == null || paymentState == null) {
			return R.failed(BusinessErrorMessage.NONE_ORDER_STATE);
		}
		if (BusinessConstants.ORDER_PAYMENT_SUCCESS != paymentState.intValue() || BusinessConstants.ORDER_DELIVERED != state) {
			return R.failed(BusinessErrorMessage.ORDER_STATE_UNNORMAL);
		}

		cmOrderShopping.setState(BusinessConstants.ORDER_RECEIVED);
		cmOrderShopping.setPaymentState(BusinessConstants.ORDER_PAYMENT_SUCCESS);
		int result = orderMapper.updateById(cmOrderShopping);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R deleteUserOrder(Long[] ids) {
		int result = orderMapper.delOrderByIds(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	@Transactional
	public R<ShoppingOrderDto> addShoppingOrder(ShoppingOrderVo vo) {
		log.info("addShoppingOrder 1: " + JSON.toJSONString(vo));
		Long skuId = vo.getSkuId();
		Integer amount = vo.getAmount();
		if (amount == null || skuId == null) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		Long userId = vo.getUserId();
		if (userId == null) {
			userId = SecurityUtils.getUser().getId();
		}

		/* 商品 SKU */
		CmProductSku productSku = productSkuMapper.selectById(skuId);
		String checkResult = checkProductSku(productSku, amount.intValue());
		log.info("addShoppingOrder 2: " + checkResult);
		if (!BusinessConstants.OK.equals(checkResult)) {
			return R.failed(checkResult);
		}
		int total = productSku.getAmount();
		productSku.setAmount(total - amount.intValue());
		int updateSkuResult = productSkuMapper.updateById(productSku);
		log.info("addShoppingOrder 3: " + updateSkuResult);
		if (updateSkuResult <= 0) {
			return R.failed();
		}

		/* 商品信息 */
		Long productId = productSku.getProductId();
		CmProduct product = productMapper.selectById(productId);
		log.info("addShoppingOrder 4: " + JSON.toJSONString(product));
		if (product == null) {
			return R.failed(BusinessErrorMessage.NONE_PRODUCT);
		}
		// 暂定“订单商品主键集” 只有一个 ——pan 2024.09
		String orderProducts = String.valueOf(productId);

		/* 优惠福利 */

		/* 商品和订单费用 */
		BigDecimal deliverPrice = vo.getDeliverPrice();
		BigDecimal singlePrice = productSku.getCurrentPrice();
		BigDecimal productPrice = singlePrice.multiply(new BigDecimal(amount));
		BigDecimal totalPrice = productPrice.add(deliverPrice);
		// 暂定“实付金额”为“订单总金额” ——pan 2024.09
		BigDecimal realPrice = totalPrice;

		Long merchantId = product.getMerchantId();
		Long userAddressId = vo.getUserAddressId();
		UserAddressDto userAddressDto = userAddressMapper.getUserAddressById(userAddressId);
		/* 新增购物订单 */
		CmOrderShopping shoppingOrder = new CmOrderShopping();
		shoppingOrder.setCreateTime(LocalDateTime.now());
		shoppingOrder.setOrderNo(uniqueOrderNo());
		shoppingOrder.setUserId(userId);
		shoppingOrder.setMerchantId(merchantId);
		shoppingOrder.setUserAddressId(userAddressId);
		shoppingOrder.setOrderProducts(orderProducts);
		shoppingOrder.setTotalPrice(totalPrice);
		shoppingOrder.setDeliverPrice(deliverPrice);
		shoppingOrder.setRealPrice(realPrice);
		shoppingOrder.setState(BusinessConstants.ORDER_PREDELIVER);
		shoppingOrder.setPaymentState(BusinessConstants.ORDER_PREPAYMENT);
		shoppingOrder.setDeleted(BusinessConstants.DELETED_NO);
		shoppingOrder.setRemarks(vo.getRemarks());
		int insertOrderResult = orderMapper.insert(shoppingOrder);
		log.info("addShoppingOrder 5: " + insertOrderResult);
		if (insertOrderResult <= 0) {
			return R.failed();
		}
		/* 新增订单关联商品 */
		CmOrderProducts cmOrderProducts = new CmOrderProducts();
		cmOrderProducts.setCreateTime(LocalDateTime.now());
		cmOrderProducts.setOrderId(shoppingOrder.getId());
		cmOrderProducts.setSkuId(skuId);
		cmOrderProducts.setAmount(amount);
		cmOrderProducts.setProductName(product.getProductName());
		cmOrderProducts.setSpecName(vo.getSpecName());
		cmOrderProducts.setDeleted(BusinessConstants.DELETED_NO);
		int insertOrderProductResult = orderProductsMapper.insert(cmOrderProducts);
		log.info("addShoppingOrder 6: " + insertOrderProductResult);
		if (insertOrderProductResult <= 0) {
			return R.failed();
		}

		/* 返回结果 */
		CmMerchantInfo merchant = merchantInfoMapper.selectById(merchantId);
		ShoppingOrderDto orderDto = CmOrderShoppingConvert.INSTANCE.covertToDto(shoppingOrder);
		orderDto.setTotalPrice(String.valueOf(shoppingOrder.getTotalPrice()));
		orderDto.setDeliverPrice(String.valueOf(shoppingOrder.getDeliverPrice()));
		orderDto.setRealPrice(String.valueOf(shoppingOrder.getRealPrice()));
		orderDto.setMerchantName(merchant.getMerchantName());
		if (userAddressDto != null) {
			orderDto.setConcatPerson(userAddressDto.getConcatPerson());
			orderDto.setContactPhone(userAddressDto.getContactPhone());
			orderDto.setFullAddress(userAddressDto.getFullAddress());
		}
		// 填充订单商品列表
		List<OrderProductDto> productDtoList = new ArrayList<>();
		OrderProductDto orderProductDto = CmOrderProductConvert.INSTANCE.covertToDto(cmOrderProducts);
		orderProductDto.setSkuName(productSku.getSkuName());
		orderProductDto.setOriginPrice(String.valueOf(productSku.getOriginPrice()));
		orderProductDto.setCurrentPrice(String.valueOf(productSku.getCurrentPrice()));
		orderProductDto.setSkuPics(productSku.getSkuPics());
		productDtoList.add(orderProductDto);
		orderDto.setProductDtoList(productDtoList);
		log.info("addShoppingOrder 7: " + JSON.toJSONString(orderDto));
		return R.ok(orderDto);
	}

	@Override
	public R<ShoppingOrderDto> updateRecipientAddress(Long id, Long userAddressId) {
		CmOrderShopping cmOrderShopping = orderMapper.selectById(id);
		if (cmOrderShopping == null) {
			return R.failed(BusinessErrorMessage.NONE_ORDER);
		}
		CmUserAddress cmUserAddress = userAddressMapper.selectById(userAddressId);
		if (cmUserAddress == null) {
			return R.failed(BusinessErrorMessage.NONE_USER_ADDRESS);
		}

		/* 先更新订单收件地址 */
		cmOrderShopping.setUserAddressId(userAddressId);
		int updateResult = orderMapper.updateById(cmOrderShopping);
		if (updateResult <= 0) {
			return R.failed();
		}
		/* 回显最新结果 */
		return getDetails(id);
	}

	@Override
	public R promotOrderDeliver(Long orderId) {
		CmOrderShopping cmOrderShopping = orderMapper.selectById(orderId);
		if (cmOrderShopping == null) {
			return R.failed(BusinessErrorMessage.NONE_ORDER);
		}

		Long merchantId = cmOrderShopping.getMerchantId();
		CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(merchantId);
		String orderNo = cmOrderShopping.getOrderNo();
		String chargePersonPhone = merchantInfo.getChargePersonPhone();

		return cmSmsService.promotOrderDeliver(orderNo, chargePersonPhone);
	}

	/**
	 * 校验商品SKU
	 */
	private String checkProductSku(CmProductSku productSku, int amount) {
		if (productSku == null) {
			return BusinessErrorMessage.NONE_SKU;
		}
		int limitAmount = productSku.getLimitAmount();
		int total = productSku.getAmount();
		if (amount > limitAmount) {
			return BusinessErrorMessage.EXCEED_SKU_LIMIT_AMOUNT;
		}
		if (amount > total) {
			return BusinessErrorMessage.PRODUCT_OUT_OF_STOCK;
		}
		return BusinessConstants.OK;
	}

	/**
	 * 生成购物订单编码
	 *
	 * @return
	 */
	private String uniqueOrderNo() {
		String orderNo = BusinessConstants.SHOPING_ORDER_PRE + RandomStringUtil.random18();
		int count = orderMapper.countOrderNo(orderNo);
		if (count == 0) {
			return orderNo;
		} else {
			return uniqueOrderNo();
		}
	}

	/**
	 * 填充商品 SKU 列表
	 *
	 * @param dto
	 */
	private void fillingProductSkuList(RelationProductDto dto) {
		Long id = dto.getId();
		LambdaQueryWrapper<CmProductSku> wrapper = new LambdaQueryWrapper();
		wrapper.eq(CmProductSku::getProductId, id)
				.eq(CmProductSku::getDeleted, "0")
				.eq(CmProductSku::getState, "1");
		List<CmProductSku> skuList = productSkuMapper.selectList(wrapper);
		if (!CollectionUtils.isEmpty(skuList)) {
			// 填充商品价格
			List<CmProductSku> resultList = skuList.stream().sorted(Comparator.comparing(CmProductSku::getCurrentPrice)).collect(Collectors.toList());
			CmProductSku productSku = resultList.get(0);
			dto.setShowedSkuId(productSku.getId());
			dto.setCurrentPrice(String.valueOf(productSku.getCurrentPrice()));
			dto.setOriginPrice(String.valueOf(productSku.getOriginPrice()));
		}
	}
}
