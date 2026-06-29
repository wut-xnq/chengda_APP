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
public class StatisticProductDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * 今日销量
	 */
	@Schema(description = "今日销量")
	private Integer todaySalesVolume;

	/**
	 * 本周销量
	 */
	@Schema(description = "本周销量")
	private Integer weekSalesVolume;
}
