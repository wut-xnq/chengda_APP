<!-- 个人认证页面 -->
<template>
	<div class="wrapper">
		<div class="perCer">
			<div class="perCers">
				<span>手机号*</span>
				<input type="text" disabled :value="companyData.phone" style="color: #808080;" />
			</div>
			<div class="perCers">
				<span>名字验证*</span>
				<input type="text" placeholder="请输入您名字最后一个字" v-model:value="companyData.nameLastChar" maxlength="1"/>
			</div>
			<div class="perCers">
				<span>公司名称*</span>

				<uni-data-select v-model="companyData.merchantId" :localdata="companyData.list" @change="handleChange"
					:clear="false" placeholder="请选择公司" class="uni-data-select"></uni-data-select>
			</div>
		</div>
		<view class="footer">
			<div class="addAddress" @click="personalCertificationSubmission()">提交</div>
		</view>
	</div>
</template>

<script lang="ts">
	import NavBar from '../../../../components/NavBar.vue';
	import { t } from '../../../../utils/i18n';
	import { defineComponent, ref, reactive } from '@vue/composition-api';

	import { identApply,identResult } from 'utils/request/user';
	import { getCompany } from 'utils/request/company';

	export default defineComponent({
		components: {
			NavBar
		},
		setup() {
			const companyData = reactive({
				merchantId: '',
				phone: '',
				nameLastChar: '',
				list: []
			})
			const user = uni.getStorageSync("firstLoginUser");
			
			console.log(user)
			companyData.phone = user.user_info.phone
			
			function getidentResult(){
				identResult({userId:user.user_id}).then(res=>{
					if(res.data.code === 1) throw new Error(res.data.msg)
					companyData.merchantId = res.data.data.merchantId
					companyData.nameLastChar = res.data.data.nameLastChar
				})
			}
			getidentResult()
			// 获取企业
			async function company() {
				try {
					await getCompany().then(res => {
						if (res.data.data === 1) {
							throw new Error("获取企业列表失败")
						} else {
							console.log(res)
							companyData.list = res.data.data.map((i) => {
								return { value: i.id, text: i.merchantName }
							})
						}
					})
				} catch (err) {
					uni.showToast({ title: err.message, icon: 'none' })
				}

			}
			company()
			// 方法定义
			const handleChange = (e : any) => {
				console.log('e:', e);
			};

			const personalCertificationSubmission = async () => {
				// 提交逻辑
				try {
					console.log(companyData.merchantId)
					if (!companyData.nameLastChar) throw new Error("请输入您名字最后一个字");
					if (!companyData.merchantId) throw new Error("请选择公司");
					await identApply(companyData).then(res => {
						let result = res?.data;
						console.log(res)
						if (result.code === 1) {
							throw new Error(result.msg);
						} else {
							uni.navigateBack()
							uni.showToast({ title: result.msg, icon: 'none' })
						}
					})
				} catch (err) {
					console.log(err)
					uni.showToast({ title: err.message, icon: 'none' })
				}


			}

			return {
				companyData,
				handleChange,
				t,
				personalCertificationSubmission,
			};
		},
	});
</script>

<style>
	page {
		background: #F6F7F9;
	}
</style>
<style lang="scss" scoped>
	@import '../../../styles/common.scss';

	.perCer {
		align-items: center;
		margin-top: 10rpx;
		padding: 0 30rpx;
		background-color: rgb(255, 255, 255)
	}

	.perCers {
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 45px;

		span {
			font-weight: 400;
			width: 30%;
			white-space: nowrap;
		}

		input {
			width: 100%;
			text-align: right;
			border: none;
			font-size: 28rpx;
		}

		input::placeholder {
			color: #ccc;
		}
	}
	.perCers:not(:nth-child(1)){
		border-top: 1px solid #E5E5E5;
	}

	::v-deep .uni-data-select {
		width: 100%;
		height: 100%;
		.uni-select {
			text-align: right;
			border: none !important;
			border-bottom: none !important;
			.uni-select__input-text {
				font-size: 28rpx;
			}
			.uni-icons {
				font-size: 35rpx !important;
			}
		}

	}

	// 原样式
	// .addAddress {
	// 	background-color: #87CEEB;
	// 	border-radius: 20px;
	// 	height: 45px;
	// 	line-height: 45px;
	// 	text-align: center;
	// 	width: 90%;
	// 	margin: 20px auto;
	// 	color: #ffffff;
	// }
	.footer{
		position: fixed;
		bottom: 0;
		left:0;
		right: 0;
		padding: 10rpx 30rpx 20rpx 30rpx;
		background-color: #ffffff;
		// 置于底部的位置
		.addAddress {
			background-color: #008CEA;
			border-radius: 40rpx;
			height: 80rpx;
			line-height: 80rpx;
			text-align: center;
			color: #ffffff;
		}
	}
	
</style>