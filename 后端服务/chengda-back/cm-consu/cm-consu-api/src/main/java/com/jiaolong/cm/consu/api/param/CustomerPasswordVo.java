package com.jiaolong.cm.consu.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户信息数据传参
 * user: pan
 * date: 2024-10-29 11:44
 */
@Data
public class CustomerPasswordVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户密码
	 */
	@Schema(description = "用户密码")
	private String password;

	/**
	 * 确认密码
	 */
	@Schema(description = "确认密码")
	private String confirmPassword;
}
