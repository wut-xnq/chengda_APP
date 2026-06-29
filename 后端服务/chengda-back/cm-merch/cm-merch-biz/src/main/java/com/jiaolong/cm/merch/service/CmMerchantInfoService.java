package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.*;
import com.jiaolong.cm.merch.api.entity.CmMerchantInfo;
import com.jiaolong.cm.merch.api.vo.MerchantInfoVo;
import com.jiaolong.cm.common.core.util.R;

import java.util.List;

/**
 * desc: 商户信息服务接口
 * user: pan
 * date: 2024-08-28 10:11
 */
public interface CmMerchantInfoService extends IService<CmMerchantInfo> {
	R<List<MerchantInfoDto>> getInfoListByPhone(String phone);

	R<MerchantInfoDto> getInfoById(Long id);

	R addMerchantInfo(MerchantInfoVo vo);

	R editMerchantInfo(MerchantInfoVo vo);

    R<StatisticMerchantInfoDto> getMerchantBasicInfo();

	R<List<StaffScoreDto>> getStaffScoreList();

    R reqTransScore(String[] ids);

	R<List<StatisticProductDto>> getProductStatisticList();

	R<StatisticOrderDto> getOrderStatisticList();
}
