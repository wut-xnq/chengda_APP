package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.merch.api.dto.SpecDto;
import com.jiaolong.cm.merch.api.entity.CmSpecification;
import com.jiaolong.cm.merch.api.vo.SpecSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * desc: 商品规格组数据交互
 * user: pan
 * date: 2024-09-01 13:03
 */
@Mapper
public interface CmSpecMapper extends BaseMapper<CmSpecification> {
	IPage<SpecDto> getPage(Page page, @Param("vo") SpecSearchVo vo);

	int deleteSpecById(@Param("ids") List<Long> ids);

	List<Long> getSpecIdListByMerchantId(@Param("merchantId") Long merchantId);

	List<SpecDto> getListByGroupId(@Param("groupId") Long groupId);

	List<SpecDto> getSpecListByProductId(@Param("productId") Long productId);

	List<SpecDto> getSpecListByMerchantId(@Param("merchantId") Long merchantId);

    int countInUseSpec(@Param("ids") List<Long> ids);
}
