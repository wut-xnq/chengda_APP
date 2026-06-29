package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.dto.MerchantListDto;
import com.jiaolong.cm.consu.api.entity.CmMerchantInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 商户信息数据交互
 * user: pan
 * date: 2024-08-21 16:29
 */
@Mapper
public interface CmMerchantInfoMapper extends BaseMapper<CmMerchantInfo> {
	List<String> selectNameList(@Param("ids") String[] ids);

    List<MerchantListDto> getNameListAll();

	int updateMerchantScore(@Param("id") Long id, @Param("score") int score);

    CmMerchantInfo getMerchantByPhone(@Param("phone") String phone);
}
