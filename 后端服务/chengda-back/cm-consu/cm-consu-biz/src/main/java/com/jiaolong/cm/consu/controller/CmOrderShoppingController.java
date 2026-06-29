package com.jiaolong.cm.consu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.consu.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.consu.api.param.ShoppingOrderParam;
import com.jiaolong.cm.consu.api.param.ShoppingOrderVo;
import com.jiaolong.cm.consu.service.CmOrderShoppingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 购物订单管理
 * user: pan
 * date: 2024-08-22 10:20
 */
@RestController
@RequestMapping("/order")
@Tag(description = "order", name = "购物订单管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmOrderShoppingController {

	@Autowired
	private CmOrderShoppingService orderService;

	/**
	 * 获取用户订单分页列表
	 *
	 * @param page  分页参数
	 * @param param 传参
	 * @return
	 */
	@GetMapping("/userOrderPage")
	@HasPermission("consu_user_order_list")
	@Operation(summary = "分页列表", description = "获取用户订单分页列表")
	public R<IPage<ShoppingOrderDto>> getUserOrderList(@ParameterObject Page page, @ParameterObject ShoppingOrderParam param) {
		return orderService.getUserOrderPage(page, param);
	}

	/**
	 * 查询用户订单详情
	 *
	 * @param id 订单主键
	 * @return
	 */
	@GetMapping("/details/{id}")
	@HasPermission("consu_user_order_list")
	@Operation(summary = "详情", description = "查询用户订单详情")
	public R<ShoppingOrderDto> getUserOrderList(@PathVariable("id") Long id) {
		return orderService.getDetails(id);
	}

	/**
	 * 用户确认订单到货
	 *
	 * @param id
	 * @return
	 */
	@SysLog("用户确认到货")
	@PutMapping("/receive/{id}")
	@HasPermission("consu_order_receive")
	@Operation(summary = "确认到货", description = "用户确认订单到货")
	public R updateState(@PathVariable("id") Long id) {
		return orderService.receiveOrder(id);
	}

	/**
	 * 用户删除订单
	 *
	 * @param ids 订单主键
	 * @return
	 */
	@SysLog("用户删除订单")
	@DeleteMapping
	@HasPermission("consu_order_del")
	@Operation(summary = "删除", description = "用户删除订单")
	public R deleteUserOrder(@RequestBody Long[] ids) {
		return orderService.deleteUserOrder(ids);
	}

	/**
	 * 新增购物订单
	 *
	 * @return
	 */
	@SysLog("新增购物订单")
	@PostMapping
	@HasPermission("consu_shopping_add")
	@Operation(summary = "新增", description = "新增购物订单")
	public R<ShoppingOrderDto> addShoppingOrder(@RequestBody ShoppingOrderVo vo) {
		return orderService.addShoppingOrder(vo);
	}

	/**
	 * 修改订单收件地址
	 *
	 * @return
	 */
	@SysLog("用户修改订单收件地址")
	@PutMapping("/receive/{orderId}/{userAddressId}")
	@Operation(summary = "修改订单收件地址", description = "修改订单收件地址")
	public R<ShoppingOrderDto> updateRecipientAddress(@PathVariable("orderId") Long orderId, @PathVariable("userAddressId") Long userAddressId) {
		return orderService.updateRecipientAddress(orderId, userAddressId);
	}

	/**
	 * 用户催发货
	 *
	 * @return
	 */
	@SysLog("用户催发货")
	@PutMapping("/promot/{orderId}")
	@Operation(summary = "订单催发货", description = "订单催发货")
	public R promotOrder(@PathVariable("orderId") Long orderId) {
		return orderService.promotOrderDeliver(orderId);
	}
}
