package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品下拉框列表数据传输对象
 * user: pan
 * date: 2024-09-05 14:53
 */
@Data
@Schema(description = "商品下拉框列表数据传输对象")
public class ProductListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * 商品编号
	 */
	@Schema(description = "商品编号")
	private String productCode;

	/**
	 * 品牌名称
	 */
	@Schema(description = "品牌名称")
	private String brandName;

	/**
	 * 商品分类（名称）
	 */
	@Schema(description = "商品分类（名称）")
	private String productClassify;
}
