package com.jiaolong.cm.merch.convert;

import com.jiaolong.cm.merch.api.entity.CmOrderLogistics;
import com.jiaolong.cm.merch.api.vo.OrderDeliverVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * desc: 订单物流信息数据转换
 * user: pan
 * date: 2024-09-03 17:08
 */
@Mapper
public interface CmOrderLogisticsConvert {
	CmOrderLogisticsConvert INSTANCE = Mappers.getMapper(CmOrderLogisticsConvert.class);

	@Mappings({
		@Mapping(source = "logisticsName", target = "logisticsName"),
		@Mapping(source = "logisticsCode", target = "logisticsCode"),
		@Mapping(source = "logisticsNo", target = "logisticsNo"),
		@Mapping(source = "orderNo", target = "shoppingOrderNo")
	})
	CmOrderLogistics create(OrderDeliverVo vo);

}
