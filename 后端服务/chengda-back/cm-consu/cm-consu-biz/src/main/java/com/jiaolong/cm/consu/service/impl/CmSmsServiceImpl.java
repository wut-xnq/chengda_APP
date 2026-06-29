package com.jiaolong.cm.consu.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.consu.api.util.YunxinSmsResolver;
import com.jiaolong.cm.consu.service.CmSmsService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.core.util.RandomStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * desc: 网易云信接口服务实现
 * user: pan
 * date: 2024-10-14 10:26
 */
@Slf4j
@Service
public class CmSmsServiceImpl implements CmSmsService {

	private static String appSecret = "2d675ad9b39d";
	private static String contentType = "application/x-www-form-urlencoded;charset=utf-8";
	private static String sendCodeUrl = "https://api.netease.im/sms/sendcode.action";
//	private static String sendCodeUrl = "https://sms.yunxinapi.com/sms/sendcode.action";
	private static String verifyCodeUrl = "https://api.netease.im/sms/verifycode.action";
//	private static String verifyCodeUrl = "https://sms.yunxinapi.com/sms/verifycode.action";
	private static String promotOrderUrl = "https://api.netease.im/sms/sendtemplate.action";

	private static String refreshUrl = "https://api.netease.im/nimserver/user/refreshToken.action";
	private static String registUrl = "https://api.netease.im/nimserver/user/create.action";

	@Value("${cm.yunxin.appkey}")
	private String appKey;
	@Value("${cm.yunxin.codeid}")
	private int codeTemplateId;
	@Value("${cm.yunxin.noticeid}")
	private int noticeTemplateId;

	@Override
	public R getMobileCode(String phone) {
		String curTime = String.valueOf(System.currentTimeMillis() / 1000);
		String nonce = RandomStringUtil.random18();
		String checkSum = YunxinSmsResolver.getCheckSum(appSecret, nonce, curTime);

		String body = HttpRequest.post(sendCodeUrl)
				.header(HttpHeaders.CONTENT_TYPE, contentType)
				.header("AppKey", appKey)
				.header("CurTime", curTime)
				.header("Nonce", nonce)
				.header("CheckSum", checkSum)
				.form("mobile", phone)
				.form("templateid", codeTemplateId)
				.execute().body();

		log.info(body);

		if (StringUtils.isNotEmpty(body)) {
			JSONObject jsonObject = JSONObject.parseObject(body);
			if (jsonObject.getInteger("code") == 200) {
				return R.ok(BusinessErrorMessage.GET_MOBILE_CODE_OK);
			}
		}
		return R.failed(BusinessErrorMessage.YUNXIN_IM_SERVICE_ERROR);
	}

	@Override
	public R verifyMobileCode(String phone, String code) {
		/* 校验方案2：直接申请由网易云信端校验 */
		String curTime = String.valueOf(System.currentTimeMillis() / 1000);
		String nonce = RandomStringUtil.random18();
		String checkSum = YunxinSmsResolver.getCheckSum(appSecret, nonce, curTime);

		String body = HttpRequest.post(verifyCodeUrl)
				.header(HttpHeaders.CONTENT_TYPE, contentType)
				.header("AppKey", appKey)
				.header("CurTime", curTime)
				.header("Nonce", nonce)
				.header("CheckSum", checkSum)
				.form("mobile", phone)
				.form("code", code)
				.execute().body();

		if (StringUtils.isNotEmpty(body)) {
			JSONObject jsonObject = JSONObject.parseObject(body);
			if (jsonObject.getInteger("code") == 200) {
				return R.ok(BusinessErrorMessage.VERIFY_MOBILE_CODE_OK);
			}
			return R.failed(BusinessErrorMessage.VERIFY_MOBILE_CODE_ERROR);
		}
		return R.failed(BusinessErrorMessage.YUNXIN_IM_SERVICE_ERROR);
	}

	@Override
	public R refreshUserToken(String phone) {
		/* 刷新当前登录网易云信平台的 Token */
		String curTime = String.valueOf(System.currentTimeMillis() / 1000);
		String nonce = RandomStringUtil.random18();
		String checkSum = YunxinSmsResolver.getCheckSum(appSecret, nonce, curTime);

		String body = HttpRequest.post(refreshUrl)
				.header(HttpHeaders.CONTENT_TYPE, contentType)
				.header("AppKey", appKey)
				.header("CurTime", curTime)
				.header("Nonce", nonce)
				.header("CheckSum", checkSum)
				.form("accid", phone)
				.execute().body();

		log.info(body);

		if (StringUtils.isNotEmpty(body)) {
			JSONObject jsonObject = JSONObject.parseObject(body);
			int code = jsonObject.getInteger("code");
			if (code == 200) {
				return R.ok(jsonObject.get("info"));
			} else if (code == 414) {
				/* 当前手机号对应的手机号，未在网易平台注册，就要注册 */
				return registerUser(phone);
			}
			return R.failed(BusinessErrorMessage.REFRESH_USER_TOKEN_ERROR);
		}
		return R.failed(BusinessErrorMessage.YUNXIN_IM_SERVICE_ERROR);
	}

	@Override
	public R promotOrderDeliver(String orderNo, String phone) {
		String curTime = String.valueOf(System.currentTimeMillis() / 1000);
		String nonce = RandomStringUtil.random18();
		String checkSum = YunxinSmsResolver.getCheckSum(appSecret, nonce, curTime);
		// 接受通知的手机号码，JSONArray 格式，传参时需要转为 JSONString
		JSONArray mobiles = new JSONArray();
		mobiles.add(phone);
		// 传参，JSONArray 格式，传参时需要转为 JSONString
		JSONArray params = new JSONArray();
		params.add(orderNo);

		String body = HttpRequest.post(promotOrderUrl)
				.header(HttpHeaders.CONTENT_TYPE, contentType)
				.header("AppKey", appKey)
				.header("CurTime", curTime)
				.header("Nonce", nonce)
				.header("CheckSum", checkSum)
				.form("templateid", noticeTemplateId)
				.form("mobiles", mobiles.toJSONString())
				.form("params", params.toJSONString())
				.execute().body();

		log.info(body);

		if (StringUtils.isNotEmpty(body)) {
			JSONObject jsonObject = JSONObject.parseObject(body);
			if (jsonObject.getInteger("code") == 200) {
				return R.ok(BusinessErrorMessage.PROMOT_MERCHANT_DELIVER);
			}
		}
		return R.failed(BusinessErrorMessage.YUNXIN_IM_SERVICE_ERROR);
	}

	/**
	 * 注册网易云信平台
	 *
	 * @param phone
	 * @return
	 */
	private R registerUser(String phone) {
		/* 注册网易云信平台账号 */
		String curTime = String.valueOf(System.currentTimeMillis() / 1000);
		String nonce = RandomStringUtil.random18();
		String checkSum = YunxinSmsResolver.getCheckSum(appSecret, nonce, curTime);

		String body = HttpRequest.post(registUrl)
				.header(HttpHeaders.CONTENT_TYPE, contentType)
				.header("AppKey", appKey)
				.header("CurTime", curTime)
				.header("Nonce", nonce)
				.header("CheckSum", checkSum)
				.form("accid", phone)
				.execute().body();

		log.info(body);

		if (StringUtils.isNotEmpty(body)) {
			JSONObject jsonObject = JSONObject.parseObject(body);
			int code = jsonObject.getInteger("code");
			if (code == 200) {
				return R.ok(jsonObject.get("info"));
			}
			return R.failed(BusinessErrorMessage.REGIST_USER_ACCOUNT_ERROR);
		}
		return R.failed(BusinessErrorMessage.YUNXIN_IM_SERVICE_ERROR);
	}
}
