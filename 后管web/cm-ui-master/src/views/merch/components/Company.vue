<template>
	<el-card>
		<div class="content" v-if='company'>
			<div class="company">
				<img class="img" v-if="company.logo" :src="company.logo"/>
				<span class="img" v-else>商</span>
				<div>
					<div class="name">{{company.merchantName}}</div>
					<div class="score">积分：{{company.merchantScore || 0}}</div>
				</div>
			</div>
			<div>上次登录时间：{{company.lastLoginTime}}</div>
			<div>上次登录地点：{{company.lastLoginArea}}</div>
		</div>
	</el-card>
</template>

<script lang="ts" setup>
	const props = defineProps({
		company: {type: Object, default: ()=>{}},
	})
	
</script>

<style lang="scss" scoped>
	.content{
		padding:0 20px;
		line-height: 28px;
	}
	.company{
		display: flex;
		padding:20px 0;
		.img{
			width: 100px;
			height: 100px;
			border-radius: 50%;
			background-color: #F2F6FC;
			margin-right: 20px;
			font-size: 46px;
			display: flex;
			align-items: center;
			justify-content: center;
			color: #C0C4CC;
		}
		.name{
			font-size: 20px;
		}
		.score{
			font-size: 16px;
			line-height: 28px;
		}
	}
</style>