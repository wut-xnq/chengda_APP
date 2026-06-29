package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * desc: 商品SKU查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class ProductSkuSearchVo extends PageDto {

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

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
	 * 价格（下限）
	 */
	@Schema(description = "价格（下限）")
	private String priceMin;

	/**
	 * 价格（上限）
	 */
	@Schema(description = "价格（上限）")
	private String priceMax;

	/**
	 * 状态;（0-禁用，1-启用）
	 */
	@Schema(description = "状态（0-禁用，1-启用）")
	private String state;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;
}
