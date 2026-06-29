package com.jiaolong.cm.merch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.merch.api.dto.ProductSkuDto;
import com.jiaolong.cm.merch.api.vo.ProductSkuSearchVo;
import com.jiaolong.cm.merch.api.vo.ProductSkuVo;
import com.jiaolong.cm.merch.service.CmProductSkuService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 商品SKU管理
 * user: pan
 * date: 2024-09-01 16:01
 */
@RestController
@RequestMapping("/sku")
@Tag(description = "sku", name = "商品SKU管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmProductSkuController {

	@Autowired
	private CmProductSkuService productSkuService;

	/**
	 * 查询商品SKU列表
	 *
	 * @param vo   查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_sku_page")
	@Operation(summary = "列表", description = "查询商品SKU列表")
	public R<IPage<ProductSkuDto>> getSkuPage(@ParameterObject ProductSkuSearchVo vo) {
		Long current = vo.getCurrent();
		Long size = vo.getSize();
		Page page = new Page<>(current, size);
		return productSkuService.getPage(page, vo);
	}

	/**
	 * 查询商品SKU详情
	 *
	 * @param id	主键
	 * @return
	 */
	@GetMapping("/detail/{id}")
	@HasPermission("merch_sku_detail")
	@Operation(summary = "详情", description = "查询商品SKU详情")
	public R<ProductSkuDto> getSkuDetail(@PathVariable("id") Long id) {
		return productSkuService.getDetail(id);
	}

	/**
	 * 商户新增商品SKU
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户新增商品SKU")
	@PostMapping
	@HasPermission("merch_sku_add")
	@Operation(summary = "新增", description = "商户新增商品SKU组")
	public R addSku(@RequestBody ProductSkuVo vo) {
		return productSkuService.addSku(vo);
	}

	/**
	 * 商户编辑商品SKU
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户编辑商品SKU")
	@PutMapping
	@HasPermission("merch_sku_edit")
	@Operation(summary = "编辑", description = "商户编辑商品SKU组")
	public R editSku(@RequestBody ProductSkuVo vo) {
		return productSkuService.editSku(vo);
	}

	/**
	 * 商户删除商品SKU
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("商户删除商品SKU")
	@DeleteMapping
	@HasPermission("merch_sku_del")
	@Operation(summary = "删除", description = "商户删除商品SKU")
	public R del(@RequestBody Long[] ids) {
		return productSkuService.deleteSku(ids);
	}

}
