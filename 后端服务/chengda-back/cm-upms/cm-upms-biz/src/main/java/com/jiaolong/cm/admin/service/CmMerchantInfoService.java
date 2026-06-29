package com.jiaolong.cm.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.admin.api.dto.MerchantDto;
import com.jiaolong.cm.admin.api.vo.MerchantSearchVo;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.dto.MerchantInfoDto;
import com.jiaolong.cm.merch.api.entity.CmMerchantInfo;
import com.jiaolong.cm.merch.api.vo.MerchantInfoVo;

import java.util.List;

/**
 * desc: 商户信息服务接口
 * user: pan
 * date: 2024-09-20 10:32
 */
public interface CmMerchantInfoService extends IService<CmMerchantInfo> {

	R<IPage<MerchantInfoDto>> getMerchantPage(Page page, MerchantSearchVo vo);

	R<MerchantInfoDto> getMerchantInfo(Long id);

	R addMerchantInfo(MerchantInfoVo vo);

	R disabledMerchant(String merchantId, String state);

	R deleteMerchant(Long[] ids);

    R<List<MerchantDto>> getMerchantList();

    R resetMerchantPassword(String merchantId);
}
