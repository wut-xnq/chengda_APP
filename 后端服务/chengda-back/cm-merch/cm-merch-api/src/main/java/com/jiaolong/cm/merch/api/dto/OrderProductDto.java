package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 订单商品数据传输对象
 * user: pan
 * date: 2024-09-05 14:53
 */
@Data
@Schema(description = "订单商品数据传输对象")
public class OrderProductDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 商品编号
	 */
	@Schema(description = "商品编号")
	private String productCode;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * SKU编号
	 */
	@Schema(description = "SKU编号")
	private String skuCode;

	/**
	 * SKU名称
	 */
	@Schema(description = "SKU名称")
	private String skuName;

	/**
	 * 所选规格名称
	 */
	@Schema(description = "所选规格名称")
	private String specName;
}
