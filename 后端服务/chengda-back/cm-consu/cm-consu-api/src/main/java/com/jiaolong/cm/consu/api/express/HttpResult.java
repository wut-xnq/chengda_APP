package com.jiaolong.cm.consu.api.express;

import lombok.Data;

/**
 * desc: http结果返回工具类
 * user: pan
 * date: 2024-10-17 18:31
 */
@Data
public class HttpResult {

	/**
	 * HTTP状态码
	 */
	private int status;

	/**
	 * HTTP响应正文
	 */
	private String body;

	/**
	 * 错误信息
	 */
	private String error;

	public HttpResult() {
	}

	public HttpResult(int status, String body, String error) {
		this.status = status;
		this.body = body;
		this.error = error;
	}
}
