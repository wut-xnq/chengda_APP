package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * desc: 商品标签
 * user: pan
 * date: 2024-08-20 10:53
 */
@Data
@Schema(description = "商品标签")
@EqualsAndHashCode(callSuper = true)
public class CmProductLabel extends Model<CmProductLabel> {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 标签主键
	 */
	@Schema(description = "标签主键")
	private Long labelId;
}
