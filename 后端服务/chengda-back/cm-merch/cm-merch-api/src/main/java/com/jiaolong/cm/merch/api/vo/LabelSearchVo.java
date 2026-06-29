package com.jiaolong.cm.merch.api.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商品标签查询传参
 * user: pan
 * date: 2024-08-29 16:53
 */
@Data
public class LabelSearchVo extends PageDto {

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 标签名称
	 */
	@Schema(description = "标签名称")
	private String labelName;

	/**
	 * 标签编号
	 */
	@Schema(description = "标签编号")
	private String labelCode;

	/**
	 * 状态;（0-禁用，1-启用）
	 */
	@Schema(description = "状态（0-禁用，1-启用）")
	private String state;
}
