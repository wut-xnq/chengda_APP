package com.jiaolong.cm.admin.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 客户查询传参
 * user: pan
 * date: 2024-09-20 09:23
 */
@Data
public class CustomerSearchVo implements Serializable {

	private static final long serialVersionUID = 1L;

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
	 * 所属商户单位主键
	 */
	@Schema(description = "所属商户单位主键")
	private Long merchantId;

	/**
	 * 锁定标记;（0-未锁定，9-已锁定）
	 */
	@Schema(description = "锁定标记（0-未锁定，9-已锁定）")
	private String lockFlag;
}
