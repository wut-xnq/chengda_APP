package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class ProductSearchVo extends PageDto {

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * 商品分类（名称）
	 */
	@Schema(description = "商品分类（名称）")
	private String productClassify;

	/**
	 * 上架状态;（0-已下架，1-已上架）
	 */
	@Schema(description = "上架状态（0-已下架，1-已上架）")
	private String shelveState;

	/**
	 * 商品上架区域;（1-国标，2-正品，3-优惠）
	 */
	@Schema(description = "商品上架区域（1-国标，2-正品，3-优惠）")
	private Integer shelveArea;

	/**
	 * 最低售价
	 */
	@Schema(description = "最低售价")
	private String minPrice;

	/**
	 * 最高售价
	 */
	@Schema(description = "最高售价")
	private String maxPrice;
}
