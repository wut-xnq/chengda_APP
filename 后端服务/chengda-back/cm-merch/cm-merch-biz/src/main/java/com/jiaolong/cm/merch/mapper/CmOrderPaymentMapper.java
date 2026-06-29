package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.merch.api.dto.PaymentOrderDto;
import com.jiaolong.cm.merch.api.dto.StatisticOrderQueryDto;
import com.jiaolong.cm.merch.api.entity.CmOrderPayment;
import com.jiaolong.cm.merch.api.vo.PaymentOrderSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * desc: 支付订单数据交互
 * user: pan
 * date: 2024-09-04 14:54
 */
@Mapper
public interface CmOrderPaymentMapper extends BaseMapper<CmOrderPayment> {
	IPage<PaymentOrderDto> getPageList(Page page, @Param("vo") PaymentOrderSearchVo vo);

	PaymentOrderDto getDetailById(@Param("id") Long id);

	int deletePaymentOrderById(@Param("ids") Long[] ids);

	CmOrderPayment getOrderPaymentRecord(@Param("shoppingOrderNo") String shoppingOrderNo);

	StatisticOrderQueryDto statisticOrderVolume(@Param("merchantId") Long merchantId, @Param("start") LocalDateTime start);
}
