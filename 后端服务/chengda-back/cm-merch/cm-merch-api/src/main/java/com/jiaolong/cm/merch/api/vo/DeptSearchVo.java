package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 部门传参
 * user: pan
 * date: 2024-08-28 18:52
 */
@Data
public class DeptSearchVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属企业/单位主键
	 */
	@Schema(description = "所属企业/单位主键")
	private String merchantId;

	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
	private String name;
}
