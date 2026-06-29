package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.merch.api.dto.CityCodeDto;
import com.jiaolong.cm.merch.api.entity.CityCode;
import com.jiaolong.cm.merch.mapper.CityCodeMapper;
import com.jiaolong.cm.merch.service.CityCodeService;
import com.jiaolong.cm.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * desc: 城市编码服务实现
 * user: pan
 * date: 2024-09-02 16:44
 */
@Service
public class CityCodeServiceImpl extends ServiceImpl<CityCodeMapper, CityCode> implements CityCodeService {

	@Autowired
	private CityCodeMapper cityCodeMapper;

	@Override
	public R<List<CityCodeDto>> getAllTree() {
		List<CityCodeDto> allList = cityCodeMapper.getAllList();
		List<CityCodeDto> higherList = allList.stream().filter(e -> e.getCityPid() == 0).collect(Collectors.toList());
		List<CityCodeDto> lowerList = allList.stream().filter(e -> e.getCityPid() != 0).collect(Collectors.toList());
		Map<Integer, List<CityCodeDto>> childrenMap = lowerList.stream().collect(Collectors.groupingBy(CityCode::getCityPid, Collectors.toList()));
		translateToTree(higherList, childrenMap);
		return R.ok(higherList);
	}

	@Override
	public R<List<CityCode>> getChildrenList(Integer cityPid) {
		if(cityPid == null){
			cityPid = 0;
		}
		List<CityCode> list = cityCodeMapper.getChildrenList(cityPid);
		return R.ok(list);
	}

	/**
	 * 递归处理级联
	 * @param dataList
	 * @param childrenMap
	 */
	private void translateToTree(List<CityCodeDto> dataList, Map<Integer, List<CityCodeDto>> childrenMap){
		for (CityCodeDto dto : dataList){
			Integer cityId = dto.getCityId();
			List<CityCodeDto> children = childrenMap.get(cityId);
			if(children != null){
				dto.setChildrenList(children);
				translateToTree(dto.getChildrenList(), childrenMap);
			}
		}
	}
}
