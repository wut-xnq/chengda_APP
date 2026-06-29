package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.entity.CmTestInfo;
import com.jiaolong.cm.consu.api.param.TestInfoParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 质检信息数据交互
 * user: pan
 * date: 2024-09-24 09:41
 */
@Mapper
public interface CmTestInfoMapper extends BaseMapper<CmTestInfo> {
	IPage<TestInfoDto> selectListPage(Page page, @Param("vo") TestInfoParam vo);

	TestInfoDto getDtoById(@Param("id") Long id);

	int deleteTestInfoByIds(@Param("ids") Long[] ids);
}
