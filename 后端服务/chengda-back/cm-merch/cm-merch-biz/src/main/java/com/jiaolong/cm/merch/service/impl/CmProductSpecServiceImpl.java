package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.dto.ProductSpecDto;
import com.jiaolong.cm.merch.api.entity.CmProductSpec;
import com.jiaolong.cm.merch.mapper.*;
import com.jiaolong.cm.merch.service.CmProductSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc: 商品规格服务实现
 * user: pan
 * date: 2024-09-01 12:54
 */
@Service
public class CmProductSpecServiceImpl extends ServiceImpl<CmProductSpecMapper, CmProductSpec> implements CmProductSpecService {

	@Autowired
	private CmProductSpecMapper productSpecMapper;

	@Override
	public R<List<ProductSpecDto>> getProductSpecListByProductId(Long productId) {
		List<ProductSpecDto> dtoList = productSpecMapper.getByProductId(productId);
		return R.ok(dtoList);
	}
}
