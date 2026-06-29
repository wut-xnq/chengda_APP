package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.DeptInfoDto;
import com.jiaolong.cm.merch.api.entity.SysDept;
import com.jiaolong.cm.merch.api.vo.DeptInfoVo;
import com.jiaolong.cm.merch.api.vo.DeptSearchVo;
import com.jiaolong.cm.common.core.util.R;

/**
 * desc: 部门管理服务接口
 * user: pan
 * date: 2024-08-29 10:40
 */
public interface SysDeptService extends IService<SysDept> {
	R<IPage<DeptInfoDto>> getDeptPage(Page page, DeptSearchVo vo);

	R<DeptInfoDto> getDeptInfo(Long deptId);

	R addDept(DeptInfoVo vo);

	R editDept(DeptInfoVo vo);

	R deleteDept(Long[] ids);
}
