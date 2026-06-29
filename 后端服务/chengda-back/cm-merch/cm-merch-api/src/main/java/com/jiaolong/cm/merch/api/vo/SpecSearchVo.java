package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品规格组传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class SpecSearchVo extends PageDto {

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
	 * 状态;（0-禁用，1-启用）
	 */
	@Schema(description = "状态（0-禁用，1-启用）")
	private String state;
}
