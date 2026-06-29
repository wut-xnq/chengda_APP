package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.merch.api.entity.KdCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * desc: 快递公司数据交互
 * user: pan
 * date: 2024-10-28 16:24
 */
@Mapper
public interface KdCompanyMapper extends BaseMapper<KdCompany> {
	List<KdCompany> getAll();
}
