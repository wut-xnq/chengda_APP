package com.jiaolong.cm.consu.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 传参
 * user: pan
 * date: 2024-08-20 16:28
 */
@Data
public class ShoppingOrderParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

	/**
	 * 订单状态;（1-待发货，2-已发货，3-已完成）
	 */
	@Schema(description = "订单状态（1-待发货，2-已发货，3-已完成）")
	private Integer state;

	/**
	 * 支付状态;（0-未支付，1-取消支付，2-支付失败，3-支付成功）
	 */
	@Schema(description = "支付状态（0-未支付，1-取消支付，2-支付失败，3-支付成功）")
	private Integer paymentState;
}
