package com.jiaolong.cm.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 质检商品数据传输对象
 * user: pan
 * date: 2024-10-25 12:20
 */
@Data
@Schema(description = "质检商品数据传输对象")
public class ExamGoodsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long procuctId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;
}
