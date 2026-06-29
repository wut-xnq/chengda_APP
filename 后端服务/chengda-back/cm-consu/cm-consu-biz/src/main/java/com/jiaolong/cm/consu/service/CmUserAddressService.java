package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.UserAddressDto;
import com.jiaolong.cm.consu.api.entity.CmUserAddress;

import java.util.List;

/**
 * desc: 用户收件地址服务接口
 * user: pan
 * date: 2024-08-22 11:01
 */
public interface CmUserAddressService extends IService<CmUserAddress> {

	R<List<UserAddressDto>> getUserAddressList(Long userId);

	R saveUserAddress(UserAddressDto userAddressDto);

	R updateUserAddress(UserAddressDto userAddressDto);

    R<UserAddressDto> getDetails(Long id);

	R<UserAddressDto> getDefaultAddress(Long userId);
}
