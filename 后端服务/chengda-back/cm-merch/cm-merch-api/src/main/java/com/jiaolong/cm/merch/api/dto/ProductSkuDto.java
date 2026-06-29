package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * desc: 商品SKU数据传输对象
 * user: pan
 * date: 2024-09-01 16:10
 */
@Data
@Schema(description = "商品SKU数据传输对象")
public class ProductSkuDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 编号
	 */
	@Schema(description = "编号")
	private String skuCode;

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
	private BigDecimal originPrice;

	/**
	 * 现价
	 */
	@Schema(description = "现价")
	private BigDecimal currentPrice;

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
	 * 状态;（0-禁用，1-启用）
	 */
	@Schema(description = "状态（0-禁用，1-启用）")
	private String state;

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

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;

	/**
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;
}
