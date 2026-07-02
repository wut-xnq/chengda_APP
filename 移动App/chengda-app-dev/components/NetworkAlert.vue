<template>
  <div v-if="!isConnected && shouldShowAlert && text" class="network-alert">
    {{ text }}
  </div>
</template>

<script lang="ts" setup>
import { autorun } from "mobx";
import { ref, onMounted, onUnmounted } from "../utils/transformVue";
import { t } from "../utils/i18n";
import { getCurrentInstance } from "@vue/runtime-dom";
const isConnected = ref(true);
const text = ref(t("connectingText"));
const shouldShowAlert = ref(false); // 控制是否显示提示条

// 延迟显示断连提示，避免刚登录/刚进入页面时的短暂连接过程弹提示
let alertTimer: ReturnType<typeof setTimeout> | null = null;

function scheduleAlert() {
  // 清除之前的定时器
  if (alertTimer) {
    clearTimeout(alertTimer);
  }
  // 断开状态持续超过 3 秒才显示提示
  alertTimer = setTimeout(() => {
    shouldShowAlert.value = true;
  }, 3000);
}

function hideAlert() {
  if (alertTimer) {
    clearTimeout(alertTimer);
    alertTimer = null;
  }
  shouldShowAlert.value = false;
}
// uni.onNetworkStatusChange((res) => {
//   if (!res.isConnected) {
//     isConnected.value = false;
//     text.value = t('offlineText');
//   } else {
//     text.value = t('connectingText');
//   }
// });

onMounted(() => {
  // @ts-ignore
  if (uni.$UIKitStore?.connectStore?.connectState === "connected") {
    console.log("已连接");
    isConnected.value = true;
    hideAlert();
    // @ts-ignore
  } else if (uni.$UIKitStore?.connectStore?.connectState === "disconnected") {
    console.log("未连接");
    isConnected.value = false;
    text.value = t("offlineText");
    scheduleAlert();
  } else {
    console.log("正在连接");
    isConnected.value = false;
    text.value = t("connectingText");
    scheduleAlert();
  }
});

const uninstallConnectWatch = autorun(() => {

  const connectState = uni.$UIKitStore?.connectStore?.connectState;
  console.log(`==========当前连接状态========: ${connectState}`);
  // @ts-ignore
  if (uni.$UIKitStore?.connectStore?.connectState === "connected") {
    isConnected.value = true;
    hideAlert();
    // @ts-ignore
  } else if (uni.$UIKitStore?.connectStore?.connectState === "disconnected") {
    isConnected.value = false;
    text.value = t("offlineText");
    scheduleAlert();
  } else {
    console.log("正在连接++++++++++++++++++++");
    isConnected.value = false;
    text.value = t("connectingText");
    scheduleAlert();
  }
});

onUnmounted(() => {
  uninstallConnectWatch();
  if (alertTimer) {
    clearTimeout(alertTimer);
    alertTimer = null;
  }
});
</script>

<style>
.network-alert {
  font-size: 14px;
  background: #fee3e6;
  color: #fc596a;
  text-align: center;
  padding: 8px 0;
}
</style>
