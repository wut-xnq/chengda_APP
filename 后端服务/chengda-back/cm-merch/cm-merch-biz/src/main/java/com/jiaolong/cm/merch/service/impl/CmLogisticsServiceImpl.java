package com.jiaolong.cm.merch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.CityCodeDto;
import com.jiaolong.cm.merch.api.dto.LogisticsDto;
import com.jiaolong.cm.merch.api.entity.CityCode;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmMerchantLogistics;
import com.jiaolong.cm.merch.api.entity.KdCompany;
import com.jiaolong.cm.merch.api.vo.LogisticSearchVo;
import com.jiaolong.cm.merch.api.vo.LogisticVo;
import com.jiaolong.cm.merch.convert.CmMerchantLogisticConvert;
import com.jiaolong.cm.merch.mapper.CityCodeMapper;
import com.jiaolong.cm.merch.mapper.CmCustomerMapper;
import com.jiaolong.cm.merch.mapper.CmLogisticsMapper;
import com.jiaolong.cm.merch.mapper.KdCompanyMapper;
import com.jiaolong.cm.merch.service.CmLogisticsService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * desc: 商户物流方案服务实现
 * user: pan
 * date: 2024-09-03 10:09
 */
@Service
public class CmLogisticsServiceImpl extends ServiceImpl<CmLogisticsMapper, CmMerchantLogistics> implements CmLogisticsService {

	@Autowired
	private CmLogisticsMapper logisticsMapper;
	@Autowired
	private CmCustomerMapper cmCustomerMapper;
	@Autowired
	private KdCompanyMapper kdCompanyMapper;

	@Override
	public R<IPage<LogisticsDto>> getLogisticPage(Page page, LogisticSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = cmCustomerMapper.selectById(cmUserId);
		if (customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()) {
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<LogisticsDto> dtoPage = logisticsMapper.getPageList(page, vo);
		return R.ok(dtoPage);
	}

	@Override
	public R<LogisticsDto> getDetail(Long id) {
		CmMerchantLogistics merchantLogistics = logisticsMapper.selectById(id);
		LogisticsDto dto = CmMerchantLogisticConvert.INSTANCE.convertToDto(merchantLogistics);
		return R.ok(dto);
	}

	@Override
	public R addLogistic(LogisticVo vo) {
		CmMerchantLogistics logistics = CmMerchantLogisticConvert.INSTANCE.create(vo);
		// 处理包邮地区字符串（去除空的错误数据）
		String region = logistics.getRegion();
		if (StringUtils.isNotBlank(region)) {
			Set<String> cityIds = Arrays.stream(region.split(StrUtil.COMMA)).filter(e -> StringUtils.isNotBlank(e.trim())).collect(Collectors.toSet());
			StringBuffer sb = new StringBuffer();
			for (String str : cityIds) {
				sb.append(str).append(StrUtil.COMMA);
			}
			String regionStr = sb.toString();
			int length = regionStr.length();
			logistics.setRegion(regionStr.substring(0, length - 1));
		}

		logistics.setCreateTime(LocalDateTime.now());
		logistics.setDeleted(BusinessConstants.DELETED_NO);
		int result = logisticsMapper.insert(logistics);
		if (result > 0) {
			return R.ok(logistics);
		}
		return R.failed();
	}

	@Override
	public R editLogistic(LogisticVo vo) {
		Long id = vo.getId();
		CmMerchantLogistics merchantLogistics = logisticsMapper.selectById(id);
		if (merchantLogistics == null) {
			return R.failed(BusinessErrorMessage.MISSING_USERDATA);
		}

		CmMerchantLogistics logistics = CmMerchantLogisticConvert.INSTANCE.update(vo);
		logistics.setUpdateTime(LocalDateTime.now());
		int result = logisticsMapper.updateById(logistics);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R deleteLogistic(Long[] ids) {
		int result = logisticsMapper.deleteLogisticById(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<List<KdCompany>> getAllCompanyList() {
		List<KdCompany> list = kdCompanyMapper.getAll();
		return R.ok(list);
	}

}
