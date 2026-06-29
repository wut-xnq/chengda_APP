<!-- 账户与安全 -->  
<!-- 组件式将代码优化 -->
<template>
	<div class="wrapper">
		 <NavBar :title="t('AccountsSecurity')" />
			 <!-- 手机号 -->
			 <div class="uniformStyle" style="margin-top: 10rpx;">
				 <span>手机号</span>
				 <span>{{userInfo.phone}}</span>
			 </div>
			 <!-- 登录密码 -->
<!-- 			 <div class="uniformStyle" @click="editPassword">
			 	<span>登录密码</span>
			 	<div class="goSetting">
					<span>去设置</span>
					<uni-icons type="right" size="25" color="#9A9A9A"></uni-icons>
				</div>
			 </div> -->
			 <!-- 登录设备 -->
			 <div class="uniformStyle" @click="lookEquipment">
				 <span>登录设备</span>
				 <uni-icons type="right" size="16" color="#9A9A9A"></uni-icons>
			 </div>
			 <!-- 注销账号 -->
			 <div class="uniformStyle" @click="cancelAccount">
				 <div class="uniformStyleText">
					 <span>注销账号</span>
					 <p>注销后无法恢复，请谨慎操作</p>
				 </div>
				 <uni-icons type="right" size="16" color="#9A9A9A"></uni-icons>
			 </div>
	</div>
</template>

<script lang="ts">
import NavBar from '../../../../components/NavBar.vue';
import { t } from '../../../../utils/i18n';
import { defineComponent } from '@vue/composition-api';
import { customNavigateTo } from '../../../../utils/customNavigate'

export default defineComponent({
	components:{
		NavBar
	},
	setup() {
		const userInfo = uni.getStorageSync("firstLoginUser").user_info;

// 设置密码
	const editPassword = () => {
		console.log('设置密码');
		customNavigateTo({
			url: '/pages/user-card/more-settings/AccountsSecurity/cryptographicDeviceLogout/cryptographic'
		})
	}
// 登录设备
	const lookEquipment = () => {
		console.log('查看登录设备');
		customNavigateTo({
			url: '/pages/user-card/more-settings/AccountsSecurity/cryptographicDeviceLogout/device'
		})
	}
// 注销账号
	const cancelAccount = () => {
		console.log('注销账号');
		customNavigateTo({
			url: '/pages/user-card/more-settings/AccountsSecurity/cryptographicDeviceLogout/logout'
		})
	}

		return {
			t,
			customNavigateTo,
			editPassword,
			lookEquipment,
			cancelAccount,
			userInfo
		}
	}
})


</script>

<style lang="scss" scoped>
@import '../../../styles/common.scss';

page {
  padding-top: var(--status-bar-height);
  height: 100vh;
  background-color: rgb(245, 246, 247);
}

.wrapper {
  background-color: rgb(245, 246, 247);
  width: 100%;
  height: 100vh;
  box-sizing: border-box;
}	
.uniformStyle {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 20rpx 30rpx;
	font-weight: 400;
	font-size: 28rpx;
	color: #000000;
	background-color: #fff;
	margin-top: 4rpx;

}
.goSetting {
	display: flex;
	align-items: center;
	span {
		color: #9A9A9A;
	}
}
.uniformStyleText p {
	font-size: 11px;
	color: #EC5A32;
}
</style>