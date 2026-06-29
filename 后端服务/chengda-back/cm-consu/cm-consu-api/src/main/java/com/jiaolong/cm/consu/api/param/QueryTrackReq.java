package com.jiaolong.cm.consu.api.param;

import lombok.Data;

/**
 * desc: 查询物流跟踪查询类
 * user: pan
 * date: 2024-10-17 18:31
 */
@Data
public class QueryTrackReq {

	/**
	 * 分配给企业的公司编号
	 */
	private String customer;

	/**
	 * 签名（用于验证身份，按“param + key + customer”的顺序进行MD5加密，然后将加密后字符串转大写）
	 */
	private String sign;

	/**
	 * 其他参数组合成的json对象
	 */
	private String param;
}
