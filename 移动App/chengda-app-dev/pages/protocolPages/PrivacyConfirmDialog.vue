<!-- components/PrivacyConfirmDialog.vue -->
<template>
  <view class="dialog-mask" v-if="show">
    <view class="dialog-box">
      <view class="title">用户隐私协议</view>
      <view class="content">
        为保障您的个人信息安全，请阅读并同意我们的《隐私政策》后再使用本应用。
      </view>

      <button class="btn-view" @click="viewPolicy">查看隐私政策</button>

      <view class="buttons">
        <button class="btn cancel" @click="disagree">不同意</button>
        <button class="btn confirm" @click="agree">同意</button>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: 'PrivacyConfirmDialog',
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    viewPolicy() {
      // 跳转到你已有的隐私政策页面（请替换为你的实际路径）
      uni.navigateTo({
        url: '/pages/protocolPages/PrivacyPolicy/index'
      });
    },
    agree() {
      uni.setStorageSync('privacy_agreed', true);
      this.$emit('agree');
    },
    disagree() {
      uni.setStorageSync('privacy_agreed', false);
      // App 端退出
      // #ifdef APP-PLUS
      plus.runtime.quit();
      // #endif
      // H5/小程序可留空或提示
      this.$emit('disagree');
    }
  }
}
</script>

<style scoped>
.dialog-mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  z-index: 99999;
  display: flex;
  align-items: flex-end;
}

.dialog-box {
  width: 100%;
  background: #fff;
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

.confirm {
  background: #007aff;
  color: #fff;
}
</style>