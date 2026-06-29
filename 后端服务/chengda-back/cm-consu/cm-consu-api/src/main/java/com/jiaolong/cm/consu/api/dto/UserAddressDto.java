package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 用户收件地址传输对象
 * user: pan
 * date: 2024-08-22 11:20
 */
@Data
@Schema(description = "用户收件地址传输对象")
public class UserAddressDto implements Serializable {

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
	 * 联系人全地址
	 */
	@Schema(description = "联系人全地址")
	private String fullAddress;

}
