package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.ProductDto;
import com.jiaolong.cm.consu.api.entity.CmProduct;
import com.jiaolong.cm.consu.api.param.ProductParam;

/**
 * desc: 商品服务接口
 * user: pan
 * date: 2024-08-20 15:27
 */
public interface CmProductService extends IService<CmProduct> {
	R<IPage<ProductDto>> getPage(Page page, ProductParam param);

	R<ProductDto> getDetails(Long id);
}
