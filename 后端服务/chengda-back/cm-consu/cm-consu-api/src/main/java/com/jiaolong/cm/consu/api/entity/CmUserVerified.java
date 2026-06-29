package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * desc: 员工申请企业认证记录
 * user: pan
 * date: 2024-08-24 12:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工申请企业认证记录")
@EqualsAndHashCode(callSuper = true)
public class CmUserVerified extends Model<CmUserVerified> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 所属用户主键
	 */
	@Schema(description = "所属用户主键")
	private Long userId;

	/**
	 * 所属商户主键
	 */
	@Schema(description = "所属商户主键")
	private Long merchantId;

	/**
	 * 操作人主键
	 */
	@Schema(description = "操作人主键")
	private Long operatorId;

	/**
	 * 认证状态;（0-未认证，1-认证不通过，2-认证通过）
	 */
	@Schema(description = "认证状态（0-未认证，1-认证不通过，2-认证通过）")
	private Integer verifiedState;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 用户姓名最后一个字
	 */
	@Schema(description = "用户姓名最后一个字")
	private String nameLastChar;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remarks;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;

	public CmUserVerified(Long userId, Long merchantId, Integer verifiedState, String nameLastChar) {
		this.userId = userId;
		this.merchantId = merchantId;
		this.verifiedState = verifiedState;
		this.nameLastChar = nameLastChar;
		this.deleted = BusinessConstants.DELETED_NO;
	}
}
