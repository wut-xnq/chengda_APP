package com.jiaolong.cm.consu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.consu.api.dto.ProductDto;
import com.jiaolong.cm.consu.api.dto.ProductVerifiedDto;
import com.jiaolong.cm.consu.api.dto.RelationProductDto;
import com.jiaolong.cm.consu.api.entity.CmProduct;
import com.jiaolong.cm.consu.api.param.ProductParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * desc: 商品数据交互
 * user: pan
 * date: 2024-08-20 15:30
 */
@Mapper
public interface CmProductMapper extends BaseMapper<CmProduct> {
	IPage<ProductDto> selectListPage(Page page, @Param("param") ProductParam param);

	List<ProductVerifiedDto> selectVerifiedProductList(@Param("ids")String[] ids);

	ProductDto getDetailsById(@Param("id") Long id);

    List<RelationProductDto> getRelationProductDtoList();
}
