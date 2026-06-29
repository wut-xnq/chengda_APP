package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.dto.CustomerDto;
import com.jiaolong.cm.admin.api.dto.UserInfo;
import com.jiaolong.cm.admin.api.vo.CustomerSearchVo;
import com.jiaolong.cm.admin.service.CmCustomerService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 客户管理模块
 * user: pan
 * date: 2024-09-14 18:19
 */
@RestController
@RequestMapping("/customer")
@Tag(description = "customer", name = "客户管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmCustomerController {

	@Autowired
	private CmCustomerService customerService;

	/**
	 * 获取客户分页列表
	 * @param page 分页参数
	 * @param vo 查询参数
	 * @return
	 */
	@GetMapping("/page")
	@HasPermission("admin_customer_page")
	@Operation(summary = "分页列表", description = "获取客户分页列表")
	public R<IPage<CustomerDto>> getPage(@ParameterObject Page page, @ParameterObject CustomerSearchVo vo){
		return customerService.getCustomerPage(page, vo);
	}

	/**
	 * 获取客户信息详情
	 * @param userId 主键
	 * @return
	 */
	@GetMapping("/details/{userId}")
	@HasPermission("admin_customer_detail")
	@Operation(summary = "详情", description = "获取客户信息详情")
	public R<CustomerDto> getDetails(@PathVariable("userId") Long userId){
		return customerService.getDetails(userId);
	}

	/**
	 * 启用/禁用客户账号
	 * @param userId 主键
	 * @return
	 */
	@SysLog("管理员启用/禁用用户")
	@PutMapping("/disabled")
	@HasPermission("admin_customer_edit")
	@Operation(summary = "启用/禁用", description = "启用/禁用客户账号")
	public R disabled(@RequestParam("userId") String userId, @RequestParam("lockFlag") String lockFlag){
		return customerService.disabledCustomer(userId, lockFlag);
	}

	/**
	 * 重置客户密码
	 * @param userId 客户主键
	 * @return
	 */
	@SysLog("管理员重置客户密码")
	@PutMapping("/resetPwd")
	@HasPermission("admin_customer_edit")
	@Operation(summary = "重置密码", description = "管理员重置客户密码")
	public R resetCustomerPassword(@RequestParam("userId") String userId){
		return customerService.resetCustomerPwd(userId);
	}

	/**
	 * 获取指定普通用户全部信息
	 *
	 * @return 用户信息
	 */
	@Inner
	@GetMapping(value = {"/info/{username}"})
	public R<UserInfo> info(@PathVariable String username, @RequestParam(required = false) String inviteCode) {
		return customerService.getCustomerInfo(username, inviteCode);
	}

	/**
	 * 获取指定商户用户全部信息
	 *
	 * @return 用户信息
	 */
	@Inner
	@GetMapping(value = {"/customerInfo/{username}"})
	public R<UserInfo> customerInfo(@PathVariable String username) {
		return customerService.getMerchantUserInfo(username);
	}

}
