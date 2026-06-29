package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.dto.ProductSpecDto;
import com.jiaolong.cm.consu.api.entity.CmProductSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 商品规格数据交互
 * user: pan
 * date: 2024-08-21 10:52
 */
@Mapper
public interface CmProductSpecMapper extends BaseMapper<CmProductSpec> {
    List<ProductSpecDto> selectListByProductId(@Param("productId") Long productId);
}
