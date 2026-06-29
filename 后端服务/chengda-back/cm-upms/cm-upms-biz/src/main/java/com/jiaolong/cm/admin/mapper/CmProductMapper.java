package com.jiaolong.cm.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaolong.cm.admin.api.dto.ExamGoodsDto;
import com.jiaolong.cm.consu.api.dto.ProductVerifiedDto;
import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.vo.ProductShelveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * desc:
 * user: pan
 * date: 2024-09-24 10:20
 */
@Mapper
public interface CmProductMapper extends BaseMapper<CmProduct> {
	List<ProductVerifiedDto> selectVerifiedProductList(@Param("ids") String[] ids);

	Set<Long> getMerchantIdByProductIds(@Param("ids") String[] ids);

    List<ExamGoodsDto> getExamGoodList();

	int shelveProduct(@Param("vo") ProductShelveVo vo);

	int unShelveMerchantProduct(@Param("merchantId") Long merchantId);
}
