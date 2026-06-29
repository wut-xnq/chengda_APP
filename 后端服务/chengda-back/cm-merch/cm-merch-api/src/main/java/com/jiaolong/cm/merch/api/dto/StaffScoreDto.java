package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 员工积分列表
 * user: pan
 * date: 2024-09-07 10:58
 */
@Data
@Schema(description = "员工积分列表")
public class StaffScoreDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Schema(description = "主键id")
	private Long userId;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名")
	private String name;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 积分
	 */
	@Schema(description = "积分")
	private Integer userScore;

	/**
	 * 头像
	 */
	@Schema(description = "头像")
	private String avatar;

	/**
	 * 是否赠送积分;（0-否，1-是）
	 */
	@Schema(description = "是否赠送积分（0-否，1-是）")
	private String ifGiftScore;

	/**
	 * 累计邀请人数
	 */
	@Schema(description = "累计邀请人数")
	private Integer invition;
}
