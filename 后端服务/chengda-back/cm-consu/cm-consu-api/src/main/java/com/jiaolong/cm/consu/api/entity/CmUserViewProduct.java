package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * desc: 用户浏览商品记录
 * user: pan
 * date: 2024-11-09 11:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工申请企业认证记录")
@EqualsAndHashCode(callSuper = true)
public class CmUserViewProduct extends Model<CmUserViewProduct> {

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

	/**
	 * 浏览的商品主键
	 */
	@Schema(description = "浏览的商品主键")
	private Long productId;

	/**
	 * 浏览时间
	 */
	@Schema(description = "浏览时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime viewTime;
}
