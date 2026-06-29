package com.jiaolong.cm.consu.service;

import com.jiaolong.cm.common.core.util.R;

/**
 * desc: 支付宝支付服务
 * user: pan
 * date: 2024-11-01 12:37
 */
public interface AlipayService {
	R payForOrder(String orderNo);

	R getPayResult(String orderNo);
}
