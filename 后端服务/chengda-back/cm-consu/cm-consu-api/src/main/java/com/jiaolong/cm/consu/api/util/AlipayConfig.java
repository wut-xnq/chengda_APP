package com.jiaolong.cm.consu.api.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * desc: 支付宝支付工具
 * user: pan
 * date: 2024-10-31 14:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "cm.alipay")
public class AlipayConfig {

	/**
	 * 应用ID
	 */
	private String appId;
	/**
	 * 支付宝公钥
	 */
	private String alipayPublicKey;
	/**
	 * 签名方式
	 */
	private String signType;
	/**
	 * 字符编码
	 */
	private String charset;
	/**
	 * 支付宝网关
	 */
	private String gatewayUrl;
	/**
	 * 异步通知地址
	 */
	private String notifyUrl;
	/**
	 * 同步跳转地址（支付完成后跳转的地址）
	 */
	private String returnUrl;
}
