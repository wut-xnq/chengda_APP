package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.CmOrderPayment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 支付订单数据交互
 * user: pan
 * date: 2024-10-31 12:28
 */
@Mapper
public interface CmOrderPaymentMapper extends BaseMapper<CmOrderPayment> {
	int countOrderNo(@Param("paymentNo") String paymentNo);

	int countFlowNo(@Param("flowNo") String flowNo);

	CmOrderPayment getOrderByNo(@Param("orderNo") String orderNo);
}
