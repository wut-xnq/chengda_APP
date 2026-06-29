package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户信息数据传输对象
 * user: pan
 * date: 2024-08-28 14:32
 */
@Data
@Schema(description = "用户信息数据传输对象")
public class UserInfoDto implements Serializable {

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
	private String name;

	/**
	 * 性别;（F-女，M-男）
	 */
	@Schema(description = "性别（1-男，2-女）")
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
	private String phone;

	/**
	 * 编号
	 */
	@Schema(description = "编号")
	private String userNumber;

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

	/**
	 * 部门名称
	 */
	@Schema(description = "用户所属部门名称")
	private String deptName;
}
