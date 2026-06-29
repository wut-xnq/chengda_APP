package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.dto.StaffDto;
import com.jiaolong.cm.consu.api.dto.UserPersonalDto;
import com.jiaolong.cm.consu.api.dto.UserScoreDto;
import com.jiaolong.cm.consu.api.entity.CmCustomer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 用户数据交互
 * user: pan
 * date: 2024-08-23 16:15
 */
@Mapper
public interface CmCustomerMapper extends BaseMapper<CmCustomer> {
	List<UserScoreDto> getTranUserList(@Param("inviterId") Long inviterId);

	UserPersonalDto getDetailsById(@Param("userId") Long userId);

	int updateGiftScore(@Param("ids") Long[] ids);

    StaffDto getStaffDetails(@Param("userId") Long userId);

	int updateUserScore(@Param("userId") Long userId, @Param("userScore") int userScore);

	UserPersonalDto getByPhone(@Param("phone") String phone);
}
