package com.jiaolong.cm.merch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.merch.api.dto.ProductDto;
import com.jiaolong.cm.merch.api.dto.ProductListDto;
import com.jiaolong.cm.merch.api.dto.StatisticProductQueryDto;
import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.vo.ProductSearchVo;
import com.jiaolong.cm.merch.api.vo.ProductShelveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 商品数据交互
 * user: pan
 * date: 2024-09-05 18:24
 */
@Mapper
public interface CmProductMapper extends BaseMapper<CmProduct> {
	IPage<ProductDto> getPageList(Page page, @Param("vo") ProductSearchVo vo);

	ProductDto getProductDetailById(@Param("id") Long id);

	int shelveProduct(@Param("vo") ProductShelveVo vo);

	int deleteProduct(@Param("ids") Long[] ids);

    List<ProductListDto> getProductByMerchantId(@Param("merchantId") Long merchantId);

    int updateSkuStr(@Param("id") Long id, @Param("productSkus") String productSkus);

	List<StatisticProductQueryDto> statisticProductVolume(@Param("merchantId") Long merchantId, @Param("start") LocalDateTime start, @Param("limitNum") Integer limitNum);
}
