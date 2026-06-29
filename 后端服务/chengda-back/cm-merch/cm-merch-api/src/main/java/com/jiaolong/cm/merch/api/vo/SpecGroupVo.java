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
public class SpecGroupVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 所属商户主键
	 */
	@NotNull
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 规格组名称
	 */
	@Schema(description = "规格组名称")
	private String groupName;
}
