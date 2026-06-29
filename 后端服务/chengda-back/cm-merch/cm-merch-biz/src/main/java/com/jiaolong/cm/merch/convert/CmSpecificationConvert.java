package com.jiaolong.cm.merch.convert;

import com.jiaolong.cm.merch.api.entity.CmSpecification;
import com.jiaolong.cm.merch.api.vo.SpecVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * desc: 商品规格信息数据转换
 * user: pan
 * date: 2024-09-01 14:23
 */
@Mapper
public interface CmSpecificationConvert {
	CmSpecificationConvert INSTANCE = Mappers.getMapper(CmSpecificationConvert.class);

	default CmSpecification convertToEntity(CmSpecification spec, SpecVo vo){
		if (vo != null) {
			spec.setMerchantId(vo.getMerchantId());
			spec.setGroupId(vo.getGroupId());
			spec.setSpecName(vo.getSpecName());
			spec.setState(vo.getState());
		}
		return spec;
	}
}
