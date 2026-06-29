package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品SKU传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class ProductSkuVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 所属商户主键
	 */
	@NotNull
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 商品主键
	 */
	@NotNull
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 编号
	 */
	@Schema(description = "编号")
	private String skuCode;

	/**
	 * SKU名称
	 */
	@NotBlank
	@Schema(description = "SKU名称")
	private String skuName;

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
	 * 此SKU对应的商品总量
	 */
	@NotNull
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
	 * SKU配图
	 */
	@Schema(description = "SKU配图")
	private String skuPics;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;
}
