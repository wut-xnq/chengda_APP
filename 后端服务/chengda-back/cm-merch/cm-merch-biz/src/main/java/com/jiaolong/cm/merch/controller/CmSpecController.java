package com.jiaolong.cm.merch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.common.security.annotation.Inner;
import com.jiaolong.cm.merch.api.dto.SpecDto;
import com.jiaolong.cm.merch.api.vo.SpecSearchVo;
import com.jiaolong.cm.merch.api.vo.SpecVo;
import com.jiaolong.cm.merch.service.CmSpecService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 商户商品规格管理
 * user: pan
 * date: 2024-09-01 13:05
 */
@RestController
@RequestMapping("/spec")
@Tag(description = "spec", name = "商户商品规格管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmSpecController {

	@Autowired
	private CmSpecService specService;

	/**
	 * 查询商户商品规格列表
	 *
	 * @param vo   查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_spec_page")
	@Operation(summary = "分页列表", description = "查询商户商品规格列表")
	public R<IPage<SpecDto>> getSpecPage(@ParameterObject SpecSearchVo vo) {
		Long current = vo.getCurrent();
		Long size = vo.getSize();
		Page page = new Page<>(current, size);
		return specService.getPage(page, vo);
	}

	/**
	 * 查询商户商品规格列表
	 *
	 * @return
	 */
	@GetMapping("/getList")
	@HasPermission("merch_spec_page")
	@Operation(summary = "列表", description = "查询商户商品规格列表")
	public R<List<SpecDto>> getSpecList(@RequestParam Long merchantId) {
		return specService.getMerchantSpecList(merchantId);
	}

	/**
	 * 商户新增商品规格
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户新增商品规格")
	@PostMapping
	@HasPermission("merch_spec_add")
	@Operation(summary = "新增", description = "商户新增商品规格组")
	public R addSpec(@RequestBody SpecVo vo) {
		return specService.addSpec(vo);
	}

	/**
	 * 商户编辑商品规格
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户编辑商品规格")
	@PutMapping
	@HasPermission("merch_spec_edit")
	@Operation(summary = "编辑", description = "商户编辑商品规格组")
	public R editSpec(@RequestBody SpecVo vo) {
		return specService.editSpec(vo);
	}

	/**
	 * 商户删除商品规格
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("商户删除商品规格")
	@DeleteMapping
	@HasPermission("merch_spec_del")
	@Operation(summary = "删除", description = "商户删除商品规格")
	public R del(@RequestBody Long[] ids) {
		return specService.deleteSpec(ids);
	}
}
