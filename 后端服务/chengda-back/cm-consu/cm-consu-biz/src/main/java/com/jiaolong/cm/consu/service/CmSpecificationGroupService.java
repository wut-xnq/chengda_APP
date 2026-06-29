package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.entity.CmSpecificationGroup;

import java.util.List;

/**
 * desc: 商品规格组服务
 * user: pan
 * date: 2024-08-21 11:02
 */
public interface CmSpecificationGroupService extends IService<CmSpecificationGroup> {
	R<List<CmSpecificationGroup>> getSpecGroupList(Long merchantId);
}
