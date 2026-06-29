<template>
  <div class="user-wrapper">
    <div class="avatar-wrapper">
      <Avatar v-if="props.account" size="70" :account="props.account"></Avatar>
    </div>
    <div class="account-wrapper">
      <div class="nick">{{ nick || account }}</div>
      <div class="account">
		  积分：{{userScore}}
      </div>
    </div>
  </div> 
</template>

<script lang="ts" setup>
import Avatar from './Avatar.vue'
import Icon from './Icon.vue'

import { ref, reactive, onMounted } from '@vue/composition-api';
import { onShow } from '@dcloudio/uni-app';

const props = defineProps({
  account: {
    type: String,
    default: ''
  },
  nick: {
    type: String,
    default: ''
  },
  userScore: {
    type: Number,
    default: ()=> 0
  },
})

const copyAccount = () => {
  uni.setClipboardData({
    data: props.account
  });
}


</script>

<style lang="scss" scoped>
@import "../pages/styles/common.scss";

.user-wrapper {
  // background-color: #fff;
  display: flex;
  align-items: center;

  .avatar-wrapper {
    // margin: 0 15px;
    // flex: 0 0 70px;
  }

  .account-wrapper {
    flex: 1;
    overflow: hidden;
	margin-left: 20rpx;
    .nick {
      font-size: 20px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      font-weight: 500;
    }

    .account {
	  margin-top: 6rpx;
      font-size: 14px;
      display: flex;
      align-items: center;
    }

    .copy-icon {
      margin-left: 2px;
    }
  }

}
</style>
