package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * desc: 字典数据交互
 * user: pan
 * date: 2024-08-21 19:58
 */
@Mapper
public interface DictMapper extends BaseMapper<SysDict> {
}
