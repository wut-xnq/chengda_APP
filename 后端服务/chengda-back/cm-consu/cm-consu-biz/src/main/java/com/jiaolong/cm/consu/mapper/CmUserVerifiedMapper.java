package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.dto.UserVerifiedDto;
import com.jiaolong.cm.consu.api.entity.CmUserVerified;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 员工申请企业认证记录
 * user: pan
 * date: 2024-08-24 12:45
 */
@Mapper
public interface CmUserVerifiedMapper extends BaseMapper<CmUserVerified> {
	UserVerifiedDto getVerifiedResultByUserId(@Param("userId") Long userId);

	int initUserVerified(@Param("userId") Long userId, @Param("merchantId") Long merchantId, @Param("nameLastChar") String nameLastChar);
}
