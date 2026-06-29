package com.jiaolong.cm.consu.convert;

import com.jiaolong.cm.consu.api.dto.UserAddressDto;
import com.jiaolong.cm.consu.api.entity.CmUserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * desc: 用户收货地址类型转换
 * user: pan
 * date: 2024-08-21 17:43
 */
@Mapper
public interface CmUserAddressConvert {
	CmUserAddressConvert INSTANCE = Mappers.getMapper(CmUserAddressConvert.class);

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "userId", target = "userId"),
		@Mapping(source = "createTime", target = "createTime"),
		@Mapping(source = "concatPerson", target = "concatPerson"),
		@Mapping(source = "contactPhone", target = "contactPhone"),
		@Mapping(source = "provinceCode", target = "provinceCode"),
		@Mapping(source = "cityCode", target = "cityCode"),
		@Mapping(source = "countryCode", target = "countryCode"),
		@Mapping(source = "address", target = "address"),
		@Mapping(source = "ifDefault", target = "ifDefault")
	})
	UserAddressDto covertToDto(CmUserAddress userAddress);

	@Mappings({
			@Mapping(source = "id", target = "id", ignore = true),
			@Mapping(source = "userId", target = "userId"),
			@Mapping(source = "createTime", target = "createTime"),
			@Mapping(source = "concatPerson", target = "concatPerson"),
			@Mapping(source = "contactPhone", target = "contactPhone"),
			@Mapping(source = "provinceCode", target = "provinceCode"),
			@Mapping(source = "cityCode", target = "cityCode"),
			@Mapping(source = "countryCode", target = "countryCode"),
			@Mapping(source = "address", target = "address"),
			@Mapping(source = "ifDefault", target = "ifDefault")
	})
	CmUserAddress create(UserAddressDto dto);

	@Mappings({
			@Mapping(source = "id", target = "id"),
			@Mapping(source = "userId", target = "userId"),
			@Mapping(source = "createTime", target = "createTime"),
			@Mapping(source = "concatPerson", target = "concatPerson"),
			@Mapping(source = "contactPhone", target = "contactPhone"),
			@Mapping(source = "provinceCode", target = "provinceCode"),
			@Mapping(source = "cityCode", target = "cityCode"),
			@Mapping(source = "countryCode", target = "countryCode"),
			@Mapping(source = "address", target = "address"),
			@Mapping(source = "ifDefault", target = "ifDefault")
	})
	CmUserAddress update(UserAddressDto dto);
}
