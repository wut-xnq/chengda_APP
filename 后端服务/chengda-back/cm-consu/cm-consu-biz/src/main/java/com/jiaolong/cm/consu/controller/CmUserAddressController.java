package com.jiaolong.cm.consu.controller;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.consu.api.dto.UserAddressDto;
import com.jiaolong.cm.consu.service.CmUserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 用户收件地址管理
 * user: pan
 * date: 2024-08-22 11:01
 */
@RestController
@RequestMapping("/userAddress")
@Tag(description = "userAddress", name = "用户收件地址管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmUserAddressController {

	@Autowired
	private CmUserAddressService userAddressService;

	/**
	 * 获取用户收件地址分页列表
	 *
	 * @param userId 用户主键
	 * @return
	 */
	@GetMapping("/list/{userId}")
	@HasPermission("consu_user_address")
	@Operation(summary = "分页列表", description = "获取用户收件地址分页列表")
	public R<List<UserAddressDto>> list(@PathVariable("userId") Long userId) {
		return userAddressService.getUserAddressList(userId);
	}

	/**
	 * 获取用户收件地址分页列表
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping("/{id}")
	@HasPermission("consu_user_address_detail")
	@Operation(summary = "详情", description = "获取用户收件地址详情")
	public R<UserAddressDto> details(@PathVariable("id") Long id) {
		return userAddressService.getDetails(id);
	}

	/**
	 * 新增用户收件地址
	 *
	 * @param userAddressDto 用户对象
	 * @return
	 */
	@PostMapping
	@SysLog("新增用户收件地址")
	@HasPermission("consu_user_addr_add")
	@Operation(summary = "新增", description = "新增用户收件地址")
	public R add(@RequestBody UserAddressDto userAddressDto) {
		return userAddressService.saveUserAddress(userAddressDto);
	}

	/**
	 * 编辑用户收件地址
	 *
	 * @param userAddressDto 用户对象
	 * @return
	 */
	@PutMapping
	@SysLog("编辑用户收件地址")
	@HasPermission("consu_user_addr_edit")
	@Operation(summary = "编辑", description = "编辑用户收件地址")
	public R edit(@RequestBody UserAddressDto userAddressDto) {
		return userAddressService.updateUserAddress(userAddressDto);
	}

	/**
	 * 获取用户收件地址分页列表
	 *
	 * @param userId 主键
	 * @return
	 */
	@GetMapping("/default/{userId}")
	@HasPermission("consu_user_default_address")
	@Operation(summary = "用户默认地址", description = "获取用户默认收件地址")
	public R<UserAddressDto> getUserDefaultAddress(@PathVariable("userId") Long userId) {
		return userAddressService.getDefaultAddress(userId);
	}
}
