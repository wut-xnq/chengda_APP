package com.jiaolong.cm.admin.api.feign;

import com.jiaolong.cm.common.core.constant.ServiceNameConstants;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.dto.CityCodeDto;
import com.jiaolong.cm.merch.api.entity.CityCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * desc: 城市编码远程查询接口
 * user: pan
 * date: 2024-09-20 10:39
 */
@FeignClient(contextId = "remoteCityCodeService", value = ServiceNameConstants.MERCH_SERVICE)
public interface RemoteCityCodeService {

	@GetMapping("/cityCode/getAll")
	R<List<CityCodeDto>> getAllTree();

	@GetMapping("/cityCode/getLowerList/{cityPid}")
	R<List<CityCode>> getChildrenList(@PathVariable("cityPid") Integer cityPid);
}
