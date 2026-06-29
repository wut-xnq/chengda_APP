package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.LogisticsDto;
import com.jiaolong.cm.merch.api.entity.CmMerchantLogistics;
import com.jiaolong.cm.merch.api.entity.KdCompany;
import com.jiaolong.cm.merch.api.vo.LogisticSearchVo;
import com.jiaolong.cm.merch.api.vo.LogisticVo;
import com.jiaolong.cm.common.core.util.R;

import java.util.List;

/**
 * desc: 商户物流方案服务接口
 * user: pan
 * date: 2024-09-03 10:08
 */
public interface CmLogisticsService extends IService<CmMerchantLogistics> {
	R<IPage<LogisticsDto>> getLogisticPage(Page page, LogisticSearchVo vo);

	R<LogisticsDto> getDetail(Long id);

	R addLogistic(LogisticVo vo);

	R editLogistic(LogisticVo vo);

	R deleteLogistic(Long[] ids);

	R<List<KdCompany>> getAllCompanyList();
}
