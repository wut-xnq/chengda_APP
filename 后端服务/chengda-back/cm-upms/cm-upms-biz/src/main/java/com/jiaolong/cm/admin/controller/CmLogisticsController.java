package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.feign.RemoteProductService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.merch.api.dto.LogisticsDto;
import com.jiaolong.cm.merch.api.vo.LogisticSearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 商户物流方案管理
 * user: pan
 * date: 2024-09-20 18:29
 */
@RestController
@RequestMapping("/logistics")
@Tag(description = "logistics", name = "商户物流方案管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmLogisticsController {

	@Autowired
	private RemoteProductService remoteProductService;

	/**
	 * 查询商户物流方案列表
	 *
	 * @param vo   查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_logis_page")
	@Operation(summary = "列表", description = "查询商户物流方案列表")
	public R<Page<LogisticsDto>> getPage(@ParameterObject LogisticSearchVo vo) {
		return remoteProductService.getLogisticsPage(vo);
	}

	/**
	 * 平台删除物流方案
	 *
	 * @param ids
	 * @return
	 */
	@SysLog("管理员删除商户物流方案")
	@DeleteMapping
	@HasPermission("merch_logis_del")
	@Operation(summary = "删除", description = "平台删除物流方案")
	public R delLogistic(@RequestBody Long[] ids) {
		return remoteProductService.delLogistics(ids);
	}
}
