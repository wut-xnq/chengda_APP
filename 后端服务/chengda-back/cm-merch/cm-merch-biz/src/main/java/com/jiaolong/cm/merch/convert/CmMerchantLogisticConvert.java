package com.jiaolong.cm.merch.convert;

import com.jiaolong.cm.merch.api.dto.LogisticsDto;
import com.jiaolong.cm.merch.api.entity.CmMerchantLogistics;
import com.jiaolong.cm.merch.api.vo.LogisticVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * desc: 商户物流方案数据转换
 * user: pan
 * date: 2024-09-03 10:58
 */
@Mapper
public interface CmMerchantLogisticConvert {
	CmMerchantLogisticConvert INSTANCE = Mappers.getMapper(CmMerchantLogisticConvert.class);

	@Mappings({
		@Mapping(source = "id", target = "id", ignore = true),
		@Mapping(source = "merchantId", target = "merchantId"),
		@Mapping(source = "logisticName", target = "logisticName"),
		@Mapping(source = "region", target = "region"),
		@Mapping(source = "chargeType", target = "chargeType"),
		@Mapping(source = "weight", target = "weight"),
		@Mapping(source = "price", target = "price"),
		@Mapping(source = "secondWeight", target = "secondWeight"),
		@Mapping(source = "secondPrice", target = "secondPrice"),
		@Mapping(source = "ifDefault", target = "ifDefault"),
		@Mapping(source = "state", target = "state")
	})
	CmMerchantLogistics create(LogisticVo vo);

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "merchantId", target = "merchantId"),
		@Mapping(source = "logisticName", target = "logisticName"),
		@Mapping(source = "region", target = "region"),
		@Mapping(source = "chargeType", target = "chargeType"),
		@Mapping(source = "weight", target = "weight"),
		@Mapping(source = "price", target = "price"),
		@Mapping(source = "secondWeight", target = "secondWeight"),
		@Mapping(source = "secondPrice", target = "secondPrice"),
		@Mapping(source = "ifDefault", target = "ifDefault"),
		@Mapping(source = "state", target = "state")
	})
	CmMerchantLogistics update(LogisticVo vo);

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "merchantId", target = "merchantId"),
		@Mapping(source = "createTime", target = "createTime"),
		@Mapping(source = "logisticName", target = "logisticName"),
		@Mapping(source = "region", target = "region"),
		@Mapping(source = "chargeType", target = "chargeType"),
		@Mapping(source = "weight", target = "weight"),
		@Mapping(source = "price", target = "price"),
		@Mapping(source = "secondWeight", target = "secondWeight"),
		@Mapping(source = "secondPrice", target = "secondPrice"),
		@Mapping(source = "ifDefault", target = "ifDefault"),
		@Mapping(source = "state", target = "state")
	})
	LogisticsDto convertToDto(CmMerchantLogistics logistics);
}
