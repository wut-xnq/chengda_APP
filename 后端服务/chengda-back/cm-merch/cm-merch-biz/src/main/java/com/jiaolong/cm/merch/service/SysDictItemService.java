package com.jiaolong.cm.merch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.merch.api.dto.DictItemDto;
import com.jiaolong.cm.merch.api.entity.SysDictItem;
import com.jiaolong.cm.common.core.util.R;

import java.util.List;

/**
 * desc: 字典内容服务接口
 * user: pan
 * date: 2024-08-30 14:01
 */
public interface SysDictItemService extends IService<SysDictItem> {
	R<List<DictItemDto>> getItemListByDictType(String dictType);

	R<List<DictItemDto>> getItemListByDictId(Long dictId);
}
