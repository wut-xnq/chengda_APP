<!-- components/PrivacyConfirm.vue -->
<template>
  <view class="privacy-mask" v-if="show">
    <view class="privacy-dialog">
      <view class="title">用户隐私协议</view>
      <view class="content">
        为保障您的个人信息安全，请仔细阅读并同意我们的《隐私政策》后再使用本应用。
      </view>

      <button class="btn-view" @click="viewPolicy">查看隐私政策</button>

      <view class="buttons">
        <button class="btn cancel" @click="handleDisagree">不同意并退出APP</button>
        <button class="btn agree" @click="handleAgree">同意</button>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: 'PrivacyConfirm',
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    viewPolicy() {
      // 跳转到你指定的隐私政策页面
      uni.navigateTo({
        url: '/pages/protocolPages/PrivacyPolicy/index'
      });
    },
    handleAgree() {
      uni.setStorageSync('privacy_agreed', true);
      this.$emit('agree');
    },
    handleDisagree() {
      uni.setStorageSync('privacy_agreed', false);
      // App 端退出应用
      // #ifdef APP-PLUS
      plus.runtime.quit();
      // #endif
      this.$emit('disagree');
    }
  }
}
</script>

<style scoped>
.privacy-mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 99999;
  display: flex;
  align-items: flex-end;
}

.privacy-dialog {
  width: 100%;
  background-color: #ffffff;
  padding: 40rpx 50rpx;
  box-sizing: border-box;
  border-top-left-radius: 20rpx;
  border-top-right-radius: 20rpx;
}

.title {
  font-size: 36rpx;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20rpx;
  color: #333;
}

.content {
  font-size: 28rpx;
  color: #666;
  text-align: center;
  line-height: 1.5;
  margin-bottom: 30rpx;
}

.btn-view {
  width: 100%;
  height: 80rpx;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 10rpx;
  margin-bottom: 30rpx;
  color: #007aff;
  font-size: 30rpx;
}

.buttons {
  display: flex;
  gap: 20rpx;
}

.btn {
  flex: 1;
  height: 80rpx;
  line-height: 80rpx;
  font-size: 32rpx;
  border-radius: 10rpx;
  border: none;
  color: #fff;
}

.cancel {
  background: #f5f5f5;
  color: #666;
}

.agree {
  background: #007aff;
  color: #fff;
}
</style>