package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.dto.SpecDto;
import com.jiaolong.cm.merch.api.entity.CmSpecification;
import com.jiaolong.cm.merch.api.vo.SpecSearchVo;
import com.jiaolong.cm.merch.api.vo.SpecVo;

import java.util.List;

/**
 * desc: 商品规格组服务接口
 * user: pan
 * date: 2024-09-01 13:06
 */
public interface CmSpecService extends IService<CmSpecification> {
	R<IPage<SpecDto>> getPage(Page page, SpecSearchVo vo);

	R addSpec(SpecVo vo);

	R editSpec(SpecVo vo);

	R deleteSpec(Long[] ids);

	R<List<SpecDto>> getMerchantSpecList(Long merchantId);
}
