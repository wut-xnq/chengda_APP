package com.jiaolong.cm.merch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.dto.UserDTO;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.merch.api.dto.UserInfoDto;
import com.jiaolong.cm.merch.api.vo.UserInfoVo;
import com.jiaolong.cm.merch.api.vo.UserSearchVo;
import com.jiaolong.cm.merch.service.CmCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 员工管理
 * user: pan
 * date: 2024-09-02 16:43
 */
@RestController
@RequestMapping("/user")
@Tag(description = "user", name = "员工管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmCustomerController {

	@Autowired
	private CmCustomerService customerService;

	/**
	 * 获取员工分页列表
	 *
	 * @param page 分页参数
	 * @param vo   传参
	 * @return
	 */
	@GetMapping("/page")
	@HasPermission("merch_user_page")
	@Operation(summary = "分页列表", description = "获取员工分页列表")
	public R<IPage<UserInfoDto>> getPage(@ParameterObject Page page, @ParameterObject UserSearchVo vo) {
		return customerService.getUserPage(page, vo);
	}

	/**
	 * 获取员工信息详情
	 *
	 * @param userId 用户主键
	 * @return
	 */
	@GetMapping("/details/{userId}")
	@HasPermission("merch_user_detail")
	@Operation(summary = "详情", description = "获取员工信息详情")
	public R<UserInfoDto> getDetail(@PathVariable("userId") Long userId) {
		return customerService.getUserInfo(userId);
	}


	/**
	 * 新增员工
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户添加员工")
	@PostMapping
	@HasPermission("merch_user_add")
	@Operation(summary = "新增", description = "新增员工")
	public R add(@RequestBody UserInfoVo vo) {
		return customerService.addUser(vo);
	}

	/**
	 * 编辑员工信息
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户编辑员工")
	@PutMapping
	@HasPermission("merch_user_edit")
	@Operation(summary = "编辑", description = "编辑员工信息")
	public R edit(@RequestBody UserInfoVo vo) {
		return customerService.editUser(vo);
	}

	/**
	 * 删除员工
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("商户删除员工")
	@DeleteMapping
	@HasPermission("merch_user_del")
	@Operation(summary = "删除", description = "删除员工")
	public R del(@RequestBody Long[] ids) {
		return customerService.deleteUser(ids);
	}

	/**
	 * 修改个人密码
	 *
	 * @param userDto
	 * @return
	 */
	@SysLog("商户修改登录密码")
	@PutMapping("/password")
	@HasPermission("merch_user_edit")
	@Operation(summary = "修改密码", description = "修改个人密码")
	public R password(@RequestBody UserDTO userDto) {
		return customerService.changePassword(userDto);
	}
}
