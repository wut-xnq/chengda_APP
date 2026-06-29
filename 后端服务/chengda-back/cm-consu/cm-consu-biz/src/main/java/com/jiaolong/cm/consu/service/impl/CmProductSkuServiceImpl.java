package com.jiaolong.cm.consu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.ProductSkuDto;
import com.jiaolong.cm.consu.api.entity.CmProductSku;
import com.jiaolong.cm.consu.convert.CmProductSkuConvert;
import com.jiaolong.cm.consu.mapper.CmProductSkuMapper;
import com.jiaolong.cm.consu.service.CmProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * desc: 商品SKU服务实现类
 * user: pan
 * date: 2024-08-21 10:41
 */
@Service
public class CmProductSkuServiceImpl extends ServiceImpl<CmProductSkuMapper, CmProductSku> implements CmProductSkuService {

	@Autowired
	private CmProductSkuMapper skuMapper;

	@Override
	public R<List<ProductSkuDto>> getListByProductId(Long productId) {
		LambdaQueryWrapper<CmProductSku> wrapper = new LambdaQueryWrapper();
		wrapper.eq(CmProductSku::getProductId, productId)
				.eq(CmProductSku::getDeleted, "0")
				.eq(CmProductSku::getState, "1");
		List<CmProductSku> cmProductSkus = skuMapper.selectList(wrapper);
		List<ProductSkuDto> skuDtos = cmProductSkus.stream().map(sku -> {
			ProductSkuDto dto = CmProductSkuConvert.INSTANCE.covertToDto(sku);
			dto.setOriginPrice(String.valueOf(sku.getOriginPrice()));
			dto.setCurrentPrice(String.valueOf(sku.getCurrentPrice()));
			return dto;
		}).collect(Collectors.toList());
		return R.ok(skuDtos);
	}
}
