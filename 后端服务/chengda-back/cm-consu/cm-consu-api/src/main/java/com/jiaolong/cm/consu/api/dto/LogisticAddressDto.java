package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 收件地址数据传输对象
 * user: pan
 * date: 2024-08-22 11:20
 */
@Data
@Schema(description = "收件地址数据传输对象")
public class LogisticAddressDto implements Serializable {

	private static final long serialVersionUID = 1L;

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
	 * 联系人地址详细地址
	 */
	@Schema(description = "联系人地址详细地址")
	private String address;

	/**
	 * 联系人全地址
	 */
	@Schema(description = "联系人全地址")
	private String fullAddress;

}
