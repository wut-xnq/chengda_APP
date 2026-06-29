<!-- 查看设备 -->
<template>
	<div class="wrapper">
		<NavBar :title="t('loginDevice')" />
		<!-- 文字提示 -->
		<div class="textSpecification">
			<p>以下展示当前正在登录您账号的设备信息。位保障您的账号安全，请保管好您的登录密码，并保持绑定手机号为可用状态</p>
			<p>说明：因网络或运营商原因，IP地址可能与实际登录地址存在不符的情况</p>
		</div>
		<!-- 登录信息展示 -->
		<div class="loginInformationDisplay">
			<div class="loginInformationDisplayText">
				<span>{{device.deviceModel}}</span>
				<!-- <span>陕西</span> -->
				<span>{{time}}</span>
			</div>
			<p>本机</p>
		</div>
	</div>
</template>

<script lang="ts">
import NavBar from '../../../../../components/NavBar.vue';
import { t } from '../../../../../utils/i18n';
import { defineComponent } from '@vue/composition-api';


export default defineComponent({
	components:{
		NavBar
	},
	setup() {
		const device =  uni.getDeviceInfo()
		const date = new Date()
		const year = date.getFullYear();
		const month = date.getMonth()+1;
		const day = date.getDate();
		const hours = date.getHours();
		const minu = date.getMinutes();
		const sec = date.getSeconds();
		const time = `${year}-${month}-${day} ${hours}:${minu}:${sec}`
		console.log(time)
		return {
			t,
			device,
			time
		}
	}
})
</script>

<style lang="scss">
page {
	background-color: #F5F6F7;
}
	.textSpecification {
		color: #9D9D9D;
		margin: 5%;
		font-size: 13px;
		font-weight: 600;
		p {
			margin-bottom: 10px;
		}
	}
	.loginInformationDisplay {
		margin: 0 5%;
		border: 1px solid #C8C8C8;
		border-radius: 5px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 4%;
		white-space: nowrap;
		p {
			color: #6C6C6C;
			font-weight: 600;
		}
		.loginInformationDisplayText {
			display: flex;
			flex-direction: column;
			color: #7D7D7D;
			font-weight: 600;
		}
		
	}
</style>