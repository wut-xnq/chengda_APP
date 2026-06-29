package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品销量查询返回结果
 * user: pan
 * date: 2024-11-11 15:56
 */
@Data
@Schema(description = "商品销量查询返回结果")
public class StatisticProductQueryDto implements Serializable {

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
	 * 销量
	 */
	@Schema(description = "销量")
	private Integer volume;

}
