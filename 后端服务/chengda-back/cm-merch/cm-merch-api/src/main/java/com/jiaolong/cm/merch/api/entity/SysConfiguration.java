package com.jiaolong.cm.merch.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 系统配置
 * user: pan
 * date: 2024-09-06 10:44
 */
@Data
@Schema(description = "系统配置")
public class SysConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

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
	private Integer conversionMolecule;

	/**
	 * 商户积分转换（分母）
	 */
	@Schema(description = "商户积分转换（分母）")
	private Integer conversionDenominator;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
}
