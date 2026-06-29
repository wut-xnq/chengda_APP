package com.jiaolong.cm.admin.api.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.common.core.constant.ServiceNameConstants;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.dto.PaymentOrderDto;
import com.jiaolong.cm.merch.api.dto.ShoppingOrderDto;
import com.jiaolong.cm.merch.api.entity.CmOrderProducts;
import com.jiaolong.cm.merch.api.vo.PaymentOrderSearchVo;
import com.jiaolong.cm.merch.api.vo.ShoppingOrderSearchVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * desc: 商户远程查询接口
 * user: pan
 * date: 2024-09-20 10:39
 */
@FeignClient(contextId = "remoteOrderService", value = ServiceNameConstants.MERCH_SERVICE)
public interface RemoteOrderService {

	@GetMapping("/paymentOrder/getPage")
	R<Page<PaymentOrderDto>> getPaymentOrderPage(@SpringQueryMap PaymentOrderSearchVo vo);

	@GetMapping("/paymentOrder/detail/{id}")
	R<PaymentOrderDto> getPaymentOrderDetail(@PathVariable("id") Long id);

	@GetMapping("/shoppingOrder/getPage")
	R<Page<ShoppingOrderDto>> getShoppingOrderPage(@SpringQueryMap ShoppingOrderSearchVo vo);

	@GetMapping("/shoppingOrder/detail/{id}")
	R<ShoppingOrderDto> getShoppingOrderDetail(@PathVariable("id") Long id);

	@GetMapping("/shoppingOrder/productList/{id}")
	R<List<CmOrderProducts>> getProductList(@PathVariable("id") Long id);

}
