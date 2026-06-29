package com.jiaolong.cm.merch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.merch.api.dto.UserVerifiedDto;
import com.jiaolong.cm.merch.api.vo.UserVerifiedAuditVo;
import com.jiaolong.cm.merch.api.vo.UserVerifiedSearchVo;
import com.jiaolong.cm.merch.service.CmUserVerifiedService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 员工认证管理
 * user: pan
 * date: 2024-08-29 16:47
 */
@RestController
@RequestMapping("/ident")
@Tag(description = "ident", name = "员工认证管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmUserVerifiedController {

	@Autowired
	private CmUserVerifiedService userVerifiedService;

	/**
	 * 获取员工认证信息分页列表
	 *
	 * @param page 分页参数
	 * @param vo   传参
	 * @return
	 */
	@GetMapping("/page")
	@HasPermission("merch_ident_page")
	@Operation(summary = "分页列表", description = "获取员工认证信息分页列表")
	public R<IPage<UserVerifiedDto>> getPage(@ParameterObject Page page, @ParameterObject UserVerifiedSearchVo vo){
		return userVerifiedService.getUserVerifiedPage(page, vo);
	}

	/**
	 * 审核（认证）员工信息
	 *
	 * @param vo   传参
	 * @return
	 */
	@SysLog("商户审核员工信息")
	@PutMapping("/audit")
	@HasPermission("merch_ident_edit")
	@Operation(summary = "审核（认证）", description = "审核（认证）员工信息")
	public R audit(@RequestBody UserVerifiedAuditVo vo){
		return userVerifiedService.auditUserVerified(vo);
	}

	/**
	 * 回收
	 *
	 * @param id   用户认证信息主键
	 * @return
	 */
	@SysLog("企业收回员工名片")
	@PutMapping("/recovery/{id}")
	@HasPermission("merch_ident_back")
	@Operation(summary = "回收", description = "回收企业名片")
	public R recovery(@PathVariable("id") Long id){
		return userVerifiedService.recoveryUserVerified(id);
	}

}
