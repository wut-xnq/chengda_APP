<template>
	<div class="main">
		<el-card class="main-box">
			<div>
				<div class="header">
					<span class="title">员工积分列表</span>

					<el-link type="primary" :underline="false" @click="selelctAll">全选</el-link>
				</div>

				<div class="footer">
					<div class="user-box" v-for="(item,index) in companyData.score">
						<el-checkbox class="check" v-model="item.checked"></el-checkbox>
						<span class="tag" :class="`sort-${index+1}`">{{index+1}}</span>
						<!-- <img class="img" src="" alt="" /> -->
						<img class="img" src="/@/assets/user-default.png" alt="" />
						<div>
							<div class="name">{{item.name}}</div>
							<div class="score">积分：{{item.userScore}}</div>
						</div>
					</div>
					<div class="user-box"></div>
					<div class="user-box"></div>
					<div class="user-box"></div>
					<div class="user-box"></div>
					<div class="user-box"></div>
				</div>

				<el-button class="btn" type="primary" @click="save">去转化</el-button>
			</div>
		</el-card>
	</div>
</template>

<script lang="ts" setup>
	import { reactive, onMounted } from 'vue'
	import { score, trans } from "/@/api/merch/statistics";
	import { useMessage } from '/@/hooks/message';
import { ElMessageBox } from 'element-plus';

	const companyData = reactive({ score: [] });

	onMounted(() => {
		getScore()
	})
	async function getScore() {
		const result = await score();
		if (result.code === 0) {
			companyData.score = result.data
		}
	}
	function selelctAll() {
		if (!companyData.score) throw new Error("数据为空")
		let length = companyData.score.filter((res : any) => res.checked).length;

		companyData.score.forEach((res : any) => {
			res.checked = length < companyData.score.length ? true : false
		})
	}
	async function save() {
		try {
			ElMessageBox.confirm('此操作将申请积分转化, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(async () => {
				let ids : Array<string> = [];
				companyData.score.forEach((res : any) => {
					if (res.checked) {
						ids.push(res.userId)
					}
				})
				if (ids.length === 0) throw new Error("转化人员不能为空")
				const result = await trans(ids)
				useMessage().success(result.msg)
			})


		} catch (err : any) {
			useMessage().wraning(err.message || err.msg)
		}
	}
</script>

<style lang="scss" scoped>
	.main {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		height: 100%;
		display: flex;
		padding: 10px;

		.main-box {
			width: 100%;

			.header {
				display: flex;
				align-items: end;
				justify-content: space-between;
				line-height: 24px;

				.title {
					font-size: 24px;
					margin: 10px 40px 0 20px;
					padding: 0 10px;
					border-left: 4px solid #409EFF;
				}
			}

			.footer {
				margin-top: 30px;
				padding-left: 20px;
				display: flex;
				flex-wrap: wrap;
				justify-content: space-around;

				.user-box {
					display: flex;
					align-items: center;
					margin-bottom: 20px;
					width: 340px;

					.check {
						margin-right: 10px;
					}

					.tag {
						width: 20px;
						height: 20px;
						background-color: aquamarine;
						border-radius: 50%;
						display: flex;
						justify-content: center;
						align-items: center;
						margin-right: 10px;
						color: #fff;
					}

					.sort-1 {
						background-color: pink;
					}

					.img {
						width: 60px;
						height: 60px;
						border-radius: 50%;
						background-color: #ccc;
						margin-right: 10px;
					}

					.name {
						font-size: 16px;

					}

					.score {
						font-size: 14px;
					}
				}
			}
		}

		.btn {
			position: absolute;
			bottom: 40px;
			right: 40px;
		}
	}
</style>