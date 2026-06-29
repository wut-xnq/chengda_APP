package com.jiaolong.cm.consu.controller;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.CityCodeDto;
import com.jiaolong.cm.consu.api.entity.CityCode;
import com.jiaolong.cm.consu.service.CityCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * desc: 城市编码管理
 * user: pan
 * date: 2024-09-02 16:43
 */
@RestController
@RequestMapping("/cityCode")
@Tag(description = "cityCode", name = "城市编码管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CityCodeController {

	@Autowired
	private CityCodeService cityCodeService;

	/**
	 * 查询所有城市编码级联列表
	 *
	 * @return
	 */
	@GetMapping
	@Operation(summary = "级联列表", description = "查询所有城市编码级联列表")
	public R<List<CityCodeDto>> getAllTree() {
		return cityCodeService.getAllTree();
	}

	/**
	 * 查询下级城市列表
	 *
	 * @param cityPid
	 * @return
	 */
	@GetMapping("/getLowerList/{cityPid}")
	@Operation(summary = "下级列表", description = "查询下级城市列表")
	public R<List<CityCode>> getChildrenList(@PathVariable("cityPid") Integer cityPid) {
		return cityCodeService.getChildrenList(cityPid);
	}
}
