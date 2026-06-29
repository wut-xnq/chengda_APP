package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.merch.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.merch.api.entity.CmOrderProducts;
import com.jiaolong.cm.merch.api.entity.CmOrderShopping;
import com.jiaolong.cm.merch.api.vo.ShoppingOrderSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 购物订单数据交互
 * user: pan
 * date: 2024-09-03 15:06
 */
@Mapper
public interface CmOrderShoppingMapper extends BaseMapper<CmOrderShopping> {
	IPage<ShoppingOrderDto> getPageList(Page page, @Param("vo") ShoppingOrderSearchVo vo);

	ShoppingOrderDto getDetailById(@Param("id") Long id);

	List<CmOrderProducts> getProductListById(@Param("id") Long id);

	int deleteOrderById(@Param("ids") Long[] ids);
}
