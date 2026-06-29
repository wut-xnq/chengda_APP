<template>
	<view>
		<view class="content">
			<view class="user">
				<view class="left">
					<image  :src="user.info.avatar || '/static/my/user-default.png'" class="user-icon"></image>
					<view>
						<view class="name">{{user.info.nickname}}</view>
						<view class="company">{{user.info.merchantName}}</view>
					</view>
				</view>
				<view class="right">
					<i class="iconfont icon-Vrenzheng" style="font-size: 36rpx;"></i>
					已认证
				</view>
			</view>
			
			<view class="text">{{user.info.brief}}</view>
		</view>
		<view class="footer">
			<view class="btn" @click="toUserAdd">加好友</view>
		</view>
	</view>
</template>

<script lang="ts" setup>
	import { ref, reactive, onMounted } from '@vue/composition-api';
	import { onLoad } from '@dcloudio/uni-app';

	import { getUserInfo } from 'utils/request/servicePerson';

	const userId = ref(null); // 商户id
	const user = reactive({info:{}})
	

	onLoad((option) => {
		console.log('传递的参数', option)
		userId.value = option?.userId
		initData()
	})
	function initData(){
		getUser()
	}
	/**
	 * 获取 人员
	 */
	function getUser() {
		getUserInfo({ userId: userId.value }).then(res => {
			console.log(res)
			const result = res?.data?.data
			if(result){
				user.info = result
			}
		})
	}
	
	/**
	 * 跳转 添加好友
	 */
	function toUserAdd(){
		uni.navigateTo({
			url:`/pages/goShopping/servicePerson/userAdd?userId=${userId.value}`
		})
	}
	
</script>

<style>
	page {
		background: #f5f7f9;
	}
</style>
<style lang="scss" scoped>
	.content{
		margin-top: 1px;
		background-color: #fff;
		padding: 20rpx 30rpx;
		.user{
			display: flex;
			align-items: center;
			justify-content: space-between;
			.left{
				display: flex;
				align-items: center;
				justify-content: space-between;
				
				.user-icon{
					width: 100rpx;
					height: 100rpx;
					margin-right: 20rpx;
					border-radius: 50%;
				}
				.name{
					font-weight: 400;
					font-size: 30rpx;
					color: #000000;
				}
				.company{
					font-weight: 400;
					font-size: 24rpx;
					color: #797979;
				}
			}
			
			.right{
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
				padding-right:20rpx;
				.tag-icon{
					width: 36rpx;
					height: 36rpx;
				}
			}
		}
	
		.text{
			margin-top: 50rpx;
			min-height: 200rpx;
			text-indent: 52rpx;
			
			font-weight: 400;
			font-size: 26rpx;
			color: #333333;
			line-height: 38rpx;
		}
	}
	.footer{
		padding: 0 30rpx;
		margin-top: 60rpx;
		.btn{
			height: 80rpx;
			line-height: 80rpx;
			text-align:center;
			background: #008CEA;
			border-radius: 40rpx;
			color:#fff;
		}
	}
</style>