package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.CmMerchantLogistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 商户物流方案数据交互
 * user: pan
 * date: 2024-09-26 11:58
 */
@Mapper
public interface CmMerchantLogisticsMapper extends BaseMapper<CmMerchantLogistics> {
	CmMerchantLogistics getDefaultLogisticsByMerchantId(@Param("merchantId") Long merchantId);
}
