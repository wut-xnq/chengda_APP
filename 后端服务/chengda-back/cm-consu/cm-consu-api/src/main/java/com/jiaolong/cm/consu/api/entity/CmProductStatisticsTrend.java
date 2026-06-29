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
 * desc: 商品热度统计
 * user: pan
 * date: 2024-10-29 13:51
 */
@Data
@Schema(description = "商品热度统计")
@EqualsAndHashCode(callSuper = true)
public class CmProductStatisticsTrend extends Model<CmProductStatisticsTrend> {

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 商品浏览量
	 */
	@Schema(description = "商品浏览量")
	private Integer viewAmount;

	/**
	 * 商品购买量
	 */
	@Schema(description = "商品购买量")
	private Integer orderAmount;

	/**
	 * 商品收藏量
	 */
	@Schema(description = "商品收藏量")
	private Integer collectAmount;

	/**
	 * 商品加购物车量
	 */
	@Schema(description = "商品加购物车量")
	private Integer cartAmount;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
}
