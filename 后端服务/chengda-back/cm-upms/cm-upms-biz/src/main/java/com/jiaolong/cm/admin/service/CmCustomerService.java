package com.jiaolong.cm.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.admin.api.dto.CustomerDto;
import com.jiaolong.cm.admin.api.dto.UserDTO;
import com.jiaolong.cm.admin.api.dto.UserInfo;
import com.jiaolong.cm.admin.api.vo.CustomerSearchVo;
import com.jiaolong.cm.admin.api.vo.CustomerVO;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.entity.CmCustomer;

/**
 * desc: 客户服务接口
 * user: pan
 * date: 2024-09-14 18:23
 */
public interface CmCustomerService extends IService<CmCustomer> {

	R<UserInfo> getCustomerInfo(String username, String inviteCode);

    R<IPage<CustomerDto>> getCustomerPage(Page page, CustomerSearchVo vo);

	R<CustomerDto> getDetails(Long userId);

	R disabledCustomer(String userId, String lockFlag);

	R<UserInfo> getMerchantUserInfo(String username);

	R<CustomerVO> getCustomerDetails(Long userId);

    R updateCustomer(UserDTO userDto);

    R resetCustomerPwd(String userId);
}
