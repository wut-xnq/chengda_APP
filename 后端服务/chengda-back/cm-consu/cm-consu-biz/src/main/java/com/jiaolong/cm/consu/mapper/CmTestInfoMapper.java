package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.entity.CmTestInfo;
import com.jiaolong.cm.consu.api.param.TestInfoParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 质检管理据交互
 * user: pan
 * date: 2024-08-20 19:07
 */
@Mapper
public interface CmTestInfoMapper extends BaseMapper<CmTestInfo> {
	IPage<TestInfoDto> selectListPage(Page page, @Param("param") TestInfoParam param);

	TestInfoDto selectByProductId(@Param("productId") Long productId);

	List<TestInfoDto> selectListByMerchantId(@Param("merchantId") Long merchantId);
}
