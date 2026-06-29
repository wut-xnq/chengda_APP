<template>
  <view>
    <!-- 背景色 -->
    <div class="custom-nav">
      <div class="status_bar"></div>
      <div class="bar-bg">
        <div class="left" @tap="back">
          <Icon type="icon-zuojiantou" :size="22" class="icon-left"></Icon>
        </div>
        <div class="title">
          <!-- <div class="title">{{ title }}</div> -->
          <!-- <div class="subTitle" v-if="subTitle">{{ subTitle }}</div> -->
        </div>
        <div class="right"></div>
      </div>
    </div>
    <!-- 导航栏占位 -->
    <div class="titleNview-placing" />

    <view class="code-box" v-if="userDateils">
      <view class="header">
        <image class="img" :src="userDateils.avatar" mode=""></image>
        <view>
          <view class="name">{{ userDateils.nickname }}</view>
        </view>
      </view>
      <view class="code-img">
        <canvas
          ref="canvas"
          canvas-id="myCanvas"
          id="qrcode"
          class="code"
        ></canvas>
      </view>
    </view>

    <view class="desc">扫一扫上面的二维码图案，加我好友。</view>
    <view
      class="code-text"
      v-if="userDateils && userDateils.verified === '2'"
      @click="copy(userDateils.invitationCode)"
    >
      邀请码：{{ userDateils.invitationCode }}
    </view>
    <!-- 名片 -->
    <view class="text" v-if="userDateils && userDateils.verified === '2'">
      <view class="tag">持有此名片者为本公司员工</view>
      <!-- <view class="desc">
					<view class="label">公司地址：</view>
					<view>{{user.info.fullAddress}}</view>
				</view> -->
      <view class="desc">
        <view class="label">发证单位：</view>
        <view>{{ userDateils.merchantName }}</view>
      </view>
    </view>
  </view>
</template>

<script lang="ts" setup>
import UQRCode from "uqrcodejs";
import { getSessinUserInfo } from "utils/request/user";
import { onMounted, ref } from "../../../utils/transformVue";
import Icon from "../../../components/Icon.vue";
const userDateils = ref(null);
onMounted(() => {
  getUser();
});
function createQRCode() {
  // 获取uQRCode实例
  // 设置二维码内容
  var qr = new UQRCode();
  const user = {
    id: userDateils.value.phone,
  };
  qr.data = JSON.stringify(user);
  // 设置二维码大小，必须与canvas设置的宽高一致
  qr.size = 220;
  // qr.backgroundColor = 'rgba(255,255,255,1)';
  qr.areaColor = "rgba(255,255,255,0)";
  qr.foregroundColor = "rgba(0,0,0,1)";

  // 调用制作二维码方法
  qr.make();
  // 获取canvas元素
  // 获取canvas上下文
  var canvasContext = uni.createCanvasContext("myCanvas", this);
  // 设置uQRCode实例的canvas上下文
  qr.canvasContext = canvasContext;
  // 调用绘制方法将二维码图案绘制到canvas上
  qr.drawCanvas();
}
async function getUser() {
  userDateils.value = await getSessinUserInfo();
  console.log(
    "===============================================",
    userDateils.value
  );
  setTimeout(() => {
    createQRCode();
  }, 30);
}
function copy(text: any) {
  uni.setClipboardData({
    data: text,
    success: function () {
      uni.showToast({
        title: "个人邀请码复制成功!",
        icon: "none",
      });
    },
  });
}
const back = () => {
  uni.navigateBack();
};
</script>

<style>
page {
  background-color: #fff;
  background-image: url("/static/my/bg-code.png");
  background-size: 100% 100%;
}
</style>
<style lang="scss" scoped>
.custom-nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 999;
  // background: #fff;
  // background-image: url("~@/static/home/whole-bg.png");
  // background-size: 100%;
  // background-repeat: no-repeat;
}

.status_bar {
  height: var(--status-bar-height);
  width: 100%;
}

.bar-bg {
  height: 44px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30rpx;
  box-sizing: border-box;
  image {
    width: 156upx;
    height: 51upx;
  }

  .right {
    width: 60rpx;
  }
}

.titleNview-placing {
  height: var(--status-bar-height);
  padding-top: 44px;
  box-sizing: content-box;
}

.icon-left {
  // filter: invert(100%);
}

.code-box {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  .header {
    width: 220px;
    height: 80rpx;
    margin-top: 100rpx;
    display: flex;
    .img {
      width: 80rpx;
      height: 80rpx;
      border-radius: 10rpx;
    }
    .name {
      font-size: 28rpx;
      color: #4b719b;
      margin-left: 10rpx;
    }
  }
  .code-img {
    margin-top: 100rpx;
    width: 220px;
    height: 220px;
    .code {
      width: 220px;
      height: 220px;
      background: transparent;
    }
  }
}
.desc {
  text-align: center;
  margin: 30rpx 0;
  font-size: 20rpx;
  color: #4b719b;
}

.code-text {
  margin-top: 30rpx;
  font-size: 48rpx;
  text-align: center;
  color: #4b719b;
}

.text {
	transform: scale(0.8);
  font-weight: 400;
  font-size: 24rpx;
  line-height: 30rpx;
  // width:690rpx;
  height: 343rpx;
  padding-left: 147rpx;
  background-image: url("/static/add-bg.png");
  background-size: 100%;

  box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.9); /* 添加阴影效果 */
  border: 1rpx solid #e0e0e0; /* 添加边框 */
  border-radius: 10rpx; /* 圆角边框 */
  .desc {
    display: flex;
    align-item: center;
    padding: 10rpx 0;
    margin-bottom: 12rpx;
    color: #e8c7ff;
    .label {
      width: 130rpx;
    }
    .setting-icon {
      width: 32rpx;
      height: 32rpx;
      margin-right: 12rpx;
    }
  }

  .tag {
    display: inline-block;
    font-weight: 600;
    border-radius: 23px;
    margin-top: 94rpx;
    margin-bottom: 41rpx;
    font-weight: 400;
    font-size: 28rpx;
    color: #fff;
    line-height: 28rpx;
  }
}
</style>
