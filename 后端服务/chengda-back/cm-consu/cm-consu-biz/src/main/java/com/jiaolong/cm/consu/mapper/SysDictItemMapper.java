package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.dto.DictItemDto;
import com.jiaolong.cm.consu.api.entity.SysDictItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 字典内容数据交互
 * user: pan
 * date: 2024-08-30 13:58
 */
@Mapper
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
	List<DictItemDto> getItemListByDictType(@Param("dictType") String dictType);

	List<DictItemDto> getItemListByDictId(@Param("dictId") Long dictId);
}
