<template>
	<div class="content" v-if="statistic">
		<div class="left">
			<el-card>
				<div class="static-box">
					<!-- <img class="icon" src="" alt="" /> -->
					<span class="icon">今</span>
					<div class="text">
						<div class="num">￥{{statistic.todayIncome || 0}}</div>
						<div class="label">今日收益</div>
					</div>
				</div>
			</el-card>
			<el-card>
				<div class="static-box">
					<!-- <img class="icon" src="" alt="" /> -->
					<span class="icon">月</span>
					<div class="text">
						<div class="num">￥{{statistic.monthIncome || 0}}</div>
						<div class="label">本月收益</div>
					</div>
				</div>
			</el-card>
		</div>
		<div class="right">
			<el-card>
				<div class="static-box">
					<!-- <img class="icon" src="" alt="" /> -->
					<span class="icon">今</span>
					<div class="text">
						<div class="num">{{statistic.todayOrderCount || 0}}</div>
						<div class="label">今日支付订单数</div>
					</div>
				</div>
			</el-card>
			<el-card>
				<div class="static-box">
					<!-- <img class="icon" src="" alt="" /> -->
					<span class="icon">月</span>
					<div class="text">
						<div class="num">{{statistic.monthOrderCount || 0}}</div>
						<div class="label">本月支付订单数</div>
					</div>
				</div>
			</el-card>
		</div>
	</div>
	
	
</template>

<script lang="ts" setup>
	const props = defineProps({
		statistic: {type: Object, default: ()=>{}},
	})
	
</script>

<style lang="scss" scoped>
	.content{
		display: flex;
		justify-content: space-between;
		height: 100%;
		.left{
			flex: 1;
			margin-right: 10px;
		}
		.right{
			flex: 1;
		}
		.left,.right{
			height: 100%;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
		}
		
		.static-box{
			height:70px;
			display: flex;
			padding-left: 90px;
			position: relative;
			.icon{
				width: 110px;
				height: 110px;
				position: absolute;
				top:-20px;
				left: -20px;
				background-color: #F2F6FC;
				font-size: 48px;
				color: #C0C4CC;
				display: flex;
				align-items: center;
				justify-content: center;
			}
			.text{
				flex: 1;
				display: flex;
				align-items: center;
				justify-content: center;
				flex-direction: column;
			}
			.num{
				font-size: 24px;
			}
			.label{
				font-size: 16px;
			}
		}
	}
</style>