package com.jiaolong.cm.merch.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 用户
 * user: pan
 * date: 2024-08-20 12:44
 */
@Data
@Schema(description = "用户")
public class CmCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键id")
	private Long userId;

	/**
	 * 用户IMToken
	 */
	@Schema(description = "用户IMToken")
	private String imAccessToken;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 电话号码
	 */
	@Schema(description = "电话号码")
	private String phone;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickname;

	/**
	 * 头像
	 */
	@Schema(description = "头像")
	private String avatar;

	/**
	 * 真实姓名
	 */
	@Schema(description = "真实姓名")
	private String name;

	/**
	 * 性别;（F-女，M-男）
	 */
	@Schema(description = "性别（F-女，M-男）")
	private String gender;

	/**
	 * 年龄
	 */
	@Schema(description = "年龄")
	private Integer age;

	/**
	 * 邮箱地址
	 */
	@Schema(description = "邮箱地址")
	private String email;

	/**
	 * 生日
	 */
	@Schema(description = "生日")
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
	 * 所属部门ID
	 */
	@Schema(description = "所属部门ID")
	private Long deptId;

	/**
	 * 编号
	 */
	@Schema(description = "编号")
	private String userNumber;

	/**
	 * 锁定标记;（0-未锁定，9-已锁定）
	 */
	@Schema(description = "锁定标记（0-未锁定，9-已锁定）")
	private String lockFlag;

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
	 * 个人邀请码
	 */
	@Schema(description = "个人邀请码")
	private String invitationCode;

	/**
	 * 邀请人主键
	 */
	@Schema(description = "邀请人主键")
	private Long inviterId;

	/**
	 * 是否认证/发名片（0-未认证，1-认证不通过，2-认证通过）
	 */
	@Schema(description = "是否认证/发名片（0-未认证，1-认证不通过，2-认证通过）")
	private String verified;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 删除标记（0-正常，1-删除）
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "删除标记（0-正常，1-已删除）")
	private String delFlag;
}
