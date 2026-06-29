package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.SpecDto;
import com.jiaolong.cm.merch.api.dto.SpecGroupDto;
import com.jiaolong.cm.merch.api.entity.CmSpecificationGroup;
import com.jiaolong.cm.merch.api.vo.SpecGroupVo;
import com.jiaolong.cm.common.core.util.R;

import java.util.List;

/**
 * desc: 商品规格组服务接口
 * user: pan
 * date: 2024-09-01 13:06
 */
public interface CmSpecGroupService extends IService<CmSpecificationGroup> {
	R<List<SpecGroupDto>> getListByMerchantId(Long merchantId);

	R<List<SpecDto>> getListByGroupId(Long groupId);

	R addSpecGroup(SpecGroupVo vo);

	R editSpecGroup(SpecGroupVo vo);

    R deleteSpecGroup(Long[] ids);
}
