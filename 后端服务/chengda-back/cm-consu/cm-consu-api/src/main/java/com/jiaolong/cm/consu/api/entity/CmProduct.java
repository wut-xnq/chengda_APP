package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * desc: 商品信息
 * user: pan
 * date: 2024-08-20 11:28
 */
@Data
@Schema(description = "商品信息")
@EqualsAndHashCode(callSuper = true)
public class CmProduct extends Model<CmProduct> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
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
	 * 商品简介
	 */
	@Schema(description = "商品简介")
	private String productBrief;

	/**
	 * 商品详情展示（图文）
	 */
	@Schema(description = "商品详情展示（图文）")
	private String productText;

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
	 * 商品售卖SKU主键集;（多个SKU主键使用英文逗号隔开）
	 */
	@Schema(description = "商品售卖SKU主键集")
	private String productSkus;

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
	 * 商品保障字典集;（使用字典管理，多个字典值主键使用英文逗号隔开）
	 */
	@Schema(description = "商品保障字典集")
	private String productGuarantees;

	/**
	 * 是否允许使用优惠;（0-否，1-是）
	 */
	@Schema(description = "是否允许使用优惠（0-否，1-是）")
	private String ifWelfare;

	/**
	 * 是否参与积分兑换;（0-否，1-是）
	 */
	@Schema(description = "是否参与积分兑换（0-否，1-是）")
	private String ifExchange;

	/**
	 * 是否参与赠品;（0-否，1-是）
	 */
	@Schema(description = "是否参与赠品（0-否，1-是）")
	private String ifGift;

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
	 * 是否首页置顶;（0-否，1-是）
	 */
	@Schema(description = "是否首页置顶（0-否，1-是）")
	private String indexTopped;

	/**
	 * 品牌名称
	 */
	@Schema(description = "品牌名称")
	private String brandName;

	/**
	 * 品牌主键
	 */
	@Schema(description = "品牌主键")
	private String brandId;

	/**
	 * 商品分类（名称）
	 */
	@Schema(description = "商品分类（名称）")
	private String productClassify;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
