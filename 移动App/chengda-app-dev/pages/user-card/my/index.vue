<template>
	<!-- 现 -->
	<!-- 用户/设置 -->
	<div class="content">
		<div class="navigation-bar"></div>
		
		<div class="setting">
			<image class="setting-icon" src="../../../static/my/my-setting.png" @click="gotoSetting" mode=""></image>
		</div>
		<div class="UserSettings">
			<div class="card-wrapper" @tap="navigatorToMyDetail">
				<UserCard :account="myUserInfo.account" v-if="showBoolen" :nick="myUserInfo.nick" :userScore="userScore"></UserCard>
			</div>
			<image class="user-code" src="../../../static/my/my-code.png" @click="userCode"  mode=""></image>
		</div>
		<div class="modelBorder">
			<!-- 购物 -->
			<div class="modelsEntrancetwo" @click="goShopping"></div>
		</div>
	</div>
</template>

<script lang="ts" setup>
	import { onLoad, onShow,onHide } from '@dcloudio/uni-app'
	import UserCard from '../../../components/UserCard.vue'
	import { onUnmounted, ref } from '../../../utils/transformVue'
	import Icon from '../../../components/Icon.vue'
	import { autorun } from 'mobx'
	import { setContactTabUnread, setTabUnread } from '../../../utils/msg'
	import { customNavigateTo } from '../../../utils/customNavigate'
	import { deepClone } from '../../../utils'
	import { t } from '../../../utils/i18n'
	import { getUserInfo } from 'utils/request/user';
	
	const myUserInfo = ref({
		account: '',
		nick: '',
	})
	const showBoolen = ref(false)
	const userScore = ref(0)

	const iStatusBarHeight = ref(0)

	const uninstallMyUserInfoWatch = autorun(() => {
		myUserInfo.value = deepClone(
			// @ts-ignore
			uni.$UIKitStore.userStore.myUserInfo
		)
		const systemInfo = uni.getSystemInfoSync();
		iStatusBarHeight.value = systemInfo.statusBarHeight ?? 0;
		console.log('打印目前高度', iStatusBarHeight.value)
	})


	onLoad(() => {
		// @ts-ignore
		myUserInfo.value = deepClone(uni.$UIKitStore.userStore.myUserInfo)
	})
	onHide(()=>{
		setTimeout(()=>{
			showBoolen.value =false
		},100)
	})
	onShow(() => {
		showBoolen.value =true
		setTabUnread()
		setContactTabUnread()
		
		const userInfo = uni.getStorageSync('firstLoginUser');
		getUser(userInfo.user_id)
	})
	/**
	 * 获取 人员
	 */
	function getUser(userId:any) {
		getUserInfo({ id: userId }).then(res => {
			const result = res?.data?.data
			if(result){
				userScore.value = result.userScore || 0
			}
		})
	}

	const gotoSetting = () => {
		customNavigateTo({
			url: '/pages/user-card/my/setting',
		})
	}
	function userCode(){
		uni.navigateTo({
			url: '/pages/user-card/my/userCode',
		})
	}

	const navigatorToMyDetail = () => {
		customNavigateTo({
			url: `/pages/user-card/my-detail/index?account=${myUserInfo.value.account}`,
		})
	}
	// 购物
	// 	console.log("去购物。。。")

	// 		customNavigateTo({
	// 			url: '/pages/goShopping/index',
	// 		})
	// }
	const goShopping = () => {
		console.log("去购物。。。")
		customNavigateTo({
			url: '/pages/goShopping/index',
		})
	}


	onUnmounted(() => {
		uninstallMyUserInfoWatch()
	})
</script>

<style lang="scss">
	@import '../../styles/common.scss';

	page {
		height: 100vh;
		background: #f0f2f5;
		overflow: hidden;
	}
	.content{
		background-image: url("../../../static/my/my-bg.png");
		background-repeat: no-repeat;
		background-size: 100% 768rpx;
		
		.navigation-bar {
			height: var(--status-bar-height);
			width: 100%;
		}
		
		.setting{
			display: flex;
			align-items: center;
			justify-content: flex-end;
			padding: 20rpx 30rpx;
			
			.setting-icon{
				width: 41rpx;
				height: 38rpx;
			}
		}
		
		.UserSettings {
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 30rpx;
			// .card-wrapper {
			// 	padding-right: 10px;
			// 	position: relative;
			// 	.arrow {
			// 		position: absolute;
			// 		right: 10px;
			// 		top: calc(50% - 8px);
			// 	}
			// }
			
			.user-code{
				width: 58rpx;
				height: 58rpx;
			}
		}
	}



	// .avatar-wrapper {
	// 	display: flex;
	// 	align-items: center;

	// 	.nick {
	// 		font-size: 20px;
	// 	}

	// 	.account {
	// 		font-size: 14px;
	// 		display: flex;
	// 		align-items: center;
	// 		color: #a6adb6;
	// 	}

	// 	.copy-icon {
	// 		color: #a6adb6;
	// 		font-family: iconfont;
	// 		background-size: 100% 100%;
	// 		width: 60rpx;
	// 		height: 60rpx;
	// 		font-size: 40rpx;
	// 		padding-left: 15rpx;
	// 		display: inline-block;
	// 	}
	// }

	// .userInfo-item-wrapper {

	// 	margin-top: 8px;

	// 	.userInfo-item {
	// 		display: flex;
	// 		height: 50px;
	// 		align-items: center;
	// 		justify-content: space-between;
	// 		padding: 0 10px 0 20px;

	// 		.item-left {
	// 			font-size: 16px;
	// 			display: flex;
	// 			align-items: center;
	// 			flex-direction: column;

	// 			.guanyu {
	// 				// margin-right: 5px;
	// 			}
	// 		}
	// 	}
	// }

	.icon-arrow {
		font-size: 16px;
	}


	.modelBorder {
		border-radius: 12px 12px 0 0;
		padding: 30rpx;
		background: #f0f2f5;
		box-shadow: 0 0 1px #FFF;
	}

	.modelsEntrance {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 160rpx;
		background-image: url('/static/DcImage/vidios.png');
		background-repeat: no-repeat;
		background-size: 100% 100%;
		margin-bottom: 20rpx;
		box-shadow: 0 0 1px #FFF;
	}

	.modelsEntrancetwo {
		display: flex;
		align-items: center;
		justify-content: center;
		background-image: url('/static/DcImage/shopping.png');
		background-repeat: no-repeat;
		background-size: 100% 100%;
		height: 160rpx;
		box-shadow: 0 0 1px #FFF;
	}

	.status-bar {
		background-color: transparent;
	}
</style>