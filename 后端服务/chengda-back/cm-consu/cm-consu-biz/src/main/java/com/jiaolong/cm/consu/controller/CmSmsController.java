package com.jiaolong.cm.consu.controller;

import com.jiaolong.cm.common.security.annotation.Inner;
import com.jiaolong.cm.consu.service.CmSmsService;
import com.jiaolong.cm.common.core.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 网易云信接口服务管理
 * user: pan
 * date: 2024-10-14 10:24
 */
@RestController
@RequestMapping("/sms")
@Tag(description = "sms", name = "网易云信接口服务管理")
public class CmSmsController {

	@Autowired
	private CmSmsService smsService;

	/**
	 * 获取手机验证码
	 *
	 * @param phone 手机号
	 * @return
	 */
	@Inner(false)
	@PostMapping("/getMobileCode")
	@Operation(summary = "发送验证码", description = "获取手机验证码")
	public R getMobileCode(@RequestParam("phone") String phone) {
		return smsService.getMobileCode(phone);
	}

	/**
	 * 校验手机验证码
	 *
	 * @param phone 手机号
	 * @return
	 */
	@Inner(false)
	@PostMapping("/verifyCode")
	@Operation(summary = "校验手机验证码", description = "校验手机验证码")
	public R verifyMobileCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
		return smsService.verifyMobileCode(phone, code);
	}

	/**
	 * 刷新用户在网易云信上的Token
	 *
	 * @param phone 手机号
	 * @return
	 */
	@Inner(false)
	@PostMapping("/refreshToken")
	@Operation(summary = "刷新用户通信Token", description = "刷新用户在网易云信上的Token")
	public R refreshUserToken(@RequestParam("phone") String phone) {
		return smsService.refreshUserToken(phone);
	}
}
