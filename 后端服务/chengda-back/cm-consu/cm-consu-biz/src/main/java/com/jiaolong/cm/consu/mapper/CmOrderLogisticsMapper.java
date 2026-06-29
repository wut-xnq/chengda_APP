package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.CmOrderLogistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 商品物流信息数据交互
 * user: pan
 * date: 2024-08-24 10:49
 */
@Mapper
public interface CmOrderLogisticsMapper extends BaseMapper<CmOrderLogistics> {

	CmOrderLogistics getByOrderNo(@Param("orderNo") String orderNo);
}
