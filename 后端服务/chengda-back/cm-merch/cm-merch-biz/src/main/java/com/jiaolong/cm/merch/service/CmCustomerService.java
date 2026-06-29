package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.admin.api.dto.UserDTO;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.dto.UserInfoDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.vo.UserInfoVo;
import com.jiaolong.cm.merch.api.vo.UserSearchVo;

/**
 * desc: 客户服务接口
 * user: pan
 * date: 2024-09-02 16:43
 */
public interface CmCustomerService extends IService<CmCustomer> {

	R<IPage<UserInfoDto>> getUserPage(Page page, UserSearchVo vo);

	R<UserInfoDto> getUserInfo(Long userId);

	R addUser(UserInfoVo vo);

	R editUser(UserInfoVo vo);

	R deleteUser(Long[] ids);

    R changePassword(UserDTO userDto);
}
