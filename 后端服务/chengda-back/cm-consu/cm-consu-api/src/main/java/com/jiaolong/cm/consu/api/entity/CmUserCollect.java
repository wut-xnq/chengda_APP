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
 * desc: 用户收藏记录
 * user: pan
 * date: 2024-08-20 12:49
 */
@Data
@Schema(description = "用户收藏记录")
@EqualsAndHashCode(callSuper = true)
public class CmUserCollect extends Model<CmUserCollect> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

	/**
	 * 收藏类型;（1-商品，2-商家，3-用户）
	 */
	@Schema(description = "收藏类型（1-商品，2-商家，3-用户）")
	private Integer collectType;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 收藏的商品主键
	 */
	@Schema(description = "收藏的商品主键")
	private Long collectProductId;

	/**
	 * 收藏/关注的商户主键
	 */
	@Schema(description = "收藏/关注的商户主键")
	private Long collectMerchantId;

	/**
	 * 关注的用户主键
	 */
	@Schema(description = "关注的用户主键")
	private Long collectUserId;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
