package com.jiaolong.cm.admin.api.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.core.constant.ServiceNameConstants;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.dto.*;
import com.jiaolong.cm.merch.api.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 商户远程查询接口
 * user: pan
 * date: 2024-09-20 10:39
 */
@FeignClient(contextId = "remoteProductService", value = ServiceNameConstants.MERCH_SERVICE)
public interface RemoteProductService {

	@GetMapping("/product/getPage")
	R<Page<ProductDto>> getProductPage(@SpringQueryMap ProductSearchVo vo);

	@GetMapping("/product/detail/{id}")
	R<ProductDto> getProductDetail(@PathVariable("id") Long id);

	@DeleteMapping("/product")
	R delProduct(@RequestBody Long[] ids);

	@GetMapping("/spec/getPage")
	R<Page<SpecDto>> getSpecPage(@SpringQueryMap SpecSearchVo vo);

	@DeleteMapping("/spec")
	R delSpec(@RequestBody Long[] ids);

	@GetMapping("/sku/getPage")
	R<Page<ProductSkuDto>> getSkuPage(@SpringQueryMap ProductSkuSearchVo vo);

	@DeleteMapping("/sku")
	R delSku(@RequestBody Long[] ids);

	@GetMapping("/label/getPage")
	R<Page<LabelDto>> getLabelPage(@SpringQueryMap LabelSearchVo vo);

	@DeleteMapping("/label")
	R delLabel(@RequestBody Long[] ids);

	@GetMapping("/logistics/getPage")
	R<Page<LogisticsDto>> getLogisticsPage(@SpringQueryMap LogisticSearchVo vo);

	@DeleteMapping("/logistics")
	R delLogistics(@RequestBody Long[] ids);

}
