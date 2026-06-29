package com.jiaolong.cm.consu.api.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 质检数据传输对象
 * user: pan
 * date: 2024-08-21 15:02
 */
@Data
@Schema(description = "质检数据传输对象")
public class TestInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 质检时间
	 */
	@Schema(description = "质检时间")
	private LocalDateTime testTime;

	/**
	 * 发布类型;（1-视频，2-图片，3-纯文件）
	 */
	@Schema(description = "发布类型（1-视频，2-图片，3-纯文件）")
	private Integer fileType;

	/**
	 * 文件标题
	 */
	@Schema(description = "文件标题")
	private String fileTitle;

	/**
	 * 文本内容
	 */
	@Schema(description = "文本内容")
	private String content;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 发布日期
	 */
	@Schema(description = "发布日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDateTime publishDate;

	/**
	 * 操作人
	 */
	@Schema(description = "操作人")
	private String updateBy;

	/**
	 * 抽样商品主键集
	 */
	@Schema(description = "抽样商品主键集")
	private String testProductIds;

	/**
	 * 抽样商品所属单位主键集
	 */
	@Schema(description = "抽样商品所属单位主键集")
	private String testMerchantIds;

	/**
	 * 关联文件主键集
	 */
	@Schema(description = "关联文件主键集")
	private String relationFileIds;

	/**
	 * 送检机构名称
	 */
	@Schema(description = "送检机构名称")
	private String agencyName;

	/**
	 * 质检结果
	 */
	@Schema(description = "质检结果")
	private String remarks;

	/**
	 * 送测商品所属单位名称
	 */
	@Schema(description = "送测商品所属单位名称")
	private List<String> merchantNames;

	/**
	 * 送测商品列表
	 */
	@Schema(description = "送测商品列表")
	private List<ProductVerifiedDto> verifiedProduct;

	/**
	 * 关联文件列表
	 */
	@Schema(description = "关联文件列表")
	private List fileList;
}
