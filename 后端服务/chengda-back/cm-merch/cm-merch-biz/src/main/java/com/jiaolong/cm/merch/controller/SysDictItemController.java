package com.jiaolong.cm.merch.controller;

import com.jiaolong.cm.merch.api.dto.DictItemDto;
import com.jiaolong.cm.merch.service.SysDictItemService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * desc: 字典内容管理
 * user: pan
 * date: 2024-08-30 14:00
 */
@RestController
@RequestMapping("/dictItem")
@Tag(description = "dictItem", name = "字典内容管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysDictItemController {

	@Autowired
	private SysDictItemService dictItemService;

	/**
	 * 根据字典类型查询字典项列表
	 *
	 * @param dictType 字典类型
	 * @return
	 */
	@GetMapping("/getListByType")
	@HasPermission("merch_dictitem_bytype")
	@Operation(summary = "按字典类型查列表", description = "根据字典类型查询字典项列表")
	public R<List<DictItemDto>> getItemListByDictType(@RequestParam("dictType") String dictType) {
		return dictItemService.getItemListByDictType(dictType);
	}

	/**
	 * 根据字典主键查询字典项列表
	 *
	 * @param dictId 字典主键
	 * @return
	 */
	@GetMapping("/getListById")
	@HasPermission("merch_dictitem_byid")
	@Operation(summary = "按字典主键查列表", description = "根据字典类型查询字典项列表")
	public R<List<DictItemDto>> getItemListByDictId(@RequestParam("dictId") Long dictId) {
		return dictItemService.getItemListByDictId(dictId);
	}
}
