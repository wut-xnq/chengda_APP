package com.jiaolong.cm.merch.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * desc: 支付订单查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class PaymentOrderSearchVo extends PageDto {

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 支付单号
	 */
	@Schema(description = "支付单号")
	private String paymentNo;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间（始）")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTimeStart;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间（止）")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTimeEnd;

	/**
	 * 支付方式;（1-微信支付，2-支付宝支付）
	 */
	@Schema(description = "支付方式（1-微信支付，2-支付宝支付）")
	private Integer paymentType;

	/**
	 * 最小交易金额
	 */
	@Schema(description = "最小交易金额")
	private String amountMin;

	/**
	 * 最大交易金额
	 */
	@Schema(description = "最大交易金额")
	private String amountMax;

	/**
	 * 交易状态;（0-未开始交易，1-取消交易，2-交易失败，3-交易成功）
	 */
	@Schema(description = "交易状态（0-未开始交易，1-取消交易，2-交易失败，3-交易成功）")
	private Integer tradeState;
}
