package com.jiaolong.cm.consu.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * desc: 订单支付记录
 * user: pan
 * date: 2024-08-20 12:55
 */
@Data
@Schema(description = "订单支付记录")
@EqualsAndHashCode(callSuper = true)
public class CmOrderPayment extends Model<CmOrderPayment> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 支付订单编号
	 */
	@Schema(description = "支付订单编号")
	private String paymentNo;

	/**
	 * 关联购物订单编号
	 */
	@Schema(description = "关联购物订单编号")
	private String shoppingOrderNo;

	/**
	 * 买家主键
	 */
	@Schema(description = "买家主键")
	private Long buyerId;

	/**
	 * 商户主键
	 */
	@Schema(description = "商户主键")
	private Long merchantId;

	/**
	 * 支付人账号
	 */
	@Schema(description = "支付人账号")
	private String userChargeAccount;

	/**
	 * 收款人账号
	 */
	@Schema(description = "收款人账号")
	private String merchantChargeAccount;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 交易金额
	 */
	@Schema(description = "交易金额")
	private BigDecimal amount;

	/**
	 * 交易状态;（0-未开始交易，1-取消交易，2-交易失败，3-交易成功）
	 */
	@Schema(description = "交易状态（0-未开始交易，1-取消交易，2-交易失败，3-交易成功）")
	private Integer tradeState;

	/**
	 * 支付方式;（1-微信支付，2-支付宝支付）
	 */
	@Schema(description = "支付方式（1-微信支付，2-支付宝支付）")
	private Integer paymentType;

	/**
	 * 第三方交易流水号
	 */
	@Schema(description = "第三方交易流水号")
	private String tradeFlowNo;

	/**
	 * 删除状态;（0-否，1-是）
	 */
	@Schema(description = "删除状态（0-否，1-是）")
	private String deleted;
}
