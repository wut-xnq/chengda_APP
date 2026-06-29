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
public class UserVerifiedAuditVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 认证信息的主键
	 */
	@NotNull
	@Schema(description = "认证信息的主键")
	private Long id;

	/**
	 * 用户的主键
	 */
	@NotNull
	@Schema(description = "用户的主键")
	private Long userId;

	/**
	 * 认证状态（1-认证不通过，2-认证通过）
	 */
	@NotNull
	@Schema(description = "认证状态（0-未认证，1-认证不通过，2-认证通过）")
	private Integer verifiedState;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;
}
