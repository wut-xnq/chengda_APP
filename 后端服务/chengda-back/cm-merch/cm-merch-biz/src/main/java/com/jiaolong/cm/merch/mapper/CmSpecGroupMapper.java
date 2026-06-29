package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.dto.SpecGroupDto;
import com.jiaolong.cm.merch.api.entity.CmSpecificationGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 商品规格组数据交互
 * user: pan
 * date: 2024-09-01 13:03
 */
@Mapper
public interface CmSpecGroupMapper extends BaseMapper<CmSpecificationGroup> {
	List<SpecGroupDto> getListByMerchantId(@Param("merchantId") Long merchantId);

	CmSpecificationGroup getByName(@Param("merchantId") Long merchantId, @Param("groupName") String groupName);

    int deleteSpecGroupById(@Param("ids") Long[] ids);
}
