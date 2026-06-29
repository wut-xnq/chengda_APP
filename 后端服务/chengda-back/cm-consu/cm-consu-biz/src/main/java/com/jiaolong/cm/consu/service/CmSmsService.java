package com.jiaolong.cm.consu.service;

import com.jiaolong.cm.common.core.util.R;

/**
 * desc: 网易云信接口服务
 * user: pan
 * date: 2024-10-14 10:26
 */
public interface CmSmsService {
	R getMobileCode(String phone);

	R verifyMobileCode(String phone, String code);

	R refreshUserToken(String phone);

	R promotOrderDeliver(String orderNo, String phone);
}
