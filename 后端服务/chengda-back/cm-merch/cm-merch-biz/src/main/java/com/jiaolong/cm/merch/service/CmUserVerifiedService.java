package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.UserVerifiedDto;
import com.jiaolong.cm.merch.api.entity.CmUserVerified;
import com.jiaolong.cm.merch.api.vo.UserVerifiedAuditVo;
import com.jiaolong.cm.merch.api.vo.UserVerifiedSearchVo;
import com.jiaolong.cm.common.core.util.R;

/**
 * desc: 员工认证服务接口
 * user: pan
 * date: 2024-08-29 16:49
 */
public interface CmUserVerifiedService extends IService<CmUserVerified> {
	R<IPage<UserVerifiedDto>> getUserVerifiedPage(Page page, UserVerifiedSearchVo vo);

	R auditUserVerified(UserVerifiedAuditVo vo);

	R recoveryUserVerified(Long id);
}
