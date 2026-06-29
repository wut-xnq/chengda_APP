package com.jiaolong.cm.consu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.MerchantListDto;
import com.jiaolong.cm.consu.api.dto.StaffDto;
import com.jiaolong.cm.consu.api.dto.UserPersonalDto;
import com.jiaolong.cm.consu.api.entity.CmCustomer;
import com.jiaolong.cm.consu.api.entity.CmMerchantInfo;
import com.jiaolong.cm.consu.convert.CmCustomerConvert;
import com.jiaolong.cm.consu.mapper.CmCustomerMapper;
import com.jiaolong.cm.consu.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.consu.service.CmMerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: 商户信息管理服务实现
 * user: pan
 * date: 2024-08-23 14:27
 */
@Service
public class CmMerchantInfoServiceImpl extends ServiceImpl<CmMerchantInfoMapper, CmMerchantInfo> implements CmMerchantInfoService {
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;
	@Autowired
	private CmCustomerMapper userMapper;

	@Override
	public R<List<MerchantListDto>> getNameListAll() {
		List<MerchantListDto> dtoList = merchantInfoMapper.getNameListAll();
		return R.ok(dtoList);
	}

	@Override
	public R<IPage<UserPersonalDto>> getUserPage(Page page, Long merchantId) {
		List<UserPersonalDto> dtoList = new ArrayList<>();
		LambdaQueryWrapper<CmCustomer> wrapper = new LambdaQueryWrapper();
		wrapper.eq(CmCustomer::getMerchantId, merchantId)
				.eq(CmCustomer::getVerified, "2")
				.eq(CmCustomer::getDelFlag, "0");
		Page pageList = userMapper.selectPage(page, wrapper);
		List<CmCustomer> records = pageList.getRecords();
		for (CmCustomer user : records) {
			UserPersonalDto personalDto = CmCustomerConvert.INSTANCE.covertToPersonalDto(user);
			dtoList.add(personalDto);
		}
		pageList.setRecords(dtoList);
		return R.ok(pageList);
	}

	@Override
	public R<StaffDto> getUserDetails(Long userId) {
		StaffDto dto = userMapper.getStaffDetails(userId);
		return R.ok(dto);
	}
}
