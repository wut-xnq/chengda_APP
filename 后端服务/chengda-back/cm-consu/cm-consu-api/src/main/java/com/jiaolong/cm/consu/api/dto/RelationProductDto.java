package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 关联商品数据传输对象
 * user: pan
 * date: 2024-08-20 15:37
 */
@Data
@Schema(description = "关联商品数据传输对象")
public class RelationProductDto implements Serializable {

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
	 * 商品主图
	 */
	@Schema(description = "商品主图")
	private String productPics;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 商品浏览量
	 */
	@Schema(description = "商品浏览量")
	private Integer viewAmount;

	/**
	 * 商品购买量
	 */
	@Schema(description = "商品购买量")
	private Integer orderAmount;

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
	 * 外层展示的 SKU 主键
	 */
	@Schema(description = "外层展示的 SKU 主键")
	private Long showedSkuId;
}
