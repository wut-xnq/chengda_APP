package com.jiaolong.cm.consu.convert;

import com.jiaolong.cm.consu.api.dto.UserPersonalDto;
import com.jiaolong.cm.consu.api.entity.CmCustomer;
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
public interface CmCustomerConvert {
	CmCustomerConvert INSTANCE = Mappers.getMapper(CmCustomerConvert.class);


	@Mappings({
		@Mapping(source = "userId", target = "userId"),
		@Mapping(source = "createTime", target = "createTime"),
		@Mapping(source = "phone", target = "phone"),
		@Mapping(source = "avatar", target = "avatar"),
		@Mapping(source = "nickname", target = "nickname"),
		@Mapping(source = "name", target = "name"),
		@Mapping(source = "birthday", target = "birthday"),
		@Mapping(source = "hobby", target = "hobby"),
		@Mapping(source = "major", target = "major"),
		@Mapping(source = "brief", target = "brief"),
		@Mapping(source = "userScore", target = "userScore"),
		@Mapping(source = "invitationCode", target = "invitationCode"),
		@Mapping(source = "verified", target = "verified"),
		@Mapping(source = "merchantId", target = "merchantId"),
		@Mapping(source = "ifGiftScore", target = "ifGiftScore")
	})
	UserPersonalDto covertToPersonalDto(CmCustomer user);
}
