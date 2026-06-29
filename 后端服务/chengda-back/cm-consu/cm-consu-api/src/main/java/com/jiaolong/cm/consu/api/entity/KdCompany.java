package com.jiaolong.cm.consu.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 快递编码
 * user: pan
 * date: 2024-10-17 15:59
 */
@Data
@Schema(description = "快递编码")
public class KdCompany implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 物流公司编码
	 */
	@Schema(description = "物流公司编码")
	private String code;

	/**
	 * 物流公司名称
	 */
	@Schema(description = "物流公司名称")
	private String name;

	/**
	 * 物流公司类型
	 */
	@Schema(description = "物流公司类型")
	private String type;

}
