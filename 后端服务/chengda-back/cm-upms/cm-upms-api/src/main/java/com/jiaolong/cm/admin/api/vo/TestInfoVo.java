package com.jiaolong.cm.admin.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 质检信息数据传输对象
 * user: pan
 * date: 2024-09-24 11:28
 */
@Data
public class TestInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 质检文件名
	 */
	@Schema(description = "质检主键")
	private Long id;

	/**
	 * 质检时间
	 */
	@Schema(description = "质检时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
	 * 抽样商品主键集
	 */
	@Schema(description = "抽样商品主键集")
	private String testProductIds;

	/**
	 * 关联文件主键集
	 */
	@Schema(description = "关联文件（附件）主键集")
	private String relationFileIds;

	/**
	 * 送检机构名称
	 */
	@Schema(description = "送检机构名称")
	private String agencyName;

	/**
	 * 备注（存放质检结果）
	 */
	@Schema(description = "备注（存放质检结果）")
	private String remarks;
}
