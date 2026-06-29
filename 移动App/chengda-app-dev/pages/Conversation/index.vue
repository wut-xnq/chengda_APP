<template>
	<ConversationList />
</template>

<script lang="ts" setup>
	import ConversationList from './conversation-list/index.vue'
	import { trackInit } from '../../utils/reporter'
	import { onShow } from '@dcloudio/uni-app'
	import { onMounted } from "@vue/composition-api"
	import { setContactTabUnread, setTabUnread } from '../../utils/msg'
	import { getSessinUserInfo } from 'utils/request/user'

	trackInit('ConversationUIKit')

	onMounted(() => {
	})
	onShow(() => {
		uni.hideLoading();
		// 重置选中会话
		// @ts-ignore
		uni.$UIKitStore?.uiStore.selectSession('')
		setTabUnread()
		setContactTabUnread()

		setTimeout(() => {
			judeg()
		}, 300)
	})

	async function judeg() {
		const userInfo : any = await getSessinUserInfo(true);
		if (!userInfo.name || !userInfo.name.trim()) {
			uni.reLaunch({
				url: "/pages/guide/guide"
			})
		} else if (userInfo.ifGiftScore === '1') {
			uni.navigateTo({
				url: "/pages/Conversation/pointsPop?name=" + userInfo.inviter
			})
		}
	}
</script>

<style lang="scss">
	@import '../styles/common.scss';

	page {
		height: 100vh;
		overflow: hidden;
	}
</style>