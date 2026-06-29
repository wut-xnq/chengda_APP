package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * desc: 支付订单数据传输对象
 * user: pan
 * date: 2024-09-03 14:29
 */
@Data
@Schema(description = "支付订单数据传输对象")
public class PaymentOrderDto implements Serializable {

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
	 * 支付单号
	 */
	@Schema(description = "支付单号")
	private String paymentNo;

	/**
	 * 关联订单编号
	 */
	@Schema(description = "关联订单编号")
	private String shoppingOrderNo;

	/**
	 * 买家主键
	 */
	@Schema(description = "买家主键")
	private Long buyerId;

	/**
	 * 商户主键
	 */
	@Schema(description = "商户主键")
	private Long merchantId;

	/**
	 * 商户名称
	 */
	@Schema(description = "商户名称")
	private String merchantName;

	/**
	 * 支付人账号
	 */
	@Schema(description = "支付人账号")
	private String userChargeAccount;

	/**
	 * 收款人账号
	 */
	@Schema(description = "收款人账号")
	private String merchantChargeAccount;

	/**
	 * 交易金额
	 */
	@Schema(description = "交易金额")
	private BigDecimal amount;

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
	 * 第三方交易流水号
	 */
	@Schema(description = "第三方交易流水号")
	private String tradeFlowNo;

	/**
	 * 买家名称
	 */
	@Schema(description = "买家名称")
	private String username;
}
