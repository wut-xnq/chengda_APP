package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 部门数据传输对象
 * user: pan
 * date: 2024-08-28 14:32
 */
@Data
@Schema(description = "部门数据传输对象")
public class DeptInfoDto implements Serializable {

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
	private String name;

	/**
	 * 父级部门id
	 */
	@Schema(description = "父级部门id")
	private Long parentId;

	/**
	 * 父级部门名称
	 */
	@Schema(description = "父级部门名称")
	private String parentName;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 所属企业/单位主键
	 */
	@Schema(description = "所属企业/单位主键")
	private String merchantId;

	/**
	 * 部门编号
	 */
	@Schema(description = "部门编号")
	private String deptNumber;
}
