/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the cm4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.jiaolong.cm.admin.api.feign;

import com.jiaolong.cm.admin.api.dto.UserInfo;
import com.jiaolong.cm.common.core.constant.SecurityConstants;
import com.jiaolong.cm.common.core.constant.ServiceNameConstants;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.feign.annotation.NoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * desc: 客户远程查询接口
 * user: pan
 * date: 2024-08-20 12:44
 */
@FeignClient(contextId = "remoteCustomerService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteCustomerService {

	/**
	 * (未登录状态调用，需要加 @NoToken) 通过用户名查询用户、角色信息
	 * @param phone 客户手机号码
	 * @return R
	 */
	@GetMapping("/customer/info/{username}")
	R<UserInfo> info(@PathVariable("username") String phone, @RequestParam(value = "inviteCode", required = false) String inviteCode, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * @param username 客户名称
	 * @return R
	 */
	@GetMapping("/customer/customerInfo/{username}")
	R<UserInfo> customerInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM) String from);

}
