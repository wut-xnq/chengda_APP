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

package com.jiaolong.cm.admin.api.dto;

import com.jiaolong.cm.admin.api.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lengleng
 * @date 2017/11/11
 */
@Data
@Schema(description = "用户信息")
public class UserInfo implements Serializable {

	/**
	 * 用户基本信息
	 */
	@Schema(description = "用户基本信息")
	private SysUser sysUser;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限标识集合")
	private String[] permissions;

	/**
	 * 角色集合
	 */
	@Schema(description = "角色标识集合")
	private Long[] roles;

	/**
	 * 用户是否收到积分转换请求（0-否，1-是）
	 */
	@Schema(description = "用户是否收到积分转换请求（0-否，1-是）")
	private String ifGiftScore = null;

	/**
	 * 邀请人
	 */
	@Schema(description = "邀请人")
	private String inviter = null;

	/**
	 * 用户所属商户主键
	 */
	@Schema(description = "用户所属商户主键")
	private Long merchantId = null;

}
