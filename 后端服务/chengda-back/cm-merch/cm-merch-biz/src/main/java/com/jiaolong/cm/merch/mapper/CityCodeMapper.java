package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.dto.CityCodeDto;
import com.jiaolong.cm.merch.api.entity.CityCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * desc: 城市编码数据交互
 * user: pan
 * date: 2024-09-02 16:41
 */
@Mapper
public interface CityCodeMapper extends BaseMapper<CityCode> {
	List<CityCodeDto> getAllList();

	List<CityCode> getChildrenList(@Param("cityPid") Integer cityPid);

    List<CityCodeDto> getListByIds(@Param("cityIds") Set<Integer> cityIds);
}
