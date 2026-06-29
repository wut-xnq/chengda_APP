package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.PaymentOrderDto;
import com.jiaolong.cm.merch.api.entity.CmOrderPayment;
import com.jiaolong.cm.merch.api.vo.PaymentOrderSearchVo;
import com.jiaolong.cm.common.core.util.R;

/**
 * desc: 支付订单服务接口
 * user: pan
 * date: 2024-09-04 14:57
 */
public interface CmOrderPaymentService extends IService<CmOrderPayment> {
	R<IPage<PaymentOrderDto>> getPage(Page page, PaymentOrderSearchVo vo);

	R<PaymentOrderDto> getDetail(Long id);

	R deletePaymentOrder(Long[] ids);
}
