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
public class StatisticOrderQueryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商户主键
	 */
	@Schema(description = "商户主键")
	private Long merchantId;

	/**
	 * 今日收益
	 */
	@Schema(description = "收益")
	private Integer income;

	/**
	 * 今日订单量
	 */
	@Schema(description = "订单量")
	private Integer orderCount;
}
