package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 员工认证传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class UserVerifiedSearchVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属商户主键
	 */
	@NotNull
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 认证状态;（0-未认证，1-认证不通过，2-认证通过）
	 */
	@Schema(description = "认证状态（0-未认证，1-认证不通过，2-认证通过）")
	private Integer verifiedState;
}
