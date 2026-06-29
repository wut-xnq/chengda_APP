package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.util.RandomStringUtil;
import com.jiaolong.cm.merch.api.dto.UserInfoDto;
import com.jiaolong.cm.merch.api.dto.UserVerifiedDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmUserVerified;
import com.jiaolong.cm.merch.api.vo.UserVerifiedAuditVo;
import com.jiaolong.cm.merch.api.vo.UserVerifiedSearchVo;
import com.jiaolong.cm.merch.mapper.CmCustomerMapper;
import com.jiaolong.cm.merch.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.merch.mapper.CmUserVerifiedMapper;
import com.jiaolong.cm.merch.service.CmUserVerifiedService;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 员工认证服务实现
 * user: pan
 * date: 2024-08-29 16:50
 */
@Service
public class CmUserVerifiedServiceImpl extends ServiceImpl<CmUserVerifiedMapper, CmUserVerified> implements CmUserVerifiedService {

	@Autowired
	private CmUserVerifiedMapper userVerifiedMapper;
	@Autowired
	private CmCustomerMapper customerMapper;
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;

	@Override
	public R<IPage<UserVerifiedDto>> getUserVerifiedPage(Page page, UserVerifiedSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = customerMapper.selectById(cmUserId);
		if (customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()) {
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<UserVerifiedDto> dtoPage = userVerifiedMapper.getUserVerifiedPage(page, vo);
		List<UserVerifiedDto> records = dtoPage.getRecords();
		if (CollectionUtils.isNotEmpty(records)) {
			for (UserVerifiedDto dto : records) {
				String username = dto.getUsername();
				if (StringUtils.isNotBlank(username)) {
					String finalName = "**" + username;
					dto.setUsername(finalName);
				}
			}
		}
		dtoPage.setRecords(records);
		return R.ok(dtoPage);
	}

	@Override
	@Transactional
	public R auditUserVerified(UserVerifiedAuditVo vo) {
		CmUser user = SecurityUtils.getUser();
		if (user == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long id = vo.getId();
		Long userId = vo.getUserId();
		Integer verifiedState = vo.getVerifiedState();
		String remarks = vo.getRemarks();

		CmUserVerified userVerified = userVerifiedMapper.selectById(id);
		if (userVerified == null) {
			return R.failed(BusinessErrorMessage.NONE_VERIFIED);
		}

		CmCustomer customer = customerMapper.selectById(userId);
		if (customer == null) {
			return R.failed(BusinessErrorMessage.NONE_USER);
		}

		/* 更新用户验证信息结果 */
		userVerified.setVerifiedState(verifiedState);
		userVerified.setRemarks(remarks);
		userVerified.setUpdateTime(LocalDateTime.now());
		userVerified.setOperatorId(user.getId());
		int result = userVerifiedMapper.updateById(userVerified);
		if (result <= 0) {
			return R.failed();
		}

		/* 更新用户信息 */
		if (verifiedState.intValue() == BusinessConstants.VERIFIED_AGREE) {
			// 如果认证通过，更新用户所属企业字段、填充用户邀请码
			String phone = user.getPhone();
			Long merchantId = merchantInfoMapper.getMerchantIdByPhone(phone);
			customer.setMerchantId(merchantId);
			customer.setInvitationCode(uniqueCode());
		}
		customer.setVerified(String.valueOf(verifiedState));
		result = customerMapper.updateById(customer);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok();
	}

	@Override
	@Transactional
	public R recoveryUserVerified(Long id) {
		CmUserVerified userVerified = userVerifiedMapper.selectById(id);
		// 清除用户实体类的所属商户主键（SysUser.merchantId）字段
		Long userId = userVerified.getUserId();
		customerMapper.updateMerchantId(userId, null);

		// 删除已经审核的申请验证信息记录
		int result = userVerifiedMapper.deleteUserVerifiedById(id);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	/**
	 * 生成个人邀请码
	 *
	 * @return
	 */
	private String uniqueCode() {
		String invitationCode = RandomStringUtil.randomNumberStr6();
		int count = customerMapper.countInvitationCode(invitationCode);
		if (count == 0) {
			return invitationCode;
		} else {
			return uniqueCode();
		}
	}
}
