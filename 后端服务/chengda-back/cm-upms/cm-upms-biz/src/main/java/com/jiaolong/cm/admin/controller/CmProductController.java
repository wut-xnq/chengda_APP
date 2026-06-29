package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.feign.RemoteProductService;
import com.jiaolong.cm.admin.service.CmProductService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.merch.api.dto.ProductDto;
import com.jiaolong.cm.merch.api.vo.ProductSearchVo;
import com.jiaolong.cm.merch.api.vo.ProductShelveVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 商品管理
 * user: pan
 * date: 2024-09-20 18:32
 */
@RestController
@RequestMapping("/product")
@Tag(description = "product", name = "商品管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmProductController {

	@Autowired
	private RemoteProductService remoteProductService;
	@Autowired
	private CmProductService productService;

	/**
	 * 查询商品列表
	 *
	 * @param vo   查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_prod_page")
	@Operation(summary = "列表", description = "查询商品列表")
	public R<Page<ProductDto>> getProductPage(@ParameterObject ProductSearchVo vo) {
		return remoteProductService.getProductPage(vo);
	}

	/**
	 * 查询商品详情
	 *
	 * @param id   主键
	 * @return
	 */
	@GetMapping("/detail/{id}")
	@HasPermission("merch_prod_detail")
	@Operation(summary = "详情", description = "查询商品详情")
	public R<ProductDto> getProductDetail(@PathVariable("id") Long id) {
		return remoteProductService.getProductDetail(id);
	}

	/**
	 * 上架商品
	 * @param vo
	 * @return
	 */
	@SysLog("管理员强制下架商品")
	@PutMapping("/shelve")
	@HasPermission("admin_prod_shelf")
	@Operation(summary = "强制下架", description = "管理员强制下架商品")
	public R shelveProduct(@RequestBody ProductShelveVo vo){
		return productService.shelveProduct(vo);
	}

	/**
	 * 删除商品
	 * @param ids
	 * @return
	 */
	@SysLog("管理员删除商品")
	@DeleteMapping
	@HasPermission("merch_prod_del")
	@Operation(summary = "删除", description = "删除商品")
	public R delProduct(@RequestBody Long[] ids){
		return remoteProductService.delProduct(ids);
	}
}
