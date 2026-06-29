package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户信息数据传输对象
 * user: pan
 * date: 2024-08-28 14:32
 */
@Data
@Schema(description = "用户信息数据传输对象")
public class DictItemDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典项主键
	 */
	@Schema(description = "字典项主键")
	private Long id;

	/**
	 * 所属字典类id
	 */
	@Schema(description = "所属字典主键")
	private Long dictId;

	/**
	 * 类型
	 */
	@Schema(description = "所属字典类型")
	private String dictType;

	/**
	 * 数据值
	 */
	@Schema(description = "字典项值")
	private String itemValue;

	/**
	 * 标签名
	 */
	@Schema(description = "标签名")
	private String label;

	/**
	 * 描述
	 */
	@Schema(description = "描述")
	private String description;

	/**
	 * 排序（升序）
	 */
	@Schema(description = "排序值")
	private Integer sortOrder;
}
