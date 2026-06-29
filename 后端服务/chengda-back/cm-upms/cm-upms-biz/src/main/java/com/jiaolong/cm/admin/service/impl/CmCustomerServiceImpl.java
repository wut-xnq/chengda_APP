package com.jiaolong.cm.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.api.dto.CustomerDto;
import com.jiaolong.cm.admin.api.dto.CustomerScoreDto;
import com.jiaolong.cm.admin.api.dto.UserDTO;
import com.jiaolong.cm.admin.api.dto.UserInfo;
import com.jiaolong.cm.admin.api.entity.SysUser;
import com.jiaolong.cm.admin.api.vo.CustomerSearchVo;
import com.jiaolong.cm.admin.api.vo.CustomerVO;
import com.jiaolong.cm.admin.convert.SysUserConvert;
import com.jiaolong.cm.admin.mapper.CmCustomerMapper;
import com.jiaolong.cm.admin.mapper.SysMenuMapper;
import com.jiaolong.cm.admin.service.CmCustomerService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.constant.CommonConstants;
import com.jiaolong.cm.common.core.constant.SecurityConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.exception.ErrorCodes;
import com.jiaolong.cm.common.core.util.MsgUtils;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.core.util.RandomStringUtil;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * desc: 客户服务实现
 * user: pan
 * date: 2024-09-14 18:23
 */
@Slf4j
@Service
@AllArgsConstructor
public class CmCustomerServiceImpl extends ServiceImpl<CmCustomerMapper, CmCustomer> implements CmCustomerService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	@Autowired
	private CmCustomerMapper customerMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public R<UserInfo> getCustomerInfo(String username, String inviteCode) {

		log.info("=========username：" + username + "=========邀请码=========：" + inviteCode);
		/* 通过手机号码获取用户信息 */
		CmCustomer user = this.getOne(Wrappers.<CmCustomer>query()
				.lambda()
				.eq(StrUtil.isNotBlank(username), CmCustomer::getUsername, username));

		if (user == null) {
			// 此手机号对应的客户信息不存在，先注册，后返回客户信息
			String randomStr = RandomStringUtil.random6();
			user = new CmCustomer();
			user.setPhone(username);
			user.setNickname(SecurityConstants.PROJECT_PREFIX + randomStr);
			user.setUsername(username);
			user.setPassword(ENCODER.encode(RandomStringUtil.random18()));
			user.setCreateTime(LocalDateTime.now());
			user.setRoleId(BusinessConstants.GENERAL_USER_ROLE);
			user.setVerified(BusinessConstants.NOT_VERIFIED);
			user.setIfGiftScore(BusinessConstants.GIFT_SCORE_NO);
			user.setLockFlag(BusinessConstants.LOCKED_NO);
			user.setDelFlag(CommonConstants.STATUS_NORMAL);
			if (StringUtils.isNotEmpty(inviteCode)) {
				// 给邀请人增加积分
				CustomerScoreDto inviterDto = customerMapper.getInviterIdByCode(inviteCode);
				if (inviterDto != null) {
					Long inviterId = inviterDto.getUserId();
					int userScore = inviterDto.getUserScore();
					int inviterScore = userScore + BusinessConstants.HUNDRED_SCORE;
					customerMapper.updateUserScore(inviterId, inviterScore);

					// 新注册用户填充“邀请人”字段值
					user.setInviterId(inviterId);
				}
			}
			customerMapper.insert(user);
		}

		/* 组装返回结果 */
		UserInfo info = new UserInfo();
		SysUser sysUser = SysUserConvert.INSTANCE.convertCustomerToUser(user);
		info.setSysUser(sysUser);
		// 填充扩展字段：用户是否收到积分转换请求（ifGiftScore）、邀请人（inviter）
		info.setIfGiftScore(user.getIfGiftScore());
		Long inviterId = user.getInviterId();
		if (inviterId != null) {
			CustomerDto inviter = customerMapper.getDetailsById(inviterId);
			info.setInviter(inviter.getName());
		}
		// 设置角色列表 （ID）
		Long roleId = user.getRoleId();
		info.setRoles(new Long[]{roleId});

		// 设置权限列表（menu.permission）
		Set<String> permissions = sysMenuMapper.findPermisionsByRoleId(roleId).stream().filter(permission -> StringUtils.isNotEmpty(permission)).collect(Collectors.toSet());
		info.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return R.ok(info);
	}

	@Override
	public R<IPage<CustomerDto>> getCustomerPage(Page page, CustomerSearchVo vo) {
		IPage<CustomerDto> dtoList = customerMapper.getPageList(page, vo);
		return R.ok(dtoList);
	}

	@Override
	public R<CustomerDto> getDetails(Long userId) {
		CustomerDto dto = customerMapper.getDetailsById(userId);
		return R.ok(dto);
	}

	@Override
	public R disabledCustomer(String userId, String lockFlag) {
		if (StringUtils.isBlank(userId.trim()) || StringUtils.isBlank(lockFlag)) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		Long customerId = Long.valueOf(userId.trim());
		CmCustomer customer = customerMapper.selectById(customerId);
		if (customer == null) {
			return R.failed(BusinessErrorMessage.NONE_USER);
		}

		if (customer.getLockFlag().equals(lockFlag)) {
			return R.ok();
		}

		int result = customerMapper.disabled(customerId, lockFlag);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<UserInfo> getMerchantUserInfo(String username) {
		/* 通过手机号码获取用户信息 */
		CmCustomer user = this.getOne(Wrappers.<CmCustomer>query()
				.lambda()
				.eq(StrUtil.isNotBlank(username), CmCustomer::getUsername, username));

		if (user == null) {
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_QUERY_ERROR));
		}
		/* 组装返回结果 */
		UserInfo info = new UserInfo();
		SysUser sysUser = SysUserConvert.INSTANCE.convertCustomerToUser(user);
		info.setSysUser(sysUser);
		// 填充扩展字段：商户主键（merchantId）
		info.setMerchantId(user.getMerchantId());
		// 设置角色列表 （ID）
		Long roleId = user.getRoleId();
		info.setRoles(new Long[]{roleId});

		// 设置权限列表（menu.permission）
		Set<String> permissions = sysMenuMapper.findPermisionsByRoleId(roleId).stream().filter(permission -> StringUtils.isNotEmpty(permission)).collect(Collectors.toSet());
		info.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return R.ok(info);
	}

	@Override
	public R<CustomerVO> getCustomerDetails(Long userId) {
		CustomerVO vo = customerMapper.getCustomerInfoVo(userId);
		return R.ok(vo);
	}

	@Override
	public R updateCustomer(UserDTO userDto) {
		Long userId = userDto.getUserId();
		CmCustomer customer = customerMapper.selectById(userId);
		if (customer != null) {
			String phone = userDto.getPhone();
			String name = userDto.getName();
			String nickname = userDto.getNickname();
			String email = userDto.getEmail();
			String avatar = userDto.getAvatar();

			if (StringUtils.isNotBlank(phone)) {
				customer.setPhone(phone);
			}
			if (StringUtils.isNotBlank(name)) {
				customer.setName(name);
			}
			if (StringUtils.isNotBlank(nickname)) {
				customer.setNickname(nickname);
			}
			if (StringUtils.isNotBlank(email)) {
				customer.setEmail(email);
			}
			if (StringUtils.isNotBlank(avatar)) {
				customer.setAvatar(avatar);
			}

			int result = customerMapper.updateById(customer);
			if (result <= 0) {
				return R.failed();
			}
		}
		return R.ok();
	}

	@Override
	public R resetCustomerPwd(String userId) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		CmCustomer customer = customerMapper.selectById(userId);
		String phone = customer.getPhone();
		customer.setPassword(ENCODER.encode(phone));
		int result = customerMapper.updateById(customer);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok();
	}
}
