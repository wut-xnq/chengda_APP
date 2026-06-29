package com.jiaolong.cm.merch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc: 商户信息数据传输对象
 * user: pan
 * date: 2024-08-28 14:32
 */
@Data
@Schema(description = "商户信息数据传输对象")
public class MerchantInfoDto  implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商户名称
	 */
	@Schema(description = "商户名称")
	private String merchantName;

	/**
	 * 商户负责人
	 */
	@Schema(description = "商户负责人")
	private String chargePersonName;

	/**
	 * 负责人联系方式
	 */
	@Schema(description = "负责人联系方式")
	private String chargePersonPhone;

	/**
	 * 商户LOGO
	 */
	@Schema(description = "商户LOGO")
	private String logo;

	/**
	 * 统一社会信用码
	 */
	@Schema(description = "统一社会信用码")
	private String creditCode;

	/**
	 * 商户所在省编码
	 */
	@Schema(description = "商户所在省编码")
	private Integer provinceCode;

	/**
	 * 商户所在市编码
	 */
	@Schema(description = "商户所在市编码")
	private Integer cityCode;

	/**
	 * 商户所在区/县编码
	 */
	@Schema(description = "商户所在区/县编码")
	private Integer countryCode;

	/**
	 * 商户详细地址
	 */
	@Schema(description = "商户详细地址")
	private String address;

	/**
	 * 员工人数
	 */
	@Schema(description = "员工人数")
	private Integer staffCount;

	/**
	 * 商户积分
	 */
	@Schema(description = "商户积分")
	private Integer merchantScore;

	/**
	 * 商户所在地完整地址
	 */
	@Schema(description = "商户所在地完整地址")
	private String fullAddress;

	/**
	 * 是否认证（0-未认证，1-认证不通过，2-认证通过）
	 */
	@Schema(description = "是否认证（0-未认证，1-认证不通过，2-认证通过）")
	private String verified;

	/**
	 * 商户入驻状态;（0-禁用，1-启用）
	 */
	@Schema(description = "商户入驻状态（0-禁用，1-启用）")
	private String state;
}
