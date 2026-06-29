package com.jiaolong.cm.consu.convert;

import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.entity.CmTestInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * desc: 质检结果类型转换
 * user: pan
 * date: 2024-08-21 15:48
 */
@Mapper
public interface CmTestInfoConvert {
	CmTestInfoConvert INSTANCE = Mappers.getMapper(CmTestInfoConvert.class);

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "testTime", target = "testTime"),
		@Mapping(source = "fileType", target = "fileType"),
		@Mapping(source = "fileTitle", target = "fileTitle"),
		@Mapping(source = "content", target = "content"),
		@Mapping(source = "createTime", target = "createTime"),
		@Mapping(source = "updateTime", target = "publishDate"),
		@Mapping(source = "testProductIds", target = "testProductIds"),
		@Mapping(source = "testMerchantIds", target = "testMerchantIds"),
		@Mapping(source = "relationFileIds", target = "relationFileIds"),
		@Mapping(source = "agencyName", target = "agencyName"),
		@Mapping(source = "remarks", target = "remarks")
	})
	TestInfoDto convertToDto(CmTestInfo info);
}
