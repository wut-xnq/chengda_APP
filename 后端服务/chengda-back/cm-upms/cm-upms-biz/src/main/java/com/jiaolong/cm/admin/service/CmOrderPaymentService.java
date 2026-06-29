package com.jiaolong.cm.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.entity.CmOrderPayment;

/**
 * desc: 支付订单服务接口
 * user: pan
 * date: 2024-09-20 16:22
 */
public interface CmOrderPaymentService extends IService<CmOrderPayment> {
	R deletePaymentOrder(Long[] ids);
}
