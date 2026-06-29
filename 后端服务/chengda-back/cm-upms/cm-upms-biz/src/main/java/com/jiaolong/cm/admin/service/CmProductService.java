package com.jiaolong.cm.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.vo.ProductShelveVo;

/**
 * desc:
 * user: pan
 * date: 2024-10-28 10:20
 */
public interface CmProductService extends IService<CmProduct> {
	R shelveProduct(ProductShelveVo vo);
}
