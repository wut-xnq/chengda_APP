package com.jiaolong.cm.merch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.api.dto.UserDTO;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.constant.SecurityConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.exception.ErrorCodes;
import com.jiaolong.cm.common.core.util.MsgUtils;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.core.util.RandomStringUtil;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.UserInfoDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmUserVerified;
import com.jiaolong.cm.merch.api.vo.UserInfoVo;
import com.jiaolong.cm.merch.api.vo.UserSearchVo;
import com.jiaolong.cm.merch.convert.CmCustomerConvert;
import com.jiaolong.cm.merch.mapper.CmCustomerMapper;
import com.jiaolong.cm.merch.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.merch.mapper.CmUserVerifiedMapper;
import com.jiaolong.cm.merch.service.CmCustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * desc: 客户服务接口实现
 * user: pan
 * date: 2024-09-14 15:35
 */
@Slf4j
@Service
@AllArgsConstructor
public class CmCustomerServiceImpl extends ServiceImpl<CmCustomerMapper, CmCustomer> implements CmCustomerService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	@Autowired
	private CmCustomerMapper customerMapper;
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;
	@Autowired
	private CmUserVerifiedMapper userVerifiedMapper;

	@Override
	public R<IPage<UserInfoDto>> getUserPage(Page page, UserSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = customerMapper.selectById(cmUserId);
		if (customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()) {
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<UserInfoDto> pageList = customerMapper.getDtoPage(page, vo);
		return R.ok(pageList);
	}

	@Override
	public R<UserInfoDto> getUserInfo(Long userId) {
		UserInfoDto dto = customerMapper.getDtoById(userId);
		return R.ok(dto);
	}

	@Override
	@Transactional
	public R addUser(UserInfoVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		LocalDateTime nowTime = LocalDateTime.now();

		String userPhone = vo.getPhone();
		/* 新增员工 */
		CmCustomer userInfo = CmCustomerConvert.INSTANCE.convertToEntity(new CmCustomer(), vo);
		userInfo.setCreateTime(nowTime);
		userInfo.setNickname(SecurityConstants.PROJECT_PREFIX + RandomStringUtil.random6());
		userInfo.setPassword(ENCODER.encode(userPhone));
		userInfo.setInviterId(cmUserId);
		userInfo.setRoleId(BusinessConstants.GENERAL_USER_ROLE);
		userInfo.setLockFlag(BusinessConstants.LOCKED_NO);
		userInfo.setDelFlag(BusinessConstants.DELETED_NO);
		userInfo.setIfGiftScore(BusinessConstants.GIFT_SCORE_NO);
		userInfo.setUserScore(BusinessConstants.ZERO_SCORE);
		// 将员工与商户id绑定
		String phone = cmUser.getPhone();
		Long merchantId = merchantInfoMapper.getMerchantIdByPhone(phone);
		userInfo.setMerchantId(merchantId);
		int insert = customerMapper.insert(userInfo);
		if (insert <= 0) {
			return R.failed(BusinessErrorMessage.ADD_STAFF_ERROR);
		}

		/* 处理员工发名片业务 */
		String verified = vo.getVerified();
		if (StringUtils.isNotBlank(verified)) {
			CmUserVerified userVerified = new CmUserVerified();
			userVerified.setCreateTime(nowTime);
			userVerified.setUserId(userInfo.getUserId());
			userVerified.setMerchantId(merchantId);
			userVerified.setOperatorId(cmUserId);
			String name = vo.getName();
			if(StringUtils.isNotBlank(name)){
				int length = name.length();
				String substring = name.substring(length - 1);
				userVerified.setNameLastChar(substring);
			}

			if (BusinessConstants.VERIFIED_PASS.equals(verified)) {
				// 认证通过，直接发名片
				userVerified.setVerifiedState(BusinessConstants.VERIFIED_AGREE);
				userVerified.setRemarks(BusinessConstants.VERIFIED_PASS_MESSAGE);
			} else {
				// 未认证
				userVerified.setVerifiedState(BusinessConstants.VERIFIED_PRO);
				userVerified.setRemarks(BusinessConstants.VERIFIED_NONE_MESSAGE);
			}

			int result = userVerifiedMapper.insert(userVerified);
			if (result <= 0) {
				return R.failed(BusinessErrorMessage.ADD_STAFF_VERIFIED_ERROR);
			}
		}
		return R.ok(userInfo);
	}

	@Override
	public R editUser(UserInfoVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long userId = vo.getUserId();
		CmCustomer user = customerMapper.selectById(userId);
		if (user == null) {
			return R.failed(BusinessErrorMessage.NONE_USER);
		}

		CmCustomer userInfo = CmCustomerConvert.INSTANCE.convertToEntity(user, vo);
		// 将员工与商户id绑定
		String phone = cmUser.getPhone();
		Long merchantId = merchantInfoMapper.getMerchantIdByPhone(phone);
		userInfo.setMerchantId(merchantId);
		int result = customerMapper.updateById(userInfo);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	@Transactional
	public R deleteUser(Long[] ids) {
		/* 删除用户信息 */
		int result = customerMapper.deleteUserByIds(ids);
		if (result <= 0) {
			return R.failed();
		}

		/* 删除用户认证信息 */
		result = userVerifiedMapper.deleteUserVerifiedByUserId(ids);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok();
	}

	@Override
	public R changePassword(UserDTO userDto) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		String username = cmUser.getUsername();
		CmCustomer customer = customerMapper.selectById(userDto.getUserId());
		if (Objects.isNull(customer)) {
			return R.failed("用户不存在");
		}

		if (StrUtil.isEmpty(userDto.getPassword())) {
			return R.failed("原密码不能为空");
		}

		if (!ENCODER.matches(userDto.getPassword(), customer.getPassword())) {
			log.info("原密码错误，修改个人信息失败:{}", username);
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_UPDATE_PASSWORDERROR));
		}

		if (StrUtil.isEmpty(userDto.getNewpassword1())) {
			return R.failed("新密码不能为空");
		}
		String password = ENCODER.encode(userDto.getNewpassword1());

		this.update(Wrappers.<CmCustomer>lambdaUpdate()
				.set(CmCustomer::getPassword, password)
				.eq(CmCustomer::getUserId, customer.getUserId()));
		return R.ok();
	}

}
