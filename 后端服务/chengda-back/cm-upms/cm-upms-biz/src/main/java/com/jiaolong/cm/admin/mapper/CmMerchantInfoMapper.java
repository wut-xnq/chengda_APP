package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.dto.MerchantDto;
import com.jiaolong.cm.admin.api.vo.MerchantSearchVo;
import com.jiaolong.cm.merch.api.dto.MerchantInfoDto;
import com.jiaolong.cm.merch.api.entity.CmMerchantInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 商户信息数据交互
 * user: pan
 * date: 2024-09-20 10:30
 */
@Mapper
public interface CmMerchantInfoMapper extends BaseMapper<CmMerchantInfo> {
	IPage<MerchantInfoDto> getMerchantPage(Page page, @Param("vo") MerchantSearchVo vo);

	MerchantInfoDto getMerchantInfoById(@Param("id") Long id);

	int disabled(@Param("id") Long id, @Param("state") String state);

	int deleteMerchantById(@Param("ids") Long[] ids);

    List<String> selectNameList(@Param("ids") String[] ids);

	List<MerchantDto> getCheckedMerchantList();

}
