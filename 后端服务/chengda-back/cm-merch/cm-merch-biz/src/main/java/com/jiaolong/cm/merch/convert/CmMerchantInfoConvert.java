package com.jiaolong.cm.merch.convert;

import com.jiaolong.cm.merch.api.entity.CmMerchantInfo;
import com.jiaolong.cm.merch.api.vo.MerchantInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * desc: 商户信息数据转换
 * user: pan
 * date: 2024-08-28 15:06
 */
@Mapper
public interface CmMerchantInfoConvert {
	CmMerchantInfoConvert INSTANCE = Mappers.getMapper(CmMerchantInfoConvert.class);

	default CmMerchantInfo convertToEntity(CmMerchantInfo info, MerchantInfoVo vo){
		if (vo != null) {
			info.setMerchantName(vo.getMerchantName());
			info.setChargePersonName(vo.getChargePersonName());
			info.setChargePersonPhone(vo.getChargePersonPhone());
			info.setLogo(vo.getLogo());
			info.setCreditCode(vo.getCreditCode());
			info.setProvinceCode(vo.getProvinceCode());
			info.setCityCode(vo.getCityCode());
			info.setCountryCode(vo.getCountryCode());
			info.setAddress(vo.getAddress());
			info.setStaffCount(vo.getStaffCount());
		}
		return info;
	}
}
