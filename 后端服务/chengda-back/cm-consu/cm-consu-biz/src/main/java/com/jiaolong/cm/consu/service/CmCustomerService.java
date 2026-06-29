package com.jiaolong.cm.consu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.UserPersonalDto;
import com.jiaolong.cm.consu.api.dto.UserScoreDto;
import com.jiaolong.cm.consu.api.dto.UserVerifiedDto;
import com.jiaolong.cm.consu.api.entity.CmCustomer;
import com.jiaolong.cm.consu.api.param.CustomerPasswordVo;
import com.jiaolong.cm.consu.api.param.CustomerVo;
import com.jiaolong.cm.consu.api.param.TransScoreParam;

import java.util.List;

/**
 * desc: 用户管理服务接口
 * user: pan
 * date: 2024-08-23 16:46
 */
public interface CmCustomerService extends IService<CmCustomer> {
	R<UserPersonalDto> getDetails(Long id);

	R<UserPersonalDto> getDetailsByPhone(String phone);

	R<List<UserScoreDto>> getListForTranScore(Long id);

	R reqTransScore(Long[] ids);

	R resTransScore(TransScoreParam param);

    R<UserVerifiedDto> getIdentResult(Long userId);

	R identApply(String nameLastChar, Long merchantId);

	R editCustomerInfo(CustomerVo vo);

	R changePassword(CustomerPasswordVo vo);
}
