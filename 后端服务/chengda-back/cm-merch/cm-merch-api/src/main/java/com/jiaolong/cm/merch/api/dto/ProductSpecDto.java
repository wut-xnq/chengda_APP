package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class ProductSpecDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 规格主键
	 */
	@Schema(description = "规格主键")
	private Long specId;

	/**
	 * 规格名称
	 */
	@Schema(description = "规格名称")
	private String specName;
}
