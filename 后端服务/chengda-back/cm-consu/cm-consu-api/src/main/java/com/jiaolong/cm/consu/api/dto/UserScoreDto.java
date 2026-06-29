package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户积分兑换传输数据
 * user: pan
 * date: 2024-08-22 11:20
 */
@Data
@Schema(description = "用户积分兑换传输数据")
public class UserScoreDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long userId;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 头像
	 */
	@Schema(description = "头像地址")
	private String avatar;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickname;

	/**
	 * 积分
	 */
	@Schema(description = "积分")
	private Integer userScore;

	/**
	 * 是否赠送积分;（0-否，1-是）
	 */
	@Schema(description = "是否赠送积分（0-否，1-是）")
	private String ifGiftScore;

}
