package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.dto.SettlementOrderDto;
import com.jiaolong.cm.merch.api.entity.CmOrderShopping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 购物订单数据交互
 * user: pan
 * date: 2024-09-20 16:31
 */
@Mapper
public interface CmOrderShoppingMapper extends BaseMapper<CmOrderShopping> {
	int deleteOrderById(@Param("ids") Long[] ids);

	List<SettlementOrderDto> getUnreceivedOrders(@Param("offsetTime") LocalDateTime offsetTime);

	List<SettlementOrderDto> getCustomerReceivedOrders(@Param("offsetTime") LocalDateTime offsetTime);

	int updateOrderSettlementAmount(@Param("id") Long id, @Param("amount") BigDecimal settlementAmount);
}
