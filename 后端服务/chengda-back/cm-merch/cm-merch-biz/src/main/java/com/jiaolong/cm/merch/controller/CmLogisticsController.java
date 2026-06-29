package com.jiaolong.cm.merch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.Inner;
import com.jiaolong.cm.merch.api.dto.LogisticsDto;
import com.jiaolong.cm.merch.api.entity.KdCompany;
import com.jiaolong.cm.merch.api.vo.LogisticSearchVo;
import com.jiaolong.cm.merch.api.vo.LogisticVo;
import com.jiaolong.cm.merch.service.CmLogisticsService;
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
 * desc: 商户物流方案管理
 * user: pan
 * date: 2024-09-03 10:07
 */
@RestController
@RequestMapping("/logistics")
@Tag(description = "logistics", name = "商户物流方案管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmLogisticsController {

	@Autowired
	private CmLogisticsService logisticsService;

	/**
	 * 查询商户物流方案列表
	 *
	 * @param vo 查询参数
	 * @return
	 */
	@GetMapping("/getPage")
	@HasPermission("merch_logis_page")
	@Operation(summary = "列表", description = "查询商户物流方案列表")
	public R<IPage<LogisticsDto>> getPage(@ParameterObject LogisticSearchVo vo) {
		Long current = vo.getCurrent();
		Long size = vo.getSize();
		Page page = new Page<>(current, size);
		return logisticsService.getLogisticPage(page, vo);
	}

	/**
	 * 查询商户物流方案详情
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping("/detail/{id}")
	@HasPermission("merch_logis_detail")
	@Operation(summary = "详情", description = "查询商户物流方案详情")
	public R<LogisticsDto> getDetail(@PathVariable("id") Long id) {
		return logisticsService.getDetail(id);
	}

	/**
	 * 商户新增物流方案
	 *
	 * @param vo
	 * @return
	 */
	@SysLog("商户新增物流方案")
	@PostMapping
	@HasPermission("merch_logis_add")
	@Operation(summary = "新增", description = "商户新增物流方案")
	public R addLogistic(@RequestBody LogisticVo vo) {
		return logisticsService.addLogistic(vo);
	}

	/**
	 * 商户编辑物流方案
	 *
	 * @param vo
	 * @return
	 */
	@SysLog("商户编辑物流方案")
	@PutMapping
	@HasPermission("merch_logis_edit")
	@Operation(summary = "编辑", description = "商户编辑物流方案")
	public R editLogistic(@RequestBody LogisticVo vo) {
		return logisticsService.editLogistic(vo);
	}

	/**
	 * 商户删除物流方案
	 *
	 * @param ids
	 * @return
	 */
	@SysLog("商户删除物流方案")
	@DeleteMapping
	@HasPermission("merch_logis_del")
	@Operation(summary = "删除", description = "商户删除物流方案")
	public R delLogistic(@RequestBody Long[] ids) {
		return logisticsService.deleteLogistic(ids);
	}

	/**
	 * 查询快递公司名称列表
	 *
	 * @return
	 */
	@GetMapping("/getCompanyList")
	@Operation(summary = "快递公司列表", description = "查询快递公司名称列表")
	public R<List<KdCompany>> getCompanyList() {
		return logisticsService.getAllCompanyList();
	}
}
