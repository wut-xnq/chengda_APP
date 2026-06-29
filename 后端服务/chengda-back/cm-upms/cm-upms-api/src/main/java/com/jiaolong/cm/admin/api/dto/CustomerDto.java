package com.jiaolong.cm.admin.api.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 系统客户传输对象
 * user: pan
 * date: 2024-09-14 16:57
 */
@Data
@Schema(description = "系统客户传输对象")
public class CustomerDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键id")
	private Long userId;

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
	 * 所属商户单位主键
	 */
	@Schema(description = "所属商户单位主键")
	private Long merchantId;

	/**
	 * 是否认证/发名片;（0-否，1-是）
	 */
	@Schema(description = "是否认证/发名片（0-未认证，1-认证不通过，2-认证通过）")
	private String verified;

	/**
	 * 锁定标记;（0-未锁定，9-已锁定）
	 */
	@Schema(description = "锁定标记（0-未锁定，9-已锁定）")
	private String lockFlag;

	/**
	 * 商户名称
	 */
	@Schema(description = "商户名称")
	private String merchantName;

}
