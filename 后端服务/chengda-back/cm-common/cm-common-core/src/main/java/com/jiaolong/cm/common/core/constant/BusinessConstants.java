package com.jiaolong.cm.common.core.constant;

/**
 * desc: 业务相关的常量
 * user: pan
 * date: 2024-08-21 17:00
 */
public interface BusinessConstants {
	/* 国标区数值常量 */
	int GB_AREA = 1;
	/* 正品区数值常量 */
	int ZP_AREA = 2;
	/* 优惠区数值常量 */
	int YH_AREA = 3;
	/* 管理员用户组 */
	int ROLE_ADMIN = 1;
	/* 普通用户组 */
	int GENERAL_USER = 2;
	/* 商户用户组 */
	int MERCHANT_USER = 3;
	/* 普通用户角色 */
	long GENERAL_USER_ROLE = 2L;
	/* 商户用户角色 */
	long MERCHANT_USER_ROLE = 3L;
	/* 拒绝 */
	int REJECTED = 0;
	/* 同意 */
	int AGREED = 1;
	/* 用户等待企业认证 */
	int VERIFIED_PRO = 0;
	/* 用户未通过企业认证 */
	int VERIFIED_REJECT = 1;
	/* 用户通过企业认证 */
	int VERIFIED_AGREE = 2;
	/* 订单待发货 */
	int ORDER_PREDELIVER = 1;
	/* 订单已发货 */
	int ORDER_DELIVERED = 2;
	/* 用户已签收 */
	int ORDER_RECEIVED = 3;
	/* 订单待结算 */
	int ORDER_PRESETTLE = 11;
	/* 订单已结算 */
	int ORDER_SETTLED = 12;
	/* 支付方式-微信 */
	int PAYMENT_WECHAT = 1;
	/* 支付方式-微信支付宝 */
	int PAYMENT_ALIPAY = 2;
	/* 订单支付-未支付 */
	int ORDER_PREPAYMENT = 0;
	/* 订单支付-取消支付 */
	int ORDER_PANMENT_CANEL = 1;
	/* 订单支付-支付失败 */
	int ORDER_PANMENT_FAILED = 2;
	/* 订单支付-支付成功 */
	int ORDER_PAYMENT_SUCCESS = 3;
	/* 积分初始化为0分 */
	int ZERO_SCORE = 0;
	/* 积分100分 */
	int HUNDRED_SCORE = 100;
	/* 系统默认配置主键 */
	long SYS_DEFAULT_CONFIG = 1L;

	/* ok */
	String OK = "ok";
	/* yes */
	String YES = "1";
	/* no */
	String NO = "0";
	/* 禁用 */
	String STATE_DISABLE = "0";
	/* 启用 */
	String STATE_ENABLE = "1";
	/* 未认证 */
	String NOT_VERIFIED = "0";
	/* 已认证 */
	String VERIFIED = "1";
	/* 认证不通过 */
	String VERIFIED_NOT_PASS = "1";
	/* 认证通过 */
	String VERIFIED_PASS = "2";
	/* 管理员未认证 */
	String VERIFIED_NONE_MESSAGE = "管理员未认证";
	/* 商户管理员发名片 */
	String VERIFIED_PASS_MESSAGE = "管理员已发名片";
	/* 未删除 */
	String DELETED_NO = "0";
	/* 已删除 */
	String DELETED_YES = "1";
	/* 未锁定 */
	String LOCKED_NO = "0";
	/* 已锁定 */
	String LOCKED_YES = "9";
	/* 不赠送个人积分 */
	String GIFT_SCORE_NO = "0";
	/* 同意赠送个人积分 */
	String GIFT_SCORE_YES = "1";
	/* 按照重量计费 */
	String CHARGE_BY_WEIGHT = "1";
	/* 按照数量计费 */
	String CHARGE_BY_COUNT = "2";
	/* 商品下架 */
	String PRODUCT_OFF_SHELF = "0";
	/* 商品上架 */
	String PRODUCT_ON_SHELF = "1";
	/* 购物订单编号前缀 */
	String SHOPING_ORDER_PRE = "GW";
	/* 支付订单编号前缀 */
	String CHARGE_ORDER_PRE = "ZF";
	/* 城市编码缓存 */
	String CITY_CODE_MAP = "CITY_CODE_MAP";
	/* 支付宝平台税率 */
	String ALIPAY_CUT = "ALIPAY_CUT";
	/* 微信支付平台税率 */
	String WECHATPAY_CUT = "WECHATPAY_CUT";
	/* 国标区商品抽成 */
	String GB_CUT = "GB_CUT";
	/* 正品区商品抽成 */
	String ZP_CUT = "ZP_CUT";
	/* 优惠区商品抽成 */
	String YH_CUT = "YH_CUT";
	/* 商户积分兑换比例 */
	String MERCH_SCORE_TRANS = "MERCH_SCORE_TRANS";
	/* 字符100 */
	String ONE_HUNDRED = "100";
	/* 字符10000 */
	String TEN_THOUSAND = "10000";
}
