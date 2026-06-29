package com.jiaolong.cm.merch.convert;

import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.vo.ProductVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * desc: 商户标签数据转换实现
 * user: pan
 * date: 2024-09-02 16:03
 */
@Mapper
public interface CmProductConvert {
	CmProductConvert INSTANCE = Mappers.getMapper(CmProductConvert.class);

	@Mappings({
		@Mapping(source = "productName", target = "productName"),
		@Mapping(source = "productCode", target = "productCode"),
		@Mapping(source = "productBrief", target = "productBrief"),
		@Mapping(source = "productText", target = "productText"),
		@Mapping(source = "merchantId", target = "merchantId"),
		@Mapping(source = "productPics", target = "productPics"),
		@Mapping(source = "minPrice", target = "minPrice"),
		@Mapping(source = "ifShipped", target = "ifShipped"),
		@Mapping(source = "logisticId", target = "logisticId"),
		@Mapping(source = "deliver", target = "deliver"),
		@Mapping(source = "shelveArea", target = "shelveArea"),
		@Mapping(source = "labels", target = "labels"),
		@Mapping(source = "productSpecs", target = "productSpecs"),
		@Mapping(source = "brandName", target = "brandName"),
		@Mapping(source = "productClassify", target = "productClassify")
	})
	CmProduct create(ProductVo dto);

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "productName", target = "productName"),
		@Mapping(source = "productCode", target = "productCode"),
		@Mapping(source = "productBrief", target = "productBrief"),
		@Mapping(source = "productText", target = "productText"),
		@Mapping(source = "merchantId", target = "merchantId"),
		@Mapping(source = "productPics", target = "productPics"),
		@Mapping(source = "minPrice", target = "minPrice"),
		@Mapping(source = "ifShipped", target = "ifShipped"),
		@Mapping(source = "logisticId", target = "logisticId"),
		@Mapping(source = "deliver", target = "deliver"),
		@Mapping(source = "shelveArea", target = "shelveArea"),
		@Mapping(source = "labels", target = "labels"),
		@Mapping(source = "productSpecs", target = "productSpecs"),
		@Mapping(source = "brandName", target = "brandName"),
		@Mapping(source = "productClassify", target = "productClassify")
	})
	CmProduct update(ProductVo dto);
}
