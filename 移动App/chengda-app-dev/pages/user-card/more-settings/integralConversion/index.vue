<!-- 积分转换页面 -->
<template>
  <div class="wrapper">
    <NavBar :title="t('integralConversion')">
      <template #right>
        <span class="selectAll" @click="selectAllClick">{{
          selectAllText
        }}</span>
      </template>
    </NavBar>
    <!-- 样式 -->
    <view class="uni-list" v-if="IntegralConversion.length > 0">
      <checkbox-group @change="checkboxChange">
        <label
          class="uni-list-cell uni-list-cell-pd pointsAll"
          v-for="item in IntegralConversion"
          :key="item.userId"
        >
          <div class="pointsImage">
            <image
              class="pointsImage"
              :src="item.avatar"
              mode="aspectFit"
            ></image>
          </div>
          <div class="pointsnj">
            <span>{{ item.nickname }}</span>
            <span>积分：{{ item.userScore }}</span>
          </div>
          <view>
            <checkbox :value="item.userId" :checked="item.checked" />
          </view>
        </label>
      </checkbox-group>
    </view>

    <view v-else class="empty">
      <view class="content">
        <i class="iconfont icon-wushuju"></i>
        <view>暂无数据</view>
      </view>
    </view>

    <button class="fixed-button" @click="initiateBtn">发起</button>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "@vue/composition-api";
import NavBar from "../../../../components/NavBar.vue";
import { t } from "../../../../utils/i18n";
import {
  initiatingIntegralConversion,
  resIntegralConversion,
} from "utils/ArequestHelper";
//暂时测试接口有误问题物流接口
// import { resLogisticsView } from 'utils/ArequestHelper';

const firstLoginUser = uni.getStorageSync("firstLoginUser");
// console.log('打印下缓存中的用户信息',firstLoginUser)
// user_id	access_token
const IntegralConversion = ref({});
const selectAllText = ref("全选");
const selectedUserIds = ref<string[]>([]); // 明确声明类型为 string[]

const selectAllClick = () => {
  const allChecked = IntegralConversion.value.every((item) => item.checked);
  const newCheckedValue = !allChecked;
  selectedUserIds.value = newCheckedValue
    ? IntegralConversion.value.map((item) => item.userId)
    : [];
  IntegralConversion.value.forEach((item) => {
    item.checked = newCheckedValue;
  });
  // 更新全选按钮的文字
  selectAllText.value = allChecked ? "全选" : "全不选";
};

const checkboxChange = (event: { detail: { value: string[] } }) => {
  console.log("Checkbox change event:", event.detail);
  selectedUserIds.value = event.detail.value;
};

// 发起按钮
const initiateBtn = async () => {
  console.log("是否为空", selectedUserIds.value);
  if (selectedUserIds.value.length == 0) {
    uni.showToast({
      title: "请选择发起人",
      icon: "none",
      duration: 2000,
    });
    return;
  }
  try {
    console.log("发起转换积分", selectedUserIds.value);
    const result = await initiatingIntegralConversion(
      firstLoginUser.access_token,
      selectedUserIds.value
    );
    console.log("打印看看", result);
    if (result.data.code === 1) throw new Error(result.data.msg);
    uni.showToast({
      title: "发起转换积分",
      icon: "success",
      duration: 2000,
    });
  } catch (e) {
    //TODO handle the exception
    uni.showToast({
      title: e.message || e.msg,
      icon: "none",
      duration: 2000,
    });
  }
};

onMounted(async () => {
  try {
    // const resIntegralConversionData = await resIntegralConversion(firstLoginUser.user_id, firstLoginUser.access_token)
    // IntegralConversion.value = resIntegralConversionData.data.data
    // console.log('打印请求的数据', IntegralConversion.value)

    const resIntegralConversionData = await resIntegralConversion(
      firstLoginUser.user_id,
      firstLoginUser.access_token
    );
    IntegralConversion.value = resIntegralConversionData.data.data.map(
      (item: any) => ({
        ...item,
        checked: false,
      })
    );
    console.log("打印请求的数据", IntegralConversion.value);

    // 请求物流接口暂时使用
    // const res = await resLogisticsView('78843047692960','中通快递',firstLoginUser.access_token)
    // console.log('打印出订单物流信息',res)
  } catch (error) {
    console.error("请求失败", error);
  }
});
</script>

<style lang="scss" scoped>
@import "../../../styles/common.scss";

.wrapper {
  background-color: rgb(245, 246, 247);
  width: 100%;
  height: 100vh;
  box-sizing: border-box;
}

.selectAll {
  color: #3a82e6;
}

.pointsAll {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5%;

  .pointsImage {
    height: 50px;
    width: 50px;
    border-radius: 50%;
  }

  .pointsnj {
    display: flex;
    flex-direction: column;
    align-items: start;
    width: 70%;

    span {
      color: #696363;
      font-weight: 600;
    }

    span + span {
      margin-top: 2px;
      color: #9a9a9a;
      font-weight: 500;
    }
  }
}

.fixed-button {
  position: fixed;
  bottom: 20px;
  left: 0;
  right: 0;
  width: 90%;
  height: 40px;
  line-height: 40px;
  text-align: center;
  background-color: #1e70e2;
  color: white;
  border: none;
  z-index: 10;
  border-radius: 20px;
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
