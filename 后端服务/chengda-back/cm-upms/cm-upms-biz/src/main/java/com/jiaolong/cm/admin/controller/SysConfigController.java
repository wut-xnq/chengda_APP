package com.jiaolong.cm.admin.controller;

import com.jiaolong.cm.admin.service.SysConfigService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.merch.api.entity.SysConfiguration;
import com.jiaolong.cm.admin.api.vo.SettingVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 系统配置管理
 * user: pan
 * date: 2024-09-24 14:52
 */
@RestController
@RequestMapping("/setting")
@Tag(description = "setting", name = "系统配置管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysConfigController {

	@Autowired
	private SysConfigService configService;

	/**
	 * 查询配置列表
	 *
	 * @return
	 */
	@GetMapping
	@HasPermission("admin_setting_list")
	@Operation(summary = "列表", description = "查询配置列表")
	public R<List<SysConfiguration>> getList() {
		return configService.getAllList();
	}

	/**
	 * 编辑配置
	 *
	 * @return
	 */
	@PutMapping
	@HasPermission("admin_setting_edit")
	@Operation(summary = "编辑", description = "编辑配置")
	public R edit(@RequestBody SettingVo vo) {
		return configService.editSetting(vo);
	}

}
