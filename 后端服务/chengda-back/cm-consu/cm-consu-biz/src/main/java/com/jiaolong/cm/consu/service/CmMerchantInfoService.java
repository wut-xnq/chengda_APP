package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.MerchantListDto;
import com.jiaolong.cm.consu.api.dto.StaffDto;
import com.jiaolong.cm.consu.api.dto.UserPersonalDto;
import com.jiaolong.cm.consu.api.entity.CmMerchantInfo;

import java.util.List;

/**
 * desc: 商户管理服务接口
 * user: pan
 * date: 2024-08-23 14:27
 */
public interface CmMerchantInfoService extends IService<CmMerchantInfo> {

	R<List<MerchantListDto>> getNameListAll();

	R<IPage<UserPersonalDto>> getUserPage(Page page, Long merchantId);

    R<StaffDto> getUserDetails(Long userId);
}
