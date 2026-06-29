<template>
	<div class="wrapper">
		<div class="addressCards" v-for="(item, index) in addressLists" :key="item.id">
			<div class="textMessage" @click="selectAddress(item)">
				<span>{{ item.concatPerson }}</span>
				<span style="font-weight: 600">{{ item.contactPhone }}</span>
				<span class="tag" v-if="item.ifDefault === '1'">默认</span>
				<p>{{ item.fullAddress }}</p>
			</div>
			<div class="editLogo" @click="editAddress(item)">
				<image src="/static/departmentStorePicture/iconPark-edit-two 1.svg" mode=""></image>
			</div>
		</div>
		<div class="addAddress">
			<view class="btn"  @click="newAddress()">新增地址</view>
		</div>
	</div>
</template>

<script lang="ts" setup>
	import { t } from "@/utils/i18n";
	import {
		customNavigateTo,
		customRedirectTo,
	} from "@/utils/customNavigate";
	import { ref } from "@vue/composition-api";
	import {
		modifyShippingAddress,
		resDeliveryAddress,
	} from "utils/ArequestHelper";
	import { onLoad, onShow } from "@dcloudio/uni-app";

	// 定义地址类型
	interface AddressLists {
		address : string;
		cityCode : number;
		concatPerson : string;
		contactPhone : string;
		countryCode : number;
		createTime : string;
		fullAddress : string;
		id : string;
		ifDefault : string;
		provinceCode : number;
		state : string;
		userId : string;
	}

	const storedData = uni.getStorageSync("firstLoginUser");

	// 响应式变量，用于存储地址列表
	const addressLists = ref<AddressLists[]>([]);
	const orderId = ref();

	// 调取页面数据方法
	const loadingResAddress = async () => {
		const resAddress = await resDeliveryAddress(
			storedData.user_id,
			storedData.access_token
		);
		console.log("resAddress", resAddress);
		// 对地址列表进行排序，将 ifDefault 为 '1' 的地址排在前面
		const sortedAddressLists = resAddress.data.data.sort((a : { ifDefault : string; }, b : { ifDefault : string; }) => {
			if (a.ifDefault === '1') return -1;
			if (b.ifDefault === '1') return 1;
			return 0;
		});
		addressLists.value = sortedAddressLists;
		console.log("地址列表", addressLists.value);
	};

	onLoad((options) => {
		// 接收传递过来的 orderId 参数
		orderId.value = decodeURIComponent(options.orderId || "");
	});

	// 页面加载时初始化地址列表
	onShow(() => {
		loadingResAddress();
	})

	// 修改地址
	const editAddress = (item : AddressLists) => {
		// console.log('修改地址......',item)
		// 将对象转换为查询字符串
		const queryString = Object.entries(item)
			.map(([key, value]) => {
				if (
					typeof value === "string" ||
					typeof value === "number" ||
					typeof value === "boolean"
				) {
					return `${key}=${encodeURIComponent(value as string)}`;
				}
				return "";
			})
			.join("&");

		customNavigateTo({
			url: `/pages/user-card/more-settings/deliveryAddress/modifyNew/editCardAddress?${queryString}`,
		});
	};

	// 新增地址
	const newAddress = async () => {
		customNavigateTo({
			url: "/pages/user-card/more-settings/deliveryAddress/modifyNew/newCardAddress",
		});
	};

	// 订单选择地址
	const selectAddress = async (data : obejct) => {
		uni.$emit('selectAdrees', { data: data })
		uni.navigateBack()
	};
</script>

<style>
	page {
		background-color: #f5f7f9;
	}
</style>
<style lang="scss" scoped>
	@import "@/pages/styles/common.scss";



	.wrapper {
		box-sizing: border-box;
		padding: 20rpx;
		font-size: 28rpx;
	}

	.addressCards {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 30rpx;
		margin-bottom: 10rpx;
		border-radius: 10rpx;

		background-color: #fff;
		box-shadow: 0 0 1px #ccc;
	}

	.textMessage {
		width: 80%;

		span {
			color: #000000;
			margin-right: 10px;
		}
		.tag{
			color: #ff0000;
			border: 1px solid #ff0000;
			padding: 4rpx 10rpx;
			border-radius: 10rpx;
			font-size: 20rpx;
		}
		p {
			margin-top: 10px;
			color: #000000;
		}
	}

	.editLogo {
		image {
			width: 20px;
			height: 20px;
		}
	}

	.addAddress {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		padding: 20rpx;
		.btn{
			height: 80rpx;
			line-height: 80rpx;
			text-align: center;
			background-color: #1E70E2;
			color: white;
			border: none;
			z-index: 10;
			border-radius: 40rpx;
		}
	}
</style>