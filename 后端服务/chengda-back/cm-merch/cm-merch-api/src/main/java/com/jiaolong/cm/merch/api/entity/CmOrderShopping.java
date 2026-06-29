package com.jiaolong.cm.merch.api.entity;

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
 * desc: 购物订单
 * user: pan
 * date: 2024-08-20 10:51
 */
@Data
@Schema(description = "购物订单")
@EqualsAndHashCode(callSuper = true)
public class CmOrderShopping extends Model<CmOrderShopping> {

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
	 * 订单编号
	 */
	@Schema(description = "订单编号")
	private String orderNo;

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 配送地址主键
	 */
	@Schema(description = "配送地址主键")
	private Long userAddressId;

	/**
	 * 订单商品主键集;（多个订单商品主键使用英文逗号隔开）
	 */
	@Schema(description = "订单商品主键集")
	private String orderProducts;

	/**
	 * 商品福利优惠主键集合;（多个福利优惠主键使用英文逗号隔开）
	 */
	@Schema(description = "商品福利优惠主键集合")
	private String orderWelfares;

	/**
	 * 订单总金额
	 */
	@Schema(description = "订单总金额")
	private BigDecimal totalPrice;

	/**
	 * 运输费用
	 */
	@Schema(description = "运输费用")
	private BigDecimal deliverPrice;

	/**
	 * 订单实付金额
	 */
	@Schema(description = "订单实付金额")
	private BigDecimal realPrice;

	/**
	 * 订单结算金额
	 */
	@Schema(description = "订单结算金额")
	private BigDecimal settlementAmount;

	/**
	 * 订单状态;（1-待发货，2-已发货，3-已完成）
	 */
	@Schema(description = "订单状态（1-待发货，2-已发货，3-已完成）")
	private Integer state;

	/**
	 * 支付方式;（1-微信支付，2-支付宝支付）
	 */
	@Schema(description = "支付方式（1-微信支付，2-支付宝支付）")
	private Integer paymentType;

	/**
	 * 支付状态;（0-未支付，1-取消支付，2-支付失败，3-支付成功）
	 */
	@Schema(description = "支付状态（0-未支付，1-取消支付，2-支付失败，3-支付成功）")
	private Integer paymentState;

	/**
	 * 订单结算状态（0-未结算，1-已结算）
	 */
	@Schema(description = "订单结算状态（0-未结算，1-已结算）")
	private Integer settlementState;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;
}
