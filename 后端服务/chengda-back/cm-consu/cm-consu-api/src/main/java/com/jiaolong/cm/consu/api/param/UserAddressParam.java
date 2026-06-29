package com.jiaolong.cm.consu.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户收件地址传参
 * user: pan
 * date: 2024-08-22 11:38
 */
@Data
public class UserAddressParam implements Serializable {

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;
}
