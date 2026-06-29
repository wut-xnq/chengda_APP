package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 物流方案查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class LogisticSearchVo extends PageDto {

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 方案名称
	 */
	@Schema(description = "方案名称")
	private String logisticName;

	/**
	 * 状态;（0-禁用，1-启用）
	 */
	@Schema(description = "状态（0-禁用，1-启用）")
	private String state;
}
