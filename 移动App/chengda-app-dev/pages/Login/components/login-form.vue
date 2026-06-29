<template>
	<div class="content">
		<div class="navigation-bar"></div>
		<div class="login-form-container">
			<div class="login-top">
				<image src="/static/login/img-logo.png" class="login-top-icon" mode=""></image>
				<div class="login-top-content">
					<div class="title">HelLo!</div>
					<div class="desc">欢迎来到诚达</div>
					<div class="text">社交互动和购物体验的融合</div>
				</div>
			</div>
			<div class="login-form">
				<!-- 输入手机号 -->
				<FormInput className="login-form-input" type="tel" class="input-box" :value="loginForm.phone"
					ref="phoneRef" @updateModelValue="(val) => (loginForm.phone = val)"
					:placeholder="i18n.mobilePlaceholder" :rule="mobileInputRule">
					<template #addonBefore>
						<span class="phone-addon-before">+86</span>
					</template>
					<template #addonAfter>
						<span :class="[
		        'sms-addon-after',
		        { disabled: smsCount > 0 && smsCount < 60 },
		      ]" @click="startSmsCount()">{{ smsText }}</span>
					</template>
				</FormInput>
				<!-- 验证码获取 -->
				<FormInput className="login-form-input" type="tel" class="input-box" ref="codeRef"
					:value="loginForm.smsCode" @updateModelValue="(val) => (loginForm.smsCode = val)"
					:placeholder="i18n.smsCodePlaceholder" :rule="smsCodeInputRule">

				</FormInput>
				<!-- 邀请码 -->
				<FormInput ref="code2Ref" placeholder="请输入邀请码(选填)" :value="loginForm.inviteCode"
					@updateModelValue="(val) => (loginForm.inviteCode = val)"></FormInput>
					
				<div class="login-btn" @click="submitLoginForm()">
					{{ i18n.loginBtnTitle }}
				</div>
			</div>
		</div>
		<!-- 协议同意区域 -->
		  <checkbox-group @change="onAgreeChange">
		    <label class="agreement-label">
		      <checkbox 
		        value="agree" 
		        :checked="isAgreed"
		      />
		      <text class="agreement-text">我已阅读并同意</text>
			  <text class="policy-link" @click.stop="goToUserAgreement">服务协议</text>
			   <text class="agreement-text">和</text>
		      <text class="policy-link" @click.stop="goToPrivacyPolicy">隐私政策</text>
		    </label>
		  </checkbox-group>
	</div>
</template>

<script lang="ts" setup>
	// @ts-ignore
	import { ref, reactive, computed } from "../../../utils/transformVue";
	import i18n from "../i18n/zh-cn";
	// @ts-ignore
	import FormInput from "./form-input.vue";

	import {
		verifyCode,
		authenticate,
		refreshToken,
		processingIntegral,
	} from "../../../utils/ArequestHelper";

	import { onMounted } from "@vue/composition-api";


	const mobileInputRule = {
		reg: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
		message: i18n.mobileErrorMsg,
		trigger: "blur",
	};
	const smsCodeInputRule = {
		reg: /^\d+$/,
		message: i18n.smsErrorMsg,
		trigger: "blur",
	};

	const smsCount = ref(60);
	const loginTabs = reactive({
		active: 0,
		list: [{ key: 0, title: i18n.loginTitle }],
	});
	const loginForm = reactive({
		phone: "",
		smsCode: "",
		inviteCode: "",
	});

	const phoneRef = ref(null)
	const codeRef = ref(null)
	const code2Ref = ref(null)
	
	const unreadSysMsgCount = ref();
	const isAgreed = ref(false);

	// 登录头
	type LoginRegisterByCodeRes = {
		accessToken : string;
		imAccid : string;
		imToken : string;
	};

	// 获取验证码倒计时
	const smsText = computed(() => {
		if (smsCount.value > 0 && smsCount.value < 60) {
			return smsCount.value + i18n.smsCodeBtnTitleCount;
		} else {
			return i18n.smsCodeBtnTitle;
		}
	});
	// 获取验证码
	async function startSmsCount() {
		if (!mobileInputRule.reg.test(loginForm.phone)) {
			uni.showToast({
				title: i18n.mobileErrorMsg,
				icon: "none",
			});
			return;
		}
		try {
			const response = await uni.request({
				// url: `http://192.168.31.99:9999/consu/sms/getMobileCode?phone=${encodeURIComponent(
				url: `https://chengboda.com/api/consu/sms/getMobileCode?phone=${encodeURIComponent(
					loginForm.phone
				)}`,
				method: "POST",
			});
			console.log("验证码发送成功:", response.data);
			// 成功发送验证码后，显示成功提示
			uni.showToast({
				title: "验证码已发送",
				icon: "success",
				duration: 2000,
			});
		} catch (error : any) {
			let msg = error.errMsg || error.msg || error.message || i18n.smsCodeFailMsg;
			if (msg.startsWith("request:fail")) {
				msg = i18n.smsCodeNetworkErrorMsg;
			}
			uni.showToast({
				title: msg,
				icon: "none",
			});
			return;
		}

		if (smsCount.value > 0 && smsCount.value < 60) {
			return;
		}
		smsCount.value--;
		const timer = setInterval(() => {
			if (smsCount.value > 0) {
				smsCount.value--;
			} else {
				clearInterval(timer);
				smsCount.value = 60;
			}
		}, 1000);
	}

	function onAgreeChange(e: any) {
	  console.log('[DEBUG] Checkbox group changed:', e.detail.value); // 应输出 ['agree'] 或 []
	  const checked = e.detail.value.includes('agree');
	  isAgreed.value = checked;
	}
	
	function goToPrivacyPolicy() {
	  // 跳转到隐私政策页面，请确保该页面已在 pages.json 中注册
	  uni.navigateTo({
	    url: '/pages/protocolPages/PrivacyPolicy/index'
	  });
	}
	function goToUserAgreement() {
	  // 跳转到服务协议页面，请确保该页面已在 pages.json 中注册
	  uni.navigateTo({
	    url: '/pages/protocolPages/UserAgreement/index'
	  });
	}
	//	/h5api/consu/sms/verifyCode
	// 登录
	async function submitLoginForm() {
		try {
			if (!isAgreed.value) {
			    uni.showToast({
			      title: '请先同意服务协议和隐私政策',
			      icon: 'none'
			    });
			    return;
			  }
			if (null != loginForm.phone && loginForm.phone == '18095129829') {
				console.log("免登录测试");
				// 上架逻辑，免验证码校验，直接登录
				// 登录购物后台
				const secondResponse = await authenticate(
					loginForm.phone,
					loginForm.inviteCode
				);
				
				if (secondResponse.data.code === 1) {
					throw new Error(secondResponse.data.msg)
				}
				unreadSysMsgCount.value = secondResponse.data;
				console.log("客户端手机登录", secondResponse);
				// 登录成功后,网易云信检验登录注册
				if (secondResponse.data && secondResponse.data.access_token) {
					const userIfResponse = await refreshToken(
						loginForm.phone,
						secondResponse.data.access_token
					);
					console.log("返回数据", userIfResponse);
				
					const app = getApp();
					app.initNim({
						account: userIfResponse.data.data.accid,
						token: userIfResponse.data.data.token,
					});
				}
			}
			phoneRef.value.handleBlur()
			codeRef.value.handleBlur()
			code2Ref.value.handleBlur()

			if (!mobileInputRule.reg.test(loginForm.phone) || !smsCodeInputRule.reg.test(loginForm.smsCode)) {
				return
			}

			// 校验验证码是否正确
			const response = await verifyCode(loginForm.phone, loginForm.smsCode);
			// console.log(response)
			// const response = {data:{code: 0}}
			if (response.data.code === 0) {
				// 登录购物后台
				const secondResponse = await authenticate(
					loginForm.phone,
					loginForm.inviteCode
				);

				if (secondResponse.data.code === 1) {
					throw new Error(secondResponse.data.msg)
				}
				unreadSysMsgCount.value = secondResponse.data;
				console.log("客户端手机登录", secondResponse);
				// 登录成功后,网易云信检验登录注册
				if (secondResponse.data && secondResponse.data.access_token) {
					const userIfResponse = await refreshToken(
						loginForm.phone,
						secondResponse.data.access_token
					);
					console.log("返回数据", userIfResponse);

					const app = getApp();
					app.initNim({
						account: userIfResponse.data.data.accid,
						token: userIfResponse.data.data.token,
					});
				}
			} else {
				throw new Error(response.data.msg);
			}
		} catch (e) {
			//TODO handle the exception
			uni.showToast({
				title: e.message,
				icon: "none",
			});
		}
	}
	
	
</script>

<style lang="scss" scoped>
	$primary-color: #008CEA;


	.navigation-bar {
		height: var(--status-bar-height);
		width: 100%;
	}

	.login-form-container {
		// padding: 0 30px;
		background-image: url("/static/login/img-bg.png");
		background-size: 100% 441rpx;
		background-repeat: no-repeat;
	}

	.login-top {
		height: 441rpx;
		display: flex;
		align-items: center;
		padding-bottom: 80rpx;
		box-sizing: border-box;
		color: #fff;

		.login-top-icon {
			width: 154rpx;
			height: 154rpx;
			padding: 0 40rpx 0 60rpx;

		}

		.login-top-content {
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			height: 154rpx;

			.title {
				font-size: 48rpx;
				line-height: 48rpx;
			}

			.desc {
				font-size: 38rpx;
				line-height: 38rpx;
			}

			.text {
				font-size: 28rpx;
				line-height: 28rpx;
			}
		}
	}


	.login-form {
		margin-top: 80rpx;
		padding: 0 60rpx;

		.input-box {
			margin-bottom: 37rpx;
		}
	}


	.phone-addon-before {
		color: #008CEA;
		// border-right: 1px solid #999999;
		padding: 0 5px;
		margin-right: 10px;
	}

	.sms-addon-after {
		color: $primary-color;

		&.disabled {
			color: #666b73;
		}
	}


	.login-btn {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 80rpx;
		width: 100%;
		background: $primary-color;
		border-radius: 40rpx;
		color: #fff;
		margin-top: 100rpx;
	}
	
	button[disabled] {
	  background-color: #ccc;
	  color: #999;
	}
	
	/* 协议容器：水平居中 */
	.agreement-label {
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  margin: 30rpx 0;
	  font-size: 26rpx;
	  color: #666;
	}
	
	.custom-checkbox {
	  transform: scale(0.8);
	  transform-origin: left center;
	  margin-right: 10rpx;
	}
	
	.policy-link {
	  color: #007aff;
	  margin-left: 6rpx;
	}
</style>