package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaolong.cm.merch.api.dto.LabelDto;
import com.jiaolong.cm.merch.api.entity.CmLabel;
import com.jiaolong.cm.merch.api.vo.LabelSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * desc: 商户标签数据交互
 * user: pan
 * date: 2024-09-02 15:48
 */
@Mapper
public interface CmLabelMapper extends BaseMapper<CmLabel> {

	IPage<LabelDto> getLabelPage(IPage page, @Param("vo") LabelSearchVo vo);

	int deleteLabelByIds(@Param("ids") Long[] ids);

	List<LabelDto> getLabelListByProductId(@Param("productId") Long productId);

	List<LabelDto> getMerchantLabelList(@Param("merchantId") Long merchantId);
}
