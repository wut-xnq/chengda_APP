package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户传参
 * user: pan
 * date: 2024-08-28 18:52
 */
@Data
public class UserSearchVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属商户单位主键
	 */
	@Schema(description = "所属商户单位主键")
	private Long merchantId;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名")
	private String name;

	/**
	 * 是否认证（0-未认证，1-认证不通过，2-认证通过）
	 */
	@Schema(description = "是否认证（0-未认证，1-认证不通过，2-认证通过）")
	private String verified;

	/**
	 * 部门ID
	 */
	@Schema(description = "用户所属部门id")
	private Long deptId;
}
