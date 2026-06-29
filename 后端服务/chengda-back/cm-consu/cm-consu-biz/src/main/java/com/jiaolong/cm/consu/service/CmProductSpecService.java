package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.ProductSpecDto;
import com.jiaolong.cm.consu.api.entity.CmProductSpec;

import java.util.List;

/**
 * desc: 商品规格管理服务
 * user: pan
 * date: 2024-08-21 10:50
 */
public interface CmProductSpecService extends IService<CmProductSpec> {
	R<List<ProductSpecDto>> getListByProductId(Long productId);
}
