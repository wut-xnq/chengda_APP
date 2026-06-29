package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 订单发货传参
 * user: pan
 * date: 2024-09-03 15:21
 */
@Data
public class OrderDeliverVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 物流公司名称
	 */
	@NotBlank
	@Schema(description = "物流公司名称")
	private String logisticsName;

	/**
	 * 物流公司编码
	 */
	@NotBlank
	@Schema(description = "物流公司编码")
	private String logisticsCode;

	/**
	 * 物流单编号
	 */
	@NotBlank
	@Schema(description = "物流单编号")
	private String logisticsNo;

	/**
	 * 购物订单主键
	 */
	@NotBlank
	@Schema(description = "购物订单主键")
	private Long orderId;

	/**
	 * 购物订单编号
	 */
	@NotBlank
	@Schema(description = "订单编号")
	private String orderNo;


}
