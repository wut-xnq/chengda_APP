<template>
	<div class="wrapper">
		<NavBar :title="t('setText')" />
		<div v-for="(item, index) in items" :key="index" class="image-container" @click="handClick(index)">
			<div class="imageIcons">
				<div class="imgText">
					<image class="icon-img" :src="item.imagePath" mode=""></image>
					<p>{{ item.textKey }}</p>
					<!-- 版本目前前端改写 -->
				</div>
				<div>
					<span style="color:#aaa;margin-right:30rpx;font-size: 28rpx;"
						v-if="item.AppEdition">V{{ systemInfo.appVersion }}</span>
					<image class="icon-right" src="../../../static/my/my-right.png"></image>
				</div>
				<!-- <Icon iconClassName="icon-arrow" type="icon-right" style="color:#AAAAAA"></Icon> -->
			</div>
			<div class="divider" :class="{ specialDivider: shouldAddSpecialLine(index) }"></div>
		</div>

		<!-- 退出 -->
		<div @click="logout" class="logout">
			{{ t("logoutText") }}
		</div>
	</div>
</template>

<script lang="ts" setup>
	import NavBar from "../../../components/NavBar.vue";
	import { t } from "../../../utils/i18n";
	import Icon from "../../../components/Icon.vue";
	import { items } from "./setCorrelation/setCorrelation";
	import { customNavigateTo } from "../../../utils/customNavigate";
	
	import {getUserInfo} from 'utils/request/user'
	const logout = () => {
		uni.showModal({
			title: t("logoutText"),
			content: t("logoutConfirmText"),
			showCancel: true,
			success: function (res) {
				if (res.confirm) {
					console.log("用户点击确定");
					const app = getApp()
					app.logout()
					// 点击确定退出，保证本平台账号也是推出的
					chengdaBackstage();
				} else if (res.cancel) {
					console.log("用户点击取消");
				}
			},
		});
	};

	// 获取当前app的版本
	const systemInfo = uni.getSystemInfoSync();

	// 退出目前自己的平台
	const chengdaBackstage = () => {
		console.log("退出目前自己的平台");
		// 先清除所有缓存
		uni.clearStorageSync();
		// 再跳转登录页
		uni.reLaunch({
			url: "/pages/Login/index",
		});
	};

	// new
	// 判断是否应该添加特殊样式的分割线
	const shouldAddSpecialLine = (index : number) : boolean => {
		// 当 index 为 1 或 3 时添加特殊样式的分割线
		return index === 0 || index === 2;
	};
	// 处理点击事件
	const handClick = (index : number) => {
		switch (index) {
			case 0:
				handleCase0();
				break;
			case 1:
				handleCase1();
				break;
			case 2:
				handleCase2();
				break;
			case 3:
				handleCase3();
				break;
			case 4:
				handleCase4();
				break;
			case 5:
				handleCase5();
				break;
			case 6:
				handleCase6();
				break;
			case 7:
				handleCase7();
				break;
			case 8:
				handleCase8();
				break;
			case 9:
				handleCase9();
				break;

			default:
				console.log(`未知的索引值：${index}`);
				break;
		}
	};

	// 不同的处理逻辑
	// 积分转化
	const handleCase0 =async () => {
		console.log("积分转化");
		const user= uni.getStorageSync("firstLoginUser");
		const userInfo = await getUserInfo({id:user.user_id})
		if(userInfo?.data?.data?.name){
			customNavigateTo({
				url: "/pages/user-card/more-settings/integralConversion/index",
			});
		}else{
			uni.showToast({
				title:"请先完善个人信息",icon:"none"
			})
		}
	};
	// 我的订单
	const handleCase1 = () => {
		console.log("我的订单");
		customNavigateTo({
			url: "/pages/goShopping/myOrder/index",
		});
	};

	// 收货地址
	const handleCase2 = async () => {
		console.log("收货地址");
		customNavigateTo({
			url: "/pages/user-card/more-settings/deliveryAddress/index",
		});
	};
	// 个人认证
	const handleCase3 = () => {
		console.log("个人认证");
		customNavigateTo({
			url: "/pages/user-card/more-settings/personalCertification/index",
		});
	};
	// 账户与安全
	const handleCase4 = () => {
		console.log("账户与安全");
		customNavigateTo({
			url: "/pages/user-card/more-settings/AccountsSecurity/index",
		});
	};
	// 清理缓存
	const handleCase5 = () => {
		// #ifdef APP-PLUS
		//使用plus.cache.clear 清除应用中的缓存数据
		plus.cache.clear(function () {
			uni.showToast({
				title: '清除成功!',
				icon: 'none'
			})
		});
		// #endif
	};
	// 服务协议
	const handleCase6 = () => {
		console.log("服务协议");
		customNavigateTo({
			url: "/pages/protocolPages/UserAgreement/index",
		});
	};
	// 隐私政策
	const handleCase7 = () => {
		console.log("隐私政策");
		customNavigateTo({
			url: "/pages/protocolPages/PrivacyPolicy/index",
		});
	};
	// App版本
	const handleCase8 = () => {
		console.log("App版本");
	};
	// 我要反馈
	const handleCase9 = () => {
		uni.showModal({
			title: '提示',
			content: '是否拨打客服电话',
			success: function (res) {
				if (res.confirm) {
					uni.makePhoneCall({
						phoneNumber: '15735399442', // 电话号码
					});
				} else if (res.cancel) {
					console.log('用户点击取消');
				}
			}
		});
	};
</script>

<style lang="scss" scoped>
	@import "../../styles/common.scss";

	page {
		padding-top: var(--status-bar-height);
		height: 100vh;
		background-color: #F6F7F9;
	}

	.wrapper {
		background-color: rgb(245, 246, 247);
		width: 100%;
		height: 100vh;
		box-sizing: border-box;
	}

	.logout {
		background-color: #008CEA;
		border-radius: 40rpx;
		height: 80rpx;
		line-height: 80rpx;
		text-align: center;
		margin: 50rpx 30rpx 0 30rpx;
		color: #ffffff;
	}

	// new
	.image-container {
		.imageIcons {
			display: flex;
			justify-content: space-between;
			padding: 30rpx;
			align-items: center;
			background-color: #fff;
		}

		.imgText {
			display: flex;
			align-items: center;
		}

		// image {
		//   width: 20px;
		//   height: 20px;
		//   margin-right: 10px;
		// }
		.icon-img {
			width: 20px;
			height: 20px;
			margin-right: 10px;
		}

		.icon-right {
			width: 14rpx;
			height: 22rpx;
		}
	}

	.divider {
		width: 100%;
		height: 1px;
	}

	.specialDivider {
		height: 20rpx; // 特殊样式的分割线高度
	}
</style>