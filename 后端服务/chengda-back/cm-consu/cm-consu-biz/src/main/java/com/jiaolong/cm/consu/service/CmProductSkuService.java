package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.ProductSkuDto;
import com.jiaolong.cm.consu.api.entity.CmProductSku;

import java.util.List;

/**
 * desc: 商品SKU服务接口
 * user: pan
 * date: 2024-08-21 10:40
 */
public interface CmProductSkuService extends IService<CmProductSku> {
	R<List<ProductSkuDto>> getListByProductId(Long productId);
}
