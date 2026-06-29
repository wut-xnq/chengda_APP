<template>
  <div class="conversation-wrapper">
    <div
      class="dropdown-mark"
      v-if="addDropdownVisible"
      @touchstart="hideAddDropdown"
    ></div>
    <div class="navigation-bar">
      <div class="logo-box">
        <image
          src="/static/home/logo.png"
          class="logo-img"
        />
        <div style="font-weight: 600;">{{ t('appText') }}</div>
      </div>
      <div :class="buttonClass">
		  
		  <!-- 以#ifdef 或#ifndef 加%PLATFORM% 开头，以 #endif 结尾。
		  #ifdef :if defined 仅在某平台存在 
		  #ifndef :if not defined 除了某平台均存在
		  %PLATFORM% :平台名称 -->

        <!-- #ifdef MP -->
        <image
          src="https://yx-web-nosdn.netease.im/common/9ae07d276ba2833b678a4077960e2d1e/Group 1899.png"
          class="button-icon"
          @tap="showAddDropdown"
        />
        <!-- #endif -->
        <!-- #ifndef MP -->
        <div class="button-icon-add" @tap="showAddDropdown">
          <!-- <Icon type="icon-More" /> -->
		  <view class="iconfont icon-tianjia" style="fonmt-size: 38rpx;"></view>
        </div>
        <!-- #endif -->
		<!-- 添加好友/创建群聊 -->
        <div v-if="addDropdownVisible" class="dropdown-container">
          <div class="add-menu-list">
			  <div class="add-menu-item" @tap="onScan()">
				<view class="iconfont icon-saoyisao" style="fonmt-size: 38rpx;margin-right: 10rpx;"></view>
			    扫一扫
			  </div>
            <div class="add-menu-item" @tap="onDropdownClick('addFriend')">
              <Icon type="icon-tianjiahaoyou" :style="{ marginRight: '5px' }" />
              {{ t('addFriendText') }}
            </div>
            <div class="add-menu-item" @tap="onDropdownClick('createGroup')">
              <Icon
                type="icon-chuangjianqunzu"
                :style="{ marginRight: '5px' }"
              />
              {{ t('createTeamText') }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="block"></div>
    <NetworkAlert />
    <!-- 页面初始化的过程中，sessionList编译到小程序和h5出现sessionList为undefined的情况，即使给了默认值为空数组，故在此处进行判断 -->
    <Empty
      v-if="!sessionList || sessionList.length === 0"
      :text="t('conversationEmptyText')"
    />
    <div v-else class="conversation-list-wrapper">
      <!-- 此处的key如果用session.id，会在ios上渲染存在问题，会出现会话列表显示undefined -->
      <div v-for="(session, index) in sessionList" :key="session.renderKey">
        <ConversationItem
          :key="session.renderKey"
          :showMoreActions="currentMoveSessionId === session.id"
          :session="session"
          @delete="handleSessionItemDeleteClick"
          @stickyToTop="handleSessionItemStickTopChange"
          @click="handleSessionItemClick"
          @leftSlide="handleSessionItemLeftSlide"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import type { NimKitCoreTypes } from '@xkit-yx/core-kit'

import { ref } from '../../../utils/transformVue'
import { autorun } from 'mobx'
import { onHide } from '@dcloudio/uni-app'

import Icon from '../../../components/Icon.vue'
import NetworkAlert from '../../../components/NetworkAlert.vue'
// @ts-ignore
import Empty from '../../../components/Empty.vue'
import ConversationItem from './conversation-item.vue'
import { setContactTabUnread, setTabUnread } from '../../../utils/msg'
import { t } from '../../../utils/i18n'
import { customNavigateTo } from '../../../utils/customNavigate'
import { deepClone } from '../../../utils'
import { AT_ALL_ACCOUNT } from '../../../utils/constants'
import type { IMMessage } from '@xkit-yx/im-store'
import { on } from 'stream'
const sessionList = ref<NimKitCoreTypes.ISession[]>([])
const addDropdownVisible = ref(false)

const currentMoveSessionId = ref('')

let buttonClass = 'button-box'
// #ifdef MP
buttonClass = 'button-box-mp'
// #endif

const handleSessionItemLeftSlide = (
  session: NimKitCoreTypes.ISession | null
) => {
  // 微信小程序点击也会触发左滑事件，但此时 session 为 null
  if (session) {
    currentMoveSessionId.value = session.id
  } else {
    currentMoveSessionId.value = ''
  }
}

let flag = false
// 点击会话
const handleSessionItemClick = async (session: NimKitCoreTypes.ISession) => {
  if (flag) return
  currentMoveSessionId.value = ''
  try {
    flag = true
    // @ts-ignore
    await uni.$UIKitStore.uiStore.selectSession(session.id)
    customNavigateTo({
      url: '/pages/Chat/index',
    })
  } catch {
    uni.showToast({
      title: t('selectSessionFailText'),
      icon: 'error',
    })
  } finally {
    flag = false
  }
  // @ts-ignore
  if (uni.$currentAudioContext) {
    // @ts-ignore
    uni.$currentAudioContext?.destroy()
    // @ts-ignore
    uni.$currentAudioContext = null
  }
}
// 删除会话
const handleSessionItemDeleteClick = async (
  session: NimKitCoreTypes.ISession
) => {
  try {
    // @ts-ignore
    await uni.$UIKitStore.sessionStore.deleteSessionActive(session.id)
  } catch {
    uni.showToast({
      title: t('deleteSessionFailText'),
      icon: 'error',
    })
  }
}
// 置顶会话
const handleSessionItemStickTopChange = async (
  session: NimKitCoreTypes.ISession
) => {
  if (session.stickTopInfo?.isStickOnTop) {
    try {
      // @ts-ignore
      await uni.$UIKitStore.sessionStore.deleteStickTopSessionActive(session.id)
    } catch {
      uni.showToast({
        title: t('deleteStickTopFailText'),
        icon: 'error',
      })
    }
  } else {
    try {
      // @ts-ignore
      await uni.$UIKitStore.sessionStore.addStickTopSessionActive(session.id)
    } catch {
      uni.showToast({
        title: t('addStickTopFailText'),
        icon: 'error',
      })
    }
  }
}

function onScan(){
	uni.showModal({
	  title: '需要相机和存储权限',
	  content: '开启相机和存储权限，即可扫描好友二维码添加联系人',
	  confirmText: t('Allow'),
	  cancelText: t('Cancel'),
	  success: (modalRes) => {
	    if (!modalRes.confirm) {
	      return; // 用户取消，不继续
	    }
	
	    // 2. 用户同意后，调用 chooseImage（此时系统会自动触发原生权限请求）
	    uni.scanCode({
	    	success: function (res) {
	    		const user = JSON.parse(res.result)
	    		uni.navigateTo({
	    			url:"/pages/Friend/add-friend/index?userId="+user.id
	    		})
	    	}
	    });
	    addDropdownVisible.value = false;
	  },
	});
	
}

const showAddDropdown = () => {
  addDropdownVisible.value = true
}

const hideAddDropdown = () => {
  addDropdownVisible.value = false
}

const onDropdownClick = (urlType: string) => {
  const urlMap = {
    // 添加好友
    addFriend: '/pages/Friend/add-friend/index',
    // 创建群聊
    createGroup: '/pages/Group/group-create/index',
  }
  addDropdownVisible.value = false
  customNavigateTo({
    // @ts-ignore
    url: urlMap[urlType],
  })
}

onHide(() => {
  addDropdownVisible.value = false
})

const needShowBeMentioned = (msgs: IMMessage[]) => {
  let flag = false
  // 遍历未读消息，判断是否被@
  msgs.forEach((msg) => {
    if (msg?.ext) {
      try {
        const extObj = JSON.parse(msg.ext)
        const yxAitMsg = extObj.yxAitMsg
        // @ts-ignore
        const account = uni.$UIKitStore?.userStore?.myUserInfo?.account
        if (yxAitMsg) {
          Object.keys(yxAitMsg).forEach((key) => {
            if (key === account || key === AT_ALL_ACCOUNT) {
              flag = true
            }
          })
        }
      } catch {}
    }
  })
  return flag
}

autorun(() => {
  //@ts-ignore
  sessionList.value = deepClone(uni.$UIKitStore?.uiStore?.sessionList)?.map(
    (session: NimKitCoreTypes.ISession, index: number) => {
      return {
        ...session,
        renderKey: session.id + index,
        beMentioned: needShowBeMentioned(session.unreadMsgs || []),
      }
    }
  )
  setTabUnread()
})

autorun(() => {
  // 为了监听会触发得留着这个 console
  console.log(
    'unreadSysMsgCount:',
    // @ts-ignore
    uni.$UIKitStore?.sysMsgStore?.unreadSysMsgCount
  )
  setContactTabUnread()
})
</script>

<style lang="scss" scoped>
@import '../../styles/common.scss';

.conversation-wrapper {
  height: 100vh;
  overflow: hidden;
}

.navigation-bar {
  position: fixed;
  height: 60px;
  border-bottom: 1rpx solid #e9eff5;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: var(--status-bar-height);
  background-color: #fff;
  width: 100%;
  opacity: 1;
  z-index: 999;
}

.block {
  height: 60px;
  width: 100%;
  display: block;
  padding-top: var(--status-bar-height);
}

.conversation-list-wrapper {
  height: calc(100% - 60px - var(--status-bar-height));
  box-sizing: border-box;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}

.logo-box {
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: 500;

  .logo-img {
    width: 32px;
    height: 32px;
    margin-right: 10px;
  }
}

.button-icon-add {
  position: relative;
  right: 20px;
}

.dropdown-mark {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 1;
}

.dropdown-container {
  position: absolute;
  // #ifdef MP
  top: -105px;
  // #endif
  // #ifndef MP
  top: 100%;
  // #endif
  right: 30px;
  min-width: 122px;
  min-height: 40px;
  background-color: #fff;
  border: 1px solid #e6e6e6;
  box-shadow: 0px 4px 7px rgba(133, 136, 140, 0.25);
  border-radius: 8px;
  z-index: 99;
}

.add-menu-list {
  padding: 15px 10px;

  .add-menu-item {
    white-space: nowrap;
    font-size: 16px;
    padding-left: 5px;
    margin-bottom: 10px;
    height: 30px;
    line-height: 30px;
    display: flex;
    align-items: center;

    &:last-child {
      margin-bottom: 0;
    }
  }
}

.conversation-block {
  width: 100%;
  height: 72px;
}
</style>
