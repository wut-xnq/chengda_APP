package com.jiaolong.cm.merch.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 地区编码
 * user: pan
 * date: 2024-09-02 16:37
 */
@Data
@Schema(description = "地区编码")
public class CityCode implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 城市编码
	 */
	@Schema(description = "城市编码")
	private Integer cityId;

	/**
	 * 上级节点编码
	 */
	@Schema(description = "上级节点编码")
	private Integer cityPid;

	/**
	 * 城市名称
	 */
	@Schema(description = "城市名称")
	private String cityName;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 行政级别;（1-省份，2-市级，3-区县）
	 */
	@Schema(description = "行政级别（1-省份，2-市级，3-区县）")
	private Integer type;
}
