package com.jiaolong.cm.consu.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * desc: 查询物流跟踪参数类
 * user: pan
 * date: 2024-10-17 15:03
 */
@Data
public class LogisticTraceParam {

	/**
	 * 物流公司编码
	 */
	@Schema(description = "物流公司编码")
	private String com;

	/**
	 * 物流单号
	 */
	@Schema(description = "物流单号")
	private String num;

	/**
	 * 收件人或寄件人的手机号或固话
	 */
	@Schema(description = "收件人或寄件人的手机号或固话")
	private String phone = "";

	/**
	 * 出发地城市，省-市-区
	 */
	@Schema(description = "出发地城市，省-市-区")
	private String from = "";

	/**
	 * 目的地城市，省-市-区
	 */
	@Schema(description = "目的地城市，省-市-区")
	private String to = "";

	/**
	 * 添加此字段表示开通行政区域解析功能。0：关闭（默认），1：开通行政区域解析功能，2：开通行政解析功能并且返回出发、目的及当前城市信息
	 */
	@Schema(description = "开通行政区域解析功能（0-关闭（默认），1-开通行政区域解析功能，2-通行政解析功能并且返回出发、目的及当前城市信息）")
	private String resultv2 = "0";

	/**
	 * 返回数据格式（0-json（默认），1-xml，2-html，3-text）
	 */
	@Schema(description = "返回数据格式（0-json（默认），1-xml，2-html，3-text）")
	private String show = "0";

	/**
	 * 返回结果排序方式（desc-降序（默认），asc-升序）
	 */
	@Schema(description = "返回结果排序方式（desc-降序（默认），asc-升序）")
	private String order = "desc";
}
