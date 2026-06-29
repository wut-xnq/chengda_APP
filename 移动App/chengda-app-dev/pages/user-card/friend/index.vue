<template>
	<div class="wrapper">
		<view class="form">
			<view class="form-item form-item-avatar">
				<image class="img" :src="userInfo.avatar || '/static/my/user-default.png'" @click="setImage"></image>
				<view class="right">
					<view>
						<view class="name">{{userInfo.nickname}}</view>
						<view class="company" v-if="userInfo.verified === '2'">{{userInfo.merchantName}}</view>
					</view>
					<view class="iendet" v-if="userInfo.verified === '2'">
						<i class="iconfont icon-Vrenzheng" style="font-size: 36rpx;"></i>
						已认证
					</view>
				</view>
			</view>
			<view class="form-item">
				<view class="label">性别</view>
				<view class="input">{{userInfo.gender === '2'?'女':'男'}}</view>
			</view>
			<view class="form-item">
				<view class="label">生日</view>
				<view class="input">{{userInfo.birthday}}</view>
			</view>
			<view class="form-item">
				<view class="label">手机</view>
				<view class="input">{{userInfo.phone}}</view>
			</view>
			<view class="form-item">
				<view class="label">邮箱</view>
				<view class="input">{{userInfo.email}}</view>
			</view>

			<view class="form-item">
				<view class="text">{{userInfo.brief}}</view>
			</view>
			<view class="form-item">
				<view class="label">加入黑名单</view>
				<switch :checked="isInBlacklist" @change="handleSwitchChange" />
			</view>
		</view>

		<div class="button" @click="gotoChat">{{ t('chatWithFriendText') }}</div>
		<div class="box-shadow"></div>
		<div class="button button-red" @click="deleteFriend">
			{{ t('deleteFriendText') }}
		</div>
	</div>
</template>

<script lang="ts" setup>
	import { onLoad } from '@dcloudio/uni-app'
	import UserCard from '../../../components/UserCard.vue'
	import { onUnmounted, ref, reactive } from '../../../utils/transformVue'
	import { t } from '../../../utils/i18n'
	import NavBar from '../../../components/NavBar.vue'
	import { autorun } from 'mobx'
	import { customRedirectTo } from '../../../utils/customNavigate'
	import { deepClone } from '../../../utils'
	import type { Relation } from '@xkit-yx/im-store'

	import { firend } from "utils/request/user"

	const relation = ref<Relation>('stranger')
	const isInBlacklist = ref(false)
	const userInfo = reactive({
		account: '',
		avatar: '',
		nick: '',
		gender: '',
		birth: '',
		tel: '',
		email: '',
		signature: '',
	})

	let account = ''

	onLoad((props) => {
		account = props ? props.account : ''
		getFirend()
	})
	async function getFirend() {
		const info = await firend({ phone: account })
		if(info.data.code === 0){
			Object.assign(userInfo,info.data.data)
		}
		console.log(info)
	}

	const handleSwitchChange = async (e : any) => {
		const isAdd = e.detail.value
		try {
			// @ts-ignore
			await uni.$UIKitStore.relationStore.setBlackActive({ account, isAdd })
		} catch (error) {
			uni.showToast({
				title: isAdd ? t('setBlackFailText') : t('removeBlackFailText'),
				icon: 'error',
			})
		}
	}

	const deleteFriend = () => {
		uni.showModal({
			title: t('deleteFriendText'),
			content:
				t('deleteFriendConfirmText') +
				'“' +
				// @ts-ignore
				uni.$UIKitStore.uiStore.getAppellation({ account }) +
				'”?',
			success: (res) => {
				if (res.confirm) {
					// @ts-ignore
					uni.$UIKitStore.friendStore
						.deleteFriendActive(account)
						.then(() => {
							uni.showToast({
								title: t('deleteFriendSuccessText'),
								icon: 'success',
							})
						})
						.then(() => {
							// @ts-ignore
							return uni.$UIKitStore.sessionStore.deleteSessionActive(
								'p2p-' + account
							)
						})
						.catch(() => {
							uni.showToast({
								title: t('deleteFriendFailText'),
								icon: 'error',
							})
						})
				} else if (res.cancel) {
					console.log('用户点击取消')
				}
			},
		})
	}

	const addFriend = async () => {
		try {
			// @ts-ignore
			await uni.$UIKitStore.friendStore.applyFriendActive(account)
			// 发送申请成功后解除黑名单
			// @ts-ignore
			await uni.$UIKitStore.relationStore.setBlackActive({
				account,
				isAdd: false,
			})
			uni.showToast({
				title: t('applyFriendSuccessText'),
				icon: 'success',
			})
		} catch (error) {
			uni.showToast({
				title: t('applyFriendFailText'),
				icon: 'error',
			})
		}
	}

	const gotoChat = async () => {
		// @ts-ignore
		await uni.$UIKitStore.uiStore.selectSession('p2p-' + account)
		customRedirectTo({
			url: '/pages/Chat/index',
		})
	}
</script>

<style>
	page {
		background-color: #F6F7F9;
		/* height: 100vh; */
	}
</style>

<style lang="scss" scoped>
	@import '../../styles/common.scss';

	.form {
		margin: 4rpx 0 10rpx 0;

		.form-item {
			background-color: #fff;
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 30rpx 30rpx;
			margin-bottom: 2rpx;
			font-size: 26rpx;

			.img {
				width: 160rpx;
				height: 160rpx;
				background-color: #ccc;
				border-radius: 50%;
			}

			.input {
				font-size: 24rpx;
				text-align: right;
				flex: 1;
			}

			.text {
				width: 100%;
				min-height: 100rpx;
				background-color: #F2F6FC;
				padding: 20rpx;
				font-size: 20rpx;
			}
		}

		.form-item-avatar {
			.right {
				flex: 1;
				display: flex;
				// align-items: center;
				justify-content: space-between;
				padding-left: 30rpx;

				line-height: 50rpx;

				.iendet {
					display: flex;
					align-items: center;
					justify-content: space-between;
					width: 130rpx;
					height: 52rpx;
					box-sizing: border-box;
					background: #D3EDFF;
					border-radius: 26rpx;

					font-weight: 400;
					font-size: 24rpx;
					color: #008CEA;
					padding-right: 20rpx;

					.tag-icon {
						width: 36rpx;
						height: 36rpx;
					}
				}
			}
		}
	}

	.wrapper {
		.button {
			// margin-top: 150px;
			display: block;
			width: 100%;
			background-color: #fff;
			color: rgb(25, 146, 239);
			text-align: center;
			height: 50px;
			font-size: 16px;
			line-height: 50px;
		}

		.button-red {
			color: #e6605c;
		}

		.box-shadow {
			height: 1px;
			background: none;
			padding: 0 25px;
			box-shadow: 0 0.5px 0 rgb(247, 244, 244);
		}
	}
</style>