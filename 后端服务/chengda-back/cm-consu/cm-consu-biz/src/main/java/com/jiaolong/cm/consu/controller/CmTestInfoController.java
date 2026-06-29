package com.jiaolong.cm.consu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.param.TestInfoParam;
import com.jiaolong.cm.consu.service.CmTestInfoService;
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

import java.util.List;

/**
 * desc: 质检管理
 * user: pan
 * date: 2024-08-21 10:44
 */
@RestController
@RequestMapping("/exam")
@Tag(description = "exam", name = "质检管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmTestInfoController {

	@Autowired
	private CmTestInfoService testInfoService;

	/**
	 * 获取分页列表
	 *
	 * @param page  分页参数
	 * @param param 传参
	 * @return
	 */
	@GetMapping("/page")
	@HasPermission("consu_exam_page")
	@Operation(summary = "分页列表", description = "获取质检分页列表")
	public R<IPage<TestInfoDto>> getPage(@ParameterObject Page page, @ParameterObject TestInfoParam param) {
		return testInfoService.getPage(page, param);
	}

	/**
	 * 根据主键获取质检详情
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping("/{id}")
	@HasPermission("consu_exam_detail")
	@Operation(summary = "详情", description = "根据主键获取质检详情")
	public R<TestInfoDto> getDetails(@PathVariable("id") Long id) {
		return testInfoService.getDetails(id);
	}

	/**
	 * 根据商品主键获取商品最新质检结果
	 *
	 * @param productId 商品主键
	 * @return
	 */
	@GetMapping("/listByProduct/{productId}")
	@HasPermission("consu_exam_gbdetail")
	@Operation(summary = "商品最新质检", description = "根据商品主键获取商品最新质检结果")
	public R<TestInfoDto> listByProduct(@PathVariable("productId") Long productId) {
		return testInfoService.getProductExamination(productId);
	}

	/**
	 * 根据商户主键获取商户质检结果列表
	 *
	 * @param merchantId 商户主键
	 * @return
	 */
	@GetMapping("/listByMerchant/{merchantId}")
	@HasPermission("consu_goods_gblist")
	@Operation(summary = "商户商品质检列表", description = "根据商户主键获取商户质检结果列表")
	public R<List<TestInfoDto>> listByMerchant(@PathVariable("merchantId") Long merchantId) {
		return testInfoService.getMerchantExamList(merchantId);
	}
}
