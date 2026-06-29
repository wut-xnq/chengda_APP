<!-- pages/privacy-agreement-entry/index.vue -->
<template>
  <view class="container">
    <!-- 弹窗组件 -->
    <PrivacyConfirm
      :show="true"
      @agree="handleAgree"
      @disagree="handleDisagree"
    />
  </view>
</template>

<script lang="ts">
import { defineComponent } from '@vue/composition-api';
import PrivacyConfirm from '@/components/PrivacyConfirm.vue';

export default defineComponent({
  components: {
    PrivacyConfirm,
  },
  methods: {
    handleAgree() {
      // 用户同意后，重新加载应用到首页
      uni.setStorageSync('privacy_agreed', true);
      uni.reLaunch({
        url: '/pages/Conversation/index'
      });
    },
    handleDisagree() {
      // 用户不同意，退出应用（仅适用于App端）
      uni.setStorageSync('privacy_agreed', false);
      // #ifdef APP-PLUS
      plus.runtime.quit();
      // #endif
    }
  }
});
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>