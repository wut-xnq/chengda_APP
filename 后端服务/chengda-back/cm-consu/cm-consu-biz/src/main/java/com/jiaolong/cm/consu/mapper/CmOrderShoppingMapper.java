package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.consu.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.consu.api.entity.CmOrderShopping;
import com.jiaolong.cm.consu.api.param.ShoppingOrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 购物订单数据交互
 * user: pan
 * date: 2024-08-22 10:22
 */
@Mapper
public interface CmOrderShoppingMapper extends BaseMapper<CmOrderShopping> {
	IPage<ShoppingOrderDto> selectOrderPage(Page page, @Param("param") ShoppingOrderParam param);

	int delOrderByIds(@Param("ids") Long[] ids);

	int receiveOrder(@Param("id") Long id);

    int countOrderNo(@Param("orderNo") String orderNo);

	CmOrderShopping getOrderByNo(@Param("orderNo") String shoppingOrderNo);
}
