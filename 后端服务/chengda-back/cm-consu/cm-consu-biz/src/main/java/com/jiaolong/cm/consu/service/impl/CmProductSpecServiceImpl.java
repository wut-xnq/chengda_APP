package com.jiaolong.cm.consu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.ProductSpecDto;
import com.jiaolong.cm.consu.api.entity.CmProductSpec;
import com.jiaolong.cm.consu.mapper.CmProductSpecMapper;
import com.jiaolong.cm.consu.service.CmProductSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc: 商品规格管理服务实现类
 * user: pan
 * date: 2024-08-21 10:51
 */
@Service
public class CmProductSpecServiceImpl extends ServiceImpl<CmProductSpecMapper, CmProductSpec> implements CmProductSpecService {

	@Autowired
	private CmProductSpecMapper productSpecMapper;

	@Override
	public R<List<ProductSpecDto>> getListByProductId(Long productId) {
		List<ProductSpecDto> productSpecDtos = productSpecMapper.selectListByProductId(productId);
		return R.ok(productSpecDtos);
	}
}
