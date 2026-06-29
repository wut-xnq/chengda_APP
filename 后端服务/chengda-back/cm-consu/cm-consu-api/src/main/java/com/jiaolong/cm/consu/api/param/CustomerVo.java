package com.jiaolong.cm.consu.api.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 用户信息数据传参
 * user: pan
 * date: 2024-10-29 11:44
 */
@Data
public class CustomerVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户主键
	 */
	@Schema(description = "用户主键")
	private Long userId;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
}
