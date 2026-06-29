package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户传参
 * user: pan
 * date: 2024-08-28 18:52
 */
@Data
public class UserInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Schema(description = "主键id")
	private Long userId;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名")
	@NotBlank(message = "姓名不能为空")
	private String name;

	/**
	 * 性别;（F-女，M-男）
	 */
	@Schema(description = "性别（F-女，M-男）")
	private String gender;

	/**
	 * 年龄
	 */
	@Schema(description = "年龄")
	private Integer age;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	@NotBlank(message = "手机号不能为空")
	private String phone;

	/**
	 * 编号
	 */
	@Schema(description = "编号")
	private String userNumber;

	/**
	 * 是否发名片（0-否，1-是）
	 */
	@Schema(description = "是否发名片（0-否，1-是）")
	@NotBlank(message = "必填字段不能为空")
	private String verified;

	/**
	 * 部门ID
	 */
	@Schema(description = "用户所属部门id")
	private Long deptId;
}
