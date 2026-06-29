package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * desc: 商户信息
 * user: pan
 * date: 2024-08-20 12:41
 */
@Data
@Schema(description = "商户信息")
@EqualsAndHashCode(callSuper = true)
public class CmMerchantInfo extends Model<CmMerchantInfo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商户名称
	 */
	@Schema(description = "商户名称")
	private String merchantName;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

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
	 * 商户简介
	 */
	@Schema(description = "商户简介")
	private String brief;

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
	 * 营业执照
	 */
	@Schema(description = "营业执照")
	private String businessLicense;

	/**
	 * 商户图片;（限制3张）
	 */
	@Schema(description = "商户图片（限制3张）")
	private String merchantPics;

	/**
	 * 入驻平台生效日期
	 */
	@Schema(description = "入驻平台生效日期")
	private LocalDateTime effectiveDate;

	/**
	 * 平台生效年限
	 */
	@Schema(description = "平台生效年限")
	private Integer effectiveYear;

	/**
	 * 员工人数
	 */
	@Schema(description = "员工人数")
	private Integer staffCount;

	/**
	 * 商户入驻状态;（0-禁用，1-启用）
	 */
	@Schema(description = "商户入驻状态（0-禁用，1-启用）")
	private String state;

	/**
	 * 商户积分
	 */
	@Schema(description = "商户积分")
	private Integer merchantScore;

	/**
	 * 微信收款码
	 */
	@Schema(description = "微信收款码")
	private String wxChargeImage;

	/**
	 * 支付宝收款码
	 */
	@Schema(description = "支付宝收款码")
	private String alipayChargeImage;

	/**
	 * 是否认证（0-未认证，1-认证不通过，2-认证通过）
	 */
	@Schema(description = "是否认证（0-未认证，1-认证不通过，2-认证通过）")
	private String verified;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
