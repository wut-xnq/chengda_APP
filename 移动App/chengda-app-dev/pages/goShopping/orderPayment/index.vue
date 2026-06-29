<!-- 订单支付 -->
<template>
	<div class="wrapper" v-if="product">
		<!-- 地址 -->
		<div class="address" @click="toEditAddress">
			<div class="addressText">
				<span v-if="!order.userInfo.phone" class="tips">请选择地址</span>
				<span>{{ order.userInfo.address }}</span>
				<span class="name">{{ order.userInfo.address }}</span>
				<span>{{ order.userInfo.name}}&nbsp;&nbsp;&nbsp;&nbsp;{{ order.userInfo.phone }}</span>
			</div>
			<uni-icons type="right" size="25"></uni-icons>
		</div>

		<!-- 商品信息展示 -->
		<div class="productInformationDis">
			<div class="productInformationDisTextImage">
				<image :src="selectProduct.skuPics" mode=""></image>
				<div class="productInformationDisText">
					<div class="textsForm">
						<span>{{product.productName}}</span>
						<span>{{ selectProduct.skuName }} &nbsp;&nbsp;{{order.specName}}</span>
					</div>
					<div class="quantityJj">
						<p>￥{{ (selectProduct.currentPrice-0).toFixed(2) }}</p>
						<div class="quantityJjbtn">
							<view class="btn" @click="order.amount == 1?1:order.amount--;diffPrice()">-</view>
							<span>{{ order.amount }}</span>
							<view class="btn" @click="order.amount++;diffPrice()">+</view>
						</div>
					</div>
				</div>
			</div>
			<div class="deliveryInform" v-if="product.deliver">
				<span>配送信息</span>
				<span>{{product.deliver}}</span>
			</div>
		</div>
		<!-- 商品文字提示及备注功能-->
		<div class="remarkTips">
			<div class="remarkTextes">
				<span class="lable">商品金额</span>
				<span>￥{{ (selectProduct.currentPrice*order.amount).toFixed(2) }}</span>
			</div>
			<div class="remarkTextes">
				<span class="lable">订单运费</span>
				<span>{{ product.ifShipped === '1'?'包邮': `￥${order.deliverPrice}`}}</span>
			</div>
			<div class="remarkTextes">
				<span class="lable">备注</span>
				<div class="remarkInput">
					<textarea class="text" v-model="order.remarks" auto-height placeholder="无备注" />
				</div>
			</div>
			<div class="moneyTips">总计：￥{{ order.totalPrice }}</div>
		</div>
		<!-- 支付方式的选择 -->
		<div class="paymentMethod">
			<radio-group @change="radioChange" class="paymentGroup">
				<view v-for="(item, index) in buys" :key="item.value">
					<label class="uni-list-cell uni-list-cell-pd paymentRadio" v-if="item.show">
						<div class="paymentText">
							<image class="icon-logo" :src="item.icon" mode=""></image>
							<span>{{ item.label }}</span>
						</div>
						<view>
							<radio :value="item.value" :checked="item.value === order.buyType" />
						</view>
					</label>
				</view>

			</radio-group>
		</div>

		<!-- 实际付款/立即支付按钮 -->
		<div class="payTipsBtn">
			<span>实付款：￥{{ order.totalPrice }}</span>
			<button @click="confirmPayment">立即支付</button>
		</div>
	</div>
</template>

<script lang="ts">
	import {
		defineComponent,
		nextTick,
		onMounted,
		ref, reactive
	} from "@vue/composition-api";
	import NavBar from "../../../components/NavBar.vue";
	import { onLoad, onShow } from "@dcloudio/uni-app";

	import { getProduct, productFreight, createOrder } from "@/utils/request/product"
	import { getUserAddress } from "@/utils/request/user"
	import { ailiPayment,paymentSuccess } from "@/utils/request/product"

	export default defineComponent({
		setup() {
			const product = ref(null);
			const selectProduct = ref(null);
			const order = reactive({
				amount: 0,
				skuId: 0,
				userInfo: { userId: null, phone: null, addressId: null, address: null, name: null },
				remarks: '',
				buyType: 'alipay',
				specName: '',

				deliverPrice: 0,
				totalPrice: 0,

				orderNo: '',
			});
			const buys = [
				{ label: "微信", value: '1', icon: '',show: false },
				{ label: "支付宝", value: 'alipay',icon: require('@/static/icons/logo-zfb.png'), show: true }]

			onLoad((options) => {
				console.log("商品订单参数--------->",options)
				const userInfo = uni.getStorageSync('firstLoginUser');
				order.userInfo.userId = userInfo.user_id
				if (options) {
					order.skuId = options.typeId
					order.amount = options.count
					order.productId = options.productId
					order.specName = options.specName
					
					getUserAddressDefault();
					getData(options.productId)
				}
				uni.$on('selectAdrees', function (data) {
					if (data.data) {
						order.userInfo.phone = data.data.contactPhone
						order.userInfo.name = data.data.concatPerson
						order.userInfo.address = data.data.fullAddress
						order.userInfo.addressId = data.data.id

					}
				})
			})
			/**
			 * 获取用户默认地址
			 */
			function getUserAddressDefault() {
				getUserAddress({ userId: order.userInfo.userId }).then(res => {
					const result = res?.data?.data;
					if (result) {
						order.userInfo.phone = result.contactPhone
						order.userInfo.name = result.concatPerson
						order.userInfo.address = result.fullAddress
						order.userInfo.addressId = result.id
					}
					console.log("用户默认地址", res)
				})
			}

			/**
			 * 获取商品详情
			 */
			function getData(id : any) {
				getProduct({ id: id }).then(res => {
					console.log("商品详情 =>", res)
					const result = res?.data?.data;
					if (result) {
						product.value = result;

						selectProduct.value = result.skuList.filter(item => item.id === order.skuId)[0]

						diffPrice();
						console.log("选择的商品", selectProduct.value)
					}
				})
			}
			/**
			 * 运费
			 */
			async function getProductFreight() {
				const { userInfo, skuId, amount } = order
				await productFreight({ userAddressId: userInfo.addressId, skuId: skuId, amount: amount }).then(res => {
					console.log("运费", res)
					const result = res?.data?.data;
					if (result) {
						order.deliverPrice = result.deliverPrice
					}
				})
			}

			/**
			 * 计算金额
			 */
			async function diffPrice() {
				if (product?.value?.ifShipped !== '1') {
					await getProductFreight()
				}
				const num = Number(selectProduct.value.currentPrice * order.amount) + Number(order.deliverPrice)
				order.totalPrice = num.toFixed(2)
			}
			// 跳转修改地址
			const toEditAddress = () => {
				uni.navigateTo({
					url: `/pages/goShopping/orderPayment/orderAddress`,
				})
			};
			const radioChange = (value : any) => {
				console.log(value)
			};
			/** 生成订单 */
			async function saveOrder() {
				const { userInfo, skuId, amount, remarks } = order
				await createOrder({
					"userId": userInfo.userId,
					"skuId": skuId,
					"amount": amount,
					"userAddressId": userInfo.addressId,
					deliverPrice:order.deliverPrice,
					"remarks": remarks
				}).then(res => {
					console.log("订单信息", res)
					const result = res?.data
					if (result.code === 0) {
						order.orderNo = result.data.orderNo
					}else{
						throw new Error("创建商品订单失败");
					}
				})
			}
			const payment = ref(false)
			const confirmPayment = async () => {
				try{
					if(payment.value) return;
					if (!order.userInfo.phone) {
						uni.showToast({ title: "请选择送货地址", icon: "none", });
						return
					}
					payment.value = true
					await saveOrder();
					ailiPayment({ orderNo: order.orderNo }).then(res => {
						console.log(res)
						const result = res?.data
						if(result.code === 0){
							uni.requestPayment({
								provider: 'alipay',
								orderInfo: result.data.responseBody, //微信、支付宝订单数据 【注意微信的订单信息，键值应该全部是小写，不能采用驼峰命名】
								success: function (res) {
									uni.showToast({title:'支付成功'})
									success(result.data.tradeNo)
								},
								fail: function (err) {
									console.log('fail:' + JSON.stringify(err));
									throw "支付失败";
								}
							});
						}else{
							throw "创建交易订单失败";
						}
					})
				} catch (err){
					payment.value = false
					uni.showToast({ title: err+'', icon: "none", });
				}
				
			};
			/** 支付成功 */
			function success(orderNo:any){
				paymentSuccess({orderNo:orderNo}).then(res=>{
					console.log(res)
					uni.redirectTo({
					  url: `/pages/goShopping/myOrder/index`,
					});
					
					uni.$emit('orderUpdate',{msg:'页面更新'})
				})
			}
			return {
				product,
				selectProduct,
				order,
				diffPrice,
				buys,
				radioChange,
				confirmPayment,
				toEditAddress,

			};
		},
	});
</script>
<style>
	page {
		background: #f5f7f9;
	}
</style>

<style lang="scss" scoped>
	.wrapper {
		margin-top: 1px;
		padding-bottom: 130rpx;
	}

	.tips {
		color: #CCCCCC;
	}

	// 地址
	.address {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 10rpx;
		background: #fff;
		padding: 20rpx 30rpx;

		.addressText {
			display: flex;
			flex-direction: column;
			color: #757070;
			font-size: 28rpx;
		}
		.name{
			font-weight: bold;
			font-size: 30rpx;
			margin: 21rpx 0;
			color: #000000;
		}
	}

	// 商品信息展示
	.productInformationDis {
		margin-bottom: 10rpx;
		background: #fff;
		padding: 30rpx;

		.productInformationDisTextImage {
			display: flex;
			justify-content: space-between;

			image {
				width: 230rpx;
				height: 220rpx;
				border-radius: 10rpx;
			}

			.productInformationDisText {
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				width: 65%;

				.textsForm {
					display: flex;
					flex-direction: column;

					span {
						display: -webkit-box;
						/* 使用弹性盒子布局 */
						-webkit-line-clamp: 1;
						/* 显示的行数 */
						-webkit-box-orient: vertical;
						/* 设置垂直排列 */
						overflow: hidden;
						font-weight: 600;
					}

					span+span {
						color: #7f7a7a;
						font-size: 12px;
						margin-top: 5px;
					}
				}

				.quantityJj {
					display: flex;
					justify-content: space-between;
					.quantityJjbtn {
						display: flex;
						align-items: center;
						justify-content: center;

						.btn {
							display: flex;
							align-items: center;
							justify-content: center;
							width: 30rpx;
							height: 30rpx;
							border-radius: 10rpx;
							background: #e3e3e3;
							margin: 0 8px;
						}
					}
				}
			}
		}

		// 配送信息
		.deliveryInform {
			display: flex;
			justify-content: space-between;

			span {
				color: #000000;
				font-weight: 600;
				margin-top: 3%;
			}

			span+span {
				color: #6f6f6f;
			}
		}
	}

	// 商品文字提示及备注功能
	.remarkTips {
		margin-bottom: 10rpx;
		background: #fff;
		padding: 30rpx;
		font-weight: 400;
		.remarkTextes {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 30rpx;
			span {
				color: rgba(0, 0, 0, 1);
			}

			.text {
				text-align: right;
			}
		}

		.moneyTips {
			width: 100%;
			text-align: right;
		}

		.lable {
			width: 200rpx;
		}
	}

	.remarkInput {
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 50%;
	}

	// 支付方式
	.paymentMethod {
		margin-bottom: 10rpx;
		background: #fff;
		padding: 30rpx;

		.paymentGroup {
			.paymentRadio:nth-child(1) {
				margin-bottom: 3%;
			}

			.paymentRadio {
				display: flex;
				justify-content: space-between;
				align-items: center;

				.paymentText {
					display: flex;
					align-items: center;
					justify-content: start;
					width: 30%;

					span {
						margin-left: 10%;
					}
					.icon-logo{
						width: 40rpx;
						height: 40rpx;
					}
				}
			}
		}
	}

	// 实际付款/立即支付按钮
	.payTipsBtn {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		background: #fff;

		padding: 20rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;

		height: 110rpx;
		box-sizing: border-box;
		box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.1);

		span {
			width: 60%;
		}

		button {
			font-size: 30rpx;
			font-weight: bold;
			color: #ffffff;
			border-radius: 34rpx;
			background-color: #0E82F0;
			width: 240rpx;
			height: 68rpx;
		}
	}
</style>