package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.merch.api.dto.DeptInfoDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.SysDept;
import com.jiaolong.cm.merch.api.vo.DeptInfoVo;
import com.jiaolong.cm.merch.api.vo.DeptSearchVo;
import com.jiaolong.cm.merch.mapper.CmCustomerMapper;
import com.jiaolong.cm.merch.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.merch.mapper.SysDeptMapper;
import com.jiaolong.cm.merch.service.SysDeptService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * desc: 部门管理服务实现
 * user: pan
 * date: 2024-08-29 10:40
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

	@Autowired
	private SysDeptMapper deptMapper;
	@Autowired
	private CmCustomerMapper customerMapper;

	@Override
	public R<IPage<DeptInfoDto>> getDeptPage(Page page, DeptSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if(cmUser == null){
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = customerMapper.selectById(cmUserId);
		if(customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()){
			vo.setMerchantId(String.valueOf(customer.getMerchantId()));
		}

		IPage<DeptInfoDto> dtoPage = deptMapper.getDtoPage(page, vo);
		return R.ok(dtoPage);
	}

	@Override
	public R<DeptInfoDto> getDeptInfo(Long deptId) {
		DeptInfoDto dto = deptMapper.getDtoById(deptId);
		return R.ok(dto);
	}

	@Override
	public R addDept(DeptInfoVo vo) {
		CmUser user = SecurityUtils.getUser();
		// 填充部门属性
		SysDept deptInfo = new SysDept();
		deptInfo.setName(vo.getName());
		deptInfo.setDeptNumber(vo.getDeptNumber());
		deptInfo.setMerchantId(vo.getMerchantId());
		deptInfo.setCreateBy(user.getName());
		deptInfo.setCreateTime(LocalDateTime.now());
		deptInfo.setDelFlag(BusinessConstants.DELETED_NO);
		// 新增部门
		int result = deptMapper.insert(deptInfo);
		if (result > 0) {
			return R.ok(deptInfo);
		}
		return R.failed();
	}

	@Override
	public R editDept(DeptInfoVo vo) {
		Long deptId = vo.getDeptId();
		if (deptId == null) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		SysDept deptInfo = deptMapper.selectById(deptId);
		if (deptInfo == null) {
			return R.failed(BusinessErrorMessage.NONE_DEPTARTMENT);
		}

		deptInfo.setName(vo.getName());
		deptInfo.setDeptNumber(vo.getDeptNumber());
		deptInfo.setMerchantId(vo.getMerchantId());
		// 编辑部门
		int result = deptMapper.updateById(deptInfo);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R deleteDept(Long[] ids) {
		int result = deptMapper.deleteDeptByIds(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}
}
