package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 首页商户基础信息
 * user: pan
 * date: 2024-09-02 16:49
 */
@Data
@Schema(description = "首页商户基础信息")
public class StatisticMerchantInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商户名称
	 */
	@Schema(description = "商户名称")
	private String merchantName;

	/**
	 * 商户LOGO
	 */
	@Schema(description = "商户LOGO")
	private String logo;

	/**
	 * 商户积分
	 */
	@Schema(description = "商户积分")
	private Integer merchantScore;

	/**
	 * 上次登录时间
	 */
	@Schema(description = "上次登录时间")
	private LocalDateTime lastLoginTime;

	/**
	 * 上次登录地点
	 */
	@Schema(description = "上次登录地点")
	private String lastLoginArea;

	/**
	 * 免费展现量算式
	 */
	@Schema(description = "免费展现量算式")
	private String displayQuantityFormula;
}
