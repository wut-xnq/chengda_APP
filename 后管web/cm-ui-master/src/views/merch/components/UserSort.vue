<template>
	<el-card>
		<div class="content">
			<div class="header">
				<span>员工积分排行榜</span>
				<span class="more" @click="toMore">查看更多></span>
			</div>

			<div class="footer">
				<div class="user-box" v-for="(item,index) in score">
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

			</div>
		</div>
	</el-card>
</template>

<script lang="ts" setup>
	import router from '/@/router';
	
	const props = defineProps({
		score:{type: Array, default: ()=>[]}
	})
	function toMore() {
		router.push("/merch/trans/index")
	}
</script>

<style lang="scss" scoped>
	.content {
		.header {
			font-size: 18px;
			display: flex;
			justify-content: space-between;
			align-items: end;

			.more {
				font-size: 14px;
				cursor: pointer;
			}
		}

		.footer {
			margin-top: 30px;
			display: flex;
			flex-wrap: wrap;
			justify-content: space-around;

			.user-box {
				display: flex;
				align-items: center;
				margin-bottom: 20px;
				width: 300px;

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
				.sort-1{
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
</style>