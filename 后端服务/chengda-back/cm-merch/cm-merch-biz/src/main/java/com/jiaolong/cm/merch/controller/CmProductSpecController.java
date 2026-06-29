package com.jiaolong.cm.merch.controller;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.merch.api.dto.ProductSpecDto;
import com.jiaolong.cm.merch.api.vo.ProductSpecVo;
import com.jiaolong.cm.merch.service.CmProductSpecService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 商品规格管理
 * user: pan
 * date: 2024-09-05 18:25
 */
@RestController
@RequestMapping("/productSpec")
@Tag(description = "productSpec", name = "商品规格管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmProductSpecController {

	@Autowired
	private CmProductSpecService cmProductSpecService;

	/**
	 * 查询指定商品绑定的规格列表
	 * @param productId 商品主键
	 * @return
	 */
	@GetMapping("/{productId}")
	@HasPermission("merch_prod_spec_query")
	@Operation(summary = "查询商品绑定的规格列表", description = "查询指定商品绑定的规格列表")
	public R<List<ProductSpecDto>> addProductSpecList(@PathVariable Long productId){
		return cmProductSpecService.getProductSpecListByProductId(productId);
	}
}
