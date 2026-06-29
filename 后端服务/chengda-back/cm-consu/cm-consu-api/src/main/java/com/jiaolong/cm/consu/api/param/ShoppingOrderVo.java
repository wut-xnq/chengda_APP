package com.jiaolong.cm.consu.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * desc: 购物订单数据传参
 * user: pan
 * date: 2024-08-20 16:28
 */
@Data
public class ShoppingOrderVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

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

	/**
	 * 所选规格名称
	 */
	@Schema(description = "所选规格名称")
	private String specName;

	/**
	 * 配送地址主键
	 */
	@Schema(description = "配送地址主键")
	private Long userAddressId;

	/**
	 * 运输费用
	 */
	@Schema(description = "运输费用")
	private BigDecimal deliverPrice;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;

}
