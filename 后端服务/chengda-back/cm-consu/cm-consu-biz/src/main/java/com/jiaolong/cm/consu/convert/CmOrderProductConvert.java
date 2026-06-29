package com.jiaolong.cm.consu.convert;

import com.jiaolong.cm.consu.api.dto.OrderProductDto;
import com.jiaolong.cm.consu.api.entity.CmOrderProducts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * desc: 商品类型转换
 * user: pan
 * date: 2024-08-21 17:43
 */
@Mapper
public interface CmOrderProductConvert {
	CmOrderProductConvert INSTANCE = Mappers.getMapper(CmOrderProductConvert.class);

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "createTime", target = "createTime"),
		@Mapping(source = "orderId", target = "orderId"),
		@Mapping(source = "skuId", target = "skuId"),
		@Mapping(source = "amount", target = "amount"),
		@Mapping(source = "productName", target = "productName"),
		@Mapping(source = "deleted", target = "deleted")
	})
	OrderProductDto covertToDto(CmOrderProducts product);
}
