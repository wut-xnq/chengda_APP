package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.CmProductStatisticsTrend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 商品热度统计数据交互
 * user: pan
 * date: 2024-10-29 13:54
 */
@Mapper
public interface CmProductStatisticsMapper extends BaseMapper<CmProductStatisticsTrend> {
	CmProductStatisticsTrend getByProductId(@Param("productId") Long productId);

	int updateViewAmount(@Param("trend") CmProductStatisticsTrend statisticsTrend);

	int updateOrderAmount(@Param("trend") CmProductStatisticsTrend statisticsTrend);
}
