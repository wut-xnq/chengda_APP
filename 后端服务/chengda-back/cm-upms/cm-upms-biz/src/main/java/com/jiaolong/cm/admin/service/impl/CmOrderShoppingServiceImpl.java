package com.jiaolong.cm.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.mapper.CmOrderShoppingMapper;
import com.jiaolong.cm.admin.service.CmOrderShoppingService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.entity.CmOrderShopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * desc: 购物订单服务实现
 * user: pan
 * date: 2024-09-20 16:35
 */
@Service
public class CmOrderShoppingServiceImpl extends ServiceImpl<CmOrderShoppingMapper, CmOrderShopping> implements CmOrderShoppingService {

	@Autowired
	private CmOrderShoppingMapper orderShoppingMapper;

	@Override
	public R deleteShoppingOrder(Long[] ids) {
		int result = orderShoppingMapper.deleteOrderById(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}
}
