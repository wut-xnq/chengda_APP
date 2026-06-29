package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 首页商品销量统计
 * user: pan
 * date: 2024-11-11 15:56
 */
@Data
@Schema(description = "首页商品销量统计")
public class StatisticOrderDto implements Serializable {

	private static final long serialVersionUID = 1L;



	/**
	 * 商户主键
	 */
	@Schema(description = "商户主键")
	private Long merchantId;

	/**
	 * 今日收益
	 */
	@Schema(description = "今日收益")
	private Integer todayIncome;

	/**
	 * 今日订单量
	 */
	@Schema(description = "今日订单量")
	private Integer todayOrderCount;

	/**
	 * 本周销量
	 */
	@Schema(description = "本月收益")
	private Integer monthIncome;

	/**
	 * 本周销量
	 */
	@Schema(description = "本月订单量")
	private Integer monthOrderCount;
}
