package com.jiaolong.cm.admin.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 系统配置数据传输
 * user: pan
 * date: 2024-09-24 15:02
 */
@Data
public class SettingVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 国标区商品抽成（%）
	 */
	@Schema(description = "国标区商品抽成（%）")
	private Integer gbRate;

	/**
	 * 正品区商品抽成（%）
	 */
	@Schema(description = "正品区商品抽成（%）")
	private Integer zpRate;

	/**
	 * 优惠区商品抽成（%）
	 */
	@Schema(description = "优惠区商品抽成（%）")
	private Integer yhRate;

	/**
	 * 商户积分转换（分子）
	 */
	@Schema(description = "商户积分转换（分子）")
	private Integer molecule;

	/**
	 * 商户积分转换（分母）
	 */
	@Schema(description = "商户积分转换（分母）")
	private Integer denominator;
}
