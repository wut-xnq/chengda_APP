package com.jiaolong.cm.common.core.exception;

/**
 * desc: 业务中出现的报错文本信息
 * user: pan
 * date: 2024-08-21 17:03
 */
public interface BusinessErrorMessage {

	String SUCCESS = "操作成功。";
	String ERROR = "操作失败！";
	String FORBIDDED = "严禁操作！";

	/* 账户相关报错信息 */
	String NONE_USER = "用户不存在！";
	String NONE_INVITER = "邀请人不存在！";
	String NONE_MERCHANT = "商户信息不存在！";
	String MERCHANT_LOCKED = "商户异常锁定！";
	String NONE_DEPTARTMENT = "部门信息不存在！";
	String NONE_VERIFIED = "用户认证信息不存在！";
	String LACK_OF_USERINFO = "请完善个人信息！";

	/* 商户相关报错信息 */
	String ACCOUNT_CONFLICT = "商户账号存在冲突，请联系管理员处理！";
	String MERCHANT_ADD_ERROR = "新增商户失败！";
	String MERCHANT_USER_ADD_ERROR = "新增商户账号失败！";
	String MERCHANT_DISABLED = "商户状态异常，请联系平台管理员！";

	/* 商品相关报错信息 */
	String FORBIDDEN_NUMBER = "非法数值！";
	String NONE_PRODUCT = "商品不存在！";
	String NONE_SKU = "商品SKU不存在！";
	String NONE_MERCHANT_SPEC = "商品规格不存在！";
	String FORBIDDEN_OPERATE = "严禁操作非个人的商户数据！";
	String EXCEED_SKU_LIMIT_AMOUNT = "超出商品限购数量！";
	String PRODUCT_OUT_OF_STOCK = "商品库存不足！";
	String NONE_PRODUCT_SKUS = "未设置SKU的商品不能直接上架";
	String NOT_GB_PRODUCT = "非国标区上架的商品！";
	String LABEL_OUTOF_MERCHANT = "存在非本商户添加的标签！";
	String PRODUCT_LABEL_ERROR = "添加商品标签异常！";
	String SPEC_OUTOF_MERCHANT = "存在非本商户添加的规格！";
	String PRODUCT_SPEC_ERROR = "添加商品规格异常！";
	String PRODUCT_ON_SHELF = "商品正在售卖中，不允许修改！";
	String SPEC_IN_USE = "存在使用中的商品规格！";


	/* 用户购物订单相关报错信息 */
	String NONE_ORDER = "订单不存在！";
	String NONE_SHOPPING_ORDER = "购物订单异常！";
	String NONE_USER_ADDRESS = "用户收件地址不存在！";
	String NONE_ORDER_LOGISTICS = "订单物流信息不存在！";
	String NONE_ORDER_STATE = "订单状态不存在！";
	String ORDER_STATE_UNNORMAL = "订单状态异常！";
	String NONE_PAYMENT_ORDER = "支付订单异常！";

	/* 用户认证相关报错信息 */
	String ADD_STAFF_ERROR = "新增员工异常！";
	String ADD_STAFF_VERIFIED_ERROR = "新增员工认证信息异常！";
	String VERIFIED_AGREE_ERROR = "您已通过企业认证，请勿重复提交！";
	String VERIFIED_REJECT_ERROR = "您的认证申请被拒，请联系企业管理员处理！";
	String VERIFIED_SUBMIT = "您的认证已提交，请等待企业管理员处理！";

	/* 访问后端接口相关报错信息 */
	String USER_NOT_LOGIN = "用户未登录，请先登录！";
	String PARAMETER_MISSING = "必要参数缺失！";
	String MULTI_USERDATA = "数据已存在，请勿重复操作！";
	String MISSING_USERDATA = "数据不存在！";

	String NONE_LOGISTIC = "物流方案不存在！";
	String ORDER_LOGISTIC_ADD_ERROR = "新增订单物流记录失败！";
	String UNABLE_TO_DELIVER = "当前地址无法配送！";

	/* 调用网易云信服务，相关报错信息 */
	String YUNXIN_IM_SERVICE_ERROR = "服务数据异常！";
	String GET_MOBILE_CODE_OK = "短信验证码已发送。";
	String VERIFY_MOBILE_CODE_ERROR = "校验短信验证码失败！";
	String VERIFY_MOBILE_CODE_OK = "校验成功。";
	String REFRESH_USER_TOKEN_ERROR = "刷新用户 Token 失败！";
	String REGIST_USER_ACCOUNT_ERROR = "用户注册网易云信失败！";
	String PROMOT_MERCHANT_DELIVER = "已提醒商家尽快发货。";

	/* 调用快递100服务，相关报错信息 */
	String KUSIDI100_SERVICE_ERROR = "物流跟踪服务异常！";
}
