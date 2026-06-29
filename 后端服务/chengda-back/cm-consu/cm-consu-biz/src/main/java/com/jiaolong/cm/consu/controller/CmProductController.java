package com.jiaolong.cm.consu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.consu.api.dto.ProductDto;
import com.jiaolong.cm.consu.api.param.ProductParam;
import com.jiaolong.cm.consu.service.CmProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc: 商品管理
 * user: pan
 * date: 2024-08-20 15:26
 */
@RestController
@RequestMapping("/product")
@Tag(description = "product", name = "商品管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmProductController {

	@Autowired
	private CmProductService productService;

	/**
	 * 获取商品分页列表
	 *
	 * @param page  分页参数
	 * @param param 查询参数
	 * @return
	 */
	@GetMapping("/page")
	@HasPermission("consu_prod_page")
	@Operation(summary = "分页列表", description = "获取商品分页列表")
	public R<IPage<ProductDto>> page(@ParameterObject Page page, @ParameterObject ProductParam param) {
		return productService.getPage(page, param);
	}

	/**
	 * 获取商品详情
	 *
	 * @param id 商品主键
	 * @return
	 */
	@GetMapping("/{id}")
	@HasPermission("consu_prod_detail")
	@Operation(summary = "详情", description = "获取商品详情")
	public R<ProductDto> details(@PathVariable("id") Long id) {
		return productService.getDetails(id);
	}
}
