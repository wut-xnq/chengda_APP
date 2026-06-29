package com.jiaolong.cm.admin.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 质检管理页面，关联的质检商品查询传参
 * user: pan
 * date: 2024-10-25 14:51
 */
@Data
public class ExamGoodSearchVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;

}
