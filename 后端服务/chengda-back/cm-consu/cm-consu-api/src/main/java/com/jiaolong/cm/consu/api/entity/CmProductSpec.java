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
 * desc: 商品规格
 * user: pan
 * date: 2024-08-20 11:20
 */
@Data
@Schema(description = "商品规格")
@EqualsAndHashCode(callSuper = true)
public class CmProductSpec extends Model<CmProductSpec> {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品主键
	 */
	@Schema(description = "商品主键")
	private Long productId;

	/**
	 * 规格主键
	 */
	@Schema(description = "规格主键")
	private Long specId;
}
