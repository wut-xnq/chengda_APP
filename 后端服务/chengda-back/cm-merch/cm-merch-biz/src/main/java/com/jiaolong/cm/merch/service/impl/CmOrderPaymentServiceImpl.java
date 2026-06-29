package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.PaymentOrderDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmOrderPayment;
import com.jiaolong.cm.merch.api.vo.PaymentOrderSearchVo;
import com.jiaolong.cm.merch.mapper.CmCustomerMapper;
import com.jiaolong.cm.merch.mapper.CmOrderPaymentMapper;
import com.jiaolong.cm.merch.service.CmOrderPaymentService;
import com.jiaolong.cm.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * desc:
 * user: pan
 * date: 2024-09-04 14:58
 */
@Service
public class CmOrderPaymentServiceImpl extends ServiceImpl<CmOrderPaymentMapper, CmOrderPayment> implements CmOrderPaymentService {

	@Autowired
	private CmOrderPaymentMapper orderPaymentMapper;
	@Autowired
	private CmCustomerMapper customerMapper;

	@Override
	public R<IPage<PaymentOrderDto>> getPage(Page page, PaymentOrderSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if(cmUser == null){
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = customerMapper.selectById(cmUserId);
		if(customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()){
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<PaymentOrderDto> dtoPage = orderPaymentMapper.getPageList(page, vo);
		return R.ok(dtoPage);
	}

	@Override
	public R<PaymentOrderDto> getDetail(Long id) {
		PaymentOrderDto dto = orderPaymentMapper.getDetailById(id);
		return R.ok(dto);
	}

	@Override
	public R deletePaymentOrder(Long[] ids) {
		int result = orderPaymentMapper.deletePaymentOrderById(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}
}
