<template>
	<!-- 样式兼容微信小程序  -->
	<div>
		<!-- <div
      class="nav-bar-wrapper"
      :style="{
        backgroundColor: backgroundColor || '#ffffff',
        backgroundImage: `url(${title})`,
        height: isWxApp ? '55px' : '40px',
        alignItems: isWxApp ? 'flex-end' : 'center',
      }"
    >
      <slot v-if="showLeft" name="left"></slot>
      <div v-else @tap="back">
        <Icon type="icon-zuojiantou" :size="22"></Icon>
      </div>
      <div class="title-container">
        <div class="title">{{ title }}</div>
        <div class="subTitle" v-if="subTitle">{{ subTitle }}</div>
        <slot name="icon"></slot>
      </div>
      <div>
        <slot name="right"></slot>
      </div>
    </div> -->

		<!-- 背景色 -->
		<div class="custom-nav">
			<div class="status_bar">
			</div>
			<div class="bar-bg">
				<div class="left" @tap="back">
					<Icon type="icon-zuojiantou" :size="22"></Icon>
				</div>
				<div class="title">
					<div class="title">{{ title }}</div>
					<div class="subTitle" v-if="subTitle">{{ subTitle }}</div>
				</div>
				<div class="right"></div>
			</div>
		</div>
		<!-- 导航栏占位 -->
		<div class="titleNview-placing" />
	</div>
</template>

<script lang="ts" setup>
	import { isWxApp } from '../../../utils'
	import Icon from '../../../components/Icon.vue'
	defineProps({
		title: {
			type: String,
			required: true,
		},
		subTitle: {
			type: String,
			default: '',
		},
		backgroundColor: {
			type: String,
			default: '',
		},
		showLeft: {
			type: Boolean,
		},
	})

	const back = () => {
		uni.navigateBack({
			delta: 1,
		})
	}
</script>

<style lang="scss" scoped>
	@import '../../../pages/styles/common.scss';


	.custom-nav {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 999;
		background: #fff;
		// background-image: url("~@/static/home/whole-bg.png");
		// background-size: 100%;
		// background-repeat: no-repeat;
	}

	.status_bar {
		height: var(--status-bar-height);
		width: 100%;
	}

	.bar-bg {
		height: 44px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 0 30rpx;
		box-sizing: border-box;
		image {
			width: 156upx;
			height: 51upx;
		}
		
		.right{
			width: 60rpx;
		}
	}
	
	.titleNview-placing {
	  height: var(--status-bar-height);
	  padding-top: 44px;
	  box-sizing: content-box;
	}


	.nav-bar-wrapper {
		display: flex;
		justify-content: space-between;
		padding: var(--status-bar-height) 10px 5px 10px;
		z-index: 9999;

		.title-container {
			position: absolute;
			left: 50%;
			transform: translateX(-50%);
			width: 230px;
			display: flex;
			justify-content: center;
			align-items: center;
		}

		.title {
			overflow: hidden;
			text-overflow: ellipsis;
			text-align: center;
			white-space: nowrap;
			font-weight: 500;
		}

		.subTitle {
			white-space: nowrap;
			font-weight: 500;
		}
	}
</style>