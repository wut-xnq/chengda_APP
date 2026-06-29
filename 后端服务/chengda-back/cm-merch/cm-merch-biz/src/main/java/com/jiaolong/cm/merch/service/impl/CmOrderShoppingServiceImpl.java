package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.OrderProductDto;
import com.jiaolong.cm.merch.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.merch.api.entity.*;
import com.jiaolong.cm.merch.api.vo.OrderDeliverVo;
import com.jiaolong.cm.merch.api.vo.ShoppingOrderSearchVo;
import com.jiaolong.cm.merch.convert.CmOrderLogisticsConvert;
import com.jiaolong.cm.merch.mapper.*;
import com.jiaolong.cm.merch.service.CmOrderShoppingService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 购物订单服务实现
 * user: pan
 * date: 2024-09-03 15:08
 */
@Service
public class CmOrderShoppingServiceImpl extends ServiceImpl<CmOrderShoppingMapper, CmOrderShopping> implements CmOrderShoppingService {

	@Autowired
	private CmOrderShoppingMapper orderShoppingMapper;
	@Autowired
	private CmOrderLogisticsMapper orderLogisticsMapper;
	@Autowired
	private CmCustomerMapper customerMapper;
	@Autowired
	private CmOrderPaymentMapper orderPaymentMapper;
	@Autowired
	private CmOrderProductsMapper orderProductsMapper;

	@Override
	public R<IPage<ShoppingOrderDto>> getOrderPage(Page page, ShoppingOrderSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = customerMapper.selectById(cmUserId);
		if (customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()) {
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<ShoppingOrderDto> dtoPage = orderShoppingMapper.getPageList(page, vo);
		List<ShoppingOrderDto> records = dtoPage.getRecords();
		for (ShoppingOrderDto dto : records) {
			// 填充支付订单流水
			fillPaymentNo(dto);
			// 填充订单商品
			fillOrderProductList(dto);
		}
		return R.ok(dtoPage);
	}

	@Override
	public R<ShoppingOrderDto> getOrderDetail(Long id) {
		ShoppingOrderDto dto = orderShoppingMapper.getDetailById(id);
		// 填充支付订单流水
		fillPaymentNo(dto);
		// 填充订单商品
		fillOrderProductList(dto);
		return R.ok(dto);
	}

	@Override
	public R<List<CmOrderProducts>> getProductList(Long id) {
		List<CmOrderProducts> list = orderShoppingMapper.getProductListById(id);
		return R.ok(list);
	}

	@Override
	@Transactional
	public R deliverOrder(OrderDeliverVo vo) {
		Long orderId = vo.getOrderId();
		CmOrderShopping cmOrderShopping = orderShoppingMapper.selectById(orderId);
		if (cmOrderShopping == null) {
			return R.failed(BusinessErrorMessage.MISSING_USERDATA);
		}

		// 添加订单物流记录
		CmOrderLogistics orderLogistics = CmOrderLogisticsConvert.INSTANCE.create(vo);
		orderLogistics.setCreateTime(LocalDateTime.now());
		orderLogistics.setDeleted(BusinessConstants.DELETED_NO);
		int result = orderLogisticsMapper.insert(orderLogistics);
		if (result <= 0) {
			return R.failed(BusinessErrorMessage.ORDER_LOGISTIC_ADD_ERROR);
		}

		// 更新订单状态（付款状态一定为“支付成功”）
		cmOrderShopping.setState(BusinessConstants.ORDER_DELIVERED);
		cmOrderShopping.setPaymentState(BusinessConstants.ORDER_PAYMENT_SUCCESS);
		result = orderShoppingMapper.updateById(cmOrderShopping);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok();
	}

	@Override
	public R deleteOrder(Long[] ids) {
		int result = orderShoppingMapper.deleteOrderById(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	/**
	 * 填充支付订单流水
	 * @param dto
	 */
	private void fillPaymentNo(ShoppingOrderDto dto) {
		String orderNo = dto.getOrderNo();
		CmOrderPayment payment = orderPaymentMapper.getOrderPaymentRecord(orderNo);
		if (payment != null) {
			dto.setPaymentNo(payment.getPaymentNo());
		}
	}

	/**
	 * 填充订单商品列表
	 * @param dto
	 */
	private void fillOrderProductList(ShoppingOrderDto dto) {
		Long orderId = dto.getId();
		List<OrderProductDto> orderProductList = orderProductsMapper.selectOrderProductList(orderId);
		dto.setOrderProductList(orderProductList);
	}
}
