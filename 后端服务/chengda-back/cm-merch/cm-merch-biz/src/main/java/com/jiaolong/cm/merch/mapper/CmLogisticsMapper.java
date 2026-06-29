package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.merch.api.dto.LogisticsDto;
import com.jiaolong.cm.merch.api.entity.CmMerchantLogistics;
import com.jiaolong.cm.merch.api.vo.LogisticSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 商户物流方案数据交互
 * user: pan
 * date: 2024-09-03 09:53
 */
@Mapper
public interface CmLogisticsMapper extends BaseMapper<CmMerchantLogistics> {
	IPage<LogisticsDto> getPageList(Page page, @Param("vo") LogisticSearchVo vo);

	int deleteLogisticById(@Param("ids") Long[] ids);
}
