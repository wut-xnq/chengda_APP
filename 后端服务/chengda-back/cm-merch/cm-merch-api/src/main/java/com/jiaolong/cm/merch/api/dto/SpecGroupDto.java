package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品规格组数据传输对象
 * user: pan
 * date: 2024-09-01 13:13
 */
@Data
@Schema(description = "商品规格组数据传输对象")
public class SpecGroupDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 规格组名称
	 */
	@Schema(description = "规格组名称")
	private String groupName;
}
