package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.merch.api.dto.SpecDto;
import com.jiaolong.cm.merch.api.dto.SpecGroupDto;
import com.jiaolong.cm.merch.api.entity.CmSpecificationGroup;
import com.jiaolong.cm.merch.api.vo.SpecGroupVo;
import com.jiaolong.cm.merch.mapper.CmSpecGroupMapper;
import com.jiaolong.cm.merch.mapper.CmSpecMapper;
import com.jiaolong.cm.merch.service.CmSpecGroupService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * desc: 商品规格组服务实现
 * user: pan
 * date: 2024-09-01 13:06
 */
@Service
public class CmSpecGroupServiceImpl extends ServiceImpl<CmSpecGroupMapper, CmSpecificationGroup> implements CmSpecGroupService {

	@Autowired
	private CmSpecGroupMapper specGroupMapper;
	@Autowired
	private CmSpecMapper specMapper;

	@Override
	public R<List<SpecGroupDto>> getListByMerchantId(Long merchantId) {
		List<SpecGroupDto> dtoList = specGroupMapper.getListByMerchantId(merchantId);
		return R.ok(dtoList);
	}

	@Override
	public R<List<SpecDto>> getListByGroupId(Long groupId) {
		List<SpecDto> dtoList = specMapper.getListByGroupId(groupId);
		return R.ok(dtoList);
	}

	@Override
	public R addSpecGroup(SpecGroupVo vo) {
		Long merchantId = vo.getMerchantId();
		String groupName = vo.getGroupName();
		CmSpecificationGroup entity = specGroupMapper.getByName(merchantId, groupName);
		if (entity != null) {
			if (BusinessConstants.DELETED_YES.equals(entity.getDeleted())) {
				entity.setDeleted(BusinessConstants.DELETED_NO);
				int updateResult = specGroupMapper.updateById(entity);
				if (updateResult > 0) {
					return R.ok(entity);
				}
			} else {
				return R.failed(BusinessErrorMessage.MULTI_USERDATA);
			}
		}

		CmSpecificationGroup specificationGroup = new CmSpecificationGroup();
		specificationGroup.setMerchantId(merchantId);
		specificationGroup.setGroupName(groupName);
		specificationGroup.setCreateTime(LocalDateTime.now());
		specificationGroup.setDeleted(BusinessConstants.DELETED_NO);
		int result = specGroupMapper.insert(specificationGroup);
		if (result > 0) {
			return R.ok(specificationGroup);
		}
		return R.failed();
	}

	@Override
	public R editSpecGroup(SpecGroupVo vo) {
		Long id = vo.getId();
		Long merchantId = vo.getMerchantId();
		String groupName = vo.getGroupName();

		CmSpecificationGroup entity = specGroupMapper.selectById(id);
		if (entity == null) {
			return R.failed(BusinessErrorMessage.MISSING_USERDATA);
		}

		entity.setMerchantId(merchantId);
		entity.setGroupName(groupName);
		entity.setDeleted(BusinessConstants.DELETED_NO);
		int result = specGroupMapper.updateById(entity);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok();
	}

	@Override
	@Transactional
	public R deleteSpecGroup(Long[] ids) {
		/* 校验规格组以及组内的规格是否在使用中 */
		List<Long> specIdList = new ArrayList<>();
		for (Long groupId : ids) {
			List<SpecDto> specDtoList = specMapper.getListByGroupId(groupId);
			List<Long> specIds = specDtoList.stream().map(SpecDto::getId).collect(Collectors.toList());
			specIdList.addAll(specIds);
		}
		if (CollectionUtils.isNotEmpty(specIdList)) {
			int count = specMapper.countInUseSpec(specIdList);
			if (count > 0) {
				return R.failed(BusinessErrorMessage.SPEC_IN_USE);
			}

			/* 删除规格组下的所有规格 */
			int result = specMapper.deleteSpecById(specIdList);
			if (result <= 0) {
				return R.failed();
			}
		}

		/* 删除规格组 */
		int result = specGroupMapper.deleteSpecGroupById(ids);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok();
	}
}
