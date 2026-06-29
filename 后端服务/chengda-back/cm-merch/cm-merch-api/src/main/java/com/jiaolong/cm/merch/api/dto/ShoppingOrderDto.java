package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 购物订单数据传输对象
 * user: pan
 * date: 2024-09-03 14:29
 */
@Data
@Schema(description = "购物订单数据传输对象")
public class ShoppingOrderDto implements Serializable {

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
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;

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

	@Schema(description = "详细地址")
	private String addressDetail;
	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;

	/**
	 * 所属用户名称
	 */
	@Schema(description = "所属用户名称")
	private String username;

	/**
	 * 物流单号
	 */
	@Schema(description = "物流单号")
	private String logisticsNo;

	/**
	 * 支付流水号
	 */
	@Schema(description = "支付流水号")
	private String paymentNo;

	/**
	 * 订单商品列表
	 */
	@Schema(description = "订单商品列表")
	private List<OrderProductDto> orderProductList;
}
