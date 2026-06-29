package com.jiaolong.cm.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.entity.CmOrderShopping;

/**
 * desc: 购物订单服务接口
 * user: pan
 * date: 2024-09-20 16:34
 */
public interface CmOrderShoppingService extends IService<CmOrderShopping> {
	R deleteShoppingOrder(Long[] ids);
}
