package com.jiaolong.cm.consu.controller;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.consu.api.dto.ProductSkuDto;
import com.jiaolong.cm.consu.service.CmProductSkuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * desc: 商品SKU管理
 * user: pan
 * date: 2024-08-21 10:39
 */
@RestController
@RequestMapping("/sku")
@Tag(description = "sku", name = "商品SKU管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmProductSkuController {

	@Autowired
	private CmProductSkuService skuService;


	/**
	 * 根据商品主键获取SKU列表
	 *
	 * @param productId 商品主键
	 * @return
	 */
	@GetMapping("/getListByProductId/{productId}")
	@HasPermission("consu_sku_list")
	@Operation(summary = "SKU列表", description = "根据商品主键获取SKU列表")
	public R<List<ProductSkuDto>> getList(@PathVariable("productId") Long productId) {
		return skuService.getListByProductId(productId);
	}
}
