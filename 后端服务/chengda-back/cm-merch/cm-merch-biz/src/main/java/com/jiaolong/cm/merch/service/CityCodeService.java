package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.CityCodeDto;
import com.jiaolong.cm.merch.api.entity.CityCode;
import com.jiaolong.cm.common.core.util.R;

import java.util.List;

/**
 * desc: 城市编码服务接口
 * user: pan
 * date: 2024-09-02 16:43
 */
public interface CityCodeService extends IService<CityCode> {
	R<List<CityCodeDto>> getAllTree();

	R<List<CityCode>> getChildrenList(Integer cityPid);
}
