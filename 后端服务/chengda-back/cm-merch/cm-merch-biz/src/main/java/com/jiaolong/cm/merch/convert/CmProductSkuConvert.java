package com.jiaolong.cm.merch.convert;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jiaolong.cm.merch.api.dto.ProductSkuDto;
import com.jiaolong.cm.merch.api.entity.CmProductSku;
import com.jiaolong.cm.merch.api.vo.ProductSkuVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

/**
 * desc: 商品SKU类型转换
 * user: pan
 * date: 2024-09-01 16:26
 */
@Mapper
public interface CmProductSkuConvert {
	CmProductSkuConvert INSTANCE = Mappers.getMapper(CmProductSkuConvert.class);

	default CmProductSku convertToEntity(CmProductSku productSku, ProductSkuVo vo) {
		if (vo != null) {
			productSku.setProductId(vo.getProductId());
			productSku.setSkuCode(vo.getSkuCode());
			productSku.setSkuName(vo.getSkuName());
			productSku.setAmount(vo.getAmount());
			productSku.setLimitAmount(vo.getLimitAmount());
			productSku.setIfDefault(vo.getIfDefault());
			productSku.setState(vo.getState());
			productSku.setSkuPics(vo.getSkuPics());
			productSku.setRemarks(vo.getRemarks());

			// 处理价格传参
			String originPrice = vo.getOriginPrice();
			if (StringUtils.isNotEmpty(originPrice)) {
				productSku.setOriginPrice(new BigDecimal(originPrice));
			}
			String currentPrice = vo.getCurrentPrice();
			if (StringUtils.isNotEmpty(originPrice)) {
				productSku.setCurrentPrice(new BigDecimal(currentPrice));
			}
		}
		return productSku;
	}

	default ProductSkuDto convertToDto(CmProductSku productSku) {
		ProductSkuDto dto = new ProductSkuDto();
		if (productSku != null) {
			dto.setId(productSku.getId());
			dto.setSkuCode(productSku.getSkuCode());
			dto.setSkuName(productSku.getSkuName());
			dto.setProductId(productSku.getProductId());
			dto.setCreateTime(productSku.getCreateTime());
			dto.setOriginPrice(productSku.getOriginPrice());
			dto.setCurrentPrice(productSku.getCurrentPrice());
			dto.setIfDefault(productSku.getIfDefault());
			dto.setSkuPics(productSku.getSkuPics());
			dto.setAmount(productSku.getAmount());
			dto.setLimitAmount(productSku.getLimitAmount());
			dto.setState(productSku.getState());
			dto.setUpdateTime(productSku.getUpdateTime());
			dto.setSortOrder(productSku.getSortOrder());
			dto.setRemarks(productSku.getRemarks());
		}
		return dto;
	}
}
