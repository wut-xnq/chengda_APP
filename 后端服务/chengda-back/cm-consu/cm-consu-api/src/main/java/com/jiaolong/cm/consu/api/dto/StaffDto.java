package com.jiaolong.cm.consu.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 企业员工数据传输对象
 * user: pan
 * date: 2024-08-22 11:20
 */
@Data
@Schema(description = "企业员工数据传输对象")
public class StaffDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "用户主键")
	private Long userId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

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
	 * 真实姓名
	 */
	@Schema(description = "真实姓名")
	private String name;

	/**
	 * 生日
	 */
	@Schema(description = "生日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime birthday;

	/**
	 * 爱好
	 */
	@Schema(description = "爱好")
	private String hobby;

	/**
	 * 专业
	 */
	@Schema(description = "专业")
	private String major;

	/**
	 * 个人简介
	 */
	@Schema(description = "个人简介")
	private String brief;

	/**
	 * 个人风采视频封面url
	 */
	@Schema(description = "个人风采视频封面url")
	private String personUrl;

	/**
	 * 个人邀请码
	 */
	@Schema(description = "个人邀请码")
	private String invitationCode;

	/**
	 * 是否认证/发名片（0-否，1-是）
	 */
	@Schema(description = "是否认证（0-否，1-是）")
	private String verified;

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
	 * 商户详细地址
	 */
	@Schema(description = "商户的详细地址")
	private String fullAddress;

}
