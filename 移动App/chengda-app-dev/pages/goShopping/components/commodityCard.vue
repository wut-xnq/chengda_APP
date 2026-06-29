<template>
	<div class="order-item" @click="handleClick">
		<!-- 上 -->
		<div class="cardTop">
			<div class="CompanyInformation">
				<!-- <uni-icons type="shop-filled" :size="20"></uni-icons> -->
				<image class="icon" :src="logo||'/static/shopp/icon-company.png'" mode=""></image>
				<span>{{ merchantName }}</span>
				<!-- <uni-icons type="right" :size="14" style="color: #333;"></uni-icons> -->
			</div>
			<span style="color: rgba(255, 150, 14, 1);font-size: 28rpx;">{{ showStatus }}</span>
		</div>
		<div v-for="({id,amount,skuName,currentPrice,productName,skuPics}) in productDtoList" :key="id">
			<!-- 中 -->
			<div class="cardCenter" >
				<div class="cardCenterImage">
					<img class="order-img" :src="skuPics" alt="" />
				</div>
				<div class="cardCenterTextWZ">
					<span>{{ productName }}</span>
					<span>{{ skuName }}</span>
				</div>
				<div class="cardCenterTextJS">
					<span>￥{{ currentPrice }}</span>
					<span>x{{ amount }}</span>
				</div>
			</div>
		</div>
		<div class="cardCenterTips" v-if="showStatus === '待付款'">
		  <view>
			  <span class="text">温馨提示</span>
			  <span >请尽快完成付款，商品火爆可能会卖断货</span>
		  </view>
		  <!-- <uni-icons type="right" :size="14"></uni-icons> -->
		</div>

		<!-- 下 -->
		<div class="cardCenterTextBtn">
			<div class="cardCenterText">
				<span>{{ paymentStatus }}：</span>
				<span class="red">￥{{ realPrice }}</span>
			</div>
			
			<div class="cardCenterBtn" v-if="showStatus === '待付款'">
				<view class="btn btn-payment" @click.stop="handlePayOrder">去支付</view>
			</div>
			<div class="cardCenterBtn" v-if="showStatus === '待发货'">
				<view class="btn btn-payment" @click.stop="handleWarn">提醒发货</view>
			</div>
			<div class="cardCenterBtn" v-if="showStatus === '待收货'">
				<view class="btn btn-logis" @click.stop="loadLogisticsInfo">查看物流</view>
				<view class="btn btn-logis" @click.stop="$emit('confirm-receipt', orderId)">确认收货</view>
			</div>
			<div class="cardCenterBtn" v-if="showStatus === '已完成'">
				<view class="btn btn-del" @click.stop="$emit('delete-order', orderId)">删除订单</view>
				<view class="btn btn-payment" @click.stop="bugAgain">再次购买</view>
			</div>
		</div>

		<PayItem ref="payRef" :pay-params="payParams" @click.native.stop="" style="position: fixed;top:0;z-index: 199;"/>
	</div>
</template>

<script lang="ts">
	import { ORDER_SHOW_TYPE } from "../../../utils/order";
	import PayItem from "./PayItem.vue";
	import { defineComponent, ref } from '@vue/composition-api';
	import { customNavigateTo } from '../../../utils/customNavigate'
    import {warn} from 'utils/request/product'
	export default defineComponent({
		name: 'OrderCard',
		components: { PayItem },
		props: {
			order: Object
		},
		setup(props) {
			const ORDER__SHOW__TYPE = ORDER_SHOW_TYPE;

			const {
				order: {
					id: orderId,
					merchantName,
					showStatus,
					productDtoList,
					realPrice,
					paymentStatus,
					logo
				}
			} = props;

			const handleClick = () => {
				console.log('点击了订单卡片');
				customNavigateTo({
					url: `/pages/goShopping/itemStatusDetailsPage/index?orderId=${orderId}`
				});
			};
			/** 提醒发货 */
			async function handleWarn(){
				try{
					await warn({orderId:orderId}).then(res=>{
						console.log("催发货 ======>",res)
						uni.showToast({title:res.data.data,icon:'none'})
					})
				}catch(err){
					uni.showToast({title:err.message,icon:'none'})
				}
			}

			// 跳转到物流页面
			const loadLogisticsInfo = () => {
				customNavigateTo({
					url: `/pages/goShopping/ciewLogistics/viewsLogistics?orderId=${orderId}&productDtoList=${JSON.stringify(productDtoList)}`
				});
			}

			const bugAgain = () => {
				const productId = props.order.orderProducts.split(',')[0];
				customNavigateTo({
					url: `/pages/goShopping/productDetailsPage/productDetailsPage?id=${productId}`
				});
			}

			const payRef = ref(null);
			const payParams = ref({});
			const assemblePayParams = () => {
				payParams.value = props.order
			}

			const handlePayOrder = (orderId : any) => {
				console.log(`处理订单 ${orderId} 的支付请求`);
				assemblePayParams();
				payRef.value.openPopup();
			}

			return {
				ORDER__SHOW__TYPE,
				orderId,
				merchantName,
				showStatus,
				productDtoList,
				realPrice,
				paymentStatus,
				payRef,
				payParams,
				handleClick,
				loadLogisticsInfo,
				bugAgain,
				handlePayOrder,
				handleWarn,
				logo
			};
		},
	});
</script>

<style lang="scss" scoped>
	.order-item {
		background-color: #fff;
		padding: 20rpx;
		border-radius: 10rpx;

		font-weight: 400;
		font-size: 28rpx;
		color: #333333;

		// 上
		.cardTop {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 20rpx;

			.CompanyInformation {
				display: flex;
				justify-content: space-between;
				align-items: center;
				
				font-weight: 400;
				font-size: 28rpx;
				color: #333333;
				.icon{
					width: 32rpx;
					height: 32rpx;
					margin-right: 14rpx;
				}
			}
		}

		// 中
		.cardCenter {
			display: flex;
			justify-content: space-between;
			margin-bottom: 10px;

			.cardCenterImage {
				.order-img {
					height: 200rpx;
					width: 200rpx;
					border-radius: 10rpx;
					object-fit: cover;
				}
			}

			.cardCenterTextWZ {
				display: flex;
				flex-direction: column;
				width: 50%;

				span {
					color: #000000;
					font-weight: bold;
					display: -webkit-box;
					-webkit-line-clamp: 1;
					-webkit-box-orient: vertical;
					overflow: hidden;
					text-overflow: ellipsis;
				}

				span+span {
					color: #ADADAD;
					font-weight: 400;
					font-size: 15px;
					margin-top: 5px;
				}
			}

			.cardCenterTextJS {
				display: flex;
				flex-direction: column;
				align-items: flex-end;
				span {
					color: #000000;
				}

				span+span {
					color: #BBBBBB;
					margin-top: 5px;
				}
			}
		}

		// 温馨提示
		.cardCenterTips {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 15rpx 20rpx;
			background: #F9F9F9;
			border-radius: 5rpx;
			font-weight: 400;
			font-size: 28rpx;
			color: #808080;
			.text{
				font-weight: bold;
				color: #333333;
				margin-right: 10rpx;
			}
		}

		// 下
		.cardCenterTextBtn {
			display: flex;
			flex-direction: column;
			align-items: flex-end;
			.btn{
				height:50rpx;
				font-size: 24rpx;
				width: 140rpx;
				border-radius: 30rpx;
				display:flex;
				justify-content: center;
				align-items:center;
				margin-left: 10rpx;
			}
			.btn-payment{
				background: #008CEA;
				border: 1px solid #0E82F0;
				color:#fff;
			}
			.btn-logis{
				background: #E6A23C;
				border: 1px solid #E6A23C;
				color:#fff;
			}
			.btn-del{
				// background: #F56C6C;
				border: 1px solid #E6E8EB;
				color: #606266;
			}
			
			
			
			.cardCenterText {
				margin: 10px 0 10px 0;
				font-weight: 400;
				font-size: 30rpx;
				color: #333333;
				.red{
					color: rgba(255, 36, 36, 1);
				}
			}

			.cardCenterBtn {
				display: flex;
				justify-content: end;
				align-items: center;

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
	}
</style>