package com.jiaolong.cm.consu.service;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.GoodsLogisticTraceDto;
import com.jiaolong.cm.consu.api.param.TraceFeeVo;

/**
 * desc: 物流跟踪服务接口
 * user: pan
 * date: 2024-10-17 14:46
 */
public interface CmLogisticService {
	R<GoodsLogisticTraceDto> traceLogistics(Long orderId);

    R traceLogisticsFee(TraceFeeVo vo);
}
