<!-- 商品状态详情页面//vue2 -->
<template>
	<div class="wrapper" :style="{ overflow: isRoll ? 'auto' : 'hidden' }">
		<!-- 背景色 -->
		<div class="custom-nav">
			<div class="status_bar"></div>
			<div class="bar-bg">
				<div class="left" @tap="back">
					<Icon type="icon-zuojiantou" :size="22"></Icon>
				</div>
				<div class="title">
					<div class="title" v-if="orderInfo.showTitle">
						{{ orderInfo.showTitle }}
					</div>
					<!-- <div class="subTitle" >wwe</div> -->
				</div>
				<div class="right"></div>
			</div>
		</div>
		<!-- 导航栏占位 -->
		<div class="titleNview-placing" />

		<!-- 商品信息携带展示 -->
		<div>
			<div style="
          background-color: #ffffff;
          margin-top: 6rpx;
          padding: 30rpx;
          font-weight: 400;
          font-size: 28rpx;
          color: #000000;
        ">
				<div style="display: flex;align-items: center;">
					<!-- <uni-icons type="shop-filled" size="20"></uni-icons> -->
					<image class="icon" style="width: 32rpx;height: 32rpx;margin-right: 14rpx;"
						:src="orderInfo.logo || '/static/shopp/icon-company.png'" mode=""></image>
					<span>{{ orderInfo.merchantName }}</span>
					<!-- <uni-icons type="right" size="20"></uni-icons> -->
				</div>
				<div v-for="product in orderInfo.productDtoList" :key="product.id" style="margin-top: 30rpx">
					<div class="between" style="align-items: flex-start">
						<image style="
                flex-shrink: 0;
                height: 150rpx;
                width: 150rpx;
                border-radius: 10rpx;
              " :src="product.skuPics" mode=""></image>
						<div style="
                margin-left: 20rpx;
                font-weight: 400;
                font-size: 26rpx;
                color: #8c8c8c;
              ">
							<div class="product-name">{{ product.productName }}</div>
							<div class="between" style="margin: 10rpx 0">
								<span>{{ product.skuName }}</span>
								<span class="money">￥{{ product.originPrice }}</span>
							</div>
							<div style="text-align: right">×{{ product.amount }}</div>
						</div>
					</div>
					<div class="currentPrice">
						<span>实付款</span>
						<span>￥{{ product.currentPrice }}</span>
					</div>
				</div>
			</div>
			<!-- 订单信息 -->
			<div class="orderInformation">
				<div class="title">订单信息</div>
				<view class="PayInformation">
					<view class="PayInformationOne">
						<span>订单编号</span>
						<span>下单时间</span>
						<span v-if="showPaymentFlag">支付方式</span>
						<span v-if="showPaymentFlag">支付时间</span>
					</view>
					<view class="PayInformationTwo">
						<div>
							<span>{{ orderInfo.orderNo }}</span>
							<span style="margin: 0 10rpx">|</span>
							<span style="color: rgba(0, 140, 234, 1)"
								@click="copyOrderNumber(orderInfo.orderNo)">复制</span>
						</div>
						<span>{{ orderInfo.createTime }}</span>
						<span v-if="showPaymentFlag">{{ orderInfo.showPaymentType }}</span>
						<span v-if="showPaymentFlag">{{ orderInfo.updateTime }}</span>
					</view>
				</view>
				<view class="addressInformation">
					<view class="addressInformationOne">
						<span v-if="showLogisticsFlag">配送方式</span>
						<span>收货信息</span>
						<span>收货地址</span>
					</view>
					<view class="addressInformationTwo">
						<span v-if="showLogisticsFlag">{{ orderInfo.logisticsName }}</span>
						<span>{{ orderInfo.concatPerson }} {{ orderInfo.contactPhone }}</span>
						<span>{{ orderInfo.fullAddress }}</span>
					</view>
				</view>
			</div>
		</div>

		<view class="love-product">
			<view class="title">你可能还喜欢</view>
			<view class="product-box">
				<view class="product" @click="toProduct(product.id)"
					v-for="product in orderInfo.relationProductDtoList">
					<image class="img" :src="product.productPics.split(',')[0]" mode=""></image>
					<view class="name">{{ product.productName }}</view>
					<view class="footer">
						<view class="left">
							<span class="red">￥{{ product.currentPrice || 0 }}</span>
							<span class="del">￥{{ product.originPrice || 0 }}</span>
						</view>
						<view class="right">{{ product.orderAmount || 0 }}人付款</view>
					</view>
				</view>
				<view class="product-empty"></view>
				<view class="product-empty"></view>
			</view>
		</view>

		<div class="fixed-button">
			<view v-if="orderInfo.showTitle === '未支付'" class="cardCenterBtn">
				<view class="btn btn-payment" @click="payEvent">立即支付</view>
			</view>
			<view v-if="orderInfo.showTitle === '卖家待发货'" class="cardCenterBtn">
				<view class="btn btn-payment" @click.stop="handleWarn">提醒发货</view>
			</view>
			<view v-if="orderInfo.showTitle === '卖家已发货'" class="cardCenterBtn">
				<view class="btn btn-logis" @click="loadLogisticsInfo">查看物流</view>
				<view class="btn btn-logis" @click="confirmReceiptEvent">确认收货</view>
			</view>
			<view v-if="orderInfo.showTitle === '已完成'" class="cardCenterBtn">
				<view class="btn btn-del" @click.stop="handleDeleteOrder">删除订单</view>
				<view class="btn btn-payment" @click.stop="bugAgain">再次购买</view>
			</view>

		</div>

		<PayItem ref="payRef" :pay-params="orderInfo" @close="isRoll = true" />
	</div>
</template>

<script lang="ts">
	import {
		ORDER_PAY_STATUS,
		ORDER_STATUS,
		PAYMENT_TYPE_MAP,
	} from "../../../utils/order";
	import to from "await-to-js";
	import PayItem from "../components/PayItem.vue";
	import uniPopup from "../../../components/uni-components/uni-popup/components/uni-popup/uni-popup.vue";
	import NavBar from "../../../components/NavBar.vue";
	import { defineComponent, onMounted, ref } from "@vue/composition-api";
	import {
		confirmReceipt,
		queryOrderInfoById, deleteUserOrder
	} from "../../../utils/ArequestHelper";
	import { customNavigateTo } from "utils/customNavigate";
	import { warn } from 'utils/request/product'

	import Icon from "../../../components/Icon.vue";

	export default defineComponent({
		components: {
			PayItem,
			NavBar,
			uniPopup,
			Icon,
		},
		onLoad: function (options) {
			if (options.orderId) {
				this.setOrderId(options.orderId);
			}

			if (!options.orderId) {
				// todo 跳转到你规定的页面
				console.error("未查询到相关订单");
				uni.redirectTo({});
			}
		},
		setup() {
			const orderId = ref("");
			const orderInfo = ref({});
			const showPaymentFlag = ref(false);
			const showLogisticsFlag = ref(false);
			const isRoll = ref(true);
			function back() {
				uni.navigateBack();
			}
			function showLabel(state : number, pState : number) {
				if (pState === 0) return "未支付";
				if (pState === 1) return "取消支付";
				if (pState === 2) return "支付失败";
				if (state === 1) return "卖家待发货";
				if (state === 2) return "卖家已发货";
				if (state === 3) return "已完成";
			}
			const setOrderId = (receiveOrderId) => {
				orderId.value = receiveOrderId;
			};
			function toProduct(id : string) {
				uni.navigateTo({
					url: `/pages/goShopping/productDetailsPage/productDetailsPage?id=${id}`,
				});
			}

			/** 提醒发货 */
			async function handleWarn() {
				try {
					await warn({ orderId: orderInfo.value.id }).then(res => {
						console.log("催发货 ======>", res)
						uni.showToast({ title: res.data.data, icon: 'none' })
					})
				} catch (err) {
					uni.showToast({ title: err.message, icon: 'none' })
				}
			}
			const bugAgain = () => {
				const productId = orderInfo.value.productDtoList[0].productId;
				customNavigateTo({
					url: `/pages/goShopping/productDetailsPage/productDetailsPage?id=${productId}`
				});
			}
			// 删除订单
			const handleDeleteOrder = async () => {
				const orderId = orderInfo.value.id
				const [error, result] = await to(deleteUserOrder([orderId]));
				if (error) {
					// todo 错误统一处理方式
					console.error(`订单${orderId} : ${error} 删除失败`);
				}
				if (result) {
					const { data: { code } } = result;
					if (code == 1) {
						// todo 错误统一处理方式
						console.error(`订单${orderId} : ${result} 删除失败`);
					}
					if (code == 0) {
						uni.$emit("orderUpdate")
						uni.navigateBack()
					}
					uni.showToast({ title: result.data.msg, icon: 'none' })
				}
			};

			onMounted(() => {
				queryOrderInfo(orderId.value);
			});

			const queryOrderInfo = async (orderId) => {
				const [error, result] = await to(queryOrderInfoById(orderId));
				if (result) {
					const {
						data: { data: originalOrderInfo, code },
					} = result;
					if (code == 1) {
						// todo 错误统一处理方式
						console.error(`订单${orderId} : ${result} 查询失败`);
					}

					if (code == 0) {
						mapOrderStatus(originalOrderInfo);
						originalOrderInfo.showTitle = showLabel(
							originalOrderInfo.state,
							originalOrderInfo.paymentState
						);
						orderInfo.value = originalOrderInfo;
						console.log("订单详情 ----------->", result);
					}
				}
			};

			// 处理一下个人信息
			const mapOrderStatus = (originalOrderInfo) => {
				hideTelephone(originalOrderInfo);
				mapPaymentStatus(originalOrderInfo);
				showOrderStatus(originalOrderInfo);
			};

			// 新增字段 showPaymentType 显示微信支付/支付宝支付
			const mapPaymentStatus = (originalOrderInfo) => {
				const { paymentState, paymentType } = originalOrderInfo;
				if (paymentState === ORDER_PAY_STATUS.SUCCESS) {
					originalOrderInfo.showPaymentType = PAYMENT_TYPE_MAP[paymentType + ""];
				}
			};
			// 隐藏电话号码
			const hideTelephone = (originalOrderInfo) => {
				const { contactPhone } = originalOrderInfo;
				originalOrderInfo.contactPhone =
					contactPhone.substring(0, 3) + "****" + contactPhone.substring(7);
			};

			const showOrderStatus = (originalOrderInfo) => {
				const { state, paymentState } = originalOrderInfo;
				// 订单支付显示与否
				showPaymentFlag.value = paymentState === ORDER_PAY_STATUS.SUCCESS;
				// 地址显示与否
				showLogisticsFlag.value =
					state === ORDER_STATUS.PENDING_RECEIPT ||
					state === ORDER_STATUS.COMPLETED;
			};

			// 跳转到物流页面
			const loadLogisticsInfo = () => {
				customNavigateTo({
					url: `/pages/goShopping/ciewLogistics/viewsLogistics?orderId=${orderId.value}`,
				});
			};

			const confirmReceiptEvent = async () => {
				const [error, result] = await to(confirmReceipt(orderId.value));
				if (error) {
					// todo 错误统一处理方式
					console.error(`订单${orderId.value} : ${error} 确认收货失败`);
				}
				if (result) {
					const {
						data: { code },
					} = result;
					if (code == 1) {
						// todo 错误统一处理方式
						console.error(`订单${orderId.value} : ${result} 确认收货失败`);
					}
					if (code == 0) {
						customNavigateTo({
							url: "/pages/goShopping/myOrder/index",
						});
					}
				}
			};

			const payRef = ref(null);
			const payParams = ref({});
			// 组装支付参数
			const assemblePayParams = (orderInfo) => {
				const { id, totalPrice, productDtoList } = orderInfo;
				// 目前规定只有一个商品，所以直接拿第一个 后续的话需要配合 支付接口进行改动
				const { amount, skuName } = productDtoList[0];
				payParams.value = {
					id,
					amount,
					skuName,
					totalPrice,
				};
			};
			const payEvent = () => {
				isRoll.value = false;
				assemblePayParams(orderInfo.value);
				payRef.value.openPopup();
			};

			return {
				orderInfo,
				setOrderId,
				showPaymentFlag,
				showLogisticsFlag,
				payRef,
				payParams,
				loadLogisticsInfo,
				confirmReceiptEvent,
				payEvent,
				back,
				showLabel,
				toProduct,
				isRoll,
				handleWarn,
				bugAgain,
				handleDeleteOrder
			};
		},
		methods: {
			copyOrderNumber(orderNumber) {
				this.copyAccount(orderNumber);
			},
			copyAccount(account) {
				uni.setClipboardData({
					data: account,
					success: function () {
						uni.showToast({
							title: "订单号已复制",
							icon: "success",
							duration: 2000,
						});
					},
					fail: function (e) {
						uni.showToast({
							title: "复制失败，请重试",
							icon: "none",
							duration: 2000,
						});
					},
				});
			},
		},
	});
</script>

<style lang="scss">
	page {
		background-color: rgb(245, 246, 247);
	}

	.logo {
		width: 40rpx;
		height: 40rpx;
	}

	.wrapper {
		background-color: rgb(245, 246, 247);
		width: 100%;
		height: 100vh;
		box-sizing: border-box;
		padding-bottom: 80rpx;
	}

	.custom-nav {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 999;
		background: #fff;
		// background-image: url("~@/static/home/whole-bg.png");
		// background-size: 100%;
		// background-repeat: no-repeat;
	}

	.status_bar {
		height: var(--status-bar-height);
		width: 100%;
	}

	.bar-bg {
		height: 44px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 0 30rpx;
		box-sizing: border-box;

		image {
			width: 156upx;
			height: 51upx;
		}

		.title {
			flex: 1;
			padding: 0 10rpx;
		}

		.right {
			width: 60rpx;
		}
	}

	.titleNview-placing {
		height: var(--status-bar-height);
		padding-top: 44px;
		box-sizing: content-box;
	}

	.product-name {
		width: 498rpx;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;

		font-weight: 400;
		font-size: 32rpx;
		color: #333333;
	}

	.money,
	.currentPrice {
		font-weight: 400;
		font-size: 28rpx;
		color: #454545;
	}

	.currentPrice {
		margin-top: 30rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.between {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	// 订单信息
	.orderInformation {
		margin-top: 10rpx;
		font-weight: 400;
		font-size: 24rpx;
		color: #000000;
		line-height: 60rpx;

		.title {
			font-weight: bold;
			font-size: 28rpx;
			padding: 10rpx 30rpx 0 30rpx;
			color: #000000;
			background-color: #ffffff;
		}

		.PayInformation {
			padding: 0 30rpx;
			display: flex;
			align-items: center;
			justify-content: space-between;
			color: #6c6666;
			background-color: #ffffff;
			font-size: 13px;

			.PayInformationOne {
				display: flex;
				flex-direction: column;
				width: 140rpx;
			}

			.PayInformationTwo {
				display: flex;
				flex-direction: column;
				align-items: flex-end;
				flex: 1;
			}
		}

		.addressInformation {
			margin-top: 5rpx;
			background-color: #ffffff;
			display: flex;
			justify-content: center;
			padding: 0 30rpx;
			color: #6c6666;
			font-size: 13px;

			.addressInformationOne {
				display: flex;
				flex-direction: column;
				width: 140rpx;
			}

			.addressInformationTwo {
				display: flex;
				flex-direction: column;
				align-items: flex-end;
				flex: 1;
			}
		}
	}

	.love-product {
		padding: 0 10rpx;
		margin-top: 20rpx;

		.title {
			font-weight: bold;
			font-size: 28rpx;
			color: #000000;
			text-align: center;
			line-height: 60rpx;
		}

		.product-box {
			display: flex;
			justify-content: space-between;
			flex-wrap: wrap;

			.product {
				width: 360rpx;
				height: 520rpx;
				background: #ffffff;
				border-radius: 10rpx;
				margin-bottom: 20rpx;

				.img {
					width: 360rpx;
					height: 360rpx;
					border-radius: 10rpx 10rpx 0px 0px;
					object-fit: cover;
				}

				.name {
					padding: 0 10rpx;
					height: 74rpx;
					font-weight: 400;
					font-size: 28rpx;
					color: #333333;
					line-height: 38rpx;

					display: -webkit-box;
					-webkit-box-orient: vertical;
					-webkit-line-clamp: 2;
					/* 指定显示的行数 */
					overflow: hidden;
					text-overflow: ellipsis;
				}

				.footer {
					display: flex;
					align-items: center;
					justify-content: space-between;
					padding: 20rpx 10rpx;

					.left {}

					.right {
						font-weight: 400;
						font-size: 20rpx;
						color: #808080;
					}

					.red {
						font-weight: 400;
						font-size: 28rpx;
						color: #ff3600;
					}

					.del {
						font-weight: 400;
						font-size: 20rpx;
						color: #aaaaaa;
						margin-left: 10rpx;
						text-decoration: line-through;
					}
				}
			}

			.product-empty {
				width: 360rpx;
			}
		}
	}

	// 下
	.fixed-button {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		display: flex;
		justify-content: flex-end;
		background-color: #fff;
		padding: 0 30rpx;

		.btn {
			height: 50rpx;
			font-size: 24rpx;
			width: 140rpx;
			border-radius: 30rpx;
			display: flex;
			justify-content: center;
			align-items: center;
			margin-left: 10rpx;
		}

		.btn-payment {
			background: #008CEA;
			border: 1px solid #0E82F0;
			color: #fff;
		}

		.btn-logis {
			background: #E6A23C;
			border: 1px solid #E6A23C;
			color: #fff;
		}

		.btn-del {
			// background: #F56C6C;
			border: 1px solid #E6E8EB;
			color: #606266;
		}



		.cardCenterText {
			margin: 10px 0 10px 0;
			font-weight: 400;
			font-size: 30rpx;
			color: #333333;
			.red {
				color: rgba(255, 36, 36, 1);
			}
		}

		.cardCenterBtn {
			display: flex;
			justify-content: end;
			align-items: center;
			padding: 20rpx 0;
			button {
				color: #000000;
				height: 40px;
				font-size: 15px;
				margin-left: 10px;
				font-weight: bold;
				box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3);
				/* 添加阴影 */
			}
		}
	}


	.payMargin {
		margin: 3% 3% 6% 3%;

		.paymentPopup {
			display: flex;
			flex-direction: column;
			align-items: center;
			margin: 5%;

			p {
				color: #000000;
				font-size: 30px;
			}

			.paymentGroup {
				width: 100%;
			}

			.paymentRadio {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin: 5% 0;
			}

			.paymentText {
				display: flex;
				align-items: center;
				justify-content: start;
				width: 30%;

				span {
					margin-left: 10%;
				}
			}

			button {
				background: #1e70e2;
				width: 100%;
				font-size: 15px;
				border-radius: 20px;
				color: #ffffff;
				letter-spacing: 5px;
				font-weight: bold;
			}
		}
	}
</style>