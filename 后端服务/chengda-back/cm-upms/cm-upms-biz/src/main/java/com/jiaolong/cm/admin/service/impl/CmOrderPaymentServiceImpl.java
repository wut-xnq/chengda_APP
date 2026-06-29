package com.jiaolong.cm.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.mapper.CmOrderPaymentMapper;
import com.jiaolong.cm.admin.service.CmOrderPaymentService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.entity.CmOrderPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * desc: 支付订单服务实现
 * user: pan
 * date: 2024-09-20 16:25
 */
@Service
public class CmOrderPaymentServiceImpl extends ServiceImpl<CmOrderPaymentMapper, CmOrderPayment> implements CmOrderPaymentService {

	@Autowired
	private CmOrderPaymentMapper orderPaymentMapper;

	@Override
	public R deletePaymentOrder(Long[] ids) {
		int result = orderPaymentMapper.deleteOrderById(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}
}
