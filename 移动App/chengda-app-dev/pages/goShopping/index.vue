<template>
  <div class="wrapper">
    <div class="header">
      <div ref="blockRef" :class="isWeixinApp ? 'block-wx' : 'block'"></div>
      <div
        ref="navBarComponentRef"
        class="nav-bar-component"
        :style="{
          backgroundColor: backgroundColor,
          height: isWeixinApp ? '55px' : '40px',
          alignItems: isWeixinApp ? 'flex-end' : 'center',
        }"
      >
        <div class="nav-bar-wrapper">
          <div class="left-icon" @click="backToWy">
            <Icon type="icon-zuojiantou" :size="15"></Icon>
          </div>
          <div class="search-product">
            <Icon
              type="icon-sousuo"
              :size="12"
              style="margin-left: 20rpx"
            ></Icon>
            <input
              style="font-size: 26rpx"
              type="text"
              placeholder="请输入商品名称"
              v-model.trim="searchMessage"
            />
            <span class="search-btn" @click="searchDebounce">搜索</span>
          </div>
          <div class="my-order" @click="clickOrder">
            <image
              class="icon-order"
              src="/static/shopp/icon-order.png"
              mode=""
            ></image>
            <span>订单</span>
          </div>
        </div>
      </div>

      <div ref="tabNavRef" class="tab_nav">
        <div
          class="navTitle"
          v-for="(item, index) in navList"
          :key="item.index"
        >
          <div
            :class="{
              active: isActive === index,
              'special-style': item.special,
            }"
            @click="checked(index)"
          >
            {{ item.title }}
          </div>
        </div>
      </div>

      <div class="bard-box" v-if="isActive === 2">
        <div class="bard">
          <image class="icon" src="/static/shopp/icon-bard.png" mode=""></image>
          <div class="weather_info_wrap">
            <div class="weather_info">平台方会定期抽检国标区的产品，并在质检区公布质检报告。如果产品不符合国家标准，平台方就会将该产品下架。</div>
          </div>

        </div>
      </div>
    </div>

    <div
      v-if="
        (isActive === 0 && yhproducts && yhproducts.length === 0) ||
        (isActive === 1 && zpproducts && zpproducts.length === 0) ||
        (isActive === 2 && gbproducts && gbproducts.length === 0) ||
        (isActive === 3 &&
          qualityInspectionData &&
          qualityInspectionData.length === 0)
      "
      class="empty"
    >
      <div class="content">
        <i class="iconfont icon-wushuju"></i>
        <view>暂无数据</view>
      </div>
    </div>

    <div v-else>
      <!-- <div class="bard-box" v-if="isActive === 2">
				<div class="bard">
					<image class="icon" src="/static/shopp/icon-bard.png" mode=""></image>
					<div>国标区的产品符合国家标准的产品，比正品区的产品更有保障</div>
				</div>
			</div> -->
      <!-- 优惠 -->
      <div class="nav_item" v-if="isActive === 0">
        <FavorAble :items="yhproducts" @click="clickProductDetails" />
      </div>
      <!-- 正品 -->
      <div class="nav_item" v-if="isActive === 1">
        <FavorAble :items="zpproducts" @click="clickProductDetails" />
      </div>
      <!-- 国标 -->
      <div class="nav_item" v-if="isActive === 2">
        <FavorAble :items="gbproducts" @click="clickProductDetails" />
      </div>
      <!-- 质检 -->
      <div class="nav_item" v-if="isActive === 3">
        <div
          class="quality-product"
          v-for="(item, index) in qualityInspectionData"
          :key="index"
          @click="toQuality(item)"
        >
          <!-- 上部 -->
          <div class="quality-product-top">
            <div class="titleImg">
              <image src="../../static/Inspectionicon/Inspectionicon.png" />
              <span>{{ item.agencyName }}</span>
            </div>
            <span>{{ item.publishDate }}</span>
          </div>
          <!-- 中部 -->
          <div class="quality-product-center">
            <div class="imageDetection">
              <image :src="item.verifiedProduct[0].productPics" />
            </div>
            <!-- 文本信息 -->
            <div class="textInfo">
              <!-- 绑定商品 -->
              <div class="productBind">
                <span class="label">绑定商品：</span>
                <span class="text" v-if="item.verifiedProduct">{{
                  item.verifiedProduct.map((res) => res.productName).join()
                }}</span>
              </div>
              <!-- 质检时间 -->
              <div class="detectionTime">
                <span>质检时间：</span>
                <span>{{ item.testTime }}</span>
              </div>
            </div>
          </div>
          <!-- 下部 -->
          <div class="quality-product-bottom">
            <span>
              {{ item.content }}
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- 加载更多提示 -->
    <!-- <uni-load-more style="position: relative;bottom: 8px;" :status="loadingStatus" :content-text="contentText" iconType='snow' /> -->
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "@vue/composition-api";
import { onReachBottom } from "@dcloudio/uni-app";
import Icon from "../../components/Icon.vue";
import { getUniPlatform } from "../../utils";
import FavorAble from "./components/FavorAble.vue"; // 引入新创建的组件
// import QualityProduct from './components/QualityProduct.vue'; // 引入新创建的组件
// import NationalStandard from './components/NationalStandard.vue'; // 引入新创建的组件
import { customNavigateTo } from "../../utils/customNavigate";
import {
  resQualityInspection,
  resShoppingHomepage,
} from "utils/ArequestHelper";

const isWeixinApp = getUniPlatform() === "mp-weixin";
const backgroundColor = ref("#ffffff");
const isActive = ref(0);
const searchMessage = ref("");
const navList = [
  { index: 3, title: "优惠区", special: false },
  { index: 2, title: "正品区", special: false },
  { index: 1, title: "国标区", special: false },
  { index: 0, title: "质检", special: true },
];

// 商品数据
const yhproducts = ref<any[]>([]);
const zpproducts = ref<any[]>([]);
const gbproducts = ref<any[]>([]);
const qualityInspectionData = ref<any[]>([]); // 新增质检数据的 ref

const page = ref(1);
const pageSize = 10;
const firstLoginUser = uni.getStorageSync("firstLoginUser");

const loadingStatus = ref("more"); // 加载状态
const contentText = {
  contentdown: "上拉显示更多",
  contentrefresh: "正在加载...",
  contentnomore: "没有更多数据了",
};

// todo 这里回头把它放到 工具js里面
const debounce = (fn: Function, delay: number) => {
  let timer: any = null;
  return function (...args: any[]) {
    if (timer) clearTimeout(timer);
    timer = setTimeout(() => {
      fn.apply(this, args);
    }, delay);
  };
};

function toQuality(data) {
  uni.navigateTo({
    url: "/pages/goShopping/productDetailsPage/quality?id=" + data.id,
  });
  console.log(data);
}

const searchEvent = () => {
  page.value = 1;
  checked(isActive.value, searchMessage.value);
};

const searchDebounce = debounce(searchEvent, 500);

// 标签选择请求
const checked = async (index: number, productName = "") => {
  // if (!productName) searchMessage.value = "";
  isActive.value = index;
  console.log("打印下标index", isActive.value);
  switch (index) {
    case 0:
      console.log("执行优惠请求");
      page.value = 1;
      const resphoneShoppingHomepage = await resShoppingHomepage(
        pageSize,
        page.value,
        "3",
        productName,
        firstLoginUser.access_token
      );
      yhproducts.value = resphoneShoppingHomepage.data.data.records;
      // loadMore();
      break;
    case 1:
      console.log("执行正品请求");
      page.value = 1;
      const zpphoneShoppingHomepage = await resShoppingHomepage(
        pageSize,
        page.value,
        "2",
        productName,
        firstLoginUser.access_token
      );
      zpproducts.value = zpphoneShoppingHomepage.data.data.records;
      // loadMore();
      break;
    case 2:
      console.log("执行国标请求");
      page.value = 1;
      const gbphoneShoppingHomepage = await resShoppingHomepage(
        pageSize,
        page.value,
        "1",
        productName,
        firstLoginUser.access_token
      );
      gbproducts.value = gbphoneShoppingHomepage.data.data.records;
      // loadMore();
      break;
    case 3:
      console.log("执行质检请求");
      page.value = 1;
      qualityInspectionData.value = [];
      const qualityInspectionResponse = await resQualityInspection(
        pageSize,
        page.value,
        productName,
        firstLoginUser.access_token
      );
      if (qualityInspectionResponse) {
        qualityInspectionData.value =
          qualityInspectionResponse.data.data.records;
      }

      console.log(qualityInspectionResponse);
      // loadMore();
      break;
    default:
      console.log("执行默认逻辑");
      break;
  }
};

onReachBottom(() => {
  console.log("上拉加载");
  loadMore();
});

// 定义 loadMore 方法
const loadMore = async () => {
  // 根据当前的 isActive.value 来决定加载哪个数据源
  loadingStatus.value = "loading"; // 设置加载状态为加载中
  page.value += 1; // 增加页码
  switch (isActive.value) {
    case 0:
      // 加载更多数据的逻辑
      const moreDataForCase0 = await resShoppingHomepage(
        pageSize,
        page.value,
        "3",
        "",
        firstLoginUser.access_token
      );
      yhproducts.value = [
        ...yhproducts.value,
        ...moreDataForCase0.data.data.records,
      ];
      break;
    case 1:
      // 加载更多正品数据的逻辑
      const moreDataForCase1 = await resShoppingHomepage(
        pageSize,
        page.value,
        "2",
        "",
        firstLoginUser.access_token
      );
      zpproducts.value = [
        ...zpproducts.value,
        ...moreDataForCase1.data.data.records,
      ];
      break;
    case 2:
      // 加载更多国标数据的逻辑
      const moreDataForCase2 = await resShoppingHomepage(
        pageSize,
        page.value,
        "1",
        "",
        firstLoginUser.access_token
      );
      gbproducts.value = [
        ...gbproducts.value,
        ...moreDataForCase2.data.data.records,
      ];
      break;
    case 3:
      // 加载更多质检数据的逻辑
      const moreDataForCase3 = await resQualityInspection(
        pageSize,
        page.value,
        "",
        firstLoginUser.access_token
      );
      qualityInspectionData.value = [
        ...qualityInspectionData.value,
        ...moreDataForCase3.data.data.records,
      ];
      console.log("我在看质检数据", qualityInspectionData.value);
      break;
    default:
      break;
  }
};

onMounted(async () => {
  console.log("加载");
  try {
    // 优惠页面
    const resphoneShoppingHomepage = await resShoppingHomepage(
      10,
      1,
      "3",
      "",
      firstLoginUser.access_token
    );
    yhproducts.value = resphoneShoppingHomepage.data.data.records;
    console.log("分页全部", yhproducts.value);
  } catch (error) {
    //TODO handle the exception
    console.error("获取质检数据失败", error);
  }
});

// 点击进入我的订单
const clickOrder = () => {
  console.log("点击进入我的订单");
  customNavigateTo({
    url: "/pages/goShopping/myOrder/index",
  });
};

// 点击进入商品详情
const clickProductDetails = async (id: string) => {
  console.log("商品详情", id);
  customNavigateTo({
    url: `/pages/goShopping/productDetailsPage/productDetailsPage?id=${id}`,
  });
};
// 返回上一层
const backToWy = () => {
  uni.navigateBack({
    delta: 1,
  });
};
</script>

<style lang="scss" scoped>
@import "../../pages/styles/common.scss";

page {
  padding-top: var(--status-bar-height);
  height: 100vh;
  background-color: rgb(245, 246, 247);
}

.wrapper {
  background-color: rgb(245, 246, 247);
  width: 100%;
  // height: 100vh;
  box-sizing: border-box;
}
.header {
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-bar-component {
  // position: fixed;
  position: sticky;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: var(--status-bar-height) 10px 5px 10px;
  z-index: 9999;
  top: 0;
  left: 0;
  right: 0;
  background-color: #ffffff;

  // box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  .nav-bar-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    // height: 50px;
    width: 100%;

    .left-icon {
      display: flex;
      align-items: center;
    }

    .search-product {
      display: flex;
      justify-content: space-between;
      align-items: center;
      //   padding: 5px 10px;
      width: 574rpx;
      height: 59rpx;
      background: #f6f7f9;
      border-radius: 29rpx;

      input {
        flex: 1;
        border: none;
        outline: none;
        background: transparent;
        margin-left: 5px;
      }

      span {
        font-size: 14px;
        font-weight: 600;
      }
    }

    .my-order {
      display: flex;
      flex-direction: column;
      align-items: center;
      font-size: 22rpx;
      font-weight: 400;
      color: #808080;
      .icon-order {
        width: 36rpx;
        height: 36rpx;
      }
    }
  }
}

.options {
  display: flex;
  justify-content: space-around;
  padding: 5px 0 15px 0;
  font-weight: 600;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  /* 添加阴影效果 */
}

.options-container {
  position: sticky;
  top: 60px;
  /* 根据导航栏的高度调整 */
  z-index: 9998;
  background-color: #ffffff;
  width: 100%;
}

.scrollable-container {
  // max-height: calc(100vh - 120px);
  border: 1px solid red;
  /* 调整此值以适应导航栏和选项卡的高度 */
  overflow-y: auto;
  /* 启用垂直滚动 */
}

// 模块样式
.moduleStyle {
  margin: 8% 4% 4% 4%;
}

// 便签样式
.tab_nav {
  display: flex;
  justify-content: center;
  align-items: center;
  background: #ffffff;
}

.navTitle {
  height: 90rpx;
  line-height: 90rpx;
  width: 100%;
  text-align: center;
  font-size: 28rpx;
  font-family: Microsoft YaHei;
  color: #000; // 默认黑色
  position: relative; // 确保伪元素定位正确
  letter-spacing: 1px;
}

.navTitle > div.active {
  color: #008cea;
  /* 点击后仍然是黑色 */
  font-weight: 900;

  /* 默认加粗 */
  &::after {
    content: "";
    position: absolute;
    width: 42rpx;
    height: 4rpx;
    background-color: #007bff;
    /* 浅蓝色条 */
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;
    z-index: 1;
    /* 确保在正确的层级上显示 */
    border-radius: 4rpx;
  }
}

.special-style {
  color: #f4352e !important;
  font-weight: 900;
}

.nav_item {
  // 可以根据需要添加更多样式
  padding: 12rpx 8rpx;
}

.bard-box {
  position: sticky;
  top: 0;
  background-color: #ffffff;
  .bard {
    display: flex;
    align-items: center;
    padding: 0 30rpx;
    height: 60rpx;
    background: rgba(255, 246, 210, 0.58);
    font-weight: 400;
    font-size: 24rpx;
    color: #c7a82b;
  }
  .icon {
    width: 36rpx;
    height: 34rpx;
    margin-right: 10rpx;
  }
}

// 质检样式
.textBoxs {
  background-color: #ffffff;
  padding: 15px;
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.1),
    inset 0 0 10px rgba(0, 0, 0, 0.05), 2px 2px 5px rgba(0, 0, 0, 0.15),
    -2px -2px 5px rgba(255, 255, 255, 0.5);
  margin-bottom: 20px;

  p {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    overflow: hidden;
  }
}

.textsTips {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;

  span {
    font-weight: 600;
    color: #000000;
  }

  span + span {
    color: #9a9a9a;
    font-size: 12px;
  }
}

// 搜素样式
.search-btn {
  background: #008cea;
  border-radius: 27rpx;
  width: 100rpx;
  height: 53rpx;
  color: #ffffff;
  font-size: 26rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}

// 质检卡片样式
.quality-product {
  width: 734rpx;
  // height: 315rpx;
  background: #ffffff;
  border-radius: 10rpx;
  padding: 22rpx 0 22rpx 0;
  margin-bottom: 10rpx;

  // 上部样式
  .quality-product-top {
    display: flex;
    align-items: center;
    padding: 0 22rpx 0 15rpx;

    .titleImg {
      display: flex;
      align-items: center;

      image {
        width: 32rpx;
        height: 32rpx;
        margin-right: 7rpx;
      }

      span {
        font-weight: bold;
        font-size: 28rpx;
        color: #333333;
      }
    }

    span {
      margin-left: auto;
      font-weight: 400;
      font-size: 26rpx;
      color: #aaaaaa;
    }
  }

  // 中部样式
  .quality-product-center {
    display: flex;
    padding: 25rpx 22rpx 22rpx 15rpx;

    .imageDetection {
      margin-right: 20rpx;

      image {
        width: 135rpx;
        height: 135rpx;
        background: #dcdcdc;
        border-radius: 5rpx;
      }
    }

    // 文本信息
    .textInfo {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      margin: 8rpx 49rpx 13rpx 0;

      .productBind {
        display: flex;
        align-items: flex-start;
        .label {
          width: 120rpx;
          color: #c4c4c4;
          font-size: 24rpx;
          flex-shrink: 0;
        }
        .text {
          color: #333333;
          font-weight: 400;
          font-size: 28rpx;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }

      .detectionTime {
        display: flex;
        align-items: center;

        span {
          color: #c4c4c4;
          font-size: 24rpx;
        }

        span + span {
          color: #333333;
          font-size: 28rpx;
          font-weight: 400;
        }
      }
    }
  }

  // 文本
  .quality-product-bottom {
    height: 57rpx;
    // margin: 0 42rpx 0 30rpx;
    padding: 0 22rpx 22rpx 15rpx;

    span {
      font-weight: 400;
      font-size: 24rpx;
      color: #595959;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
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

// 国标公告

.weather_info_wrap {
		width: 90%;
		overflow: hidden;
		white-space: nowrap;
		font-size: 24rpx;
		display: flex;
	}

	.weather_info {
		animation: 15s wordsLoop linear infinite forwards;
	}
	

	@keyframes wordsLoop {
		0% {
			transform: translateX(0);
			-webkit-transform: translateX(100%);
		}
		100% {
			transform: translateX(-100%);
			-webkit-transform: translateX(-100%);
		}
	}

	@-webkit-keyframes wordsLoop {
		0% {
			transform: translateX(0);
			-webkit-transform: translateX(100%);
		}

		100% {
			transform: translateX(-100%);
			-webkit-transform: translateX(-100%);
		}
	}

</style>
