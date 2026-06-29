package com.jiaolong.cm.merch.service.impl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.merch.api.dto.*;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmMerchantInfo;
import com.jiaolong.cm.merch.api.entity.SysConfiguration;
import com.jiaolong.cm.merch.api.entity.SysLog;
import com.jiaolong.cm.merch.api.vo.MerchantInfoVo;
import com.jiaolong.cm.merch.convert.CmMerchantInfoConvert;
import com.jiaolong.cm.merch.mapper.*;
import com.jiaolong.cm.merch.service.CmMerchantInfoService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * desc: 商户信息服务接口
 * user: pan
 * date: 2024-08-28 10:12
 */
@Service
public class CmMerchantInfoServiceImpl extends ServiceImpl<CmMerchantInfoMapper, CmMerchantInfo> implements CmMerchantInfoService {

	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;
	@Autowired
	private CmCustomerMapper customerMapper;
	@Autowired
	private SysLogMapper sysLogMapper;
	@Autowired
	private CmProductMapper productMapper;
	@Autowired
	private CmOrderPaymentMapper orderPaymentMapper;
	@Autowired
	private SysConfigMapper configMapper;

	@Override
	public R<List<MerchantInfoDto>> getInfoListByPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			CmUser user = SecurityUtils.getUser();
			phone = user.getPhone();
		}
		List<MerchantInfoDto> dtoList = merchantInfoMapper.getMerchantListByPhone(phone);
		return R.ok(dtoList);
	}

	@Override
	public R<MerchantInfoDto> getInfoById(Long id) {
		MerchantInfoDto dto = merchantInfoMapper.getMerchantInfoById(id);
		return R.ok(dto);
	}

	@Override
	@Transactional
	public R addMerchantInfo(MerchantInfoVo vo) {
		// 获取当前登录用户
		CmUser user = SecurityUtils.getUser();
		if (user == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}
		// 组装实体类
		Long chargePersonId = user.getId();
		if (StringUtils.isEmpty(vo.getChargePersonName())) {
			vo.setChargePersonName(user.getName());
		}
		if (StringUtils.isEmpty(vo.getChargePersonPhone())) {
			vo.setChargePersonName(user.getPhone());
		}

		CmMerchantInfo info = CmMerchantInfoConvert.INSTANCE.convertToEntity(new CmMerchantInfo(), vo);
		info.setCreateTime(LocalDateTime.now());
		info.setState(BusinessConstants.STATE_ENABLE);
		info.setVerified(BusinessConstants.VERIFIED);
		info.setDeleted(BusinessConstants.DELETED_NO);
		int result = merchantInfoMapper.insert(info);
		if (result > 0) {
			// 给登录的商户账号，绑定商户id
			customerMapper.updateMerchantId(chargePersonId, info.getId());
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R editMerchantInfo(MerchantInfoVo vo) {
		// 获取当前登录用户
		CmUser user = SecurityUtils.getUser();
		if (user == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}
		// 获取商户信息数据
		Long id = vo.getId();
		CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(id);
		if (merchantInfo == null) {
			return R.failed(BusinessErrorMessage.NONE_MERCHANT);
		}
		// 更新数据
		CmMerchantInfo info = CmMerchantInfoConvert.INSTANCE.convertToEntity(merchantInfo, vo);
		int result = merchantInfoMapper.updateById(info);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<StatisticMerchantInfoDto> getMerchantBasicInfo() {
		// 获取当前登录用户
		CmUser user = SecurityUtils.getUser();
		if (user == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		/* 获取商户基础数据 */
		String phone = user.getPhone();
		StatisticMerchantInfoDto info = merchantInfoMapper.getMerchantInfoByPhone(phone);

		/* 填充用户最后一次的登录数据 */
		SysLog sysLog = sysLogMapper.getUserLastLoginRecord(user.getUsername());
		info.setLastLoginTime(sysLog.getCreateTime());
		info.setLastLoginArea(sysLog.getRemoteAddr());

		/* 填充免费展现量属性 */
		SysConfiguration sysConfig = configMapper.selectById(BusinessConstants.SYS_DEFAULT_CONFIG);
		Integer conversionMolecule = sysConfig.getConversionMolecule();
		Integer conversionDenominator = sysConfig.getConversionDenominator();
		BigDecimal molecule = new BigDecimal(conversionMolecule);
		BigDecimal denominator = new BigDecimal(conversionDenominator);
		BigDecimal percentMulti100 = molecule.multiply(new BigDecimal(BusinessConstants.ONE_HUNDRED)).divide(denominator, 0, RoundingMode.DOWN);

		Integer score = info.getMerchantScore();
		if (score == null) {
			score = 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(score).append("*").append(percentMulti100).append("%=");
		if (score != null && score.intValue() > 0) {
			BigDecimal scoreStr = new BigDecimal(score);
			BigDecimal formulaValue = scoreStr.multiply(molecule).divide(denominator, 0, RoundingMode.DOWN);
			sb.append(formulaValue);
		} else {
			sb.append("0");
		}
		info.setDisplayQuantityFormula(sb.toString());
		return R.ok(info);
	}

	@Override
	public R<List<StaffScoreDto>> getStaffScoreList() {
		// 获取当前登录用户
		CmUser user = SecurityUtils.getUser();
		if (user == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		String phone = user.getPhone();
		Long merchantId = merchantInfoMapper.getMerchantIdByPhone(phone);
		List<StaffScoreDto> staffScoreList = customerMapper.getStaffScoreList(merchantId);
		return R.ok(staffScoreList);
	}

	@Override
	public R reqTransScore(String[] ids) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long userId = cmUser.getId();
		CmCustomer customer = customerMapper.selectById(userId);
		if (StringUtils.isBlank(customer.getName())) {
			return R.failed(BusinessErrorMessage.LACK_OF_USERINFO);
		}

		List<Long> idList = Arrays.stream(ids).map(e -> Long.valueOf(e)).collect(Collectors.toList());
		int result = customerMapper.updateGiftScore(idList);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<List<StatisticProductDto>> getProductStatisticList() {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long merchantId = merchantInfoMapper.getMerchantIdByPhone(cmUser.getPhone());

		/* 统计本周商品销量 */
		Calendar now = CalendarUtil.calendar();
		Calendar thisWeekStart = CalendarUtil.beginOfWeek(now);
		LocalDateTime start = CalendarUtil.toLocalDateTime(thisWeekStart);
		List<StatisticProductQueryDto> statisticList = productMapper.statisticProductVolume(merchantId, start, 10);

		/* 统计今日商品销量 TOP10 */
		Calendar today = CalendarUtil.calendar();
		Calendar thisDayStart = CalendarUtil.beginOfDay(today);
		LocalDateTime start0 = CalendarUtil.toLocalDateTime(thisDayStart);
		List<StatisticProductQueryDto> list = productMapper.statisticProductVolume(merchantId, start0, null);
		Map<Long, StatisticProductQueryDto> statisticMap = list.stream().collect(Collectors.toMap(StatisticProductQueryDto::getProductId, dto -> dto));

		/* 组装返回结果 */
		List<StatisticProductDto> resultList = new ArrayList<>();
		for (StatisticProductQueryDto queryDto : statisticList) {
			StatisticProductDto dto = new StatisticProductDto();
			Long productId = queryDto.getProductId();
			dto.setProductId(productId);
			dto.setProductName(queryDto.getProductName());
			dto.setWeekSalesVolume(queryDto.getVolume());
			StatisticProductQueryDto productQueryDto = statisticMap.get(productId);
			if (productQueryDto != null) {
				dto.setTodaySalesVolume(productQueryDto.getVolume());
			} else {
				dto.setTodaySalesVolume(0);
			}
			resultList.add(dto);
		}

		return R.ok(resultList);
	}

	@Override
	public R<StatisticOrderDto> getOrderStatisticList() {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long merchantId = merchantInfoMapper.getMerchantIdByPhone(cmUser.getPhone());

		/* 统计本月收益 */
		Calendar now = CalendarUtil.calendar();
		Calendar thisMonthStart = CalendarUtil.beginOfMonth(now);
		LocalDateTime start = CalendarUtil.toLocalDateTime(thisMonthStart);
		StatisticOrderQueryDto monthStatistic = orderPaymentMapper.statisticOrderVolume(merchantId, start);

		/* 统计今日收益 */
		Calendar today = CalendarUtil.calendar();
		Calendar thisDayStart = CalendarUtil.beginOfDay(today);
		LocalDateTime start0 = CalendarUtil.toLocalDateTime(thisDayStart);
		StatisticOrderQueryDto todayStatistic = orderPaymentMapper.statisticOrderVolume(merchantId, start0);

		/* 组装返回结果 */
		StatisticOrderDto dto = new StatisticOrderDto();
		dto.setMerchantId(merchantId);
		dto.setMonthIncome(monthStatistic.getIncome());
		dto.setMonthOrderCount(monthStatistic.getOrderCount());
		dto.setTodayIncome(todayStatistic.getIncome());
		dto.setTodayOrderCount(todayStatistic.getOrderCount());
		return R.ok(dto);
	}
}
