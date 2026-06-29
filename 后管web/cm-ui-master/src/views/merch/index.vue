<template>
	<div class="main">
		<div class="left">
			<Company class="left-top" :company="companyData.data"></Company>
			<ProductSort class="left-bottom" :product="companyData.product" :score="companyData?.data?.displayQuantityFormula || 0"></ProductSort>
		</div>
		<div class="right">
			<Statistics class="right-top" :statistic="companyData.statistic" ></Statistics>
			<UserSort class="right-bottom" :score="companyData.score"></UserSort>
		</div>
	</div>
</template>

<script lang="ts" setup>
	import Company from "./components/Company.vue";
	import ProductSort from "./components/ProductSort.vue";
	import UserSort from "./components/UserSort.vue";
	import Statistics from "./components/Statistics.vue";
	
	import {reactive,onMounted} from 'vue'
	import {company,statistics,benefit,score} from "/@/api/merch/statistics";
	 
	const companyData = reactive({data:null,product:null,statistic:null,score:null});
	
	onMounted(()=>{
		getCompany()
		getBenefit()
		getStatistics()
		getScore()
	})
	async function getStatistics(){
		const result = await statistics();
		if(result.code === 0){
			companyData.product = result.data
		}
	}
	async function getCompany(){
		const result =await company()
		if(result.code === 0){
			companyData.data = result.data
		}
	}

	async function getBenefit(){
		const result = await benefit();
		if(result.code === 0){
			companyData.statistic = result.data
		}
	}
	async function getScore(){
		const result = await score();
		if(result.code === 0){
			companyData.score = result.data
		}
	}
</script>

<style lang="scss" scoped>
	.main{
		// position: absolute;
		// top: 0;
		// left: 0;
		// right: 0;
		// bottom: 0;
		// height: 100%;
		display: flex;
		padding: 10px;
		
		.left{
			flex:1;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			margin-right: 10px;
			.left-top{
				height: 236px;
				margin: 0 0 10px 0;
			}
			.left-bottom{
				flex: 1;
			}
		}
		.right{
			flex:2;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			.right-top{
				margin: 0 0 10px 0;
				height: 236px;
			}
			.right-bottom{
				flex: 1;
			}
		}
	}
</style>