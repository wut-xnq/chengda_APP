<!-- 我的订单 -->
<template>
  <div class="wrapper">
    <div class="tabs">
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        class="tab-item"
        :class="{ active: tab.value == active }"
        @click="handleTableClick(tab.value)"
      >
        {{ tab.label }}
        <div class="badge" v-if="tab.showCount && tab.count">{{ total }}</div>
      </div>
    </div>
    <!-- 订单列表 -->
    <div class="ordersLists" v-if="orderList.length > 0">
      <commodityCards
        v-for="(order, index) in orderList"
        :key="order.id"
        :order="order"
        @confirm-receipt="handleConfirmReceipt"
        @delete-order="handleDeleteOrder"
        style="margin-bottom: 14rpx"
      ></commodityCards>
    </div>
    <div v-else class="empty">
      <div class="content">
        <i class="iconfont icon-wushuju"></i>
        <view>暂无数据</view>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "@vue/composition-api";
import { onLoad, onReachBottom } from "@dcloudio/uni-app";
import { t } from "../../../utils/i18n";
import NavBar from "../../../components/NavBar.vue";
import PayItem from "../components/PayItem.vue";
import commodityCards from "../components/commodityCard.vue";
import uniPopup from "../../../components/uni-components/uni-popup/components/uni-popup/uni-popup.vue";
import { customNavigateTo } from "utils/customNavigate";
import {
  confirmReceipt,
  deleteUserOrder,
  queryUserOrders,
} from "../../../utils/ArequestHelper";
import to from "await-to-js";
import {
  ORDER_PAY_STATUS,
  ORDER_PAYMENT,
  ORDER_SHOW_TYPE,
  ORDER_STATUS,
  PAYMENT_TYPE,
  PAYMENT_TYPE_STATUS,
  TABS,
} from "../../../utils/order";

export default defineComponent({
  components: {
    PayItem,
    NavBar,
    commodityCards,
    uniPopup,
  },
  setup() {
    const active = ref("-1");
    const tabs = [
      { label: "全部", value: "-1", count: 0, showCount: true },
      { label: "待付款", value: "0", count: 0, showCount: false },
      { label: "待发货", value: "1", count: 0, showCount: false },
      { label: "待收货", value: "2", count: 0, showCount: false },
      { label: "已完成", value: "3", count: 0, showCount: false },
    ];
    const total = ref(0);
    onLoad(() => {
      initData();

      uni.$on("orderUpdate", (data) => {
        console.log(data);
        active.value = "-1";
        initData();
      });
    });

    onReachBottom(() => {
      console.log("触底");
      loadMore();
    });

    // 菜单栏
    function handleTableClick(state: string) {
      //传递数据的多元化
      if (active.value !== state) {
        active.value = state;
        tabs.forEach((tab) => {
          tab.showCount = tab.value === state;
        });
        initData();
      }
    }
    // 初始化列表
    function initData() {
      page.value = 1;
      orderList.value = [];
      queryUserOrdersFunc(composeQueryParams());
    }

    // 确认收货
    const handleConfirmReceipt = async (orderId: any) => {
      console.log(`订单 ${orderId} 确认收货`);
      const [error, result] = await to(confirmReceipt(orderId));
      if (error) {
        // todo 错误统一处理方式
        console.error(`订单${orderId} : ${error} 确认收货失败`);
      }
      if (result) {
        const {
          data: { code },
        } = result;
        if (code == 1) {
          // todo 错误统一处理方式
          console.error(`订单${orderId} : ${result} 确认收货失败`);
        }
        if (code == 0) {
          refreshActiveTab();
        }
      }
    };
    // 删除订单
    const handleDeleteOrder = async (orderId: any) => {
      console.log(`删除 ${orderId} 的订单`);
      const [error, result] = await to(deleteUserOrder([orderId]));
      if (error) {
        // todo 错误统一处理方式
        console.error(`订单${orderId} : ${error} 删除失败`);
      }
      if (result) {
        const {
          data: { code },
        } = result;
        if (code == 1) {
          // todo 错误统一处理方式
          console.error(`订单${orderId} : ${result} 删除失败`);
        }
        if (code == 0) {
          refreshActiveTab();
        }
      }
    };

    const refreshActiveTab = () => {
      page.value = 1;
      queryUserOrdersFunc(composeQueryParams());
    };

    // 订单列表
    const orderList = ref([]);
    const page = ref(1);
    const pageSize = 10;

    const composeQueryParams = () => {
      console.log(active.value);
      const queryParams = {
        current: page.value,
        size: pageSize,
        paymentState: active.value == -1 ? "" : active.value == 0 ? 0 : 3,
        state: active.value <= 0 ? "" : active.value,
      };
      console.log(queryParams);
      return queryParams;
    };
    const queryUserOrdersFunc = async (params, push = false) => {
      const [error, result] = await to(queryUserOrders(params));
      if (error) {
        // todo 错误处理方式待统一
        console.error(`查询订单列表失败 ${error}`);
      }
      if (result) {
        console.log("订单列表", result);
        const {
          data: {
            data: { records, total: totalCount },
            code,
          },
        } = result;
        if (code == 1) {
          // todo 错误处理方式待统一
          console.error(`查询订单列表失败 ${result}`);
        }
        if (code == 0) {
          records.forEach(addOrderStatus);
          if (push) {
            orderList.value.push(...records);
          } else {
            orderList.value = records;
          }

          const counts = {
            all: 0,
            unpaid: 0,
            pendingShipment: 0,
            pendingReceipt: 0,
            completed: 0,
          };
          records.forEach((order) => {
            counts.all++;
            if (order.paymentState === ORDER_PAY_STATUS.UNPAID) {
              counts.unpaid++;
            }
            if (order.state === ORDER_STATUS.PENDING_SHIPMENT) {
              counts.pendingShipment++;
            }
            if (order.state === ORDER_STATUS.PENDING_RECEIPT) {
              counts.pendingReceipt++;
            }
            if (order.state === ORDER_STATUS.COMPLETED) {
              counts.completed++;
            }
          });
          tabs.forEach((tab) => {
            switch (tab.value) {
              case "-1":
                tab.count = counts.all;
                break;
              case "0":
                tab.count = counts.unpaid;
                break;
              case "1":
                tab.count = counts.pendingShipment;
                break;
              case "2":
                tab.count = counts.pendingReceipt;
                break;
              case "3":
                tab.count = counts.completed;
                break;
            }
          });
          if (totalCount > 0) {
            if (!push) {
              total.value = totalCount;
              console.log("总条数=======——————====", total.value);
            }
          } else {
            console.log("总条数不变，totalCount 为 0");
          }
        }
      }
    };

    const loadMore = () => {
      page.value += 1;
      queryUserOrdersFunc(composeQueryParams(), true);
    };

    // 新增字段 showStatus 页面上显示订单状态
    // 新增字段 paymentStatus 页面上显示支付状态
    // 新增字段 showPaymentType 显示微信支付/支付宝支付
    const addOrderStatus = (order) => {
      let showStatus = "";
      let paymentStatus = "";

      // 待付款
      if (order.paymentState === ORDER_PAY_STATUS.UNPAID) {
        showStatus = ORDER_SHOW_TYPE.UNPAID;
        paymentStatus = ORDER_PAYMENT.UNPAID;
      }

      // 付款成功
      if (order.paymentState === ORDER_PAY_STATUS.SUCCESS) {
        paymentStatus = ORDER_PAYMENT.ACTUAL_PAYMENT;

        // 待发货
        if (order.state === ORDER_STATUS.PENDING_SHIPMENT) {
          showStatus = ORDER_SHOW_TYPE.SHIPMENT;
        }

        // 待收货
        if (order.state === ORDER_STATUS.PENDING_RECEIPT) {
          showStatus = ORDER_SHOW_TYPE.RECEIPT;
        }

        // 订单已完成
        if (order.state === ORDER_STATUS.COMPLETED) {
          showStatus = ORDER_SHOW_TYPE.COMPLETED;
        }
      }

      //  微信支付/支付宝支付
      order.showPaymentType =
        order.paymentType === PAYMENT_TYPE_STATUS.WECHAT
          ? PAYMENT_TYPE.WECHAT
          : PAYMENT_TYPE.ALIPAY;

      order.showStatus = showStatus;
      order.paymentStatus = paymentStatus;
    };

    return {
      t,
      active,
      tabs,
      orderList,
      handleTableClick,
      handleConfirmReceipt,
      handleDeleteOrder,
      loadMore,
      customNavigateTo,
      total,
    };
  },
});
</script>

<style lang="scss">
// 暂时移除scoped，生效page样式
// scoped:在 Vue 单文件组件中，scoped 属性会限制样式只在当前组件内生效。如果 page 是全局样式的一部分，需要确保样式正确应用。
* {
  margin: 0;
  padding: 0;
}

page {
  background-color: #f6f7f9;
}

// 切换标签
.tabs {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 0 20rpx;
  position: sticky;
  z-index: 99;
  top: 0;
  background-color: #ffffff;
  width: 100%;
  height: 80rpx;
  box-sizing: border-box;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

  .tab-item {
    padding: 10px;
    font-size: 16px;
    cursor: pointer;
    position: relative;

    font-weight: 400;
    font-size: 28rpx;
    color: #888888;

    &.active {
      font-weight: bold;
      color: #008cea;
      &::after {
        content: "";
        position: absolute;
        left: 50%;
        bottom: 0;
        transform: translateX(-50%);
        width: 80%;
        height: 2px;
        background-color: #008cea;
        border-radius: 2px;
      }
    }
  }
  // 标签
  .badge {
    position: absolute;
    top: 0rpx;
    right: -12rpx;
    background-color: #ffffff;
    border: 1px solid red;
    color: red;
    border-radius: 50%;
    width: 30rpx;
    height: 30rpx;
    font-size: 19rpx;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}

.ordersLists {
  padding: 20rpx 0;
}

// 去支付弹窗
.payMagin {
  margin: 3% 3% 6% 3%;

  .paymentPopup {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 5%;

    p {
      color: #000000;
      font-size: 30px;
    }

    .paymentGroup {
      width: 100%;
    }

    .paymentRadio {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin: 5% 0;
    }

    .paymentText {
      display: flex;
      align-items: center;
      justify-content: start;
      width: 30%;

      span {
        margin-left: 10%;
      }
    }

    button {
      background: #1e70e2;
      width: 100%;
      font-size: 15px;
      border-radius: 20px;
      color: #ffffff;
      letter-spacing: 5px;
      font-weight: bold;
    }
  }
}
.scrollable-container {
  max-height: calc(100vh - 140px); /* 调整此值以适应导航栏和选项卡的高度 */
  overflow-y: auto; /* 启用垂直滚动 */
}

.empty {
  width: 100%;
  height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
  .content {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    color: #c0c4cc;
    .icon-wushuju {
      font-size: 120rpx;
      color: #c0c4cc;
      margin-bottom: 20rpx;
    }
  }
}
</style>
