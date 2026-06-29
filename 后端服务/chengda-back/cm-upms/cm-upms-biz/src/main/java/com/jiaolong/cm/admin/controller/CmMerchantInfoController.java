package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.dto.MerchantDto;
import com.jiaolong.cm.admin.api.vo.MerchantSearchVo;
import com.jiaolong.cm.admin.service.CmMerchantInfoService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.merch.api.dto.MerchantInfoDto;
import com.jiaolong.cm.merch.api.vo.MerchantInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 商户信息管理
 * user: pan
 * date: 2024-09-20 10:32
 */
@RestController
@RequestMapping("/merchant")
@Tag(description = "merchant", name = "商户信息管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmMerchantInfoController {

	@Autowired
	private CmMerchantInfoService merchantInfoService;

	/**
	 * 查询商户分页列表
	 *
	 * @param page 分页参数
	 * @param vo   查询参数
	 * @return
	 */
	@GetMapping("/page")
	@HasPermission("admin_merch_page")
	@Operation(summary = "列表", description = "查询商户分页列表")
	public R<IPage<MerchantInfoDto>> getPage(@ParameterObject Page page, @ParameterObject MerchantSearchVo vo) {
		return merchantInfoService.getMerchantPage(page, vo);
	}

	/**
	 * 查询商户详情
	 *
	 * @param id 商户主键
	 * @return
	 */
	@GetMapping("/info")
	@HasPermission("admin_merch_detail")
	@Operation(summary = "详情", description = "查询商户详情")
	public R<MerchantInfoDto> info(@RequestParam("id") Long id) {
		return merchantInfoService.getMerchantInfo(id);
	}

	/**
	 * 新增
	 *
	 * @param vo
	 * @return
	 */
	@SysLog("管理员新增商户")
	@PostMapping
	@HasPermission("admin_merch_add")
	@Operation(summary = "新增", description = "新增商户信息")
	public R add(@RequestBody MerchantInfoVo vo) {
		return merchantInfoService.addMerchantInfo(vo);
	}

	/**
	 * 启用/禁用商户
	 *
	 * @param merchantId
	 * @param state
	 * @return
	 */
	@SysLog("管理员启用/禁用商户")
	@PutMapping
	@HasPermission("admin_merch_edit")
	@Operation(summary = "启用/禁用", description = "启用/禁用商户")
	public R disabled(@RequestParam("merchantId") String merchantId, @RequestParam("state") String state) {
		return merchantInfoService.disabledMerchant(merchantId, state);
	}

	/**
	 * 删除商户
	 *
	 * @param ids
	 * @return
	 */
	@SysLog("管理员删除商户")
	@DeleteMapping
	@HasPermission("admin_merch_del")
	@Operation(summary = "删除", description = "删除商户")
	public R delete(@RequestBody Long[] ids) {
		return merchantInfoService.deleteMerchant(ids);
	}

	/**
	 * 查询平台入驻的商户列表
	 *
	 * @return
	 */
	@GetMapping("/list")
	@Operation(summary = "列表", description = "查询平台入驻的商户列表")
	public R<List<MerchantDto>> getList() {
		return merchantInfoService.getMerchantList();
	}

	/**
	 * 管理员重置商户登录密码
	 *
	 * @param merchantId
	 * @return
	 */
	@SysLog("管理员重置商户登录密码")
	@PutMapping("/resetPwd")
	@HasPermission("admin_merch_edit")
	@Operation(summary = "重置商户密码", description = "管理员重置商户登录密码")
	public R resetMerchantPassword(@RequestParam("merchantId") String merchantId) {
		return merchantInfoService.resetMerchantPassword(merchantId);
	}

}
