package com.jiaolong.cm.merch.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * desc: 订单物流信息
 * user: pan
 * date: 2024-08-20 10:45
 */
@Data
@Schema(description = "订单物流信息")
@EqualsAndHashCode(callSuper = true)
public class CmOrderLogistics extends Model<CmOrderLogistics> {

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
	private LocalDateTime createTime;

	/**
	 * 物流公司名称
	 */
	@Schema(description = "物流公司名称")
	private String logisticsName;

	/**
	 * 物流公司编码
	 */
	@Schema(description = "物流公司编码")
	private String logisticsCode;

	/**
	 * 物流单编号
	 */
	@Schema(description = "物流单编号")
	private String logisticsNo;

	/**
	 * 关联购物订单编号
	 */
	@Schema(description = "关联购物订单编号")
	private String shoppingOrderNo;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
