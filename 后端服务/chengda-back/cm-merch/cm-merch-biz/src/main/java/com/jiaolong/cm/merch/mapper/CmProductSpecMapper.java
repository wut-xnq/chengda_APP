package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.dto.ProductSpecDto;
import com.jiaolong.cm.merch.api.entity.CmProductSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * desc: 商品规格数据交互
 * user: pan
 * date: 2024-09-01 12:50
 */
@Mapper
public interface CmProductSpecMapper extends BaseMapper<CmProductSpec> {

	int deleteByProductId(@Param("productId") Long productId);

	List<ProductSpecDto> getByProductId(@Param("productId") Long productId);

	int insertBatch(@Param("productId") Long productId, @Param("specSet") Set<Long> specSet);
}
