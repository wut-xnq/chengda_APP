package com.jiaolong.cm.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc:
 * user: pan
 * date: 2024-11-07 16:21
 */
@Data
@Schema(description = "商户信息传输对象")
public class MerchantDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商户主键
	 */
	@Schema(description = "商户主键")
	private Long merchantId;

	/**
	 * 商户名称
	 */
	@Schema(description = "商户名称")
	private String merchantName;

}
