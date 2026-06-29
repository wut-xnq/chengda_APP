package com.jiaolong.cm.consu.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 计算商品运费数据传参
 * user: pan
 * date: 2024-10-31 16:19
 */
@Data
public class TraceFeeVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 配送地址主键
	 */
	@NotNull
	@Schema(description = "配送地址主键")
	private Long userAddressId;

	/**
	 * SKU主键
	 */
	@NotNull
	@Schema(description = "SKU主键")
	private Long skuId;

	/**
	 * 商品数量
	 */
	@NotNull
	@Schema(description = "商品数量")
	private Integer amount;

}
