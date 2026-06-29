package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.consu.api.entity.KdCompany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * desc: 快递公司编码数据交互
 * user: pan
 * date: 2024-10-17 16:04
 */
@Mapper
public interface KdCompanyMapper extends BaseMapper<KdCompany> {
	String getCodeByName(@Param("name") String name);
}
