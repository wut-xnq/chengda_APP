// 商品订单状态
export enum ORDER_STATUS {
    PENDING_SHIPMENT = 1, // 待发货
    PENDING_RECEIPT = 2, // 待收货
    COMPLETED = 3, // 已完成
}

// 订单支付状态
export enum ORDER_PAY_STATUS {
    UNPAID  = 0, // 未支付
    CANCELED = 1, // 取消支付
    FAILED = 2, // 支付失败
    SUCCESS = 3, // 支付成功
}

export enum ORDER_PAYMENT {
    UNPAID = '待付款',
    ACTUAL_PAYMENT = '实付款'
}

export enum PAYMENT_TYPE_STATUS {
    WECHAT = 1,
    ALIPAY = 2
}

export enum PAYMENT_TYPE {
    WECHAT = '微信支付',
    ALIPAY = '支付宝支付'
}

export const PAYMENT_TYPE_MAP = {
    [PAYMENT_TYPE_STATUS.WECHAT]: PAYMENT_TYPE.WECHAT,
    [PAYMENT_TYPE_STATUS.ALIPAY]: PAYMENT_TYPE.ALIPAY
}

export const PAYMENT_STYLE = [
    {
        name: '微信',
        value:'weChat'
    },
    {
        name: '支付宝',
        value:'aliPay'
    }
];

// 上方的TAB切换状态
export enum ORDER_SHOW_TYPE {
    TOTAL = '全部',
    UNPAID = '待付款',
    SHIPMENT = '待发货',
    RECEIPT = '待收货',
    COMPLETED = '已完成',
}

export const TABS = [
    {
        state: ORDER_SHOW_TYPE.TOTAL,
        title: ORDER_SHOW_TYPE.TOTAL
    },
    {
        state: ORDER_SHOW_TYPE.UNPAID,
        title: ORDER_SHOW_TYPE.UNPAID
    },
    {
        state: ORDER_SHOW_TYPE.SHIPMENT,
        title: ORDER_SHOW_TYPE.SHIPMENT
    },
    {
        state: ORDER_SHOW_TYPE.RECEIPT,
        title: ORDER_SHOW_TYPE.RECEIPT
    },
    {
        state: ORDER_SHOW_TYPE.COMPLETED,
        title: ORDER_SHOW_TYPE.COMPLETED
    }
];
