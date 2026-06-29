package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.entity.CmTestInfo;
import com.jiaolong.cm.consu.api.param.TestInfoParam;

import java.util.List;

/**
 * desc: 质检管理服务接口
 * user: pan
 * date: 2024-08-21 10:45
 */
public interface CmTestInfoService extends IService<CmTestInfo> {

	R<IPage<TestInfoDto>> getPage(Page page, TestInfoParam param);

	R<TestInfoDto> getDetails(Long id);

	R<TestInfoDto> getProductExamination(Long productId);

	R<List<TestInfoDto>> getMerchantExamList(Long merchantId);
}
