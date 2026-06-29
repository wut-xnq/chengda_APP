<!-- 商品详情页面 -->
<template>
	<div class="wrapper">
		<NavBar :title="t('productDetailsPage')"></NavBar>
		<swiper class="swiper" circular  :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000">
			<swiper-item>
				<image src="https://inews.gtimg.com/om_bt/OGlQWfsaAoKkuCcMZ2o9IVEPqd-72DQy5EAN02XBHUwfYAA/641" mode=""></image>
			</swiper-item>
			<swiper-item>
				<image src="https://inews.gtimg.com/news_bt/OIPr9G8LrCpP4K3cdWrmvalN2p2YWWB3URkibvS38awOEAA/641" mode=""></image>
			</swiper-item>
			<swiper-item>
				<image src="https://inews.gtimg.com/om_bt/Ov57JiYAMdKYk7aVFb2l1zHEvtrLVKe7MMeEaWOQiKYpYAA/641" mode=""></image>
			</swiper-item>
		</swiper>
		<!-- 商品信息 -->
		<div class="CommodityInformation">
			<div class="CommodityInformationOne">
				<span>￥39.99</span>
				<span>到手价￥39.99</span>
				<!-- <span>152人付款</span> -->
			</div>
			<div class="CommodityInformationTwo">
				<!-- <span>9折</span>
				<span>优惠</span>
				<span>安徽合肥有限公司</span> -->
			</div>
			<p>HUAWEI笔记本电脑国产麒麟芯片20年自研生产白领高层专用轻薄电脑</p>
		</div>
		<!-- 保障 -->
		<div class="guarantee">
			<!-- 上 -->
			<div class="guaranteeTop">
				<span class="textBlack">保障</span>
				<!-- <span>
					<uni-icons type="wallet-filled" :size="20"></uni-icons>
					<span>极速退款</span>
				</span> -->
				<span>
					<uni-icons type="loop" :size="20"></uni-icons>
					<span>7天无理由退货</span>
				</span>
				<uni-icons type="right" :size="20"></uni-icons>
			</div>
			<!-- 中 -->
			<div class="guaranteeCenter" @click="specificationSelection">
				<span class="textBlack">已选</span>
				<span>X系列-10骨全自动-黑胶轻语-深灰色-折叠...</span>
				<uni-icons type="right" :size="20"></uni-icons>
			</div>
			<!-- 下 -->
			<!-- <div class="guaranteeBottom">
				<span class="textBlack">发货</span>
				<span>48小时内发货，预计3天后到达目的地 | 包邮</span>
			</div> -->
		</div>
		<!-- 质检结果 -->
		<div class="qualityInspectionRes">
			<table>
			    <tr>
			        <td>质检单位</td>
			        <td>质检时间</td>
			        <td>结果</td>
			    </tr>
			    <tr>
			        <td>北京第三电子产品质检检测中心</td>
			        <td>2024-08-08</td>
			        <td>该商品符合国家标准</td>
			    </tr>
			</table>
		</div>
		<!-- 国标文档 -->
		<div class="documentView">
			<span>国标文档（可查看）平台进行管理</span>
		</div>
		<!-- 商品详情 -->
		<div class="productIntroduction">
			<span>商品详情</span>
			<image src="https://inews.gtimg.com/om_bt/Ov57JiYAMdKYk7aVFb2l1zHEvtrLVKe7MMeEaWOQiKYpYAA/641" mode=""></image>
			<image src="https://inews.gtimg.com/om_bt/Ov57JiYAMdKYk7aVFb2l1zHEvtrLVKe7MMeEaWOQiKYpYAA/641" mode=""></image>
			<image src="https://inews.gtimg.com/om_bt/Ov57JiYAMdKYk7aVFb2l1zHEvtrLVKe7MMeEaWOQiKYpYAA/641" mode=""></image>
			<image src="https://inews.gtimg.com/om_bt/Ov57JiYAMdKYk7aVFb2l1zHEvtrLVKe7MMeEaWOQiKYpYAA/641" mode=""></image>
		</div>
		<!-- 客服/购买 -->
		<div class="customerServicePurchase">
			<div class="customerService">
				<uni-icons type="person" :size="25" color="#14B877"></uni-icons>
				<span>客服</span>
			</div>
			<button>为您服务的人</button>
			<button>购买</button>
		</div>
		<!-- 弹窗规格选择 -->
		<uni-popup ref="popup" type="bottom" background-color="#fff">底部弹出 Popup</uni-popup>
</div>
</template>

<script lang="ts">
import { defineComponent , ref } from '@vue/composition-api';
import { t } from '../../../utils/i18n';
import NavBar from '../../../components/NavBar.vue'
import uniPopup from "../../../uni_modules/uni-popup/components/uni-popup/uni-popup.vue"

export default defineComponent({
	components:{
		NavBar,
		uniPopup
	},
	
	setup(){
		const popupRef = ref(null);
		// 规格选购
		const specificationSelection = () => {
		      console.log('规格选购');
		      if (popupRef.value?.open) {
		        popupRef.value.open();
		      }
		};
		// 关闭弹窗
		    const closePopup = () => {
		      if (popupRef.value?.close) {
		        popupRef.value.close();
		      }
		    };
		return {
			t,
			specificationSelection,
			closePopup,
			popupRef
		}
	}
})
</script>

<style lang="scss">
* {
	margin: 0;
	padding: 0;
}
  .swiper {
    width: 100%;
    height: 300px; // 或者根据实际需求调整高度
    
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
	  border-bottom: 1px solid #F0F0F0;
	  .CommodityInformationOne{
		  height: 30px;
		  display: flex;
		  justify-content: space-between;
		  align-items: end;
		  white-space: nowrap;
		  span {
			  color: #EB4E24;
			  font-weight: bold;
			  font-size: 20px;
		  }
		  span+span {
			  color:#ffffff;
			  background-color: #EA471B;
			  padding: 2px 20px;
			  border-radius: 15px;
			  font-weight: 400;
		  }
		  span+span+span {
			  font-size: 12px;
			  color: #ABABAB;
			  background-color: transparent;
		  }
	  }
	  .CommodityInformationTwo {
		  padding: 2%;
		  span {
			color: #EA471B;
			padding: 1px 4px;
			border: 1px solid #EA471B;
			margin-right: 5px;
		  }
	  }
	  p {
		  color: #000000;
		  font-weight: bold;
		  font-size: 17px;
	  }
  }
  // 保障
  .guarantee {
	  padding: 2% 5%;
	  border-bottom: 6px solid #F0F0F0;
	  .guaranteeTop {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		  white-space: nowrap;
		  span+span+span {
			  width: 50%;
		  }
	  }
	  .guaranteeCenter {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		  margin: 5% 0;
		  white-space: nowrap;
	  }
	  .guaranteeBottom {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		  margin-bottom: 2%;
		  white-space: nowrap;
	  }
	  .textBlack {
		  color: #000000;
		  font-weight: bold;
	  }
  }
  // 质检结果表格
  .qualityInspectionRes {
	  display: flex;
	  justify-content: center;
	  padding: 3% 5%;
	  border-bottom: 1px solid #F0F0F0;
	table {
			border-collapse: collapse;
			width: 100%;
			border: 1px solid #D4D3D3;
	  }
	td {
	        border: 1px solid #D4D3D3;
	        padding: 15px;
	        text-align: center;
			width: 30%;
	    }
	tr:first-child td {
	            background-color: #F7F3F3;
	    }
	tr:nth-child(2) td:nth-child(3) {
	            color: #FD3905;
	    }
  }
  // 国标文档查看
  .documentView {
	  display: flex;
	  flex-direction: column;
	  justify-content: center;
	  padding: 3% 5%;
	  border-bottom: 1px solid #F0F0F0;
	  span {
		  font-weight: bold;
	  }
	  span:first-child {
		  margin-bottom: 3%;
	  }
  }
  // 商品详情
  .productIntroduction {
		padding: 3% 5%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		gap: 0;
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
	  padding: 3% 8% 8% 8%;
	  display: flex;
	  justify-content: space-between;
	  align-items: end;
	  height: 40px;
	  .customerService {
		  display: flex;
		  flex-direction: column;
		  justify-content: center;
		  align-items: center;
		  span {
			  font-weight: bold;
			  font-size: 15px;
		  }
	  }
	  button {
		  display: flex;
		  align-items: center;
		  justify-content: center;
		  height: 40px;
		  background-color: transparent;
		  color: #1E70E2;
		  border: 1px solid #1E70E2;
		  width: 40%;
		  border-radius: 20px;
	  }
	  button+button {
		  background-color: #1E70E2;
		  color: #ffffff;
		  width: 40%;
	  }
  }
</style>