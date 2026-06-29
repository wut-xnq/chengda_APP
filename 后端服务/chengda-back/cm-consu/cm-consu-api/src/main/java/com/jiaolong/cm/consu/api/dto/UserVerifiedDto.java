package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 用户认证数据传输对象
 * user: pan
 * date: 2024-08-22 11:20
 */
@Data
@Schema(description = "用户认证数据传输对象")
public class UserVerifiedDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;

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
	 * 用户姓名最后一个字
	 */
	@Schema(description = "用户姓名最后一个字")
	private String nameLastChar;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
