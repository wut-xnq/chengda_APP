package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 商品规格类型数据传输对象
 * user: pan
 * date: 2024-08-21 18:03
 */
@Data
@Schema(description = "商品规格类型数据传输对象")
public class ProductSpecDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "规格主键")
	private Long id;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 规格组主键
	 */
	@Schema(description = "规格组主键")
	private Long groupId;

	/**
	 * 规格名称
	 */
	@Schema(description = "规格名称")
	private String specName;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 状态;（0-禁用，1-启用）
	 */
	@Schema(description = "状态（0-禁用，1-启用）")
	private String state;

	/**
	 * 规格组名称
	 */
	@Schema(description = "规格组名称")
	private String groupName;

	/**
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;
}
