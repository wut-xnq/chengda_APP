package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.merch.api.entity.CmOrderProducts;
import com.jiaolong.cm.merch.api.entity.CmOrderShopping;
import com.jiaolong.cm.merch.api.vo.OrderDeliverVo;
import com.jiaolong.cm.merch.api.vo.ShoppingOrderSearchVo;
import com.jiaolong.cm.common.core.util.R;

import java.util.List;

/**
 * desc: 购物订单服务接口
 * user: pan
 * date: 2024-09-03 15:07
 */
public interface CmOrderShoppingService extends IService<CmOrderShopping> {
	R<IPage<ShoppingOrderDto>> getOrderPage(Page page, ShoppingOrderSearchVo vo);

	R<ShoppingOrderDto> getOrderDetail(Long id);

	R<List<CmOrderProducts>> getProductList(Long id);

	R deliverOrder(OrderDeliverVo vo);

	R deleteOrder(Long[] ids);
}
