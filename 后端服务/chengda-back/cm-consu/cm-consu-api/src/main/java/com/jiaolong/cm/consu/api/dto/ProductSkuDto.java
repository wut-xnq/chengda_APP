package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 商品SKU数据传输对象
 * user: pan
 * date: 2024-08-21 17:27
 */
@Data
@Schema(description = "质检数据传输对象")
public class ProductSkuDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * SKU名称
	 */
	@Schema(description = "SKU名称")
	private String skuName;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 原价
	 */
	@Schema(description = "原价")
	private String originPrice;

	/**
	 * 现价
	 */
	@Schema(description = "现价")
	private String currentPrice;

	/**
	 * 是否默认;（0-否，1-是）
	 */
	@Schema(description = "是否默认（0-否，1-是）")
	private String ifDefault;

	/**
	 * SKU配图
	 */
	@Schema(description = "SKU配图")
	private String skuPics;

	/**
	 * 此SKU对应的商品总量
	 */
	@Schema(description = "此SKU对应的商品总量")
	private Integer amount;

	/**
	 * 限购数量
	 */
	@Schema(description = "限购数量")
	private Integer limitAmount;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 排序值
	 */
	@Schema(description = "排序值")
	private Integer sortOrder;
}
