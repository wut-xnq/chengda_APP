package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 商品数据传输对象
 * user: pan
 * date: 2024-09-05 14:53
 */
@Data
@Schema(description = "商品数据传输对象")
public class ProductDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

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
	 * 商品主图
	 */
	@Schema(description = "商品主图")
	private String productPics;

	/**
	 * 商品详情展示（图文）
	 */
	@Schema(description = "商品详情展示（图文）")
	private String productText;

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
	 * 品牌名称
	 */
	@Schema(description = "品牌名称")
	private String brandName;

	/**
	 * 商品分类（名称）
	 */
	@Schema(description = "商品分类（名称）")
	private String productClassify;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;

	/**
	 * 商品SKU列表
	 */
	@Schema(description = "商品SKU列表")
	private List<ProductSkuDto> productSkuList;

	/**
	 * 商品规格主键集;（多个规格主键使用英文逗号隔开）
	 */
	@Schema(description = "商品规格主键集")
	private String productSpecs;

	/**
	 * 商品规格列表
	 */
	@Schema(description = "商品规格列表")
	private List<SpecDto> productSpecList;

	/**
	 * 商品标签主键集;（多个标签主键使用英文逗号隔开）
	 */
	@Schema(description = "商品标签主键集")
	private String labels;

	/**
	 * 商品标签列表
	 */
	@Schema(description = "商品标签列表")
	private List<LabelDto> productLabelList;
}
