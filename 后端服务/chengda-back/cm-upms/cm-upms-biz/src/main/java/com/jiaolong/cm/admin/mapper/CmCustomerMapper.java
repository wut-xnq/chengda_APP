package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.dto.CustomerDto;
import com.jiaolong.cm.admin.api.dto.CustomerScoreDto;
import com.jiaolong.cm.admin.api.vo.CustomerSearchVo;
import com.jiaolong.cm.admin.api.vo.CustomerVO;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 客户数据交互
 * user: pan
 * date: 2024-09-14 18:21
 */
@Mapper
public interface CmCustomerMapper extends BaseMapper<CmCustomer> {
	int countInvitationCode(@Param("code") String code);

	CustomerScoreDto getInviterIdByCode(@Param("inviteCode") String inviteCode);

    IPage<CustomerDto> getPageList(Page page, @Param("vo") CustomerSearchVo vo);

	CustomerDto getDetailsById(@Param("userId") Long userId);

	int disabled(@Param("userId") Long userId, @Param("lockFlag") String lockFlag);

    CmCustomer getCustomerByPhone(@Param("phone") String phone);

	CustomerVO getCustomerInfoVo(@Param("userId") Long userId);

	int updateUserScore(@Param("userId") Long userId, @Param("userScore") int userScore);
}
