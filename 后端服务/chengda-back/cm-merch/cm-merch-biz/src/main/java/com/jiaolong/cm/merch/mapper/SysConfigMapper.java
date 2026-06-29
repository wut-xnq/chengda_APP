package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.entity.SysConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 系统配置数据交互
 * user: pan
 * date: 2024-09-06 10:55
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfiguration> {
}
