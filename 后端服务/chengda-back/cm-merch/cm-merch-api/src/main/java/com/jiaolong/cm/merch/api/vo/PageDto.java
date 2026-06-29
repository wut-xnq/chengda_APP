package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 公共分页参数
 * user: pan
 * date: 2024-10-25 19:13
 */
@Data
public class PageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前页码（默认第 1 页）
	 */
	@Schema(description = "分页参数：当前页码")
	private Long current = 1L;

	/**
	 * 页面大小（默认每页 10 条数据）
	 */
	@Schema(description = "分页参数：页面数据容量")
	private Long size = 10L;
}
