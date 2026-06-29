package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.feign.RemoteProductService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.merch.api.dto.SpecDto;
import com.jiaolong.cm.merch.api.vo.SpecSearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 商户商品规格管理
 * user: pan
 * date: 2024-09-20 18:20
 */
@RestController
@RequestMapping("/spec")
@Tag(description = "spec", name = "商户商品规格管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmSpecController {

	@Autowired
	private RemoteProductService remoteProductService;

	/**
	 * 查询商户商品规格列表
	 *
	 * @param vo   查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_spec_page")
	@Operation(summary = "列表", description = "查询商户商品规格列表")
	public R<Page<SpecDto>> getSpecPage(@ParameterObject SpecSearchVo vo) {
		return remoteProductService.getSpecPage(vo);
	}

	/**
	 * 平台删除商品规格
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("管理员删除商户商品规格")
	@DeleteMapping
	@HasPermission("merch_spec_del")
	@Operation(summary = "删除", description = "平台删除商品规格")
	public R del(@RequestBody Long[] ids) {
		return remoteProductService.delSpec(ids);
	}
}
