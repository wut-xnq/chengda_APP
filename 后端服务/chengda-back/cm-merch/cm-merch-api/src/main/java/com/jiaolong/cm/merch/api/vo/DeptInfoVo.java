package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 部门传参
 * user: pan
 * date: 2024-08-28 18:52
 */
@Data
public class DeptInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门id
	 */
	@Schema(description = "部门id")
	private Long deptId;

	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
	@NotBlank(message = "部门名称不能为空")
	private String name;

	/**
	 * 部门编号
	 */
	@Schema(description = "部门编号")
	@NotBlank(message = "部门编号不能为空")
	private String deptNumber;

	/**
	 * 所属企业/单位主键
	 */
	@Schema(description = "所属企业/单位主键")
	private Long merchantId;
}
