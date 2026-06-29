package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.feign.RemoteOrderService;
import com.jiaolong.cm.admin.service.CmOrderShoppingService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.common.security.annotation.Inner;
import com.jiaolong.cm.merch.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.merch.api.entity.CmOrderProducts;
import com.jiaolong.cm.merch.api.vo.ShoppingOrderSearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 购物订单管理
 * user: pan
 * date: 2024-09-20 16:15
 */
@RestController
@RequestMapping("/shoppingOrder")
@Tag(description = "shoppingOrder", name = "购物订单管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmShoppingOrderController {

	@Autowired
	private RemoteOrderService remoteOrderService;
	@Autowired
	private CmOrderShoppingService orderShoppingService;


	/**
	 * 查询购物订单列表
	 *
	 * @param vo   查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_shopping_page")
	@Operation(summary = "列表", description = "查询购物订单列表")
	public R<Page<ShoppingOrderDto>> getPage(@ParameterObject ShoppingOrderSearchVo vo) {
		return remoteOrderService.getShoppingOrderPage(vo);
	}

	/**
	 * 查询购物订单详情
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping("/detail/{id}")
	@HasPermission("merch_shopping_detail")
	@Operation(summary = "详情", description = "查询购物订单详情")
	public R<ShoppingOrderDto> getDetail(@PathVariable("id") Long id) {
		return remoteOrderService.getShoppingOrderDetail(id);
	}

	/**
	 * 查询购物订单商品列表
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping("/productList/{id}")
	@HasPermission("merch_shopping_goods")
	@Operation(summary = "商品列表", description = "查询购物订单商品列表")
	public R<List<CmOrderProducts>> getProductList(@PathVariable("id") Long id) {
		return remoteOrderService.getProductList(id);
	}

	/**
	 * 删除购物订单
	 *
	 * @param ids
	 * @return
	 */
	@SysLog("管理员删除购物订单")
	@DeleteMapping
	@HasPermission("admin_order_del")
	@Operation(summary = "删除", description = "删除购物订单")
	public R delOrderShopping(@RequestBody Long[] ids) {
		return orderShoppingService.deleteShoppingOrder(ids);
	}
}
