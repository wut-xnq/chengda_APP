package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.dto.ProductSpecDto;
import com.jiaolong.cm.merch.api.entity.CmProductSpec;
import com.jiaolong.cm.merch.api.vo.ProductSpecVo;

import java.util.List;

/**
 * desc: 商品规格服务接口
 * user: pan
 * date: 2024-10-23 20:44
 */
public interface CmProductSpecService extends IService<CmProductSpec> {

	R<List<ProductSpecDto>> getProductSpecListByProductId(Long productId);
}
