package com.jiaolong.cm.consu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.entity.CmSpecificationGroup;
import com.jiaolong.cm.consu.mapper.CmSpecificationGroupMapper;
import com.jiaolong.cm.consu.service.CmSpecificationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc: 商品规格组服务实现类
 * user: pan
 * date: 2024-08-21 11:03
 */
@Service
public class CmSpecificationGroupServiceImpl extends ServiceImpl<CmSpecificationGroupMapper, CmSpecificationGroup> implements CmSpecificationGroupService {

	@Autowired
	private CmSpecificationGroupMapper specGroupMapper;


	@Override
	public R<List<CmSpecificationGroup>> getSpecGroupList(Long merchantId) {
		LambdaQueryWrapper<CmSpecificationGroup> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CmSpecificationGroup::getMerchantId,merchantId);
		List<CmSpecificationGroup> list = specGroupMapper.selectList(wrapper);
		return R.ok(list);
	}
}
