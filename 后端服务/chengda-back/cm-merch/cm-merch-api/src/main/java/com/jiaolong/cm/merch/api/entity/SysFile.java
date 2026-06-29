package com.jiaolong.cm.merch.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 文件素材管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Data
@Schema(description = "文件素材")
@EqualsAndHashCode(callSuper = true)
public class SysFile extends Model<SysFile> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 文件名
	 */
	@Schema(description = "素材文件名")
	private String fileName;

	/**
	 * 原文件名
	 */
	@Schema(description = "原始文件名")
	private String original;

	/**
	 * 容器名称
	 */
	@Schema(description = "文件存储桶名称")
	private String bucketName;

	/**
	 * 存储位置
	 */
	@Schema(description = "存储位置")
	private String url;

	/**
	 * 内容简介
	 */
	@Schema(description = "内容简介")
	private String brief;

	/**
	 * 文件类型
	 */
	@Schema(description = "文件类型")
	private String type;

	/**
	 * 文件大小
	 */
	@Schema(description = "文件大小")
	private Long fileSize;

	/**
	 * 排序值
	 */
	@Schema(description = "排序值")
	private Integer sortOrder;

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

	/**
	 * 是否默认;（0-否，1-是）
	 */
	@Schema(description = "是否默认")
	private Integer ifDefault;

	/**
	 * 上传人
	 */
	@Schema(description = "创建者")
	private String createBy;

	/**
	 * 上传时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新者")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 删除标记（0-正常，1-已删除）
	 */
	@TableLogic
	@Schema(description = "删除标记（0-正常，1-已删除）")
	private String delFlag;
}
