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
 * desc: 平台下架商品记录
 * user: pan
 * date: 2024-11-20 10:56
 */
@Data
@Schema(description = "平台下架商品记录")
public class CmDelistingProduct implements Serializable {

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
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 操作人主键
	 */
	@Schema(description = "操作人主键")
	private Long operator;

	/**
	 * 下架原因
	 */
	@Schema(description = "下架原因")
	private String reason;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;
}
