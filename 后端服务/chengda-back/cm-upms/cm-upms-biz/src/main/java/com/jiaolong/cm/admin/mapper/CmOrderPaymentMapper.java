package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.entity.CmOrderPayment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 支付订单数据交互
 * user: pan
 * date: 2024-09-20 16:26
 */
@Mapper
public interface CmOrderPaymentMapper extends BaseMapper<CmOrderPayment> {
	int deleteOrderById(@Param("ids") Long[] ids);
}
