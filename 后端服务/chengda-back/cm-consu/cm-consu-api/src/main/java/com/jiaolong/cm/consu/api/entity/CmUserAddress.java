package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * desc: 用户收货地址
 * user: pan
 * date: 2024-08-20 10:48
 */
@Data
@Schema(description = "用户收货地址")
@EqualsAndHashCode(callSuper = true)
public class CmUserAddress extends Model<CmUserAddress> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
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
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 联系人姓名
	 */
	@Schema(description = "联系人姓名")
	private String concatPerson;

	/**
	 * 联系人电话
	 */
	@Schema(description = "联系人电话")
	private String contactPhone;

	/**
	 * 联系人地址所在省编码
	 */
	@Schema(description = "联系人地址所在省编码")
	private Integer provinceCode;

	/**
	 * 联系人地址所在市编码
	 */
	@Schema(description = "联系人地址所在市编码")
	private Integer cityCode;

	/**
	 * 联系人地址所在区/县编码
	 */
	@Schema(description = "联系人地址所在区/县编码")
	private Integer countryCode;

	/**
	 * 联系人地址详细地址
	 */
	@Schema(description = "联系人地址详细地址")
	private String address;

	/**
	 * 是否默认;（0-否，1-是）
	 */
	@Schema(description = "是否默认（0-否，1-是）")
	private String ifDefault;

	/**
	 * 地址状态;（0-禁用，1-启用）
	 */
	@Schema(description = "地址状态（0-禁用，1-启用）")
	private String state;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
