package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.SpecDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmSpecification;
import com.jiaolong.cm.merch.api.vo.SpecSearchVo;
import com.jiaolong.cm.merch.api.vo.SpecVo;
import com.jiaolong.cm.merch.convert.CmSpecificationConvert;
import com.jiaolong.cm.merch.mapper.CmCustomerMapper;
import com.jiaolong.cm.merch.mapper.CmSpecMapper;
import com.jiaolong.cm.merch.service.CmSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * desc: 商品规格组服务实现
 * user: pan
 * date: 2024-09-01 13:06
 */
@Service
public class CmSpecServiceImpl extends ServiceImpl<CmSpecMapper, CmSpecification> implements CmSpecService {

	@Autowired
	private CmSpecMapper specMapper;
	@Autowired
	private CmCustomerMapper cmCustomerMapper;

	@Override
	public R<IPage<SpecDto>> getPage(Page page, SpecSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = cmCustomerMapper.selectById(cmUserId);
		if (customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()) {
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<SpecDto> dtoPage = specMapper.getPage(page, vo);
		return R.ok(dtoPage);
	}

	@Override
	public R addSpec(SpecVo vo) {
		CmSpecification cmSpec = CmSpecificationConvert.INSTANCE.convertToEntity(new CmSpecification(), vo);
		cmSpec.setCreateTime(LocalDateTime.now());
		cmSpec.setDeleted(BusinessConstants.DELETED_NO);
		int result = specMapper.insert(cmSpec);
		if (result > 0) {
			return R.ok(cmSpec);
		}
		return R.failed();
	}

	@Override
	public R editSpec(SpecVo vo) {
		Long id = vo.getId();
		CmSpecification cmSpecification = specMapper.selectById(id);
		if (cmSpecification == null) {
			return R.failed(BusinessErrorMessage.MISSING_USERDATA);
		}

		CmSpecification cmSpec = CmSpecificationConvert.INSTANCE.convertToEntity(cmSpecification, vo);
		int result = specMapper.updateById(cmSpec);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R deleteSpec(Long[] ids) {
		List<Long> specIdList = Arrays.stream(ids).collect(Collectors.toList());
		int result = specMapper.deleteSpecById(specIdList);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<List<SpecDto>> getMerchantSpecList(Long merchantId) {
		List<SpecDto> list = specMapper.getSpecListByMerchantId(merchantId);
		return R.ok(list);
	}
}
