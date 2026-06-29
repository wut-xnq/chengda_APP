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
 * desc: 质检信息
 * user: pan
 * date: 2024-08-20 12:44
 */
@Data
@Schema(description = "质检信息")
@EqualsAndHashCode(callSuper = true)
public class CmTestInfo extends Model<CmTestInfo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 发布类型;（1-视频，2-图片，3-纯文件）
	 */
	@Schema(description = "发布类型（1-视频，2-图片，3-纯文件）")
	private Integer fileType;

	/**
	 * 质检时间
	 */
	@Schema(description = "质检时间")
	private LocalDateTime testTime;

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
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createBy;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

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
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updateBy;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;

	/**
	 * 备注（存放质检结果）
	 */
	@Schema(description = "备注（存放质检结果）")
	private String remarks;
}
