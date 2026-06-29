<template>
	<div class="main">
		<el-card class="main-box">
			<div class="content">
				<el-form class="form" label-width="180">
					<el-form-item label="优惠区抽成比例：">
						<el-input v-model="rule.yhRate" suffix-icon="el-icon-date">
							<template #append>%</template>
						</el-input>
					</el-form-item>
					<el-form-item label="正品区抽成比例：">
						<el-input v-model="rule.zpRate" >
							<template #append>%</template>
						</el-input>
					</el-form-item>
					<el-form-item label="国标区抽成比例：">
						<el-input v-model="rule.gbRate">
							<template #append>%</template>
						</el-input>
					</el-form-item>
					<el-form-item label="转化：">
						<el-input style="width: 100px;" v-model="rule.conversionMolecule"></el-input>
						积分转化
						<el-input style="width: 100px;" v-model="rule.conversionDenominator"></el-input>
						展现量
					</el-form-item>
				</el-form>
				<el-button type="primary" class="btn" @click="save">提交</el-button>
			</div>
		</el-card>
	</div>
</template>

<script lang="ts" setup>
	import { ElMessageBox } from 'element-plus';
	import { useMessage } from '/@/hooks/message';
	import { select,update } from '/@/api/admin/setting';
	
	const rule = reactive({})
	async function getData(){
		try{
			const result = await select()
			console.log("配置-------->",result)
			if(result.code === 1) throw new Error(result.msg)
			if(result.code === 0) Object.assign(rule,result.data[0]);
		}catch(err:any){
			console.log(err)
			useMessage().wraning(err.message || err.msg)
		}
	}
	getData()
	async function save() {
		try{
			await ElMessageBox.confirm('此操作将更新分润配置, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(async () => {
				const result = await update({id:rule.id,yhRate:rule.yhRate,zpRate:rule.zpRate,gbRate:rule.gbRate ,molecule:rule.conversionMolecule,denominator:rule.conversionDenominator })
				if(result.code === 1) throw new Error(result.msg)
				useMessage().success(result.msg)
			})
		}catch(err){
			useMessage().wraning(err.message || err.msg)
		}
		
	}
</script>

<style lang="scss" scoped>
	::v-deep .el-tabs__item {
		color: #303133;
	}

	::v-deep .is-active {
		background-color: transparent;
		color: #409EFF;
	}

	::v-deep .el-tabs__active-bar {
		background-color: #409EFF;
	}

	.main {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		padding: 10px;
		height: 100%;

		.main-box {
			height: 100%;

			.content {
				// display: flex;
				padding-top: 20px;
				.form{
					width: 500px;
				}
			}

			.btn {
				position: absolute;
				left: 40px;
				bottom: 40px;
			}
		}
	}
</style>