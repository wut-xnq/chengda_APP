package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * desc: 商户物流方案
 * user: pan
 * date: 2024-08-20 11:24
 */
@Data
@Schema(description = "商户物流方案")
@EqualsAndHashCode(callSuper = true)
public class CmMerchantLogistics extends Model<CmMerchantLogistics> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 方案名称
	 */
	@Schema(description = "方案名称")
	private String logisticName;

	/**
	 * 物流覆盖区域
	 */
	@Schema(description = "物流覆盖区域")
	private String region;

	/**
	 * 计费方式;（1-按重量，2-按数量）
	 */
	@Schema(description = "计费方式（1-按重量，2-按数量）")
	private String chargeType;

	/**
	 * 首重;（多少KG/件的计费金额）
	 */
	@Schema(description = "首重（多少KG/多少件的计费金额）")
	private Integer weight;

	/**
	 * 首重价格
	 */
	@Schema(description = "首重价格")
	private BigDecimal price;

	/**
	 * 续重;（超出部分每KG/件计费金额）
	 */
	@Schema(description = "续重（超出部分每KG/每件计费金额）")
	private Integer secondWeight;

	/**
	 * 续重价格
	 */
	@Schema(description = "续重价格")
	private BigDecimal secondPrice;

	/**
	 * 是否默认;（0-否，1-是）
	 */
	@Schema(description = "是否默认（0-否，1-是）")
	private String ifDefault;

	/**
	 * 方案状态;（0-禁用，1-启用）
	 */
	@Schema(description = "方案状态（0-禁用，1-启用）")
	private String state;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
