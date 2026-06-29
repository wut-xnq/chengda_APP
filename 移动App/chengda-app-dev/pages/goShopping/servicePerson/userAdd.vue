<template>
	<view>
		<view class="content">
			<view class="text">
				<view class="tag">持有此名片者为本公司员工</view>
				<view class="desc">
					<view class="label">公司地址：</view>
					<view>{{user.info.fullAddress}}</view>
				</view>
				<view class="desc">
					<view class="label">发证单位：</view>
					<view>{{user.info.merchantName}}</view>
				</view>
			</view>
			
			<view class="message">发送添加好友通知</view>
			<textarea class="input" placeholder="请输入好友申请说明" placeholder-style="color:#ccc;"></textarea>
		</view>
		<view class="footer">
			<view class="btn" @click="addUser">发送</view>
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
	 * 添加好友
	 */
	async function addUser(){
		try{
			console.log(user.info.phone)
			await uni.$UIKitStore.friendStore.applyFriendActive(user.info.phone);
			uni.navigateBack()
			uni.showToast({title: '发送成功'})
		}catch(e){
			uni.showToast({title: '发送失败',icon:'error'})
		}
		
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
		.message{
			margin-top:30rpx;
			font-weight: 400;
			font-size: 24rpx;
			color: #797979;
			// line-height: 33rpx;
		}
		.text{
			font-weight: 400;
			font-size: 24rpx;
			line-height: 30rpx;
			// width:690rpx;
			height:343rpx;
			padding-left: 147rpx;
			background-image: url("/static/add-bg.png");
			background-size: 100%;
			.desc{
				display:flex;
				align-item: center;
				padding: 10rpx 0;
				margin-bottom: 12rpx;
				color:#E8C7FF;
				.label{
					width:130rpx;
				}
				.setting-icon{
					width: 32rpx;
					height:32rpx;
					margin-right: 12rpx;
				}
			}
			
			.tag{
				display:inline-block;
				font-weight: 600;
				border-radius: 23px;
				margin-top: 94rpx;
				margin-bottom: 41rpx;
				font-weight: 400;
				font-size: 28rpx;
				color: #FFF;
				line-height: 28rpx;
			}
		}
		.input{
			padding:20rpx;
			width: 100%;
			box-sizing: border-box;
			height: 263rpx;
			border: 1px solid #CCCCCC;
			// margin-top: 24rpx;
			
			font-weight: 400;
			font-size: 28rpx;
			line-height: 28rpx;
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