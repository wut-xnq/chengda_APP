package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品名称数据传输对象
 * user: pan
 * date: 2024-08-20 15:37
 */
@Data
@Schema(description = "商品名称数据传输对象")
public class ProductNameDto implements Serializable {

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
	 * 商品主图
	 */
	@Schema(description = "商品主图")
	private String productPics;
}
