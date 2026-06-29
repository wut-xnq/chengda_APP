package com.jiaolong.cm.consu.convert;

import com.jiaolong.cm.consu.api.dto.ProductSkuDto;
import com.jiaolong.cm.consu.api.entity.CmProductSku;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * desc: 商品SKU类型转换
 * user: pan
 * date: 2024-08-21 17:43
 */
@Mapper
public interface CmProductSkuConvert {
	CmProductSkuConvert INSTANCE = Mappers.getMapper(CmProductSkuConvert.class);

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "skuName", target = "skuName"),
		@Mapping(source = "productId", target = "productId"),
		@Mapping(source = "createTime", target = "createTime"),
		@Mapping(source = "ifDefault", target = "ifDefault"),
		@Mapping(source = "skuPics", target = "skuPics"),
		@Mapping(source = "amount", target = "amount"),
		@Mapping(source = "limitAmount", target = "limitAmount"),
		@Mapping(source = "updateTime", target = "updateTime"),
		@Mapping(source = "sortOrder", target = "sortOrder")
	})
	ProductSkuDto covertToDto(CmProductSku productSku);
}
