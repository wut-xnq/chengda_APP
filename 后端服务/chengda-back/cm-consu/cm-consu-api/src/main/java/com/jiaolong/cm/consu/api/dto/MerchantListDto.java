package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商户列表数据传输对象
 * user: pan
 * date: 2024-10-23 14:32
 */
@Data
@Schema(description = "商户列表数据传输对象")
public class MerchantListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 企业主键
	 */
	@Schema(description = "企业主键")
	private Long id;

	/**
	 * 商户名称
	 */
	@Schema(description = "企业名称")
	private String merchantName;
}
