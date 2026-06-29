package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * desc: 待结算订单数据
 * user: pan
 * date: 2024-11-13 12:02
 */
@Data
public class SettlementOrderDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "购物订单主键")
	private Long orderId;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 支付方式;（1-微信支付，2-支付宝支付）
	 */
	@Schema(description = "支付方式（1-微信支付，2-支付宝支付）")
	private Integer paymentType;

	/**
	 * 订单实付金额
	 */
	@Schema(description = "订单实付金额")
	private BigDecimal realPrice;

	/**
	 * 订单商品上架区域（1-国标，2-正品，3-优惠）
	 */
	@Schema(description = "订单商品上架区域（1-国标，2-正品，3-优惠）")
	private Integer shelveArea;
}
