<template>
	<uni-popup ref="payPopup" style="touch-action:none" type="bottom" background-color="#fff" :isMaskClick='false'>
		<div class="payMargin">
			<uni-icons type="closeempty" :size="20" @click="closePopup"></uni-icons>
			<div class="paymentPopup">
				<p>￥{{payParams.totalPrice}}</p>
				<radio-group @change="radioChange($event)" class="paymentGroup">
					<label class="uni-list-cell uni-list-cell-pd paymentRadio" v-for="(item, index) in PaymentStyle"
						:key="item.value">
						<div class="paymentText">
							<image class="icon-logo" :src="item.icon" mode=""></image>
							
							<span>{{ item.name }}</span>
						</div>
						<view>
							<radio :value="item.value" :checked="index === current" />
						</view>
					</label>
				</radio-group>
				<button @click="confirmPayment">确认支付</button>
			</div>
		</div>
	</uni-popup>
</template>
<script lang="ts">
	import { defineComponent, ref } from "@vue/composition-api";
	import uniPopup from '../../../components/uni-components/uni-popup/components/uni-popup/uni-popup.vue';
	import { PAYMENT_STYLE } from "../../../utils/order";

	import { ailiPayment,paymentSuccess } from "@/utils/request/product"
	export default defineComponent({
		components: { uniPopup },
		props: {
			payParams: {
				type: Object,
				required: false
			}
		},
		setup(props,{ emit }) {
			const PaymentStyle = [{
				name: '支付宝',
				value: 'aliPay',
				icon: require('@/static/icons/logo-zfb.png')
			}]  //PAYMENT_STYLE;
			const current = ref<number>(0); // 显式指定类型为 number
			const radioChange = (evt : any) => {
				// const value = evt.detail.value;
				// const index = PaymentStyle.findIndex(item => item.value === value);
				// if (index !== -1) {
				//   current.value = index;
				// }
			};

			// 初始化 payPopup
			const payPopup = ref(null);

			const openPopup = () => {
				payPopup.value?.open();
			}
			const closePopup = () => {
				emit("close")
				payPopup.value?.close();
			}
			const confirmPayment = () => {
				try{
					console.log(props.payParams)
					ailiPayment({ orderNo: props.payParams.orderNo }).then(res => {
						console.log("支付订单信息 = >",res)
						const result = res?.data?.data;
						if(res.data.code === 1) throw new Error(res.data.msg);
						uni.requestPayment({
							provider: 'alipay',
							orderInfo: result.responseBody, //微信、支付宝订单数据 【注意微信的订单信息，键值应该全部是小写，不能采用驼峰命名】
							success: function (res) {
								uni.showToast({title:'支付成功'})
								success(result.tradeNo)
							},
							fail: function (err) {
								throw new Error(err);
							}
						});
					})
				}catch(err){
					uni.showToast({title:err.message,icon: 'none'})
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
				payPopup,
				current,
				PaymentStyle,
				radioChange,
				closePopup,
				openPopup,
				confirmPayment,
			}
		},
	});
</script>

<style lang="scss" scoped>
	.payMargin {
		margin: 3% 3% 6% 3%;
		;

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
				.icon-logo{
					width: 40rpx;
					height: 40rpx;
				}
			}

			button {
				background: #1E70E2;
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