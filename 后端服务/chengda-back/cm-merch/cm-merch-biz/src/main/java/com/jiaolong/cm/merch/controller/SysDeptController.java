package com.jiaolong.cm.merch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.merch.api.dto.DeptInfoDto;
import com.jiaolong.cm.merch.api.vo.DeptInfoVo;
import com.jiaolong.cm.merch.api.vo.DeptSearchVo;
import com.jiaolong.cm.merch.service.SysDeptService;
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
 * desc: 部门管理
 * user: pan
 * date: 2024-08-29 10:39
 */
@RestController
@RequestMapping("/dept")
@Tag(description = "dept", name = "部门管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysDeptController {

	@Autowired
	private SysDeptService deptService;

	/**
	 * 获取部门分页列表
	 *
	 * @param page 分页参数
	 * @param vo   传参
	 * @return
	 */
	@GetMapping("/page")
	@HasPermission("merch_dept_page")
	@Operation(summary = "分页列表", description = "获取部门分页列表")
	public R<IPage<DeptInfoDto>> getPage(@ParameterObject Page page, @ParameterObject DeptSearchVo vo){
		return deptService.getDeptPage(page, vo);
	}

	/**
	 * 获取部门信息详情
	 *
	 * @param deptId 部门主键
	 * @return
	 */
	@GetMapping("/details/{deptId}")
	@HasPermission("merch_dept_detail")
	@Operation(summary = "详情", description = "获取部门信息详情")
	public R<DeptInfoDto> getDetail(@PathVariable("deptId") Long deptId) {
		return deptService.getDeptInfo(deptId);
	}


	/**
	 * 新增部门
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户新增部门")
	@PostMapping
	@HasPermission("merch_dept_add")
	@Operation(summary = "新增", description = "新增部门")
	public R add(@RequestBody DeptInfoVo vo) {
		return deptService.addDept(vo);
	}

	/**
	 * 编辑部门信息
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户编辑部门信息")
	@PutMapping
	@HasPermission("merch_dept_edit")
	@Operation(summary = "编辑", description = "编辑部门信息")
	public R edit(@RequestBody DeptInfoVo vo) {
		return deptService.editDept(vo);
	}

	/**
	 * 删除部门
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("商户删除部门")
	@DeleteMapping
	@HasPermission("merch_dept_del")
	@Operation(summary = "删除", description = "删除部门")
	public R del(@RequestBody Long[] ids) {
		return deptService.deleteDept(ids);
	}
}
