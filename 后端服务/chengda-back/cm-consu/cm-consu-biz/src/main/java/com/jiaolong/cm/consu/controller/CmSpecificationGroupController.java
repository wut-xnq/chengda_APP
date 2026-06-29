package com.jiaolong.cm.consu.controller;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.entity.CmSpecificationGroup;
import com.jiaolong.cm.consu.service.CmSpecificationGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * desc: 商品规格组管理
 * user: pan
 * date: 2024-08-21 11:00
 */
@RestController
@RequestMapping("/specGroup")
@Tag(description = "specGroup", name = "商品规格组管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmSpecificationGroupController {

	@Autowired
	private CmSpecificationGroupService specGroupService;

	/**
	 * 获取商户规格组列表
	 *
	 * @param merchantId 商户主键
	 * @return
	 */
	@GetMapping("/list")
	@Operation(summary = "规格组列表", description = "获取商户规格组列表")
	public R<List<CmSpecificationGroup>> getSpecGroupList(@RequestParam(name = "merchantId") Long merchantId) {
		return specGroupService.getSpecGroupList(merchantId);
	}
}
