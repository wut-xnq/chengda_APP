package com.jiaolong.cm.consu.api.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc: 支付宝支付处理类
 * user: pan
 * date: 2024-10-31 18:14
 */
@Configuration
public class AlipayResolver {

	@Resource
	private AlipayConfig alipayConfig;

	/**
	 * 诚博达商户私钥
	 */
//	private static String MERCHANT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQibiANJoM8hvVHcFtJBjq8uUPoQfbRUP4fPnIL01pN6ENAb/QJ0tMs4nalVn8P1dZivr8jsLxxp0INwwaf2fZN208gCh90g7vXonbPerK2MZ/pjghgtVLIi6EKeRdf7MjfuhMc7C6Yu2Q9YvkHJ4PdT4GsXwYy2m2rjjoADXpv5C5sVLEMJ4JU9XkyFvFguq9YvVxBLGuqlc38BennsAfO+wtiPzQls0rTDXhak+f4Mgq69aH0yHzJDNka9qftlW/XP6WQdZP+dt2dPr3vB6mrAYVV/GM8p6anef+p4sFfFmEdErtvmm/MdjW44jM7Rzl5TXH6aLlRNkBUOKzBLpFAgMBAAECggEBAIWjgL6xZBpCJK/IwlYaQRvDFz17I1t+0JjJXN3VtpppP/RZwLNCEaJKglRjMP9ExYvaL7Qif2ayv/CfjQZW9Hrnf42n5UhoVzzFSWs39YY2JlpSIqYBZRsB8MZsQWSdtNFF/pzpfubmn0cfcYepWg9q1L21jssSEJ1CuwTvZOnIvCMyAPfig8CMmwWNXxFOv2VbyX4Yhs0j+4+0bILC6e2amxePOMFQMuWU7qn+LrO2YQA+93kfst0XAiWWvbN8oxz5n9lEG2I49b1XSiNd9/mr4HBIsK/KapbkR20GdiPxkZHXSb/uybVQTppArTo6mBjNhzQptQpB5jxl+zs+ZIUCgYEAzRlA0rjLXAuuZvX7xItWT0RZ8T77ENd+zhDX2+dfIqPWwsQM5QtyEuGYFnIkhZGAXxd7nS9Uoqtu0pFqwNsc9/F/2/J9hJekAggUkpdhUkcO2pO+ECr8IQtCg5V5fKJWkRNhJk/yGsr3V+hIXsTq7y4Lw+U3jswO/Vay9qxl4PMCgYEAtGjNh2ikhE1zXPfqIcaW6p6EQOsolnysEkpEFNX4cRcUdzgNuClIjYgXbeFp9XY/GW6gP4fF10hxtHA5s/IfsMNuAwwecHVGK98/6s4ST/9sy70MHCFbcr5HIhOR0s7QGSVRGMgpbIOp5gY0WjPYceZqmXonbu94S3PhPKAGBecCgYB688CgkpeWvon2v1AHK2C6dEdXEGKCXi1uazp8Yd/ubDm62LzCMnJYT1VVX2f1ipxiZTl9qXobapotMW/F3ZUoLmZv/JayfhWcAiwTFR0Krf6BmkA+gQZU6OMe7uS9irRKlEuRJCwHU9WjpWR9K5+LcxcqgKclu77gsLDon8ETcQKBgGxFFUu1dj5cSOa7dgzNJNcrSM25E0O1EINthIxImXAtXL/rqPgj9oHR8dWk2TVRwK6foJZgAlCgDnUugMLIhVJNBuHXTRFKoFFGCxL8lyrhbMac7v6W2k3zebcurW4pZeQhbBkGTy3rmnQMtzCHOteoY8M19yJxnu0RX1RF6TdzAoGAIj5t2jAYT3ATcTAsC9aaZRHOSbQRWHn9tpAd2u3KIX+POBVfTvkC5JtbTJ4p7O9aPDMSsmo/UouxyFenb3s/Y7Z4JAiIsE4jwN54TcfAJ6hSnWOrTsUpFzrNXJkDybpRK0DduLhy5hjgZXtoeTu0vudF7JjkmYIvi3txpoASUFs=";
	private static String MERCHANT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUJYR+nA7Ap4zLpWnjiRdJjwgi0AYo5V1MSz/kr9+kpe3rhv8ZCwyaHCXsTmGJ/wWX9ef/lMyXOpQ2CPod3yppw9cRJniSa0tcPt+kCd1pBr5vr4ZQD3DNbtCdmpNSGbpv06S38ZGtgwy0anJa8/QcswlxCJmlKX36ib3ZrwPet85UNHMeyfFUswchCWptmbU24t5GK3iT8YGiIzjJNZBn7/yvTIFQgJD6JSK86n26g4hBVeXAXL8rChfK3cBAjBQaVkkqxd/+AagJhivQWv16R+hwUaV38JxBJGkcl8GSd42ICHeFt6MhcEyMIgnpiknWMPtkS/MaV64U65b1ZMn3AgMBAAECggEAKpsfn8oB9CY7t9W6vlWO/PjFvj1ViC1ux6Jcnch26UNPSY1pkCo9VZUC0xM3uhg945Y1QqvoqxWNynyKckzcRc6s66HEy1dQNRdf+i1cH/hwjG5OknfeWTaliK4L8HC1zbNwi/ZNjzlBTiHPAZlne1mCPZ/bkER+lq9GinRrCIIiTKvfBs3O1YX4+3JNBxG+2cgq2XWFMmnPfzgQO5FnAD6EGvjkKBkpFQMZm6lM2Hvy0nz2sSwKJrpPRlz8jP1Ep49JU8OPXSJuYxW7wfDfDgt3IRmbfVvWxFhigFOwIUlI3nbUci17xm/JTSnz5l54EJ6Yv5U4BtPjTnp4qt2NAQKBgQDp9mHc/lYpXo+vgZe55NGv3fGbpHP6H6jDefm9xN9G1nqEVn6Nc/oxPRkLXV9Quh4vGi0WyHSVc+WRzBienzE31bAzFziu42YY7hkWPOot0IAf13fOS3da3n089K3rwSCkmVx1TlBLCL6bRxSxRL/hlleNLbG/vb6FCwLymkRXlQKBgQCiGdO7QHDhIAPZ/nnVIjlhVV65sxje1d+JAj8Aniz2UDBN11L2dK7/DpfYXjNDlA1l/vVlmEAF0geJ5dRN09tF0aJhOg8a7Zk6tojqqQA6KdrWVbCYtYBe8CNilZMejTD1bnVsJR9wmZ+onlNO3Cyn75GWqLi9MdecL1FKv6kIWwKBgQCU+DVN4n/4LWat6IU0bzb4lQgfAzJ5g6kQdl0HcRB96VjvZKeF59/ZdD67FsP3AdrwQOmOxSo+cyIsmFjgvxo2pBBiJ5oG+XHr08cTesvdShIatDZrm2+u41W49066eykOxTuy0Z+cdz/uXO8XBGBtAi5TDVJNdUKXMfC5inKTlQKBgFfOfa603XBVE0EGY6BcBZD/2Wvl49bxK0DgApinFdvm3AOOW9Y8pn5qvT3q6QH2NLvHO85ohmWgu4yq/jGaTPCo+biI5p3DjnkLX2cdXI4lFfF8QUi3KG5EziYct7CY8qg/yUNnaHxp+t4vxRi2k0n0r3xYsJlrYkusJfDDmSB5AoGAc4kRdvxzomrJ+01PlPrrXWfmm+JdOA5dYZagK7N0/NyWZimWsWnqnnuUh7ZQqPPKjjyz+hYFvnvE5Yy/FElFj9viYHfq0KmeU9f976B+eDuKa2ZanBYqMZFSMUt1US+X0m/3H5pXQY+St0IEJE1LlYsPVbz6ECLMVNJmW0jxP6w=";
	private static String MERCHANT_NUM = "2021005104629921";

	/**
	 * 创建支付客户端
	 * @return
	 */
	@Bean
	public AlipayClient alipayClient(){
		AlipayClient client = new DefaultAlipayClient(
			alipayConfig.getGatewayUrl(),
			alipayConfig.getAppId(),
			MERCHANT_PRIVATE_KEY,
			"json",
			alipayConfig.getCharset(),
			alipayConfig.getAlipayPublicKey(),
			alipayConfig.getSignType()
		);
		return client;
	}

	/**
	 * 创建支付请求对象
	 * @return
	 */
	@Bean
	public AlipayTradeAppPayRequest alipayTradeAppPayRequest(){
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		request.setNotifyUrl(alipayConfig.getNotifyUrl());
		request.setReturnUrl(alipayConfig.getReturnUrl());
		return request;
	}

	/**
	 * 创建支付请求对象
	 * @return
	 */
	@Bean
	public AlipayTradeQueryRequest alipayTradeQueryRequest(){
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setNotifyUrl(alipayConfig.getNotifyUrl());
		request.setReturnUrl(alipayConfig.getReturnUrl());
		return request;
	}
}
