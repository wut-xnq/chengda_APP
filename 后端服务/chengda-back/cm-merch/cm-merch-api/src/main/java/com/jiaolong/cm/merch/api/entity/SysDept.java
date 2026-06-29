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
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.jiaolong.cm.merch.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * desc: 部门
 * user: pan
 * date: 2024-08-20 12:44
 */
@Data
@Schema(description = "部门")
@EqualsAndHashCode(callSuper = true)
public class SysDept extends Model<SysDept> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "dept_id", type = IdType.ASSIGN_ID)
	@Schema(description = "部门id")
	private Long deptId;

	/**
	 * 父级部门id
	 */
	@Schema(description = "父级部门id")
	private Long parentId;

	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
	@NotBlank(message = "部门名称不能为空")
	private String name;

	/**
	 * 排序
	 */
	@Schema(description = "排序值")
	private Integer sortOrder;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createBy;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updateBy;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 删除标记（0-正常，1-已删除）
	 */
	@TableLogic
	@Schema(description = "删除标记（0-正常，1-已删除）")
	@TableField(fill = FieldFill.INSERT)
	private String delFlag;

	/**
	 * 所属企业/单位主键
	 */
	@Schema(description = "所属企业/单位主键")
	private Long merchantId;

	/**
	 * 部门编号
	 */
	@Schema(description = "部门编号")
	@NotBlank(message = "部门编号不能为空")
	private String deptNumber;
}
