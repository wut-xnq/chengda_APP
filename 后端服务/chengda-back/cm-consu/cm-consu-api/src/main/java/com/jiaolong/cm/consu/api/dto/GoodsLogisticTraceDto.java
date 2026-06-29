package com.jiaolong.cm.consu.api.dto;

import com.jiaolong.cm.consu.api.express.QueryTrackResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * desc: 物流追踪数据类
 * user: pan
 * date: 2024-10-17 15:29
 */
@Data
@Schema(description = "物流追踪数据类")
public class GoodsLogisticTraceDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品名称列表
	 */
	@Schema(description = "商品名称列表")
	private List<ProductNameDto> productList;

	/**
	 * 物流单号
	 */
	@Schema(description = "物流单号")
	private String logisticsNo;

	/**
	 * 物流公司名称
	 */
	@Schema(description = "物流公司名称")
	private String logisticsName;

	/**
	 * 收件地址数据
	 */
	@Schema(description = "收件地址数据")
	private LogisticAddressDto logisticAddressDto;

	/**
	 * 物流追踪数据
	 */
	@Schema(description = "物流追踪数据")
	private QueryTrackResp queryTrackResp;

}
