package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.merch.api.dto.StaffScoreDto;
import com.jiaolong.cm.merch.api.dto.UserInfoDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.vo.UserSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 客户数据交互
 * user: pan
 * date: 2024-09-02 16:41
 */
@Mapper
public interface CmCustomerMapper extends BaseMapper<CmCustomer> {
	IPage<UserInfoDto> getDtoPage(@Param("page") Page page, @Param("vo") UserSearchVo vo);

	UserInfoDto getDtoById(@Param("userId") Long userId);

	int deleteUserByIds(@Param("ids") Long[] ids);

	int countInvitationCode(@Param("code") String code);

	int updateMerchantId(@Param("userId") Long userId, @Param("merchantId") Long merchantId);

	List<StaffScoreDto> getStaffScoreList(@Param("merchantId") Long merchantId);

    int updateGiftScore(@Param("ids") List<Long> ids);
}
