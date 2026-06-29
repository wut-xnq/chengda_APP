package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.entity.CmDelistingProduct;
import org.apache.ibatis.annotations.Mapper;

/**
 * desc: 平台下架商品记录数据交互
 * user: pan
 * date: 2024-11-20 10:58
 */
@Mapper
public interface CmDelistingProductMapper extends BaseMapper<CmDelistingProduct> {
}
