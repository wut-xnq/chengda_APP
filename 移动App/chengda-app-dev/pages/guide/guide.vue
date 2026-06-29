<template>
	<view>
		<view class="form">
			<view class="form-item form-item-avatar">
				<view class="avatar">
					<image class="img" :src="userInfo.avatar" @click="setImage"></image>
				</view>
			</view>
			<view class="form-item">
				<view class="label">昵称</view>
				<input class="input" v-model="userInfo.nickname" placeholder="昵称" />
			</view>
			<view class="form-item">
				<view class="label">真实姓名</view>
				<input class="input" v-model="userInfo.name" placeholder="真实姓名" />
			</view>
			<view class="form-item">
				<textarea class="text" v-model="userInfo.brief" maxlength="200" placeholder="自我介绍..."></textarea>
			</view>
		</view>

	</view>
</template>

<script lang="ts" setup>
	import { t } from '../../utils/i18n';
	import { handleNoPermission, requestCameraPermission } from '../../utils/permission'
	import { onNavigationBarButtonTap } from '@dcloudio/uni-app';
	import { reactive } from '@vue/composition-api';
	import {uploadFile} from "utils/request/upload"
	import {updateUser} from "utils/request/user"
	const userInfo = reactive({avatar: ''})
	
	onNavigationBarButtonTap((e:any)=>{
		if(e.text === '保存'){
			save()
		}
	})
	
	async function save(){
		try{
			// if(!userInfo.avatar || !userInfo.avatar.trim()) throw new Error("请上传头像")
			if(!userInfo.nickname || !userInfo.nickname.trim()) throw new Error("请输入昵称")
			if(!userInfo.name || !userInfo.name.trim()) throw new Error("请输入真实姓名")
			// 更新网易云信息
			const uikUser = uni.$UIKitStore.userStore.myUserInfo
			let newUik = {avatar: userInfo.avatar,nick:userInfo.nickname ,ext:userInfo.name ,signature:userInfo.brief}
			await uni.$UIKitStore.userStore.saveMyUserInfoActive({...uikUser,...newUik})
			// 更新平台个人信息
			console.log(userInfo)
			await updateUser(userInfo).then(res=>{
				console.log(res)
				if(res.data.code === 1) throw new Error(res.data.msg)
				uni.showLoading({title: '跳转中...'});
				uni.switchTab({url:'/pages/Conversation/index'})
				
			}).catch(err=>{
				console.log(err)
				throw new Error(err)
			})
		}catch(err){
			uni.showToast({
				title:err.message,icon:"none"
			})
		}
		
	}

	function setImage() {
		// 1. 弹出自定义权限说明弹窗（关键：必须在 chooseImage 之前！）
		uni.showModal({
		  title: t('NeedPhotoPermissionTitle'), // 建议：'需要相册权限'
		  content: t('NeedPhotoPermissionContent'), // 建议：'为了设置您的头像，我们需要访问设备中的照片。授权后您可在系统设置中随时关闭。'
		  confirmText: t('Allow'),
		  cancelText: t('Cancel'),
		  success: (modalRes) => {
		    if (!modalRes.confirm) {
		      return; // 用户取消，不继续
		    }
		
		    // 2. 用户同意后，调用 chooseImage（此时系统会自动触发原生权限请求）
		    uni.chooseImage({
		      count: 1,
		      sizeType: ['original'],
		      sourceType: ['album'], // 明确只从相册选择（避免同时触发相机权限）
		      success: (chooseRes) => {
		        const filePath = chooseRes.tempFilePaths[0];
		        if (!filePath) {
		          uni.showToast({ title: t('SelectAvatarFailed'), icon: 'none' });
		          return;
		        }
		
				upload(filePath);
				
		      },
		      fail: function (error) { },
		      //没有开启权限时，提示开启权限
		      complete: handleNoPermission,
		    });
		  },
		});
	}
	async function upload(file:any){
		try{
			uploadFile(file).then(res=>{
				if(res.code === 1) throw new Error(res.msg)
				userInfo.avatar = res.data.url
			}).catch(err=>{
				console.log(err)
				throw new Error(err)
			})
		}catch(err){
			uni.showToast({
				title:err.message,icon:"none"
			})
		}
	}
	
</script>

<style>
	page {
		background-color: #F6F7F9;
	}
</style>
<style lang="scss" scoped>
	// page {
	// 	height: 100vh;
	// 	background-color: #F6F7F9;
	// }

	.form {
		margin-top: 10rpx;

		.form-item {
			background-color: #fff;
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 30rpx 30rpx;
			margin-bottom: 2rpx;
			font-size: 26rpx;

			.avatar {
				flex: 1;
				display: flex;
				align-items: center;
				justify-content: center;

				.img {
					width: 160rpx;
					height: 160rpx;
					background-color: #ccc;
					border-radius: 50%;
					border: 20rpx solid #F6F7F9;
				}
			}

			.input {
				font-size: 24rpx;
				text-align: right;
				flex: 1;
			}

			.text {
				width: 100%;
				background-color: #F2F6FC;
				padding: 20rpx;
				font-size: 20rpx;
			}
		}
		.form-item-avatar{
			background-image: linear-gradient( to top, #fff,#F6F7F9);
		}
	}
</style>