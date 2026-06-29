<template>
	<el-card>
		<div class="content">
			<div class="header">
				<div>系统计算出您的下月免费展现量为</div>
				<div>{{score}}（次）</div>
			</div>
			<div class="table">
				<el-table :data="product" height="440" border style="width: 100%">
					<el-table-column prop="productName" label="名称" show-overflow-tooltip></el-table-column>
					<el-table-column prop="todaySalesVolume" label="今日销量" ></el-table-column>
					<el-table-column prop="weekSalesVolume" label="本周销量" ></el-table-column>
				</el-table>
			</div>
		</div>
	</el-card>
</template>

<script lang="ts" setup>
	
	const props = defineProps({
		product: {type: Array, default: ()=>[]},
		score:{type: String, default: ""}
	})
</script>

<style lang="scss" scoped>
	.header {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		height: 80px;
		background-color: #F2F6FC;
		font-size: 16px;
		// color: #fff;
	}
	.table{
		margin-top: 20px;
		width: 100%;
	}
</style>