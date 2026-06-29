package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.dto.ProductLabelDto;
import com.jiaolong.cm.consu.api.entity.CmProductLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 商品标签数据交互
 * user: pan
 * date: 2024-08-21 10:58
 */
@Mapper
public interface CmProductLabelMapper extends BaseMapper<CmProductLabel> {
    List<ProductLabelDto> selectListByProductId(@Param("productId") Long productId);
}
