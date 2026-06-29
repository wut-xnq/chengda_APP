package com.jiaolong.cm.consu.convert;

import com.jiaolong.cm.consu.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.consu.api.entity.CmOrderShopping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * desc: 订单中商品类型转换
 * user: pan
 * date: 2024-08-24 10:16
 */
@Mapper
public interface CmOrderShoppingConvert {
	CmOrderShoppingConvert INSTANCE = Mappers.getMapper(CmOrderShoppingConvert.class);

	@Mappings({
			@Mapping(source = "id", target = "id"),
			@Mapping(source = "createTime", target = "createTime"),
			@Mapping(source = "orderNo", target = "orderNo"),
			@Mapping(source = "userId", target = "userId"),
			@Mapping(source = "merchantId", target = "merchantId"),
			@Mapping(source = "userAddressId", target = "userAddressId"),
			@Mapping(source = "orderProducts", target = "orderProducts"),
			@Mapping(source = "orderWelfares", target = "orderWelfares"),
			@Mapping(source = "state", target = "state"),
			@Mapping(source = "paymentType", target = "paymentType"),
			@Mapping(source = "paymentState", target = "paymentState"),
			@Mapping(source = "updateTime", target = "updateTime"),
			@Mapping(source = "deleted", target = "deleted"),
			@Mapping(source = "remarks", target = "remarks")
	})
	ShoppingOrderDto covertToDto(CmOrderShopping order);
}
