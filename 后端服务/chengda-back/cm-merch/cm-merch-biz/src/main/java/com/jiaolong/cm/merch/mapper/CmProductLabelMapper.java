package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.entity.CmProductLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * desc: 商品标签数据交互
 * user: pan
 * date: 2024-09-01 12:50
 */
@Mapper
public interface CmProductLabelMapper extends BaseMapper<CmProductLabel> {

	int insertBatch(@Param("productId") Long productId, @Param("labelSet") Set<Long> labelSet);

	void deleteByProductId(@Param("productId") Long productId);
}
