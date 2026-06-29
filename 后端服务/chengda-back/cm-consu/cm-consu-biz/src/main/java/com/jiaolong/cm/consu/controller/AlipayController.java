package com.jiaolong.cm.consu.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.consu.api.util.AlipayConfig;
import com.jiaolong.cm.consu.service.AlipayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * desc: 支付宝支付
 * user: pan
 * date: 2024-10-31 18:30
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/alipay")
@Tag(description = "alipay", name = "支付宝支付管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AlipayController {

	@Resource
	private AlipayConfig alipayConfig;

	@Autowired
	private AlipayService alipayService;

	/**
	 * 发起支付
	 * @param orderNo 商户订单编号
	 */
	@SneakyThrows
	@SysLog("用户发起支付")
	@PostMapping("/pay")
	@Operation(summary = "发起支付", description = "发起支付")
	public R pay(@RequestParam String orderNo){
		return alipayService.payForOrder(orderNo);
	}

	/**
	 * 查询支付结果
	 * @param orderNo 商户订单编号
	 */
	@PostMapping("/payResult")
	@Operation(summary = "支付结果", description = "查询支付结果")
	public R payResult(@RequestParam String orderNo){
		return alipayService.getPayResult(orderNo);
	}

	@SneakyThrows
	@PostMapping("/notify")
	public String notify(HttpServletRequest request, HttpServletResponse response){

		//获取支付宝 POST 过来反馈信息
		Map<String,String> params = new HashMap<>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		boolean signVerified = AlipaySignature.rsaCheckV1(params,
				alipayConfig.getAlipayPublicKey(),
				alipayConfig.getCharset(),
				alipayConfig.getSignType()); //调用SDK验证签名

		if(!signVerified){
			log.info("验证签名失败！");
			return "fail!";
		}

		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		// 付款金额
		String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

		if (trade_status.equals("TRADE_SUCCESS")){
			// 处理关联的业务
		}

		log.info("************* 支付成功(支付宝异步通知) - 时间: {} *************", LocalDateTime.now());
		log.info("* 订单号: {}", out_trade_no);
		log.info("* 支付宝交易号: {}", trade_no);
		log.info("* 实付金额: {}", total_amount);
		log.info("* 交易状态: {}", trade_status);
		log.info("*****************************************************************************");

		return "success";
	}
}
