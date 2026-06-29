package com.jiaolong.cm.admin.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商户信息传参
 * user: pan
 * date: 2024-08-28 11:16
 */
@Data
public class MerchantSearchVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商户编号（主键）
	 */
	@Schema(description = "商户编号（主键）")
	private Long merchantId;

	/**
	 * 商户名称
	 */
	@Schema(description = "商户名称")
	private String merchantName;

	/**
	 * 是否认证;（0-未认证，1-已认证）
	 */
	@Schema(description = "是否认证（0-未认证，1-已认证）")
	private String verified;
}
