package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.dto.MerchantInfoDto;
import com.jiaolong.cm.merch.api.dto.StatisticMerchantInfoDto;
import com.jiaolong.cm.merch.api.entity.CmMerchantInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 商户信息数据交互
 * user: pan
 * date: 2024-08-28 10:08
 */
@Mapper
public interface CmMerchantInfoMapper extends BaseMapper<CmMerchantInfo> {
	List<MerchantInfoDto> getMerchantListByPhone(@Param("phone") String phone);

	MerchantInfoDto getMerchantInfoById(@Param("id") Long id);

	Long getMerchantIdByPhone(@Param("phone") String phone);

    StatisticMerchantInfoDto getMerchantInfoByPhone(@Param("phone") String phone);

}
