package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.ProductSkuDto;
import com.jiaolong.cm.merch.api.entity.CmProductSku;
import com.jiaolong.cm.merch.api.vo.ProductSkuSearchVo;
import com.jiaolong.cm.merch.api.vo.ProductSkuVo;
import com.jiaolong.cm.common.core.util.R;

/**
 * desc: 商品SKU服务接口
 * user: pan
 * date: 2024-09-01 16:02
 */
public interface CmProductSkuService extends IService<CmProductSku> {
	R<IPage<ProductSkuDto>> getPage(Page page, ProductSkuSearchVo vo);

	R<ProductSkuDto> getDetail(Long id);

	R addSku(ProductSkuVo vo);

	R editSku(ProductSkuVo vo);

	R deleteSku(Long[] ids);
}
