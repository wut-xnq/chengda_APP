package com.jiaolong.cm.merch.convert;

import com.jiaolong.cm.merch.api.dto.LabelDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmLabel;
import com.jiaolong.cm.merch.api.vo.LabelVo;
import com.jiaolong.cm.merch.api.vo.UserInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * desc: 商户标签数据转换实现
 * user: pan
 * date: 2024-09-02 16:03
 */
@Mapper
public interface CmCustomerConvert {
	CmCustomerConvert INSTANCE = Mappers.getMapper(CmCustomerConvert.class);

	default CmCustomer convertToEntity(CmCustomer user, UserInfoVo vo) {
		if (vo != null) {
			user.setName(vo.getName());
			user.setGender(vo.getGender());
			user.setAge(vo.getAge());
			user.setPhone(vo.getPhone());
			user.setUsername(vo.getPhone());
			user.setUserNumber(vo.getUserNumber());
			user.setVerified(vo.getVerified());
			user.setDeptId(vo.getDeptId());
		}
		return user;
	}
}
