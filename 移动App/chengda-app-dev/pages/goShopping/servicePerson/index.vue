<template>
  <view>
    <view class="content" v-if="users.list.length > 0">
      <view class="content-item" v-for="item in users.list">
        <view class="title">自我介绍</view>
        <view class="desc">
          <view class="ellipsis-multiline">
            {{ item.brief }}
          </view>
        </view>
        <view class="user">
          <image
            @click="toUserInfo(item.userId)"
            :src="item.avatar || '/static/my/user-default.png'"
            class="user-img"
          ></image>
          <i @click="toUserInfo(item.userId)" class="user-name">{{
            item.nickname
          }}</i>
          <view class="user-add" @click="toUserAdd(item.userId)">加好友</view>
        </view>
      </view>
      <view style="width: 358rpx"></view>
    </view>

    <view v-else class="empty">
      <view class="content">
        <i class="iconfont icon-wushuju"></i>
        <view>暂无数据</view>
      </view>
    </view>
  </view>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from "@vue/composition-api";
import { onLoad, onReachBottom } from "@dcloudio/uni-app";

import { staff } from "utils/request/servicePerson";

const merchantId = ref(null); // 商户id
const users = reactive({
  current: 1,
  size: 10,
  total: 0,
  pages: 1,
  list: [],
}); // 人员列表

onLoad((option) => {
  console.log("传递的参数", option);
  merchantId.value = option?.id;
  initData();
});
function initData() {
  users.current = 1;
  users.total = 0;
  users.list = [];
  getList();
}
/**
 * 获取 人员列表
 */
function getList() {
  staff({ merchantId: merchantId.value }).then((res) => {
    console.log(res);
    const result = res?.data?.data;
    if (result) {
      users.current = result.current;
      users.pages = result.pages;
      users.total = result.total;
      users.list = users.list.concat(result.records);
    }
  });
}
onReachBottom(() => {
  console.log("触底");
  if (users.current < users.pages) {
    ++users.current;
    getList();
  }
});

/**
 * 跳转 个人简介
 */
function toUserInfo(userId: any) {
  uni.navigateTo({
    url: `/pages/goShopping/servicePerson/userInfo?userId=${userId}`,
  });
}
/**
 * 跳转 添加好友
 */
function toUserAdd(userId: any) {
  uni.navigateTo({
    url: `/pages/goShopping/servicePerson/userAdd?userId=${userId}`,
  });
}

/**
 * 添加好友
 * @param {Object} account 用户手机号
 */
function addUser(account: string) {
  uni.$UIKitStore.friendStore.applyFriendActive(account);
}
</script>

<style>
page {
  background: #f5f7f9;
}
</style>
<style lang="scss" scoped>
.content {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;

  padding: 12rpx 10rpx;

  .content-item {
    width: 358rpx;
    height: 367rpx;
    background: #fff;
    margin-bottom: 12rpx;
    padding: 10rpx;
    box-sizing: border-box;

    .title {
      font-weight: bold;
      font-size: 28rpx;
      color: #000000;
      line-height: 28rpx;
    }

    .desc {
      margin-top: 10rpx;
      padding: 26rpx 12rpx;
      height: 214rpx;
      box-sizing: border-box;
      border: 1px solid #ebebeb;

      font-weight: 400;
      font-size: 24rpx;
      color: #797979;
      line-height: 33rpx;
    }

    .ellipsis-multiline {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 5;
      /* 显示的行数，可以根据需要修改 */
      overflow: hidden;
      // text-overflow: ellipsis;
    }

    .user {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-top: 20rpx;

      .user-img {
        width: 72rpx;
        height: 72rpx;
        border-radius: 50%;
      }

      .user-name {
        width: 100rpx;
        font-weight: 400;
        font-size: 30rpx;
        color: #000000;
        line-height: 30rpx;
        margin-right: 20rpx;

        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .user-add {
        width: 130rpx;
        height: 52rpx;
        background: #008cea;
        border-radius: 26rpx;
        border: 1px solid #0e82f0;

        font-weight: 400;
        font-size: 28rpx;
        color: #ffffff;
        line-height: 52rpx;
        text-align: center;
      }
    }
  }
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
