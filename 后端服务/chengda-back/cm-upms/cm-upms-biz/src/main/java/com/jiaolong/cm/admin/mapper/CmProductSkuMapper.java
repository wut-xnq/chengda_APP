package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.dto.ProductSkuDto;
import com.jiaolong.cm.merch.api.entity.CmProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 商品SKU数据交互
 * user: pan
 * date: 2024-09-01 16:00
 */
@Mapper
public interface CmProductSkuMapper extends BaseMapper<CmProductSku> {

	List<ProductSkuDto> getSkuListByProductId(@Param("productId") Long productId);
}
