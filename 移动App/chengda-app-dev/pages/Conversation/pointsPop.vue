<template>
	<view class="pop-mask">
		<view class="mian">
			<view class="header">
				<image class="icon" src="/static/login/icon-tip.png" mode=""></image>
				提醒
			</view>
			<view class="content">
				邀请人（{{name}}）想要您的积
				分，是否同意
			</view>
			<view class="footer">
				<view class="btn reject" @click="reject">拒绝</view>
				<view class="btn result" @click="result">同意</view>
			</view>
		</view>
	</view>
</template>

<script lang="ts" setup>
	import { onLoad } from '@dcloudio/uni-app';
	import {ref} from "@vue/composition-api";
	
	import { processingIntegral} from "utils/ArequestHelper";
	import {getSessinUserInfo} from 'utils/request/user'
	
	const name = ref('')
	const user = uni.getStorageSync('firstLoginUser');
	onLoad((option)=>{
		name.value = option.name
	})
	
	function reject(){
		save(0)
	}
	function result(){
		save(1)
	}
	
	async function save(type:number){
		try{
			const data = {userId: user.user_id,agreed: type};
			const result =  await processingIntegral(user.access_token,data);
			if(result.data.code === 1) throw new Error(result.data.msg)
			const userInfo:any = await getSessinUserInfo();
			userInfo.ifGiftScore = '0'
			uni.setStorageSync("s_userInfo",userInfo)
			user.user_info.attributes.ifGiftScore = '0'
			uni.setStorageSync("firstLoginUser",user)
			uni.navigateBack()
			
			uni.showToast({title:"操作成功",icon:"none"})
		}catch(err){
			uni.showToast({title:err.message,icon:"none"})
		}
	}

</script>

<style >
page{
	background-color: transparent;
}
.pop-mask{
	position: fixed;
	left: 0;
	bottom: 0;
	right: 0;
	top: 0;
	background-color: rgba(0, 0, 0, .4);
	display: flex;
	align-items: center;
	justify-content: center;
}
.mian{
	width: 610rpx;
	background-color: #fff;
	padding: 40rpx;
	margin: 0 60rpx;
	border-radius: 20rpx;
}
.header{
	display: flex;
	align-items: center;
	
	font-weight: 400;
	font-size: 32rpx;
	color: #000000;
	line-height: 26rpx;
}
.icon{
	width: 34rpx;
	height: 34rpx;
	margin-right: 20rpx;
}

.content{
	font-weight: 400;
	font-size: 26rpx;
	color: #333333;
	line-height: 40rpx;
	margin: 60rpx 0;
}

.footer{
	display: flex;
	align-items: center;
	justify-content: space-between;
}
.btn{
	display: flex;
	align-items: center;
	justify-content: center;
	width: 248rpx;
	height: 80rpx;
	
	border-radius: 40rpx;
	font-weight: 400;
	font-size: 30rpx;
}
.reject{
	border: 1px solid #CCCCCC;
	background: rgba(0,140,234,0);
	color: #808080;
}
.result{
	background: rgba(0, 140, 234, 1);
	color: #FFF;
}
</style>
