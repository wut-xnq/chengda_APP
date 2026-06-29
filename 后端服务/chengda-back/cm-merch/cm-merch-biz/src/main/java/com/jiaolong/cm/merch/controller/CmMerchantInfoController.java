package com.jiaolong.cm.merch.controller;

/**
 * desc: 商户信息管理
 * user: pan
 * date: 2024-08-28 10:11
 */

import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.merch.api.dto.MerchantInfoDto;
import com.jiaolong.cm.merch.api.vo.MerchantInfoVo;
import com.jiaolong.cm.merch.service.CmMerchantInfoService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 商户信息管理
 * user: pan
 * date: 2024-08-23 16:46
 */
@RestController
@RequestMapping("/merchant")
@Tag(description = "merchant", name = "商户信息管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmMerchantInfoController {

	@Autowired
	private CmMerchantInfoService merchantInfoService;

	/**
	 * 查询商户列表
	 *
	 * @param phone 法人手机号
	 * @return
	 */
	@GetMapping("/list")
	@HasPermission("merch_info_list")
	@Operation(summary = "列表", description = "查询商户列表")
	public R<List<MerchantInfoDto>> infoList(@RequestParam("phone") String phone) {
		return merchantInfoService.getInfoListByPhone(phone);
	}

	/**
	 * 查询商户详情
	 *
	 * @param id 商户主键
	 * @return
	 */
	@GetMapping("/info")
	@HasPermission("merch_info")
	@Operation(summary = "详情", description = "查询商户详情")
	public R<MerchantInfoDto> info(@RequestParam("id") Long id) {
		return merchantInfoService.getInfoById(id);
	}

	/**
	 * 编辑
	 *
	 * @param vo
	 * @return
	 */
	@SysLog("商户编辑企业信息")
	@PutMapping
	@HasPermission("merch_edit")
	@Operation(summary = "编辑", description = "编辑商户信息")
	public R edit(@RequestBody MerchantInfoVo vo) {
		return merchantInfoService.editMerchantInfo(vo);
	}
}
