package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * desc: 订单商品
 * user: pan
 * date: 2024-08-20 10:42
 */
@Data
@Schema(description = "订单商品")
@EqualsAndHashCode(callSuper = true)
public class CmOrderProducts extends Model<CmOrderProducts> {

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
	 * 关联购物订单主键
	 */
	@Schema(description = "关联购物订单主键")
	private Long orderId;

	/**
	 * 关联SKU主键
	 */
	@Schema(description = "关联SKU主键")
	private Long skuId;

	/**
	 * 商品数量
	 */
	@Schema(description = "商品数量")
	private Integer amount;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * 所选规格名称
	 */
	@Schema(description = "所选规格名称")
	private String specName;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
