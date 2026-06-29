package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.ProductDto;
import com.jiaolong.cm.merch.api.dto.ProductListDto;
import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.vo.ProductSearchVo;
import com.jiaolong.cm.merch.api.vo.ProductShelveVo;
import com.jiaolong.cm.merch.api.vo.ProductVo;
import com.jiaolong.cm.common.core.util.R;

import java.util.List;

/**
 * desc: 商品服务接口
 * user: pan
 * date: 2024-09-05 18:26
 */
public interface CmProductService extends IService<CmProduct> {
	R<IPage<ProductDto>> getPage(Page page, ProductSearchVo vo);

	R<ProductDto> getDetail(Long id);

	R addProduct(ProductVo vo);

	R editProduct(ProductVo vo);

	R shelveProduct(ProductShelveVo vo);

	R deleteProduct(Long[] ids);

	R<List<ProductListDto>> getMerchantProductList(Long merchantId);
}
