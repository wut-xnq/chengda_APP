package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.CmUserViewProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 用户浏览商品记录数据交互
 * user: pan
 * date: 2024-11-09 11:56
 */
@Mapper
public interface CmUserViewProductMapper extends BaseMapper<CmUserViewProduct> {
	CmUserViewProduct getRecord(@Param("userId") Long userId, @Param("productId") Long productId);

	int insertRecord(@Param("record") CmUserViewProduct record);

	int updateViewTime(@Param("record") CmUserViewProduct record);
}
