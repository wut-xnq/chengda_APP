package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.consu.api.entity.CmOrderShopping;
import com.jiaolong.cm.consu.api.param.ShoppingOrderParam;
import com.jiaolong.cm.consu.api.param.ShoppingOrderVo;

/**
 * desc: 购物订单服务接口
 * user: pan
 * date: 2024-08-22 10:20
 */
public interface CmOrderShoppingService extends IService<CmOrderShopping> {
	R<IPage<ShoppingOrderDto>> getUserOrderPage(Page page, ShoppingOrderParam param);

	R<ShoppingOrderDto> getDetails(Long id);

	R deleteUserOrder(Long[] ids);

	R receiveOrder(Long id);

    R<ShoppingOrderDto> addShoppingOrder(ShoppingOrderVo vo);

	R<ShoppingOrderDto> updateRecipientAddress(Long id, Long userAddressId);

    R promotOrderDeliver(Long orderId);
}
