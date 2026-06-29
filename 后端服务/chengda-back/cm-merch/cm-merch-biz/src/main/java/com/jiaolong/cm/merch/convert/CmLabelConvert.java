package com.jiaolong.cm.merch.convert;

import com.jiaolong.cm.merch.api.dto.LabelDto;
import com.jiaolong.cm.merch.api.entity.CmLabel;
import com.jiaolong.cm.merch.api.vo.LabelVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * desc: 商户标签数据转换实现
 * user: pan
 * date: 2024-09-02 16:03
 */
@Mapper
public interface CmLabelConvert {
	CmLabelConvert INSTANCE = Mappers.getMapper(CmLabelConvert.class);

	default CmLabel convertYoEntity(CmLabel label, LabelVo vo){
		if (vo != null) {
			label.setMerchantId(vo.getMerchantId());
			label.setLabelName(vo.getLabelName());
			label.setLabelCode(vo.getLabelCode());
			label.setState(vo.getState());
		}
		return label;
	}

	default LabelDto convertToDto(CmLabel label){
		LabelDto dto = new LabelDto();
		if (label != null) {
			dto.setId(label.getId());
			dto.setLabelName(label.getLabelName());
			dto.setLabelCode(label.getLabelCode());
			dto.setMerchantId(label.getMerchantId());
			dto.setCreateTime(label.getCreateTime());
			dto.setUpdateTime(label.getUpdateTime());
			dto.setState(label.getState());
		}
		return dto;
	}
}
