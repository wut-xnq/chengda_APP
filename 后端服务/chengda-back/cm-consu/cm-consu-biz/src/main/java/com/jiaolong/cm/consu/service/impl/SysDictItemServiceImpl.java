package com.jiaolong.cm.consu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.DictItemDto;
import com.jiaolong.cm.consu.api.entity.SysDictItem;
import com.jiaolong.cm.consu.mapper.SysDictItemMapper;
import com.jiaolong.cm.consu.service.SysDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc: 字典内容服务实现
 * user: pan
 * date: 2024-08-30 14:01
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

	@Autowired
	private SysDictItemMapper dictItemMapper;

	@Override
	public R<List<DictItemDto>> getItemListByDictType(String dictType) {
		List<DictItemDto> dtoList = dictItemMapper.getItemListByDictType(dictType);
		return R.ok(dtoList);
	}

	@Override
	public R<List<DictItemDto>> getItemListByDictId(Long dictId) {
		List<DictItemDto> dtoList = dictItemMapper.getItemListByDictId(dictId);
		return R.ok(dtoList);
	}
}
