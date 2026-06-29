package com.jiaolong.cm.consu.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 用户传输对象
 * user: pan
 * date: 2024-08-22 11:20
 */
@Data
@Schema(description = "用户传输对象")
public class UserPersonalDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
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
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

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
	 * 性别（1-男，2-女）
	 */
	@Schema(description = "性别（1-男，2-女）")
	private String gender;

	/**
	 * 年龄
	 */
	@Schema(description = "年龄")
	private Integer age;

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
	 * 用户角色主键
	 */
	@Schema(description = "用户角色主键")
	private Long roleId;

	/**
	 * 所属商户单位主键
	 */
	@Schema(description = "所属商户单位主键")
	private Long merchantId;

	/**
	 * 所属商户单位名称
	 */
	@Schema(description = "所属商户单位名称")
	private String merchantName;

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
	 * 是否认证/发名片（0-未认证，1-认证不通过，2-认证通过）
	 */
	@Schema(description = "是否认证/发名片（0-未认证，1-认证不通过，2-认证通过）")
	private String verified;

	/**
	 * 邀请人主键
	 */
	@Schema(description = "邀请人主键")
	private Long inviterId;

	/**
	 * 邀请人姓名
	 */
	@Schema(description = "邀请人姓名")
	private String inviter;
}
