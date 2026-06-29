package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 商户物流方案数据传输对象
 * user: pan
 * date: 2024-09-03 10:28
 */
@Data
@Schema(description = "商户物流方案数据传输对象")
public class LogisticsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

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
	 * 方案状态;（0-禁用，1-启用）
	 */
	@Schema(description = "方案状态（0-禁用，1-启用）")
	private String state;

	/**
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;

	/**
	 * 物流覆盖区域展示
	 */
	@Schema(description = "物流覆盖区域展示")
	private List<CityCodeDto> regionNameList;
}
