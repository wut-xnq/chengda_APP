package com.jiaolong.cm.consu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.consu.api.dto.MerchantListDto;
import com.jiaolong.cm.consu.api.dto.StaffDto;
import com.jiaolong.cm.consu.api.dto.UserPersonalDto;
import com.jiaolong.cm.consu.service.CmMerchantInfoService;
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
 * date: 2024-08-23 14:26
 */
@RestController
@RequestMapping("/merchant")
@Tag(description = "merchant", name = "商户信息管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmMerchantInfoController {

	@Autowired
	private CmMerchantInfoService merchantInfoService;

	/**
	 * 获取企业员工列表
	 * @return
	 */
	@GetMapping("/nameList")
	@Operation(summary = "企业名称列表", description = "获取经过平台验证的企业名称列表")
	public R<List<MerchantListDto>> getNameListAll(){
		return merchantInfoService.getNameListAll();
	}

	/**
	 * 获取企业员工列表
	 * @param merchantId
	 * @return
	 */
	@GetMapping("/getUserPage")
	@HasPermission("consu_cuser_page")
	@Operation(summary = "员工列表", description = "获取企业已认证的员工列表")
	public R<IPage<UserPersonalDto>> getUserPage(@ParameterObject Page page, @RequestParam Long merchantId){
		return merchantInfoService.getUserPage(page, merchantId);
	}

	/**
	 * 获取企业员工详情
	 * @param userId 用户主键
	 * @return
	 */
	@GetMapping("/getUserDetails/{userId}")
	@Operation(summary = "员工详情", description = "获取企业员工详情")
	public R<StaffDto> getUserDetails(@PathVariable Long userId){
		return merchantInfoService.getUserDetails(userId);
	}
}
