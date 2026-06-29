package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.feign.RemoteProductService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.merch.api.dto.LabelDto;
import com.jiaolong.cm.merch.api.vo.LabelSearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 商户标签管理
 * user: pan
 * date: 2024-09-20 18:27
 */
@RestController
@RequestMapping("/label")
@Tag(description = "label", name = "商户标签管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmLabelController {

	@Autowired
	private RemoteProductService remoteProductService;

	/**
	 * 查询商品标签列表
	 *
	 * @param vo   查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_label_page")
	@Operation(summary = "列表", description = "查询商品标签列表")
	public R<Page<LabelDto>> getPage(@ParameterObject LabelSearchVo vo) {
		return remoteProductService.getLabelPage(vo);
	}

	/**
	 * 平台删除商品标签
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("管理员删除商户商品标签")
	@DeleteMapping
	@HasPermission("merch_label_del")
	@Operation(summary = "删除", description = "平台删除商品标签")
	public R del(@RequestBody Long[] ids) {
		return remoteProductService.delLabel(ids);
	}
}
