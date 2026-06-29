package com.jiaolong.cm.consu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * desc: 物流追踪数据类
 * user: pan
 * date: 2024-10-17 15:29
 */
@Data
@Schema(description = "物流追踪数据类")
public class LogisticTraceDto {

	/**
	 * 原始格式的时间
	 */
	@Schema(description = "原始格式的时间")
	private String time;

	/**
	 * 物流轨迹节点内容
	 */
	@Schema(description = "物流轨迹节点内容")
	private String context;

	/**
	 * 格式化后时间
	 */
	@Schema(description = "格式化后时间")
	private String ftime;

	/**
	 * 行政区域编码
	 */
	@Schema(description = "行政区域编码")
	private String areaCode;

	/**
	 * 行政区域名称
	 */
	@Schema(description = "行政区域名称")
	private String areaName;

	/**
	 * 签收状态（0-在途，1-揽收，2-疑难，3-签收，4-退签，5-派件，6-退回，7-转投）
	 */
	@Schema(description = "签收状态（0-在途，1-揽收，2-疑难，3-签收，4-退签，5-派件，6-退回，7-转投）")
	private String status;
}
