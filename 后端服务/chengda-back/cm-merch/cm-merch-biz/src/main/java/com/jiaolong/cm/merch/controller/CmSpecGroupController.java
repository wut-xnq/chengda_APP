package com.jiaolong.cm.merch.controller;

import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.merch.api.dto.SpecDto;
import com.jiaolong.cm.merch.api.dto.SpecGroupDto;
import com.jiaolong.cm.merch.api.vo.SpecGroupVo;
import com.jiaolong.cm.merch.service.CmSpecGroupService;
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
 * desc: 商户商品规格组管理
 * user: pan
 * date: 2024-09-01 13:05
 */
@RestController
@RequestMapping("/specGroup")
@Tag(description = "specGroup", name = "商户商品规格组管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmSpecGroupController {

	@Autowired
	private CmSpecGroupService specGroupService;

	/**
	 * 查询商户商品规格组列表
	 *
	 * @param merchantId 商户主键
	 * @return
	 */
	@GetMapping("/getListByMerchantId/{merchantId}")
	@HasPermission("merch_spec_group_list")
	@Operation(summary = "规格组列表", description = "查询商户商品规格组列表")
	public R<List<SpecGroupDto>> getMerchantSpecGroupList(@PathVariable("merchantId") Long merchantId) {
		return specGroupService.getListByMerchantId(merchantId);
	}

	/**
	 * 查询规格组下的规格列表
	 *
	 * @param groupId 规格组主键
	 * @return
	 */
	@GetMapping("/getList/{groupId}")
	@HasPermission("merch_spec_page")
	@Operation(summary = "规格列表", description = "查询规格组下的规格列表")
	public R<List<SpecDto>> getSpecList(@PathVariable("groupId") Long groupId) {
		return specGroupService.getListByGroupId(groupId);
	}

	/**
	 * 商户新增商品规格组
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户新增商品规格组")
	@PostMapping
	@HasPermission("merch_spec_group_add")
	@Operation(summary = "新增", description = "商户新增商品规格组")
	public R addSpecGroup(@RequestBody SpecGroupVo vo) {
		return specGroupService.addSpecGroup(vo);
	}

	/**
	 * 商户编辑商品规格组
	 *
	 * @param vo 传参
	 * @return
	 */
	@SysLog("商户编辑商品规格组")
	@PutMapping
	@HasPermission("merch_spec_group_edit")
	@Operation(summary = "编辑", description = "商户编辑商品规格组")
	public R editSpecGroup(@RequestBody SpecGroupVo vo) {
		return specGroupService.editSpecGroup(vo);
	}

	/**
	 * 商户删除商品规格
	 *
	 * @param ids 主键列表
	 * @return
	 */
	@SysLog("商户删除商品规格组")
	@DeleteMapping
	@HasPermission("merch_spec_group_del")
	@Operation(summary = "删除", description = "商户删除商品规格组")
	public R del(@RequestBody Long[] ids) {
		return specGroupService.deleteSpecGroup(ids);
	}
}
