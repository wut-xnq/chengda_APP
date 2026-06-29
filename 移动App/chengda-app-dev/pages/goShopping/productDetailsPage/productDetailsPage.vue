<!-- 商品详情页面 -->
<template>
	<view class="content">
		<div class="wrapper" v-if="productDetails.id" :style="{overflow: popShow?'hidden':'auto'}">
			<div>
				<swiper class="swiper" circular :indicator-dots="true" :autoplay="true" :interval="3000"
					:duration="1000">
					<swiper-item v-if="productDetails.productPics" v-for="url in productDetails.productPics.split(',')">
						<image :src="url" mode="scaleToFill"></image>
					</swiper-item>
				</swiper>
			</div>
			<div class="CommodityInformation">
				<div class="CommodityInformationOne">
					<span>￥{{ selectedSku.originPrice }}</span>
					<div class="CommodityInformationThree">
						到手价￥{{ selectedSku.currentPrice }}
					</div>
					<div class="CommodityInformationFour">
						<!-- {{ productDetails.orderAmount }}人购买 -->
					</div>
				</div>
				<div class="CommodityInformationTwo">
					<!-- <span>9折</span>
					<span>优惠</span> -->
				</div>
				<p>[本店热销]{{ productDetails.productName }}</p>
			</div>
			<div class="guarantee">
				<div class="guaranteeTop">
					<span class="textBlack">保障</span>
					<!-- <span style="display: flex; align-items: center; justify-content: center">
						<image style="height: 33rpx; width: 33rpx; margin-right: 2px"
							src="../../../static/DcImage/JSTK.png" mode=""></image>
						<span class="textBlackSpan">极速退款</span>
					</span> -->
					<span style="display: flex; align-items: center">
						<image style="height: 33rpx; width: 33rpx; margin-right: 2px"
							src="../../../static/DcImage/SWLY.png" mode=""></image>
						<span class="textBlackSpan">7天无理由退货</span>
					</span>
				</div>
				<div class="guaranteeCenter" @click="selectSpecification">
					<span class="textBlack">已选</span>
					<span class="select-text">{{selectedSku.skuName || "X系列-10骨全自动-黑胶轻语-深灰色-折叠..."}}</span>
					<image class="icon-right" src="../../../static/my/my-right.png"></image>
				</div>
				<!-- <div class="guaranteeBottom">
					<span class="textBlack">发货</span>
					<span>{{ productDetails.deliver }}发货，预计3天后到达目的地 | 包邮</span>
				</div> -->
			</div>
			<div class="qualityInspectionRes" v-if="productDetails.shelveArea === 1 && InspectionList">
				<table>
					<tr>
						<td>质检单位</td>
						<td>质检时间</td>
						<td>结果</td>
					</tr>
					<tr>
						<td>{{ InspectionList.agencyName }}</td>
						<td>{{ InspectionList.publishDate }}</td>
						<td>{{ InspectionList.remarks }}</td>
					</tr>
				</table>
			</div>
			<div class="documentView" v-if="productDetails.shelveArea === 1">
				<span>国标文档/常见问题<span class="viewSpan" @click="showQ = !showQ">（可查看）</span></span>

				<view :style="{height: showQ?'auto':'0px',overflow:'hidden',transition: 'all 1s'}">
					<view v-for="(item,index) in productDetails.fileList">
						<view class="down" @click="downFile(item)">附件{{index+1}}：{{item.original}}</view>
					</view>
				</view>

			</div>
			<div class="productIntroduction" v-if="productDetails.productText">
				<div v-for="i in productDetails.productText.split(',')">
					<img :src="i" mode="" style="width: 100%;"></img>
				</div>
			</div>
		</div>
		<!-- 客服/购买 -->
		<div class="customerServicePurchase" v-show="isCustomerServiceVisible">
			<view class="left" @click="service">
				<view class="iconfont icon-kefu"></view>
				<view>客服</view>
			</view>
			<view class="btn-box">
				<view class="btn-1" @click="toServicePerson">为您服务的人</view>
				<view class="btn-2" @click="openPurchasePopup">购买</view>
			</view>

		</div>
		<!-- 规格选择弹窗 -->
		<uni-popup ref="popupRef" type="bottom" class="uni-popup" background-color="#fff" :mask-click="false">
			<div class="popopContent">
				<!-- 商品信息 -->
				<div class="productContent">
					<image :src="selectedSku.skuPics" mode=""></image>
					<view style="flex:1;margin:0 20rpx;">
						<view class="oldPrice">￥{{ selectedSku.originPrice }}</view>
						<view class="newPrice">到手价￥{{ selectedSku.currentPrice }}</view>
					</view>
					<uni-icons type="closeempty" :size="20" color="#9A9A9A" @click="closePopup"></uni-icons>
				</div>
				<!-- sku集合 -->
				<div class="specificationContent" v-if="productDetails.id">
					<p>分类选择</p>
					<span v-for="item in productDetails.skuList" :key="item.id" @click="selectSku(item)"
						:class="{ selected: item.selected }">
						{{ item.skuName }}
					</span>
				</div>
				<!-- 规格集合 -->
				<div class="specificationContent" v-if="
		    productDetails &&
		    productDetails.specList &&
		    productDetails.specList.length > 0
		  ">
					<p>规格</p>
					<span v-for="item in productDetails.specList" :key="item.id" @click="selectSpec(item)"
						:class="{ selected: item.selected }">
						{{ item.specName }}
					</span>
				</div>

				<!-- 数量 -->
				<div class="numbersContent">
					<p>数量</p>
					<div class="numbersContentJJ">
						<i class="iconfont icon-jianhao" @click="decreaseQuantity"></i>
						<span>{{ quantity }}</span>
						<i class="iconfont icon-jiahao" @click="increaseQuantity"></i>
					</div>
				</div>
				<!-- 弹窗中购买按钮 -->
				<button @click="orderPayment">购买</button>
			</div>
		</uni-popup>
	</view>
</template>

<script lang="ts">
	import {
		defineComponent,
		ref, reactive,
		onMounted,
		computed
	} from "@vue/composition-api";
	import { onLoad } from '@dcloudio/uni-app';
	import { t } from "../../../utils/i18n";
	import uniPopup from "../../../components/uni-components/uni-popup/components/uni-popup/uni-popup.vue";
	import { customNavigateTo, customRedirectTo } from "utils/customNavigate";
	import {
		resClickBuy,
		resDeliveryAddress,
		resNewQualityInspection,
		resProductDetails,
	} from "utils/ArequestHelper";

	export default defineComponent({
		components: {
			uniPopup,
		},

		setup() {
			const showQ = ref(false)
			const isCustomerServiceVisible = ref(true);
			const popupRef = ref(null);
			const statusBarHeight = ref();
			const defaultAddressId = ref(); // 用于存储默认地址的 id

			const popShow = ref(false)
			const height = uni.getSystemInfoSync();
			statusBarHeight.value = height.statusBarHeight;

			const iconStyle = computed(() => {
				return {
					color: "#ffffff",
					fontSize: "20px",
					position: "absolute",
					top: `${statusBarHeight.value}px`, // 动态设置 top 值
					left: "15px",
					background: "#424D57",
					borderRadius: "50%",
					padding: "3px",
					zIndex: 9999,
				};
			});

			const productDetails = reactive({ id: '', specList: [] });
			const InspectionList = ref(null);

			onLoad((option) => {
				fetchProductDetails(option.id);
			})
			/**
			 * 获取商品详情
			 */
			const fetchProductDetails = (id : string) => {
				resProductDetails(id).then((response) => {
					const data = response.data.data;
					if (data) {
						console.log("---------------------商品详情------------------------------")
						console.log(data)
						Object.assign(productDetails, data)
						const filterData = data.skuList.filter(item => {
							if (item.id === data.showedSkuId) {
								item.selected = true
							}
							return item.id === data.showedSkuId
						})
						selectedSku.value = filterData[0]
					}
					if (data.shelveArea === 1) {
						fetchInspectionList(data.id);
					}
				})
			};
			// 商品最新质检
			const fetchInspectionList = (productId : number) => {
				resNewQualityInspection(productId)
					.then((response) => {
						InspectionList.value = response.data.data;
						console.log("商品最新质检", InspectionList.value);
					})
					.catch((error) => {
						// todo 这个接口也查询失败
						console.error("商品最新质检失败:", error);
					});
			};

			const payment = ref(false)

			const orderPayment = () => {
				if (quantity.value > selectedSku.value.limitAmount) {
					uni.showToast({
						title: "您购买的数量超过库存了",
						icon: "none",
						duration: 2000,
					});
					quantity.value = 1;
					return;
				}
				closePopup();

				let specs = productDetails.specList.filter(res => res.selected)
				let specName = '';
				if (specs.length > 0) specName = specs[0].specName

				uni.navigateTo({
					url: `/pages/goShopping/orderPayment/index?productId=${productDetails.id}&typeId=${selectedSku.value.id}&count=${quantity.value}&specName=${specName}`,
				});
			};


			/**
			 * 跳转到为您服务的人页面
			 */
			function toServicePerson() {
				uni.navigateTo({
					url: `/pages/goShopping/servicePerson/index?id=${productDetails.merchantId}`,
				});
			}

			const goBack = () => {
				uni.navigateBack({
					delta: 1, // 返回的页面数，如果 delta 大于现有页面数，则返回到首页
				});
			};

			function service() {
				uni.showModal({
					title: '提示',
					content: '是否拨打客服电话',
					success: function (res) {
						if (res.confirm) {
							uni.makePhoneCall({
								phoneNumber: productDetails.customerServicePhone, // 电话号码
							});
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			}

			const selectSpecification = () => {
				// isCustomerServiceVisible.value = false;
				// 首先检查 popupRef 是否存在并且有 open 方法
				if (popupRef.value && typeof popupRef.value.open === "function") {
					popupRef.value.open();
				}
			};


			// 关闭弹窗
			const closePopup = () => {
				popShow.value = false
				// isCustomerServiceVisible.value = true;
				popupRef.value.close();
			};
			const openPurchasePopup = () => {
				console.log("商品详情页购买按钮");
				popShow.value = true

				// isCustomerServiceVisible.value = false;

				if (popupRef.value && popupRef.value.open) {
					popupRef.value.open();
				}
			};
			// sku选购
			const selectedSku = ref({});
			const selectSku = (item : any) => {
				productDetails.skuList.forEach((sku : any) => {
					sku.selected = false;
				});
				item.selected = true;
				selectedSku.value = item;
				console.log("选中的 SKU:", item);
				console.log("选中的 selectedSku.value:", selectedSku.value.id);
			};
			// 规格选购
			function selectSpec(data : any) {
				console.log(data)
				productDetails.specList.forEach(res => {
					res.selected = false
					if (data.id === res.id) {
						res.selected = true
					}
				})
				productDetails.specList.splice(0, 0)
			}
			// 选择认购数量
			const quantity = ref(1); // 初始化数量为1
			const increaseQuantity = () => {
				quantity.value += 1;
			};

			const decreaseQuantity = () => {
				if (quantity.value > 1) {
					quantity.value -= 1;
				}
			};
			// 订单支付详情页面
			const firstLoginUser = uni.getStorageSync("firstLoginUser");
			// 弹窗选择商品之后点击商品购买
			const clickBuy = (data : any) => {
				console.log("用户信息", firstLoginUser);
				console.log("顶顶顶顶", data);
				return resClickBuy(firstLoginUser.access_token, data); // 返回 Promise
			};
			// 进页面获取用户id，并获取默认地址的id方法
			const getAddressId = async () => {
				console.log("打印用户id", firstLoginUser.user_id);
				// 用该用户的id，取到目前默认地址，如果没有跳转页面让添加地址
				const res = await resDeliveryAddress(
					firstLoginUser.user_id,
				);
				const AddressLists = res.data.data; // 过滤出ifDefault是1的地址id
				console.log("打印这个用户id下的地址", AddressLists);

				// 遍历AddressLists，找到ifDefault为1的对象并打印其id
				const defaultAddress = AddressLists.find(
					(address : any) => address.ifDefault == 1
				);
				if (defaultAddress) {
					console.log("==============默认地址id:", defaultAddress.id);
					defaultAddressId.value = defaultAddress.id;
				} else {
					console.log("没有找到默认地址");
					// 销毁原购买页面
					customRedirectTo({
						url: "/pages/user-card/more-settings/deliveryAddress/index",
					});
				}
			};
			function downFile(data : any) {
				uni.navigateTo({
					url: "/pages/goShopping/file?url=" + data.url
				})
			}



			return {
				t,
				selectSpecification,
				closePopup,
				popupRef,
				popShow,
				openPurchasePopup,
				customNavigateTo,
				orderPayment,
				statusBarHeight,
				goBack,
				productDetails,
				InspectionList,
				selectSku,
				selectedSku,
				quantity, // 返回数量状态
				increaseQuantity, // 返回增加数量的方法
				decreaseQuantity, // 返回减少数量的方法
				iconStyle,
				isCustomerServiceVisible,
				toServicePerson,
				defaultAddressId,
				selectSpec,
				service,
				showQ,
				downFile
			};
		},
	});

	function getStorageInfo(arg0 : string) {
		throw new Error("Function not implemented.");
	}
</script>

<style lang="scss" scoped>
	* {
		margin: 0;
		padding: 0;
	}

	.content {
		position: relative;
	}

	.wrapper {
		height: 100vh;
		padding-bottom: 100rpx;
		box-sizing: border-box;
		/* 根据“客服/购买”部分的高度调整 */
		// overflow-y: auto; /* 确保内容可以滚动 */
	}

	.no-scroll {
		overflow: hidden;
		position: fixed;
		width: 100%;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
	}

	.swiper {
		width: 100%;
		height: 750rpx; // 或者根据实际需求调整高度

		/* #ifdef APP-PLUS */
		height: 400px; // 在APP-PLUS中设置照片高度为400px
		/* #endif */

		swiper-item {
			display: flex;
			justify-content: center;
			align-items: center;

			image {
				object-fit: cover; // 使图片填充其容器
				width: 100%;
				height: 100%;
			}
		}
	}

	// 商品信息
	.CommodityInformation {
		padding: 5%;
		border-bottom: 1px solid #f0f0f0;

		.CommodityInformationOne {
			height: 30px;
			display: flex;
			justify-content: space-between;
			align-items: center;
			white-space: nowrap;

			span {
				color: #eb4e24;
				font-weight: bold;
				font-size: 36rpx;
				font-weight: 400;
			}

			.CommodityInformationThree {
				width: 235rpx;
				height: 48rpx;
				background-color: #ea471b;
				color: #ffffff;
				border-radius: 24rpx;
				margin-right: 186rpx;
				display: flex;
				justify-content: center;
				align-items: center;
			}

			.CommodityInformationFour {
				font-size: 12px;
				color: #ababab;
				background-color: transparent;
			}
		}

		.CommodityInformationTwo {
			padding: 2%;

			span {
				color: #ea471b;
				padding: 1px 4px;
				border: 1px solid #ff2f2f;
				margin-right: 5px;
				border-radius: 5px;
				font-size: 24rpx;
			}
		}

		p {
			color: #000000;
			font-weight: 400;
			font-size: 32rpx;
			font-family: MicrosoftYaHei;
		}
	}

	// 保障
	.guarantee {
		padding: 2% 5%;
		border-bottom: 6px solid #f0f0f0;

		.guaranteeTop {
			display: flex;
			// justify-content: space-between;
			align-items: center;
			white-space: nowrap;
			margin-top: 2%;

			span+span+span {
				width: 50%;
			}
		}

		.guaranteeCenter {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin: 5% 0;

			// white-space: nowrap;
			span+span {
				font-weight: 400;
				font-size: 26rpx;
				color: #333333;
			}

			.select-text {
				flex: 1;
			}

			.icon-right {
				width: 26rpx;
				height: 26rpx;
			}
		}

		.guaranteeBottom {
			display: flex;
			// justify-content: space-between;
			align-items: center;
			margin-bottom: 2%;
			white-space: nowrap;
		}

		.guaranteeBottom span:last-child {
			font-weight: 400;
			font-size: 26rpx;
			color: #333333;
		}

		.textBlack {
			color: #808080;
			font-size: 26rpx;
			font-weight: 400;
			width: 15%;
		}
	}

	.textBlackSpan {
		font-weight: 400;
		font-size: 26rpx;
		color: #333333;
	}

	// 质检结果表格
	.qualityInspectionRes {
		display: flex;
		justify-content: center;
		padding: 3% 5%;
		border-bottom: 1px solid #ededed;

		table {
			border-collapse: collapse;
			width: 100%;
			border: 1px solid #d4d3d3;
		}

		td {
			border: 1px solid #d4d3d3;
			padding: 15px;
			text-align: center;
			width: 30%;
			font-size: 26rpx;
		}

		tr:first-child td {
			color: #333333;
			font-size: 26rpx;
			background-color: #f9f9f9;
		}

		tr:nth-child(2) td:nth-child(3) {
			color: #ff2f2f;
		}
	}

	// 国标文档查看
	.documentView {
		display: flex;
		flex-direction: column;
		justify-content: center;
		padding: 3% 5%;
		border-bottom: 1px solid #f0f0f0;

		span {
			font-weight: bold;
			font-size: 26rpx;
		}

		span:first-child {
			margin-bottom: 3%;
		}
	}

	.viewSpan {
		color: #008cea;
	}

	// 商品详情
	.productIntroduction {
		display: flex;
		flex-direction: column;
		justify-content: center;
		gap: 0;

		.tilte {}

		span {
			font-weight: bold;
			line-height: 40px;
		}

		image {
			width: 100%;
			display: inline-block;
			vertical-align: middle;
			border: none;
		}
	}

	// 客服/购买
	.customerServicePurchase {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		padding: 20rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		height: 110rpx;
		box-sizing: border-box;
		background-color: #fff;
		/* 添加背景色以避免内容被遮挡 */
		box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.1);
		/* 添加阴影效果 */
		z-index: 90;
		/* 确保在最顶层 */
		font-size: 30rpx;

		.left {
			width: 200rpx;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			color: #FF960E;
		}

		.icon-kefu {
			font-size: 43rpx;
			color: rgba(255, 150, 14, 1);
		}

		.btn-box {
			display: flex;

			.btn-1,
			.btn-2 {
				display: flex;
				align-items: center;
				justify-content: center;
				height: 68rpx;
				background-color: transparent;
				color: #808080;
				width: 240rpx;
				border-radius: 34rpx;
			}
			.btn-1 {
				background-color: #FF0F23;
				color: #ffffff;
				margin-left: 20rpx;
			}

			.btn-2 {
				background-color: #0E82F0;
				color: #ffffff;
				margin-left: 20rpx;
			}
		}

	}

	// 弹窗样式
	.popopContent {

		padding: 30rpx;

		.productContent {
			display: flex;
			justify-content: space-between;

			image {
				width: 220rpx;
				height: 220rpx;
				border-radius: 5px;
			}

			.oldPrice {
				font-weight: 400;
				font-size: 28rpx;
				color: #808080;
				line-height: 26rpx;
			}

			.newPrice {
				display: inline-block;
				margin-top: 10rpx;
				background: #FF2F2F;
				border-radius: 24rpx;
				font-weight: 400;
				font-size: 30rpx;
				color: #FFFFFF;
				line-height: 33rpx;
				padding: 10rpx 19rpx;
			}
		}

		.specificationContent {
			display: flex;
			flex-direction: column;

			span {
				color: rgba(99, 99, 99, 1);
				padding: 1% 5%;
				background-color: #f0f0f0;
				width: 50%;
				text-align: center;
				margin: 1% 0;
				border-radius: 20px;
				font-size: 28rpx;

				&.selected {
					background-color: #d1e8ff; // 浅蓝色背景
					font-weight: 400;
					color: #008CEA;
				}
			}

			p {
				color: #000000;
				font-weight: bold;
				margin: 1% 0;
			}
		}

		// 数量
		.numbersContent {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-top: 5%;

			p {
				color: #000000;
				font-weight: bold;
				margin: 1% 0;
			}

			.numbersContentJJ {
				display: flex;
				// justify-content: end;
				align-items: center;
				width: 40%;

				.icon-jianhao {
					font-size: 54rpx;
				}

				.icon-jiahao {
					font-size: 62rpx;
				}

				span {
					width: 100rpx;
					display: flex;
					align-items: center;
					justify-content: center;
				}
			}
		}

		button {
			margin-top: 5%;
			background-color: #1e70e2;
			color: #ffffff;
			font-weight: bold;
			border-radius: 20px;
			font-size: 15px;
			letter-spacing: 20px;
		}
	}

	.down {
		font-size: 24rpx;
		line-height: 40rpx;
		color: #409EFF;
	}
</style>