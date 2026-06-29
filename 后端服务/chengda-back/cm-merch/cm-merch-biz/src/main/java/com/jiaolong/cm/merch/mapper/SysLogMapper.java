package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 系统日志数据交互
 * user: pan
 * date: 2024-09-02 16:41
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

	SysLog getUserLastLoginRecord(@Param("username") String username);
}
