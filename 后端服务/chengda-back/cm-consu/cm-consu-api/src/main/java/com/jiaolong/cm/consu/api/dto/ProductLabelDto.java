package com.jiaolong.cm.consu.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 商品标签数据传输对象
 * user: pan
 * date: 2024-08-20 10:53
 */
@Data
@Schema(description = "商品标签数据传输对象")
public class ProductLabelDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 标签主键
	 */
	@Schema(description = "标签主键")
	private Long id;

	/**
	 * 标签名称
	 */
	@Schema(description = "标签名称")
	private String labelName;

	/**
	 * 标签编号
	 */
	@Schema(description = "标签编号")
	private String labelCode;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private String merchantId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 状态;（0-禁用，1-启用）
	 */
	@Schema(description = "状态（0-禁用，1-启用）")
	private String state;

	/**
	 * 所属商户名称
	 */
	@Schema(description = "所属商户名称")
	private String merchantName;
}
