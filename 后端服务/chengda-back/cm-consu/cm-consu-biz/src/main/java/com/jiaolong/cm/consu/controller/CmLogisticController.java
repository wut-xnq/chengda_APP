package com.jiaolong.cm.consu.controller;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.GoodsLogisticTraceDto;
import com.jiaolong.cm.consu.api.param.TraceFeeVo;
import com.jiaolong.cm.consu.service.CmLogisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 物流跟踪管理
 * user: pan
 * date: 2024-10-17 14:44
 */
@RestController
@RequestMapping("/logistic")
@Tag(description = "logistic", name = "物流跟踪管理")
public class CmLogisticController {

	@Autowired
	private CmLogisticService logisticService;

	/**
	 * 物流配送进度详情跟踪
	 * @param orderId 订单主键
	 * @return
	 */
	@PostMapping("/trace")
	@Operation(summary = "物流跟踪", description = "物流配送进度详情跟踪")
	public R<GoodsLogisticTraceDto> getLogisticTraceList(@RequestParam Long orderId){
		return logisticService.traceLogistics(orderId);
	}

	/**
	 * 计算商品运费
	 * @param vo 传参
	 * @return
	 */
	@PostMapping("/fee")
	@Operation(summary = "商品运费", description = "计算商品运费")
	public R getLogisticFee(@ParameterObject TraceFeeVo vo){
		return logisticService.traceLogisticsFee(vo);
	}
}
