package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.SysDictItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * desc: 字典项数据交互
 * user: pan
 * date: 2024-08-21 20:00
 */
@Mapper
public interface DictItemMapper extends BaseMapper<SysDictItem> {
}
