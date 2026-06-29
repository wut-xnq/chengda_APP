<!-- 登录密码修改 -->
<template>
	<div class="wrapper">
		<NavBar :title="t('setPassword')" />
		 <uniPopupMessage
		      v-if="showSuccessPopup"
		      type="success"
		      message="操作成功！"
		      :duration= '2000'
		      :maskShow="maskShow.value"
			  @close="handlePopupClose"
		    />
		    <uniPopupMessage
		      v-if="showErrorPopup"
		      type="error"
		      message="操作失败，请重试！"
		      :duration= '2000'
		      :maskShow="maskShow.value"
		    />
		<div class="inputStyle">
			<input class="uni-input" 
					placeholder="请输入密码"
					 :type="showPassword ? 'text' : 'password'"
						v-model="password"/>
			<uni-icons @click="togglePasswordVisibility" :type="showPassword ? 'eye' : 'eye-slash'" size="30"></uni-icons>
		</div>
		
		<div class="inputStyle">
		    <input
		        class="uni-input"
		        :type="showConfirmPassword ? 'text' : 'password'"
		        placeholder="请再次输入密码"
		        v-model="confirmPassword"
		    />
		        <uni-icons class="uni-icons" @click="toggleConfirmPasswordVisibility" :type="showConfirmPassword ? 'eye' : 'eye-slash'" size="30"></uni-icons>
		</div>
		<div class="textTips">8-20位密码，至少包括英文字母、数字、特殊符号中的2种</div>
		<button class="finishButton" @click="submitForm">完成</button>
	</div>
</template>

<script lang="ts">
import NavBar from '../../../../../components/NavBar.vue';
import { t } from '../../../../../utils/i18n';
import { defineComponent , onMounted, ref } from '@vue/composition-api';
import uniPopupMessage from '../../../../../components/uni-components/uni-popup/components/uni-popup-message/uni-popup-message.vue';


export default defineComponent({
	components:{
		NavBar,
		uniPopupMessage
	},
	
	setup() {
		const showPassword = ref(false); // 控制密码是否可见
		const showConfirmPassword = ref(false); // 控制确认密码是否可见
		const password = ref(''); // 存储输入的密码
		const confirmPassword = ref(''); // 存储确认密码
		const showSuccessPopup = ref(false); // 控制成功提示是否显示
		const showErrorPopup = ref(false); // 控制错误提示是否显示
		const maskShow = ref(false);
		
		// 初始化popup
		onMounted(() =>{
			if(!uniPopupMessage.popup){
				uniPopupMessage.popup = {};  //确保popup被正确的初始化
			}
			if(!('maskShow' in uniPopupMessage.popup)){
				uniPopupMessage.popup.maskShow = maskShow.value;  //初始化maskShow
			}
		})
		
		
		const togglePasswordVisibility =() => {
			 showPassword.value = !showPassword.value;
		}
		const toggleConfirmPasswordVisibility = () => {
		            showConfirmPassword.value = !showConfirmPassword.value;
		};
		
		const submitForm = () => {
		            if(password.value !== confirmPassword.value || password.value === '' || confirmPassword.value === ''){
						console.log('密码不一样，重新设置');
						showErrorPopup.value = true;
						showSuccessPopup.value = false;
					}else {
						console.log('提交成功');
						showSuccessPopup.value = true;
						showErrorPopup.value = false;
					}
		        };
				
		const handlePopupClose = () => { showSuccessPopup.value = false; };
		
		return {
			t,
			showPassword,
			showConfirmPassword,
			password,
			confirmPassword,
			togglePasswordVisibility,
			toggleConfirmPasswordVisibility,
			submitForm,
			showSuccessPopup,
			showErrorPopup,
			maskShow,
			handlePopupClose,
		}
	}
})

</script>

<style lang="scss" scoped>
@import '../../../../styles/common.scss';

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
.inputStyle {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin: 5%;
	height: 40px;
	border: 1px solid #BBBBBB;
	border-radius: 5px;
	.uni-input {
		border: none;
		background-color: transparent;
		height: 40px;
		width: 80%;
		padding: 0 2%;
	}
	.uni-icons {
		padding: 0 2%;
	}
}
.textTips {
	display: flex;
	justify-content: center;
	align-items: center;
	white-space: nowrap;
	font-size: 13px;
	font-weight: 600
}
.finishButton {
	background-color: #87CEEB;
	border-radius: 20px;
	height: 45px;
	line-height: 45px;
	text-align: center;
	width: 90%;
	margin: 20px auto;
	color: #ffffff;
	letter-spacing: 10px;
}
</style>