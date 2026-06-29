package com.jiaolong.cm.merch.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * desc: 购物订单查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class ShoppingOrderSearchVo extends PageDto {

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 订单编号
	 */
	@Schema(description = "订单编号")
	private String orderNo;

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
	 * 所属用户名称
	 */
	@Schema(description = "所属用户名称")
	private String username;

	/**
	 * 订单状态;（1-待发货，2-已发货，3-已完成）
	 */
	@Schema(description = "订单状态（1-待发货，2-已发货，3-已完成）")
	private Integer state;
}
