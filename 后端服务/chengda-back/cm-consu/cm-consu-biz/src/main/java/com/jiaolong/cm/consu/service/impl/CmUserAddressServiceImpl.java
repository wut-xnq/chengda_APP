package com.jiaolong.cm.consu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.consu.api.dto.UserAddressDto;
import com.jiaolong.cm.consu.api.entity.CmUserAddress;
import com.jiaolong.cm.consu.convert.CmUserAddressConvert;
import com.jiaolong.cm.consu.mapper.CmUserAddressMapper;
import com.jiaolong.cm.consu.service.CmUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * desc: 用户收件地址服务实现
 * user: pan
 * date: 2024-08-22 11:02
 */
@Service
public class CmUserAddressServiceImpl extends ServiceImpl<CmUserAddressMapper, CmUserAddress> implements CmUserAddressService {

	@Autowired
	private CmUserAddressMapper userAddressMapper;


	@Override
	public R<List<UserAddressDto>> getUserAddressList(Long userId) {
		List<UserAddressDto> userAddressDtos = userAddressMapper.getUserAddressList(userId);
		return R.ok(userAddressDtos);
	}

	@Override
	public R<UserAddressDto> getDetails(Long id) {
		UserAddressDto dto = userAddressMapper.getUserAddressById(id);
		return R.ok(dto);
	}

	@Override
	@Transactional
	public R saveUserAddress(UserAddressDto userAddressDto) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		CmUserAddress cmUserAddress = CmUserAddressConvert.INSTANCE.create(userAddressDto);
		/* 查询用户当前可用的收货地址，如果没有可用的收货地址，新增的地址直接绑定用户默认收货地址 */
		Long userId = cmUser.getId();
		int count = userAddressMapper.countAvailableUserAddressByUserId(userId);
		if (count == 0) {
			cmUserAddress.setIfDefault(BusinessConstants.YES);
		}

		/* 如果当前地址为默认收货地址 */
		String ifDefault = cmUserAddress.getIfDefault();
		if (count > 0 && BusinessConstants.YES.equals(ifDefault)) {
			// 先清除用户名下其它默认的收货地址
			userAddressMapper.clearOtherDefaultAddress(cmUser.getId());
		}

		// 收货地址必需可用
		cmUserAddress.setState(BusinessConstants.STATE_ENABLE);
		cmUserAddress.setDeleted(BusinessConstants.DELETED_NO);
		int insertResult = userAddressMapper.insert(cmUserAddress);
		if (insertResult > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	@Transactional
	public R updateUserAddress(UserAddressDto userAddressDto) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		CmUserAddress cmUserAddress = CmUserAddressConvert.INSTANCE.update(userAddressDto);
		/* 如果当前地址为默认收货地址 */
		String ifDefault = cmUserAddress.getIfDefault();
		if (BusinessConstants.YES.equals(ifDefault)) {
			// 清除用户名下其它默认的收货地址
			userAddressMapper.clearOtherDefaultAddress(cmUser.getId());
		}
		// 默认当前的收货地址必需可用
		cmUserAddress.setState(BusinessConstants.YES);
		cmUserAddress.setDeleted(BusinessConstants.DELETED_NO);
		int updateResult = userAddressMapper.updateById(cmUserAddress);
		if (updateResult > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<UserAddressDto> getDefaultAddress(Long userId) {
		UserAddressDto address = userAddressMapper.getDefaultUserAddressByUserId(userId);
		return R.ok(address);
	}
}
