package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.feign.RemoteOrderService;
import com.jiaolong.cm.admin.service.CmOrderPaymentService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.common.security.annotation.Inner;
import com.jiaolong.cm.merch.api.dto.PaymentOrderDto;
import com.jiaolong.cm.merch.api.vo.PaymentOrderSearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 支付订单管理
 * user: pan
 * date: 2024-09-20 16:15
 */
@RestController
@RequestMapping("/paymentOrder")
@Tag(description = "paymentOrder", name = "支付订单管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmPaymentOrderController {

	@Autowired
	private RemoteOrderService remoteOrderService;
	@Autowired
	private CmOrderPaymentService orderPaymentService;


	/**
	 * 查询支付订单列表
	 * @param vo 查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_purch_page")
	@Operation(summary = "列表", description = "查询支付订单列表")
	public R<Page<PaymentOrderDto>> getPage(@ParameterObject PaymentOrderSearchVo vo){
		return remoteOrderService.getPaymentOrderPage(vo);
	}

	/**
	 * 查询支付订单详情
	 * @param id 主键
	 * @return
	 */
	@GetMapping("/detail/{id}")
	@HasPermission("merch_purch_detail")
	@Operation(summary = "详情", description = "查询支付订单详情")
	public R<PaymentOrderDto> getDetail(@PathVariable("id") Long id){
		return remoteOrderService.getPaymentOrderDetail(id);
	}

	/**
	 * 删除支付订单
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("管理员删除支付订单")
	@DeleteMapping
	@HasPermission("admin_purch_del")
	@Operation(summary = "删除", description = "删除支付订单")
	public R del(@RequestBody Long[] ids){
		return orderPaymentService.deletePaymentOrder(ids);
	}
}
