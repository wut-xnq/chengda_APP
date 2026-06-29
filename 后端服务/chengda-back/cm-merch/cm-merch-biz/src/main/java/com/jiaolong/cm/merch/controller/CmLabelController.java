package com.jiaolong.cm.merch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.Inner;
import com.jiaolong.cm.merch.api.dto.LabelDto;
import com.jiaolong.cm.merch.api.vo.LabelSearchVo;
import com.jiaolong.cm.merch.api.vo.LabelVo;
import com.jiaolong.cm.merch.service.CmLabelService;
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
 * desc: 商户标签管理
 * user: pan
 * date: 2024-09-02 15:49
 */
@RestController
@RequestMapping("/label")
@Tag(description = "label", name = "商户标签管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmLabelController {

	@Autowired
	private CmLabelService labelService;

	/**
	 * 查询商品标签列表
	 *
	 * @param vo 查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_label_page")
	@Operation(summary = "分页列表", description = "查询商品标签分页列表")
	public R<IPage<LabelDto>> getPage(@ParameterObject LabelSearchVo vo) {
		Long current = vo.getCurrent();
		Long size = vo.getSize();
		Page page = new Page<>(current, size);
		return labelService.getPage(page, vo);
	}

	/**
	 * 查询商品标签详情
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping("/detail/{id}")
	@HasPermission("merch_label_detail")
	@Operation(summary = "详情", description = "查询商品标签详情")
	public R<LabelDto> getDetail(@PathVariable("id") Long id) {
		return labelService.getDetail(id);
	}

	/**
	 * 商户新增商品标签
	 *
	 * @param vo
	 * @return
	 */
	@SysLog("商户新增标签")
	@PostMapping
	@HasPermission("merch_label_add")
	@Operation(summary = "新增", description = "商户新增商品标签")
	public R addLabel(@RequestBody LabelVo vo) {
		return labelService.addLabel(vo);
	}

	/**
	 * 商户编辑商品标签
	 *
	 * @param vo
	 * @return
	 */
	@SysLog("商户编辑标签")
	@PutMapping
	@HasPermission("merch_label_edit")
	@Operation(summary = "编辑", description = "商户编辑商品标签")
	public R editLabel(@RequestBody LabelVo vo) {
		return labelService.editLabel(vo);
	}

	/**
	 * 商户删除商品标签
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("商户删除标签")
	@DeleteMapping
	@HasPermission("merch_label_del")
	@Operation(summary = "删除", description = "商户删除商品标签")
	public R del(@RequestBody Long[] ids) {
		return labelService.deleteLabel(ids);
	}

	/**
	 * 查询商户商品标签列表
	 *
	 * @param merchantId 商户主键
	 * @return
	 */
	@GetMapping("/getList")
	@HasPermission("merch_label_page")
	@Operation(summary = "商户标签列表", description = "查询商户商品标签列表")
	public R<List<LabelDto>> getMerchantLabel(@RequestParam Long merchantId) {
		return labelService.getMerchantLabelList(merchantId);
	}
}
