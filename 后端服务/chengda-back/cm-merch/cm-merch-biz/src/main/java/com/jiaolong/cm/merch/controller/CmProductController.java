package com.jiaolong.cm.merch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.Inner;
import com.jiaolong.cm.merch.api.dto.ProductDto;
import com.jiaolong.cm.merch.api.dto.ProductListDto;
import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.vo.ProductSearchVo;
import com.jiaolong.cm.merch.api.vo.ProductShelveVo;
import com.jiaolong.cm.merch.api.vo.ProductVo;
import com.jiaolong.cm.merch.service.CmProductService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 商品管理
 * user: pan
 * date: 2024-09-05 18:25
 */
@RestController
@RequestMapping("/product")
@Tag(description = "product", name = "商品管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmProductController {

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
	public R<IPage<ProductDto>> getProductPage(@ParameterObject ProductSearchVo vo) {
		Long current = vo.getCurrent();
		Long size = vo.getSize();
		Page page = new Page<>(current, size);
		return productService.getPage(page, vo);
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
		return productService.getDetail(id);
	}

	/**
	 * 新增商品
	 * @param vo
	 * @return
	 */
	@SysLog("商户新增商品")
	@PostMapping
	@HasPermission("merch_prod_add")
	@Operation(summary = "新增", description = "新增商品")
	public R addProduct(@RequestBody ProductVo vo){
		return productService.addProduct(vo);
	}

	/**
	 * 编辑商品
	 * @param vo
	 * @return
	 */
	@SysLog("商户编辑商品")
	@PutMapping
	@HasPermission("merch_prod_edit")
	@Operation(summary = "编辑", description = "编辑商品")
	public R editProduct(@RequestBody ProductVo vo){
		return productService.editProduct(vo);
	}

	/**
	 * 上架商品
	 * @param vo
	 * @return
	 */
	@SysLog("商品上下架")
	@PutMapping("/shelve")
	@HasPermission("merch_prod_shelf")
	@Operation(summary = "上下架", description = "商品上下架")
	public R shelveProduct(@RequestBody ProductShelveVo vo){
		return productService.shelveProduct(vo);
	}

	/**
	 * 删除商品
	 * @param ids
	 * @return
	 */
	@SysLog("商户删除商品")
	@DeleteMapping
	@HasPermission("merch_prod_del")
	@Operation(summary = "删除", description = "删除商品")
	public R delProduct(@RequestBody Long[] ids){
		return productService.deleteProduct(ids);
	}

	/**
	 * 查询待绑定SKU商品列表
	 *
	 * @param merchantId   商户主键
	 * @return
	 */
	@GetMapping("/getList")
	@HasPermission("merch_prod_page")
	@Operation(summary = "商户商品列表", description = "查询待绑定SKU商品列表")
	public R<List<ProductListDto>> getProductPage(@RequestParam Long merchantId) {
		return productService.getMerchantProductList(merchantId);
	}
}
