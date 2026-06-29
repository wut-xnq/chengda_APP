package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.merch.api.dto.UserVerifiedDto;
import com.jiaolong.cm.merch.api.entity.CmUserVerified;
import com.jiaolong.cm.merch.api.vo.UserVerifiedSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 员工认证数据交互
 * user: pan
 * date: 2024-08-29 16:48
 */
@Mapper
public interface CmUserVerifiedMapper extends BaseMapper<CmUserVerified> {
	IPage<UserVerifiedDto> getUserVerifiedPage(Page page, @Param("vo") UserVerifiedSearchVo vo);

	int deleteUserVerifiedById(@Param("id") Long id);

	int deleteUserVerifiedByUserId(@Param("userIds") Long[] userIds);
}
