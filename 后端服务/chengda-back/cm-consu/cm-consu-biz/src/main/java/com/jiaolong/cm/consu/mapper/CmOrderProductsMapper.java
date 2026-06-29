package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.dto.OrderProductDto;
import com.jiaolong.cm.consu.api.dto.ProductNameDto;
import com.jiaolong.cm.consu.api.entity.CmOrderProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 订单商品数据交互
 * user: pan
 * date: 2024-08-22 10:24
 */
@Mapper
public interface CmOrderProductsMapper extends BaseMapper<CmOrderProducts> {
	List<OrderProductDto> getOrderProductListByOrderId(@Param("orderId") Long orderId);

	List<ProductNameDto> getOrderProductNameList(@Param("orderId") Long orderId);
}
