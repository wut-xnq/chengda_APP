package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.entity.SysFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 文件素材数据交互
 * user: pan
 * date: 2024-09-24 10:03
 */
@Mapper
public interface SysFileMapper extends BaseMapper<SysFile> {
	List<SysFile> selectFileListByIds(@Param("ids") String[] ids);
}
