package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * desc: 商品查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class ProductVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "商品主键")
	private Long id;

	/**
	 * 商品名称
	 */
	@NotBlank
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * 商品编号
	 */
	@Schema(description = "商品编号")
	private String productCode;

	/**
	 * 商品简介
	 */
	@Schema(description = "商品简介")
	private String productBrief;

	/**
	 * 商品富文本
	 */
	@Schema(description = "商品富文本")
	private String productText;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String productPics;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 最低售价
	 */
	@Schema(description = "最低售价")
	private BigDecimal minPrice;

	/**
	 * 是否包邮;（0-否，1-是）
	 */
	@Schema(description = "是否包邮（0-否，1-是）")
	private String ifShipped;

	/**
	 * 物流方案主键
	 */
	@Schema(description = "物流方案主键")
	private Long logisticId;

	/**
	 * 发货期
	 */
	@Schema(description = "发货期")
	private String deliver;

	/**
	 * 商品上架区域;（1-国标，2-正品，3-优惠）
	 */
	@Schema(description = "商品上架区域（1-国标，2-正品，3-优惠）")
	private Integer shelveArea;

	/**
	 * 商品规格主键集;（多个规格主键使用英文逗号隔开）
	 */
	@Schema(description = "商品规格主键集")
	private String productSpecs;

	/**
	 * 商品标签主键集;（多个标签主键使用英文逗号隔开）
	 */
	@Schema(description = "商品标签主键集")
	private String labels;

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
