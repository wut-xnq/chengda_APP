package com.jiaolong.cm.consu.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * desc: 支付订单页面数据传输
 * user: pan
 * date: 2024-09-26 10:22
 */
@Data
public class OrderPrePaymentVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联商品订单编号
	 */
	@NotNull
	@Schema(description = "关联商品订单编号")
	private String orderNo;

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
	 * 交易支付方（用户）主键
	 */
	@Schema(description = "交易支付方（用户）主键")
	private Long userId;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "交易收款方（商户）主键")
	private Long merchantId;

	/**
	 * 交易金额
	 */
	@NotNull
	@Schema(description = "交易金额")
	private BigDecimal amount;

	/**
	 * 第三方交易流水号
	 */
	@NotNull
	@Schema(description = "第三方交易流水号")
	private String tradeFlowNo;

	/**
	 * 交易状态;（0-未开始交易，1-取消交易，2-交易失败，3-交易成功）
	 */
	@Schema(description = "交易状态（0-未开始交易，1-取消交易，2-交易失败，3-交易成功）")
	private Integer tradeState;

}
