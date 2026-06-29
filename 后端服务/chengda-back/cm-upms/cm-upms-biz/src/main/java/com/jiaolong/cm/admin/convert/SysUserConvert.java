package com.jiaolong.cm.admin.convert;

import com.jiaolong.cm.admin.api.entity.SysUser;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * desc:
 * user: pan
 * date: 2024-09-14 18:28
 */
@Mapper
public interface SysUserConvert {
	SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

	default SysUser convertCustomerToUser(CmCustomer customer){
		SysUser user = new SysUser();
		if(customer != null){
			user.setUserId(customer.getUserId());
			user.setCreateTime(customer.getCreateTime());
			user.setDeptId(customer.getDeptId());
			user.setPhone(customer.getPhone());
			user.setPassword(customer.getPassword());
			user.setUsername(customer.getUsername());
			user.setName(customer.getName());
			user.setNickname(customer.getNickname());
			user.setAvatar(customer.getAvatar());
			user.setEmail(customer.getEmail());
			user.setLockFlag(customer.getLockFlag());
			user.setDelFlag(customer.getDelFlag());
		}
		return user;
	}
}
