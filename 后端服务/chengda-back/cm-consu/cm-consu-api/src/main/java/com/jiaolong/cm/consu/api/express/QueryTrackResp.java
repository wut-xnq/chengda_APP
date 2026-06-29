package com.jiaolong.cm.consu.api.express;

import com.jiaolong.cm.consu.api.dto.LogisticTraceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * desc: 查询物流跟踪结果返回类
 * user: pan
 * date: 2024-10-17 18:31
 */
@Data
@Schema(description = "查询物流跟踪结果返回类")
public class QueryTrackResp {

	/**
	 * 消息体（可忽略）
	 */
	@Schema(description = "消息体（可忽略）")
	private String message;

	/**
	 * 快递单号
	 */
	@Schema(description = "快递单号")
	private String nu;

	/**
	 * 是否签收标记
	 */
	@Schema(description = "是否签收标记")
	private String ischeck;

	/**
	 * 快递公司编码，一律用小写字母
	 */
	@Schema(description = "快递公司编码，一律用小写字母")
	private String com;

	/**
	 * 通讯状态
	 */
	@Schema(description = "通讯状态")
	private String status;

	/**
	 * 轨迹详情数组
	 */
	@Schema(description = "轨迹详情数组")
	private List<LogisticTraceDto> data;

	/**
	 * 快递单当前状态
	 * 包括13个状态（0-在途，1-揽收，2-疑难，3-签收，4-退签，5-派件，6-退回，
	 * 7-转投，10-待清关，11-清关中，12-已清关，13-清关异常，14-拒签）
	 */
	@Schema(description = "快递单当前状态，包括13个状态（0-在途，1-揽收，2-疑难，3-签收，4-退签，5-派件，6-退回，7-转投，10-待清关，11-清关中，12-已清关，13-清关异常，14-拒签）")
	private String state;

	/**
	 * 快递单明细状态标记
	 */
	@Schema(description = "快递单明细状态标记")
	private String condition;

	/**
	 * 查不到轨迹或者其他问题返回码
	 */
	@Schema(description = "查不到轨迹或者其他问题返回码")
	private String returnCode;

	/**
	 * 查不到轨迹或者其他问题返回结果
	 */
	@Schema(description = "查不到轨迹或者其他问题返回结果")
	private boolean result;
}
