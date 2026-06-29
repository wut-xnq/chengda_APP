package com.jiaolong.cm.merch.api.dto;

import com.jiaolong.cm.merch.api.entity.CityCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * desc: 城市编码数据传输对象
 * user: pan
 * date: 2024-09-02 16:49
 */
@Data
@Schema(description = "城市编码数据传输对象")
public class CityCodeDto extends CityCode {

	/**
	 * 下级城市编码列表
	 */
	@Schema(description = "下级城市编码列表")
	private List<CityCodeDto> childrenList;
}
