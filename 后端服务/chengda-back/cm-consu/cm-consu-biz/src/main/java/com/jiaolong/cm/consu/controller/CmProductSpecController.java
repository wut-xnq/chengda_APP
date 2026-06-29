package com.jiaolong.cm.consu.controller;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.consu.api.dto.ProductSpecDto;
import com.jiaolong.cm.consu.service.CmProductSpecService;
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
 * desc: 商品规格管理
 * user: pan
 * date: 2024-08-21 10:49
 */
@RestController
@RequestMapping("/spec")
@Tag(description = "spec", name = "商品规格管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmProductSpecController {

	@Autowired
	private CmProductSpecService specService;

	/**
	 * 根据商品主键获取商品规格列表
	 *
	 * @param productId 商品主键
	 * @return
	 */
	@GetMapping("/getListByProductId/{productId}")
	@HasPermission("consu_spec_list")
	@Operation(summary = "规格列表", description = "根据商品主键获取商品规格列表")
	public R<List<ProductSpecDto>> getList(@PathVariable("productId") Long productId) {
		return specService.getListByProductId(productId);
	}
}
