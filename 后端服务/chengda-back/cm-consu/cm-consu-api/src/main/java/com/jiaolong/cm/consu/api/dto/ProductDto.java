package com.jiaolong.cm.consu.api.dto;

import com.jiaolong.cm.consu.api.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 商品数据传输对象
 * user: pan
 * date: 2024-08-20 15:37
 */
@Data
@Schema(description = "商品数据传输对象")
public class ProductDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;

	/**
	 * 商品简介
	 */
	@Schema(description = "商品简介")
	private String productBrief;

	/**
	 * 商品详情展示（图文）
	 */
	@Schema(description = "商品详情展示（图文）")
	private String productText;

	/**
	 * 商品主图
	 */
	@Schema(description = "商品主图")
	private String productPics;

	/**
	 * 是否包邮;（0-否，1-是）
	 */
	@Schema(description = "是否包邮")
	private String ifShipped;

	/**
	 * 发货期
	 */
	@Schema(description = "发货期")
	private String deliver;

	/**
	 * 商品售卖SKU主键集;（多个SKU主键使用英文逗号隔开）
	 */
	@Schema(description = "商品售卖SKU主键集")
	private String productSkus;

	/**
	 * 商品规格主键集;（多个规格主键使用英文逗号隔开）
	 */
	@Schema(description = "商品规格主键集")
	private String productSpecs;

	/**
	 * 商品标签主键集;（多个标签主键使用英文逗号隔开）
	 */
	@Schema(description = "商品标签主键集")
	private String labels;

	/**
	 * 商品保障字典集;（使用字典管理，多个字典值主键使用英文逗号隔开）
	 */
	@Schema(description = "商品保障字典集")
	private String productGuarantees;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 商户客服电话
	 */
	@Schema(description = "商户客服电话")
	private String customerServicePhone;

	/**
	 * 商品上架区域;（1-国标，2-正品，3-优惠）
	 */
	@Schema(description = "商品上架区域（1-国标，2-正品，3-优惠）")
	private Integer shelveArea;

	/**
	 * 商品浏览量
	 */
	@Schema(description = "商品浏览量")
	private Integer viewAmount;

	/**
	 * 商品购买量
	 */
	@Schema(description = "商品购买量")
	private Integer orderAmount;

	/**
	 * 原价
	 */
	@Schema(description = "原价")
	private String originPrice;

	/**
	 * 现价
	 */
	@Schema(description = "现价")
	private String currentPrice;

	/**
	 * 质检单位
	 */
	@Schema(description = "质检单位")
	private String agencyName;

	/**
	 * 质检时间
	 */
	@Schema(description = "质检时间")
	private LocalDateTime testTime;

	/**
	 * 质检结果
	 */
	@Schema(description = "质检结果")
	private String testResult;

	/**
	 * 外层展示的 SKU 主键
	 */
	@Schema(description = "外层展示的 SKU 主键")
	private Long showedSkuId;

	/**
	 * 商品SKU集
	 */
	@Schema(description = "商品SKU集")
	private List<CmProductSku> skuList;

	/**
	 * 商品规格集
	 */
	@Schema(description = "商品规格集")
	private List<ProductSpecDto> specList;

	/**
	 * 商品标签集
	 */
	@Schema(description = "商品标签集")
	private List<ProductLabelDto> labelList;

	/**
	 * 商品保障字典集
	 */
	@Schema(description = "商品保障字典集")
	private List<SysDictItem> productGuaranteeDicList;

	/**
	 * 质检相关文档
	 */
	@Schema(description = "质检相关文档")
	private List<SysFile> fileList;

	/**
	 * 商品运营数据统计
	 */
	@Schema(description = "商品运营数据统计")
	private CmProductStatisticsTrend statisticsTrend = null;
}
