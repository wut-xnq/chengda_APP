<template>
	<!-- 查看物流 -->
	<div class="wrapper" v-if="materialFlow">
		<!-- 上 -->
		<!-- <NavBar class="nStyle" :title="t('weslogisticsView')" /> -->
		<div class="allMaterialFlow" >
			<div class="MaterialFlow">
				<div class="MaterialFlowImage">
					<image :src="materialFlow.productList[0].productPics.split(',')[0]" style="width: 100%; height: 100%; object-fit: cover;" alt="" />
				</div>
				<div class="MaterialFlowText">
					<view>{{getLabel(materialFlow.queryTrackResp.state)}}</view>
					<view class="text">{{materialFlow.queryTrackResp.data[0].context}}</view>
					<!-- <view class="phone">{{materialFlow.queryTrackResp.data[0].context}}</view> -->
				</div>
			</div>
			<!-- 中 -->
			<div class="MaterialFlowCenter">
				<uni-icons type="contact" size="15">
					<span>{{ materialFlow.logisticsName }}</span>
					<span>{{ materialFlow.logisticsNo }}</span>
				</uni-icons>
				<uni-icons type="contact" size="15" @click="copyText(materialFlow.logisticsNo)">
					<span>复制</span>
				</uni-icons>
			</div>
			<!-- 自行书写步骤条 -->
			<div class="logistics-steps">
				<div v-for="(step, index) in materialFlowList" :key="index" class="step">
					<div class="step-number" :class="['step-number', { 'is-first': index === 0 }]"></div>
					<div class="step-content">
						<div class="step-content-texts">
							<!-- <div class="step-title">已完成</div> -->
							<div class="step-desc">{{getLabel(step.status)}}{{ step.time }}</div>
						</div>
						<div class="step-texts">{{ step.context }}</div>
					</div>
					<div v-if="index !== materialFlowList.length - 1" class="step-line"></div>
				</div>
			</div>
		</div>
		<!-- 下（用户信息） -->
		<div class="usersAddress">
			<div class="adress">
				<div class="shou">收</div>
				{{ materialFlow.logisticAddressDto.fullAddress }}
			</div>
			<p class="name">
				{{ materialFlow.logisticAddressDto.concatPerson}}
				{{ materialFlow.logisticAddressDto.contactPhone }}
			</p>
		</div>
	</div>
</template>

<script lang="ts" setup>
	import { onMounted, ref } from "@vue/composition-api";
	import NavBar from "../../../components/NavBar.vue";
	import { t } from "../../../utils/i18n";
	import { resLogisticsView } from "utils/ArequestHelper";
	import { onLoad } from "@dcloudio/uni-app";
	const materialFlow = ref(null);

	const copyText = (data : any) => {
		console.log("data", data);
		uni.setClipboardData({
			data: data,
			success: function (res) {
				console.log("success", res);
				uni.getClipboardData({
					success: function (res) {
						console.log("粘贴", res);
					},
				});
			},
			fail: (error) => {
				console.log("失败", error);
				uni.showToast({
					title: "复制失败",
					icon: "none",
				});
			},
		});
	};

	interface MaterialFlowList {
		time : string;
		context : string;
	}
	const materialFlowList = ref<MaterialFlowList[]>([]);

	const orderId = ref();

	// 手机状态栏高度
	const statusBarHeight = ref();
	const height = uni.getSystemInfoSync();
	statusBarHeight.value = height.statusBarHeight;

	const productDtoList = ref({});

	// 使用 onLoad 钩子获取传递的参数
	onLoad(async (options : { orderId ?: number, productDtoList ?: string }) => {
		if (options?.orderId) {
			orderId.value = options.orderId;
			console.log("接收到的 orderId:", orderId.value);
			const firstLoginUser = uni.getStorageSync("firstLoginUser");
			try {
			  const res = await resLogisticsView(
			    orderId.value,
			    firstLoginUser.access_token
			  );
			  console.log("resLogisticsView=========", res);
			  // 将 API 返回的结果赋值给 materialFlow
			  materialFlow.value = res.data.data;
			  materialFlowList.value = res.data.data.queryTrackResp.data;
			} catch (error) {
			  console.error("Error fetching data:", error);
			}
		}
		
	});
	
	function getLabel(state:string){
		if(state === '0') return "在途";
		if(state === '2') return "揽收";
		if(state === '3') return "疑难";
		if(state === '4') return "签收";
		if(state === '5') return "退签";
		if(state === '6') return "派件";
		if(state === '7') return "转投";
		// if(state === '8') return "在途";
		// if(state === '9') return "在途";
		if(state === '10') return "待清关";
		if(state === '11') return "清关中";
		if(state === '12') return "已清关";
		if(state === '13') return "清关异常";
		if(state === '14') return "拒签";
		
	}
</script>

<style>
	page{
		background-color: rgb(245, 246, 247);
	}
</style>
<style lang="scss" scoped>
	// .wrapper {
	// 	background-color: rgb(245, 246, 247);
	// 	width: 100%;
	// 	// height: 100%;
	// 	box-sizing: border-box;
	// }

	.nStyle {
		box-shadow: 0 10px 10px 3px rgba(0, 0, 0, 0.1);
	}
	// 上
	.MaterialFlow {
		display: flex;
		justify-content: space-between;
		align-items: center;
		background-color: #ffffff;
		margin-top: 10rpx;
		padding: 20rpx 30rpx;

		.MaterialFlowImage {
			height: 150rpx;
			width: 150rpx;
			flex-shrink: 0;
		}

		.MaterialFlowText {
			display: flex;
			flex-direction: column;
			margin-left: 20rpx;
			
			font-weight: 400;
			font-size: 32rpx;
			color: #333333;
			.text{
				font-weight: 400;
				font-size: 26rpx;
				color: #8C8C8C;
				line-height: 34rpx;
				
				overflow: hidden;
				word-break: break-all;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-line-clamp: 3;
				-webkit-box-orient: vertical;
			}
		}
	}

	// 中
	.MaterialFlowCenter {
		display: flex;
		justify-content: space-between;
		align-items: center;
		background: #ffffff;
		margin-top:10rpx;
		padding: 20rpx 30rpx;
		.textBig {
			font-size: 20px;
		}
	}

	// 步骤条样式
	.logistics-steps {
		display: flex;
		flex-direction: column;
		position: relative;
		padding: 0 30rpx;
		background-color: #ffffff;
	}

	.step {
		display: flex;
		align-items: center;
		position: relative;
		height: 180rpx;
	}

	.step-number {
		width: 30rpx;
		height: 30rpx;
		border-radius: 50%;
		background-color: #ccc;
		color: white;
		display: flex;
		align-items: center;
		justify-content: center;
		font-weight: bold;
	}

	.step-number.is-first {
		background-color: royalblue;
		color: white;
	}

	.step-line {
		position: absolute;
		left: 14rpx;
		top: 108rpx;
		/* 从 .step-number 底部开始，减去边框的厚度 */
		height: calc(100% - 20px);
		/* 减去 .step-number 的高度，包括边框 */
		border-left: 4rpx dashed #d6d6d6;
		/* 虚线 */
		transform: translateX(-50%);
	}

	.step-content {
		margin-left: 20rpx;
		width: 90%;
		font-weight: 400;
		font-size: 26rpx;
		color: #8C8C8C;
		
		.step-content-texts {
			display: flex;
			align-items: center;
		}
	}

	.step-title {
		font-weight: bold;
		font-size: 16px;
		margin-right: 5px;
		margin-bottom: 5px;
	}

	.step-desc {
		font-weight: 400;
		font-size: 24rpx;
		color: #333333;
	}

	.step-texts {
		color: #999;
		font-size: 12px;
	}

	.usersAddress {
		margin-top: 2rpx;
		padding: 20rpx 30rpx 30rpx 30rpx;
		background-color: #ffffff;
		.adress {
			display: flex;
			align-items: center;
			margin-bottom: 10px;
			font-weight: 400;
			font-size: 28rpx;
			color: #333333;
			
			.shou {
				width: 40rpx;
				height: 40rpx;
				border-radius: 50%;
				color: #ffffff;
				background-color: #d6d6d6;
				margin-right: 10px;
				font-size: 24rpx;
				display: flex;
				justify-content: center;
				align-items: center;
			}
		}

		.name {
			margin-left: 60rpx;
			
			font-weight: 400;
			font-size: 26rpx;
			color: #8C8C8C;
		}
	}
</style>