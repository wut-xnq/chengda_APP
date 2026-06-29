<template>
  <div :class="className">
    <div
      v-for="(item, index) in items"
      :key="item.id"
      :class="itemClass"
      @click="$emit('click', item.id)"
    >
      <div
        :class="imageClass"
        :style="{ backgroundImage: `url(${item.productPics.split(',')[0]})` }"
      ></div>
      <span :class="titleStyle">{{ item.productName }}</span>
      <div :class="textTipsClass">
        <span>￥{{ item.currentPrice }}</span>
        <span>￥{{ item.originPrice }}</span>
        <!-- <span>{{ item.orderAmount !== null ? item.orderAmount : 0 }}人付款</span> -->
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType, computed } from "@vue/composition-api";

interface Item {
  id: string;
  productPics: string;
  productName: string;
  currentPrice: string;
  originPrice: string;
  imageUrl: string;
  title: string;
  price: string;
  orderAmount: string;
}

export default defineComponent({
  name: "FavorAble",
  props: {
    className: {
      type: String,
      default: "hezipail",
    },
    itemClass: {
      type: String,
      default: "kkkkil",
    },
    imageClass: {
      type: String,
      default: "images",
    },
    textTipsClass: {
      type: String,
      default: "textTips",
    },
    titleStyle: {
      type: String,
      default: "titleStyle",
    },
    items: {
      type: Array as PropType<Item[]>,
      default: () => [],
    },
  },
  setup() {
    return {};
  },
});
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
}
.hezipail {
  display: flex;
  flex-wrap: wrap;
  justify-content: start;
  gap: 14rpx;
}

.images {
  background-color: skyblue;
  width: 360rpx;
  height: 360rpx;
  background-size: cover;
  background-position: center;
  border-radius: 10rpx 10rpx 0rpx 0rpx;
}

.kkkkil {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 360rpx;
  height: 520rpx;
  background: #ffffff;
  border-radius: 10rpx;
  margin-top: 10rpx;
}

.textTips {
  display: flex;
  justify-content: start;
  align-items: center;
  width: 100%;
  margin-bottom: 31rpx;
  margin-left: 21rpx;
  span {
    color: #ff3600;
    font-weight: 400;
    font-size: 28rpx;
    font-weight: 400;
  }
  span + span {
    color: #aaaaaa;
    font-size: 20rpx;
    white-space: nowrap;
    text-decoration: line-through;
    margin: 0 26rpx 0 9rpx;
  }
  span + span + span {
    color: #808080;
    font-size: 20rpx;
    font-weight: 400;
    white-space: nowrap;
    text-decoration: dashed;
    margin-left: auto;
  }
}

.titleStyle {
  width: 318rpx;
  display: -webkit-box; /* 使用弹性盒模型 */
  -webkit-box-orient: vertical; /* 垂直排列子元素 */
  -webkit-line-clamp: 2; /* 限制显示的行数 */
  overflow: hidden; /* 隐藏溢出的内容 */
  text-overflow: ellipsis; /* 超出部分用省略号代替 */
  word-break: break-all;
  font-size: 28rpx;
  margin: 20rpx 21rpx 31rpx 21rpx;
  font-weight: 400;
}
</style>
