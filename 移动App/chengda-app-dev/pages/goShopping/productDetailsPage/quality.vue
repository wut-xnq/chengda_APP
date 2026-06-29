<template>
	<view v-if="data" class="main">
		<view class="title">{{data.fileTitle}}</view>
		<view class="subTitle">
			<view>质检公司：{{data.agencyName}}</view>
			<view>质检时间：{{data.testTime}}</view>
		</view>
		<view class="content">
			{{data.content}}
		</view>
		<view class="file">
			<view v-for="(item,index) in data.fileList">
				<view class="down" @click="downFile(item)">附件{{index+1}}：{{item.original}}</view>
			</view>
		</view>

		<view class="image">
			<view v-for="item in data.verifiedProduct">
				<img style="width:100%;" :src="item.productPics"></img>
			</view>
		</view>
	</view>
</template>

<script lang="ts" setup>
	import { onLoad } from "@dcloudio/uni-app";
	import { reactive } from "@vue/composition-api";
	import { quality } from "utils/request/product"

	const data = reactive({
		"testTime": null,
		"fileTitle": "震惊一万年",
		"content": "22222222保质保量",
		"createTime": "2024-11-07 15:50:58",
		"publishDate": "2024-11-14",
		"updateBy": "duhan",
		"testProductIds": "1854050918333575170",
		"testMerchantIds": "1853985120818720770",
		"relationFileIds": "",
		"agencyName": "震惊一万年",
		"remarks": "通过",
		"merchantNames": [
			"三只羊贸易"
		],
		"verifiedProduct": [
			{
				"id": "1854050918333575170",
				"productName": "逃离地球美式蝴蝶卫衣女秋季连帽衫2024新款爆款国潮宽松慵懒外套",
				"productPics": "http://47.108.105.25:9000/default-admin-bucket/b84a5226f26841eba2f637a2cad932e5.jpg"
			}
		],
		"fileList": null
	})

	onLoad((option : any) => {
		console.log(option)
		getQuality(option.id)
	})

	function getQuality(id : string) {
		quality({ id: id }).then((res : any) => {
			console.log(res.data.code === 0)
			if (res.data.code === 0) {
				Object.assign(data, res.data.data)
			}
			console.log(data)
		})
	}
	function downFile(data:any){
		console.log(111)
		uni.navigateTo({
			url:"/pages/goShopping/file?url="+data.url
		})
		// uni.downloadFile({
		// 	url: data.url, //仅为示例，并非真实的资源
		// 	success: (res) => {
		// 		if (res.statusCode === 200) {
		// 			console.log('下载成功');
		// 			uni.showToast({
		// 				title:"下载成功"
		// 			})
		// 		}
		// 	},
		// 	fail(err) {
		// 		uni.showToast({
		// 			title:"下载失败"
		// 		})
		// 		console.log(err)
		// 	}
		// });
	}
</script>

<style>
	page {
		background-color: #F6F7F9;
	}
</style>
<style lang="scss" scoped>

	.main {
		padding: 30rpx;
		margin: 10rpx 0;
		background: #fff;
		letter-spacing: 2rpx;
	}

	.title {
		font-size: 38rpx;
		line-height: 60rpx;
		color:#303133;
		font-weight: bold;
	}

	.subTitle {
		font-size: 28rpx;
		line-height: 60rpx;
		color:#606266;
		font-weight: bold;
		// display: flex;
		// align-items: center;
		// justify-content: space-between;
	}

	.content {
		font-size: 26rpx;
		line-height: 46rpx;
		color:#606266;
	}
	.image{
		margin-top: 30rpx;
	}
	.down{
		font-size: 26rpx;
		line-height: 46rpx;
		color:#409EFF;
	}
</style>