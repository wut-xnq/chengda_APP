package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class ProductShelveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@NotNull
	@Schema(description = "商品主键")
	private Long id;

	/**
	 * 上架状态;（0-已下架，1-已上架）
	 */
	@NotBlank
	@Schema(description = "上架状态（0-已下架，1-已上架）")
	private String shelveState;

	/**
	 * 商品上架区域;（1-国标，2-正品，3-优惠）
	 */
	@Schema(description = "商品上架区域（1-国标，2-正品，3-优惠）")
	private Integer shelveArea;

	/**
	 * 下架原因
	 */
	@Schema(description = "下架原因")
	private String reason;
}
