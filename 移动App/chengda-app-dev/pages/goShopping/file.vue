<template>
	<view>
		<web-view :src="attachmentUrl"></web-view>
	</view>
</template>

<script lang="ts" setup>
	import { onLoad, onReachBottom } from '@dcloudio/uni-app';
	import { ref, reactive, onMounted } from '@vue/composition-api';
	const attachmentUrl = ref('')
	onLoad((options) => {
		// attachmentUrl.value = option.url

		let infoUrl = options.url;
		let imgtype = ['png', 'jpg', 'jpeg', 'pdf', 'PNG', 'JPG', 'JPEG', 'PDF'];
		const arr = infoUrl.split('.');
		if (imgtype.indexOf(arr[arr.length - 1]) != -1) {
			// 图片类型、pdf--直接webview
			attachmentUrl.value = options.url;
		} else {
			// 其他文档类型--微软
			attachmentUrl.value = 'http://view.officeapps.live.com/op/view.aspx?src=' + infoUrl;
		}
	})
</script>

<style>

</style>