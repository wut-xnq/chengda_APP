package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.dto.UserAddressDto;
import com.jiaolong.cm.consu.api.entity.CmUserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 用户收件地址数据交互
 * user: pan
 * date: 2024-08-22 10:59
 */
@Mapper
public interface CmUserAddressMapper extends BaseMapper<CmUserAddress> {
	List<UserAddressDto> getUserAddressList(@Param("userId") Long userId);

	UserAddressDto getUserAddressById(@Param("id") Long id);

	UserAddressDto getDefaultUserAddressByUserId(@Param("userId") Long userId);

	int countAvailableUserAddressByUserId(@Param("userId") Long userId);

	int clearOtherDefaultAddress(@Param("userId") Long userId);
}
