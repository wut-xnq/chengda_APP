package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * desc: 菜单数据交互
 * user: pan
 * date: 2024-09-14 16:44
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	Set<String> findMenuByRoleId(@Param("roleId") Long roleId);
}
