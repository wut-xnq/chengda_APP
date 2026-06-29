package com.jiaolong.cm.merch.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * desc: 商品标签传参
 * user: pan
 * date: 2024-09-02 15:08
 */
@Data
public class LogisticVo implements Serializable {

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
	private String merchantId;

	/**
	 * 方案名称
	 */
	@Schema(description = "方案名称")
	private String logisticName;

	/**
	 * 物流覆盖区域
	 */
	@Schema(description = "物流覆盖区域")
	private String region;

	/**
	 * 计费方式;（1-按重量，2-按数量）
	 */
	@Schema(description = "计费方式（1-按重量，2-按数量）")
	private String chargeType;

	/**
	 * 首重;（多少KG/件的计费金额）
	 */
	@Schema(description = "首重（多少KG/多少件的计费金额）")
	private Integer weight;

	/**
	 * 首重价格
	 */
	@Schema(description = "首重价格")
	private BigDecimal price;

	/**
	 * 续重;（超出部分每KG/件计费金额）
	 */
	@Schema(description = "续重（超出部分每KG/每件计费金额）")
	private Integer secondWeight;

	/**
	 * 续重价格
	 */
	@Schema(description = "续重价格")
	private BigDecimal secondPrice;

	/**
	 * 是否默认;（0-否，1-是）
	 */
	@Schema(description = "是否默认（0-否，1-是）")
	private String ifDefault;

	/**
	 * 状态;（0-禁用，1-启用）
	 */
	@Schema(description = "状态（0-禁用，1-启用）")
	private String state;
}
