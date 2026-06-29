package com.jiaolong.cm.consu.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.consu.api.dto.UserPersonalDto;
import com.jiaolong.cm.consu.api.dto.UserScoreDto;
import com.jiaolong.cm.consu.api.dto.UserVerifiedDto;
import com.jiaolong.cm.consu.api.entity.CmCustomer;
import com.jiaolong.cm.consu.api.entity.CmMerchantInfo;
import com.jiaolong.cm.consu.api.entity.CmUserVerified;
import com.jiaolong.cm.consu.api.param.CustomerPasswordVo;
import com.jiaolong.cm.consu.api.param.CustomerVo;
import com.jiaolong.cm.consu.api.param.TransScoreParam;
import com.jiaolong.cm.consu.convert.CmCustomerConvert;
import com.jiaolong.cm.consu.mapper.CmCustomerMapper;
import com.jiaolong.cm.consu.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.consu.mapper.CmUserVerifiedMapper;
import com.jiaolong.cm.consu.service.CmCustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 用户管理服务实现类
 * user: pan
 * date: 2024-08-23 16:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class CmCustomerServcieImpl extends ServiceImpl<CmCustomerMapper, CmCustomer> implements CmCustomerService {

	@Autowired
	private CmCustomerMapper userMapper;
	@Autowired
	private CmUserVerifiedMapper userVerifiedMapper;
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	@Override
	public R<UserPersonalDto> getDetails(Long id) {
		log.info("getDetails reqInfo: " + id);
		UserPersonalDto dto = userMapper.getDetailsById(id);
		String verified = dto.getVerified();
		if (BusinessConstants.VERIFIED_PASS.equals(verified)) {
			Long merchantId = dto.getMerchantId();
			CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(merchantId);
			dto.setInviter(merchantInfo.getMerchantName());
		} else {
			Long inviterId = dto.getInviterId();
			if (inviterId != null) {
				UserPersonalDto inviterUser = userMapper.getDetailsById(inviterId);
				dto.setInviter(inviterUser.getName());
			}
		}
		return R.ok(dto);
	}

	@Override
	public R<UserPersonalDto> getDetailsByPhone(String phone) {
		UserPersonalDto dto = userMapper.getByPhone(phone);
		String verified = dto.getVerified();
		if (BusinessConstants.VERIFIED_PASS.equals(verified)) {
			Long merchantId = dto.getMerchantId();
			CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(merchantId);
			dto.setInviter(merchantInfo.getMerchantName());
		} else {
			Long inviterId = dto.getInviterId();
			if (inviterId != null) {
				UserPersonalDto inviterUser = userMapper.getDetailsById(inviterId);
				dto.setInviter(inviterUser.getName());
			}
		}
		return R.ok(dto);
	}

	@Override
	public R<List<UserScoreDto>> getListForTranScore(Long id) {
		CmCustomer cmCustomer = userMapper.selectById(id);
		if (cmCustomer == null) {
			return R.failed(BusinessErrorMessage.NONE_USER);
		}
		// 角色为普通用户，操作对象是该用户在平台邀请的其他用户列表；角色为商户，操作对象是该企业员工
		List<UserScoreDto> userDtoList = userMapper.getTranUserList(id);
		return R.ok(userDtoList);
	}

	@Override
	public R reqTransScore(Long[] ids) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long userId = cmUser.getId();
		CmCustomer customer = userMapper.selectById(userId);
		if (StringUtils.isBlank(customer.getName())) {
			return R.failed(BusinessErrorMessage.LACK_OF_USERINFO);
		}

		int result = userMapper.updateGiftScore(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	@Transactional
	public R resTransScore(TransScoreParam param) {
		Long userId = param.getUserId();
		Integer agreed = param.getAgreed();

		/* 校验用户 */
		CmCustomer cmCustomer = userMapper.selectById(userId);
		if (cmCustomer == null) {
			return R.failed(BusinessErrorMessage.NONE_USER);
		}

		/* 校验邀请人 */
		UserPersonalDto inviterDto = null;
		Long inviterId = cmCustomer.getInviterId();
		if (inviterId != null) {
			// 邀请人为用户
			inviterDto = userMapper.getDetailsById(inviterId);
		} else {
			// 邀请人为商户
			String verified = cmCustomer.getVerified();
			if (BusinessConstants.VERIFIED_PASS.equals(verified)) {
				Long merchantId = cmCustomer.getMerchantId();
				CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(merchantId);
				inviterDto = userMapper.getByPhone(merchantInfo.getChargePersonPhone());
			}
		}
		if (inviterDto == null) {
			return R.failed(BusinessErrorMessage.NONE_INVITER);
		}

		/* 同意积分转换 */
		if (agreed.intValue() == BusinessConstants.AGREED) {
			int userScore = cmCustomer.getUserScore();
			/* 1、更新邀请者的积分 */
			// 1-1、邀请人是商户账号，同步商户积分
			if (inviterDto.getRoleId() == BusinessConstants.MERCHANT_USER) {
				Long merchantId = cmCustomer.getMerchantId();
				CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(merchantId);
				int inviterScore = merchantInfo.getMerchantScore();
				int score = inviterScore + userScore;
				int updateMerchantScore = merchantInfoMapper.updateMerchantScore(inviterDto.getMerchantId(), score);
				if (updateMerchantScore < 0) {
					return R.failed();
				}
			}

			// 1-2、邀请人是客户账号，同步客户积分
			if (inviterDto.getRoleId() == BusinessConstants.GENERAL_USER) {
				int inviterScore = inviterDto.getUserScore();
				int score = inviterScore + userScore;
				int updateResult = userMapper.updateUserScore(inviterDto.getUserId(), score);
				if (updateResult < 0) {
					return R.failed();
				}
			}

			// 2、更新用户 if_gift_score 字段、user_score 字段
			cmCustomer.setUserScore(BusinessConstants.ZERO_SCORE);
			cmCustomer.setIfGiftScore(BusinessConstants.GIFT_SCORE_NO);
			int updateResult = userMapper.updateById(cmCustomer);
			if (updateResult < 0) {
				return R.failed();
			}
		}

		/* 拒绝积分转换 */
		if (agreed.intValue() == BusinessConstants.REJECTED) {
			// 更新用户 if_gift_score 字段
			cmCustomer.setIfGiftScore(BusinessConstants.GIFT_SCORE_NO);
			int result = userMapper.updateById(cmCustomer);
			if (result < 0) {
				return R.failed();
			}
		}
		return R.ok();
	}

	@Override
	public R<UserVerifiedDto> getIdentResult(Long userId) {
		UserVerifiedDto dto = userVerifiedMapper.getVerifiedResultByUserId(userId);
		return R.ok(dto);
	}

	@Override
	public R identApply(String nameLastChar, Long merchantId) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long userId = cmUser.getId();
		UserVerifiedDto verifiedResult = userVerifiedMapper.getVerifiedResultByUserId(userId);
		/* 没有认证数据，新增一条“未认证”的数据 */
		if (verifiedResult == null) {
			CmUserVerified userVerified = new CmUserVerified(userId, merchantId, BusinessConstants.VERIFIED_PRO, nameLastChar);
			int result = userVerifiedMapper.insert(userVerified);
			if (result <= 0) {
				return R.failed();
			}
		} else {
			/* 有认证数据，不能重复提交，但对于“已删除”、“认证不通过”的数据，允许覆盖 */
			Integer verifiedState = verifiedResult.getVerifiedState();
			String deleted = verifiedResult.getDeleted();
			if (BusinessConstants.DELETED_YES.equals(deleted)) {
				// 原来的认证信息已被删除，覆盖原来的数据
				int result = userVerifiedMapper.initUserVerified(userId, merchantId, nameLastChar);
				if (result <= 0) {
					return R.failed();
				}
				return R.ok();
			}

			if (verifiedState.intValue() == BusinessConstants.VERIFIED_PRO) {
				return R.failed(BusinessErrorMessage.VERIFIED_SUBMIT);
			}
			if (verifiedState.intValue() == BusinessConstants.VERIFIED_AGREE) {
				return R.failed(BusinessErrorMessage.VERIFIED_AGREE_ERROR);
			}
			if (verifiedState.intValue() == BusinessConstants.VERIFIED_REJECT) {
				Long verifiedDataMerchantId = verifiedResult.getMerchantId();
				if (!merchantId.equals(verifiedDataMerchantId)) {
					// 原来的认证信息未被删除，但新提交的数据为其他商户的认证数据，覆盖原来的数据
					int result = userVerifiedMapper.initUserVerified(userId, merchantId, nameLastChar);
					if (result <= 0) {
						return R.failed();
					}
				}
			}
		}

		return R.ok();
	}

	@Override
	public R editCustomerInfo(CustomerVo vo) {
		Long userId = vo.getUserId();
		if (userId == null) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		CmCustomer cmCustomer = userMapper.selectById(userId);
		if (cmCustomer == null) {
			return R.failed(BusinessErrorMessage.NONE_USER);
		}

		String nickname = vo.getNickname();
		String avatar = vo.getAvatar();
		String name = vo.getName();
		String gender = vo.getGender();
		Integer age = vo.getAge();
		LocalDateTime birthday = vo.getBirthday();
		String hobby = vo.getHobby();
		String major = vo.getMajor();
		String brief = vo.getBrief();

		if (StringUtils.isNotBlank(nickname)) {
			cmCustomer.setNickname(nickname);
		}
		if (StringUtils.isNotBlank(avatar)) {
			cmCustomer.setAvatar(avatar);
		}
		if (StringUtils.isNotBlank(name)) {
			cmCustomer.setName(name);
		}
		if (StringUtils.isNotBlank(gender)) {
			cmCustomer.setGender(gender);
		}
		if (age != null) {
			cmCustomer.setAge(age);
		}
		if (birthday != null) {
			cmCustomer.setBirthday(birthday);
		}
		if (StringUtils.isNotBlank(hobby)) {
			cmCustomer.setHobby(hobby);
		}
		if (StringUtils.isNotBlank(major)) {
			cmCustomer.setMajor(major);
		}
		if (StringUtils.isNotBlank(brief)) {
			cmCustomer.setBrief(brief);
		}
		int updateResult = userMapper.updateById(cmCustomer);
		if (updateResult > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R changePassword(CustomerPasswordVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long userId = cmUser.getId();
		String password = vo.getPassword();
		String confirmPassword = vo.getConfirmPassword();
		if (StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		if (!password.equals(confirmPassword)) {
			return R.failed();
		}

		String newPassword = ENCODER.encode(password);
		userMapper.update(Wrappers.<CmCustomer>lambdaUpdate()
				.set(CmCustomer::getPassword, newPassword)
				.eq(CmCustomer::getUserId, userId));
		return R.ok();
	}
}
