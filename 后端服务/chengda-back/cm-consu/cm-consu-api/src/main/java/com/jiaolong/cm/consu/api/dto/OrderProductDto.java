package com.jiaolong.cm.consu.api.dto;

import com.jiaolong.cm.consu.api.entity.CmOrderProducts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * desc: 商品数据传输对象
 * user: pan
 * date: 2024-08-20 15:37
 */
@Data
@Schema(description = "订单中商品数据传输对象")
public class OrderProductDto extends CmOrderProducts {

	/**
	 * SKU名称
	 */
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
	 * SKU配图
	 */
	@Schema(description = "SKU配图")
	private String skuPics;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private String productId;

	/**
	 * 所选规格名称
	 */
	@Schema(description = "所选规格名称")
	private String specName;
}
