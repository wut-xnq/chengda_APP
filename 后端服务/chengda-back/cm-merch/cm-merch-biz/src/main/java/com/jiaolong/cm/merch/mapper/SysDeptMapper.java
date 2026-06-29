package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.merch.api.dto.DeptInfoDto;
import com.jiaolong.cm.merch.api.entity.SysDept;
import com.jiaolong.cm.merch.api.vo.DeptSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 部门数据交互
 * user: pan
 * date: 2024-08-29 10:38
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
	IPage<DeptInfoDto> getDtoPage(Page page, @Param("vo") DeptSearchVo vo);

	DeptInfoDto getDtoById(@Param("deptId") Long deptId);

	int deleteDeptByIds(@Param("ids") Long[] ids);
}
