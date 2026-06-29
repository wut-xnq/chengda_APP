package com.jiaolong.cm.merch.controller;

import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.merch.api.dto.StaffScoreDto;
import com.jiaolong.cm.merch.api.dto.StatisticMerchantInfoDto;
import com.jiaolong.cm.merch.api.dto.StatisticOrderDto;
import com.jiaolong.cm.merch.api.dto.StatisticProductDto;
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
 * desc: 首页数据统计
 * user: pan
 * date: 2024-09-06 10:15
 */
@RestController
@RequestMapping("/statistics")
@Tag(description = "statistics", name = "首页数据统计")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class StatisticsController {

	@Autowired
	private CmMerchantInfoService merchantInfoService;

	/**
	 * 首页商户基本信息
	 * @return
	 */
	@GetMapping("/merchantInfo")
	@HasPermission("merch_basic_info")
	@Operation(summary = "商户信息", description = "首页商户基本信息")
	public R<StatisticMerchantInfoDto> statisticsMerchantBasicInfo(){
		return merchantInfoService.getMerchantBasicInfo();
	}

	/**
	 * 首页商品销量统计
	 * @return
	 */
	@GetMapping("/product")
	@HasPermission("merch_stati_amount")
	@Operation(summary = "商品销量统计", description = "首页商品销量统计")
	public R<List<StatisticProductDto>> statisticsProduct(){
		return merchantInfoService.getProductStatisticList();
	}

	/**
	 * 首页订单收益统计
	 * @return
	 */
	@GetMapping("/order")
	@HasPermission("merch_stati_order")
	@Operation(summary = "订单收益统计", description = "首页订单收益统计")
	public R<StatisticOrderDto> statisticsOrder(){
		return merchantInfoService.getOrderStatisticList();
	}

	/**
	 * 员工积分统计列表
	 * @return
	 */
	@GetMapping("/staffScore")
	@HasPermission("merch_score_list")
	@Operation(summary = "员工积分统计", description = "员工积分统计列表")
	public R<List<StaffScoreDto>> statisticsStaffScore(){
		return merchantInfoService.getStaffScoreList();
	}

	/**
	 * 发起积分转化
	 *
	 * @param ids 用户主键
	 * @return
	 */
	@SysLog("发起积分转换")
	@PutMapping("/tranScore")
	@HasPermission("merch_score_trans")
	@Operation(summary = "发起积分转化", description = "发起积分转化")
	public R reqTransScore(@RequestBody String[] ids) {
		return merchantInfoService.reqTransScore(ids);
	}
}
