package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.CmProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 商品SKU数据交互
 * user: pan
 * date: 2024-08-20 18:59
 */
@Mapper
public interface CmProductSkuMapper extends BaseMapper<CmProductSku> {
	CmProductSku selectOnlySku(@Param("productId") Long productId);
}
