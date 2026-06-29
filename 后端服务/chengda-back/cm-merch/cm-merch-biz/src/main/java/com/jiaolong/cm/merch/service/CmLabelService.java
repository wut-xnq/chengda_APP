package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.LabelDto;
import com.jiaolong.cm.merch.api.entity.CmLabel;
import com.jiaolong.cm.merch.api.vo.LabelSearchVo;
import com.jiaolong.cm.merch.api.vo.LabelVo;
import com.jiaolong.cm.common.core.util.R;

import java.util.List;

/**
 * desc: 商户标签服务接口
 * user: pan
 * date: 2024-09-02 15:49
 */
public interface CmLabelService extends IService<CmLabel> {
	R<IPage<LabelDto>> getPage(Page page, LabelSearchVo vo);

	R<LabelDto> getDetail(Long id);

	R addLabel(LabelVo vo);

	R editLabel(LabelVo vo);

	R deleteLabel(Long[] ids);

    R<List<LabelDto>> getMerchantLabelList(Long merchantId);
}
