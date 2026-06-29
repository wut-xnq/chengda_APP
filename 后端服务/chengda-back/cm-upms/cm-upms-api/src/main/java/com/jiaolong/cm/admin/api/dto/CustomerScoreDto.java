package com.jiaolong.cm.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户积分对象
 * user: pan
 * date: 2024-11-07 11:49
 */
@Data
@Schema(description = "用户积分对象")
public class CustomerScoreDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户主键")
	private Long userId;

	/**
	 * 积分
	 */
	@Schema(description = "用户积分")
	private Integer userScore;
}
