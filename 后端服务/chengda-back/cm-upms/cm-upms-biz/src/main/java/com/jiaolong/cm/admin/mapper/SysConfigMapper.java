package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.entity.SysConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 系统配置数据交互
 * user: pan
 * date: 2024-09-24 14:51
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfiguration> {
	List<SysConfiguration> getList();
}
