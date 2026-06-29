package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 用户认证信息数据传输对象
 * user: pan
 * date: 2024-08-28 14:32
 */
@Data
@Schema(description = "用户认证信息数据传输对象")
public class UserVerifiedDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

	/**
	 * 员工姓名（最后一个字）
	 */
	@Schema(description = "员工姓名（最后一个字）")
	private String username;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 操作人主键
	 */
	@Schema(description = "操作人主键")
	private Long operatorId;

	/**
	 * 认证状态;（0-未认证，1-认证不通过，2-认证通过）
	 */
	@Schema(description = "认证状态（0-未认证，1-认证不通过，2-认证通过）")
	private Integer verifiedState;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;
}
