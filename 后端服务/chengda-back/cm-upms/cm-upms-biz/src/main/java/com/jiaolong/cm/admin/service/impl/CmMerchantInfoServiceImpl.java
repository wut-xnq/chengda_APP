package com.jiaolong.cm.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.api.dto.MerchantDto;
import com.jiaolong.cm.admin.api.vo.MerchantSearchVo;
import com.jiaolong.cm.admin.mapper.CmCustomerMapper;
import com.jiaolong.cm.admin.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.admin.mapper.CmProductMapper;
import com.jiaolong.cm.admin.service.CmMerchantInfoService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.constant.SecurityConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.core.util.RandomStringUtil;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.MerchantInfoDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmMerchantInfo;
import com.jiaolong.cm.merch.api.vo.MerchantInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 商户信息服务实现
 * user: pan
 * date: 2024-09-20 10:33
 */
@Service
@AllArgsConstructor
public class CmMerchantInfoServiceImpl extends ServiceImpl<CmMerchantInfoMapper, CmMerchantInfo> implements CmMerchantInfoService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;
	@Autowired
	private CmCustomerMapper customerMapper;
	@Autowired
	private CmProductMapper productMapper;

	@Override
	public R<IPage<MerchantInfoDto>> getMerchantPage(Page page, MerchantSearchVo vo) {
		IPage<MerchantInfoDto> dtoList = merchantInfoMapper.getMerchantPage(page, vo);
		return R.ok(dtoList);
	}

	@Override
	public R<MerchantInfoDto> getMerchantInfo(Long id) {
		MerchantInfoDto dto = merchantInfoMapper.getMerchantInfoById(id);
		return R.ok(dto);
	}

	@Override
	@Transactional
	public R addMerchantInfo(MerchantInfoVo vo) {
		String chargePersonName = vo.getChargePersonName();
		String chargePersonPhone = vo.getChargePersonPhone();
		if (StringUtils.isBlank(chargePersonName) || StringUtils.isBlank(chargePersonPhone)) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}
		/* 新增商户信息 */
		CmMerchantInfo info = new CmMerchantInfo();
		info.setMerchantName(vo.getMerchantName());
		info.setCreateTime(LocalDateTime.now());
		info.setChargePersonName(chargePersonName);
		info.setChargePersonPhone(chargePersonPhone);
		info.setLogo(vo.getLogo());
		info.setCreditCode(vo.getCreditCode());
		info.setProvinceCode(vo.getProvinceCode());
		info.setCityCode(vo.getCityCode());
		info.setCountryCode(vo.getCountryCode());
		info.setAddress(vo.getAddress());
		info.setStaffCount(vo.getStaffCount());
		info.setState(BusinessConstants.STATE_ENABLE);
		info.setVerified(BusinessConstants.VERIFIED_PASS);
		info.setDeleted(BusinessConstants.DELETED_NO);
		int result = merchantInfoMapper.insert(info);
		if (result <= 0) {
			return R.failed(BusinessErrorMessage.MERCHANT_ADD_ERROR);
		}

		/* 获取 phone 对应的客户账号（roleId = BusinessConstants.MERCHANT_USER_ROLE），并绑定商户 */
		CmCustomer customer = customerMapper.getCustomerByPhone(chargePersonPhone);
		if (customer == null) {
			customer = new CmCustomer();
			customer.setName(chargePersonName);
			customer.setPhone(chargePersonPhone);
			customer.setCreateTime(LocalDateTime.now());
			customer.setNickname(SecurityConstants.PROJECT_PREFIX + RandomStringUtil.random6());
			customer.setUsername(chargePersonPhone);
			customer.setPassword(ENCODER.encode(chargePersonPhone));
			customer.setMerchantId(info.getId());
			customer.setRoleId(BusinessConstants.MERCHANT_USER_ROLE);
			customer.setIfGiftScore(BusinessConstants.GIFT_SCORE_NO);
			customer.setUserScore(BusinessConstants.ZERO_SCORE);
			customer.setInvitationCode(uniqueCode());
			customer.setLockFlag(BusinessConstants.LOCKED_NO);
			customer.setDelFlag(BusinessConstants.DELETED_NO);
			int insertResult = customerMapper.insert(customer);
			if (insertResult <= 0) {
				return R.failed(BusinessErrorMessage.MERCHANT_USER_ADD_ERROR);
			}
		} else {
			Long roleId = customer.getRoleId();
			// 如果原来的用户是普通用户，更新原账号角色为商户用户
			if (BusinessConstants.GENERAL_USER_ROLE == roleId.longValue()) {
				customer.setMerchantId(info.getId());
				customer.setRoleId(BusinessConstants.MERCHANT_USER_ROLE);
				customer.setUserScore(BusinessConstants.ZERO_SCORE);
				int updateResult = customerMapper.updateById(customer);
				if (updateResult <= 0) {
					return R.failed();
				}
			} else {
				return R.failed(BusinessErrorMessage.ACCOUNT_CONFLICT);
			}
		}
		return R.ok();
	}

	@Override
	@Transactional
	public R disabledMerchant(String merchantId, String state) {
		if (StringUtils.isBlank(merchantId.trim()) || StringUtils.isBlank(state)) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		Long id = Long.valueOf(merchantId.trim());
		CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(id);
		if (merchantInfo == null) {
			return R.failed(BusinessErrorMessage.NONE_MERCHANT);
		}

		/* 禁用商户时，该商户所有上架售卖中的商品，立即下架 */
		if (BusinessConstants.STATE_DISABLE.equals(state)) {
			productMapper.unShelveMerchantProduct(id);
		}

		/* 变更商户状态 */
		if (merchantInfo.getState().equals(state)) {
			return R.ok();
		}

		int result = merchantInfoMapper.disabled(id, state);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok();
	}

	@Override
	public R deleteMerchant(Long[] ids) {
		int result = merchantInfoMapper.deleteMerchantById(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<List<MerchantDto>> getMerchantList() {
		List<MerchantDto> list = merchantInfoMapper.getCheckedMerchantList();
		return R.ok(list);
	}

	@Override
	public R resetMerchantPassword(String merchantId) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long id = Long.valueOf(merchantId);
		CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(id);
		if (merchantInfo == null) {
			return R.failed(BusinessErrorMessage.MISSING_USERDATA);
		}

		String phone = merchantInfo.getChargePersonPhone();
		CmCustomer merchantUser = customerMapper.getCustomerByPhone(phone);
		merchantUser.setPassword(ENCODER.encode(phone));
		merchantUser.setMerchantId(id);
		merchantUser.setRoleId(BusinessConstants.MERCHANT_USER_ROLE);
		int result = customerMapper.updateById(merchantUser);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok();
	}

	/**
	 * 生成个人邀请码
	 *
	 * @return
	 */
	private String uniqueCode() {
		String invitationCode = RandomStringUtil.random6();
		int count = customerMapper.countInvitationCode(invitationCode);
		if (count == 0) {
			return invitationCode;
		} else {
			return uniqueCode();
		}
	}
}
