<template>
  <div class="wrapper">
    <NavBar :title="t('DeliveryAddress')" />
    <div style="margin-top: 10rpx" v-if="addressLists.length > 0">
      <div
        class="addressCards"
        v-for="(item, index) in addressLists"
        :key="item.id"
      >
        <div class="textMessage" @click="selectAddress(item)">
          <span>{{ item.concatPerson }}</span>
          <span>{{ item.contactPhone }}</span>
          <span class="tag" v-if="item.ifDefault === '1'">默认</span>
          <p>{{ item.fullAddress }}</p>
        </div>
        <div class="editLogo" @click="editAddress(item)">
          <image
            src="/static/departmentStorePicture/iconPark-edit-two 1.svg"
            mode=""
          ></image>
        </div>
      </div>
    </div>

    <div v-else class="empty">
      <div class="content">
        <i class="iconfont icon-wushuju"></i>
        <view>暂无收货地址</view>
      </div>
    </div>

    <div class="addAddress">
      <view class="btn" @click="newAddress()">新增地址</view>
    </div>
  </div>
</template>

<script lang="ts" setup>
import NavBar from "../../../../components/NavBar.vue";
import { t } from "../../../../utils/i18n";
import {
  customNavigateTo,
  customRedirectTo,
} from "../../../../utils/customNavigate";
import { ref } from "@vue/composition-api";
import {
  modifyShippingAddress,
  resDeliveryAddress,
} from "utils/ArequestHelper";
import { onLoad, onShow } from "@dcloudio/uni-app";

// 定义地址类型
interface AddressLists {
  address: string;
  cityCode: number;
  concatPerson: string;
  contactPhone: string;
  countryCode: number;
  createTime: string;
  fullAddress: string;
  id: string;
  ifDefault: string;
  provinceCode: number;
  state: string;
  userId: string;
}

const storedData = uni.getStorageSync("firstLoginUser");

// 响应式变量，用于存储地址列表
const addressLists = ref<AddressLists[]>([]);
const orderId = ref();

// 调取页面数据方法
const loadingResAddress = async () => {
  const resAddress = await resDeliveryAddress(storedData.user_id);
  console.log("resAddress", resAddress);
  // 对地址列表进行排序，将 ifDefault 为 '1' 的地址排在前面
  const sortedAddressLists = resAddress.data.data.sort(
    (a: { ifDefault: string }, b: { ifDefault: string }) => {
      if (a.ifDefault === "1") return -1;
      if (b.ifDefault === "1") return 1;
      return 0;
    }
  );
  addressLists.value = sortedAddressLists;
  console.log("地址列表", addressLists.value);
};

onLoad((options) => {
  // 接收传递过来的 orderId 参数
  orderId.value = decodeURIComponent(options.orderId || "");
});

// 页面加载时初始化地址列表
onShow(() => {
  loadingResAddress();
});

// 修改地址
const editAddress = (item: AddressLists) => {
  // console.log('修改地址......',item)
  // 将对象转换为查询字符串
  const queryString = Object.entries(item)
    .map(([key, value]) => {
      if (
        typeof value === "string" ||
        typeof value === "number" ||
        typeof value === "boolean"
      ) {
        return `${key}=${encodeURIComponent(value as string)}`;
      }
      return "";
    })
    .join("&");

  customNavigateTo({
    url: `/pages/user-card/more-settings/deliveryAddress/modifyNew/editCardAddress?${queryString}`,
  });
};

// 新增地址
const newAddress = async () => {
  console.log("新增地址......");
  customNavigateTo({
    url: "/pages/user-card/more-settings/deliveryAddress/modifyNew/newCardAddress",
  });
};

// 订单选择地址
const selectAddress = async (data: obejct) => {
  uni.$emit("selectAdrees", { data: data });
  // uni.navigateBack()
  // console.log("开始选择地址......");
  // if (!orderId.value) {
  //   console.log("没有订单ID orderId");
  //   return;
  // }
  // try {
  //   console.log("地址的Address的ID", id);
  //   console.log("接收到的订单ID:", orderId.value);
  //   const firstLoginUser = uni.getStorageSync("firstLoginUser");
  //   const newData = await modifyShippingAddress(
  //     orderId.value,
  //     id,
  //     firstLoginUser.access_token
  //   );
  //   console.log("是否成功了，老铁", newData);
  //   // 将新数据转换为 JSON 字符串并进行 URL 编码
  //   const newDataString = encodeURIComponent(JSON.stringify(newData));
  //   customRedirectTo({
  //     url: `/pages/goShopping/orderPayment/index?data=${newDataString}`,
  //   });
  // } catch (error) {
  //   console.error("修改配送地址失败", error);
  // }
};
</script>

<style lang="scss" scoped>
@import "../../../styles/common.scss";

page {
  padding-top: var(--status-bar-height);
  height: 100vh;
  background-color: rgb(245, 246, 247);
}

.wrapper {
  background-color: rgb(245, 246, 247);
  width: 100%;
  height: 100vh;
  box-sizing: border-box;
}

.addressCards {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30rpx;
  background-color: #fff;
  margin-bottom: 2rpx;
}

.textMessage {
  width: 80%;

  span {
    font-weight: 400;
    font-size: 28rpx;
    color: #000000;
    line-height: 26rpx;
    margin-right: 20rpx;
  }

  .tag {
    padding: 1rpx 6rpx;
    border: 1px solid #ff1200;
    font-weight: 400;
    font-size: 24rpx;
    line-height: 24rpx;
    color: #e70000;
    border-radius: 4rpx;
  }
  p {
    font-weight: 400;
    font-size: 24rpx;
    color: #808080;
    line-height: 32px;
  }
}

.editLogo {
  image {
    width: 20px;
    height: 20px;
  }
}

.addAddress {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 10;
  padding: 10rpx 30rpx 30rpx 30rpx;
  background-color: #fff;
  .btn {
    height: 80rpx;
    line-height: 80rpx;
    text-align: center;
    background-color: rgba(0, 140, 234, 1);
    color: white;
    border: none;
    border-radius: 40rpx;
  }
}

.empty{
	width: 100%;
	height: 80vh;
	display: flex;
	align-items: center;
	justify-content: center;
	.content{
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		color: #C0C4CC;
		.icon-wushuju{
			font-size: 120rpx;
			color: #C0C4CC;
			margin-bottom: 20rpx;
		}
	}
}
</style>
