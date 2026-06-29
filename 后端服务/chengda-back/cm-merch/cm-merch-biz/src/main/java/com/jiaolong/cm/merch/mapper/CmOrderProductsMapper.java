package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.dto.OrderProductDto;
import com.jiaolong.cm.merch.api.entity.CmOrderProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 订单商品数据交互
 * user: pan
 * date: 2024-11-12 18:00
 */
@Mapper
public interface CmOrderProductsMapper extends BaseMapper<CmOrderProducts> {
	List<OrderProductDto> selectOrderProductList(@Param("orderId") Long orderId);
}
