<template>
	<div>
		<indexPage :isComp="true" :merchantId="data.merchantId"></indexPage>
	</div>
</template>
<script lang="ts" name="merchGoods" setup>
import { useUserInfo } from '/@/stores/userInfo';
const data = useUserInfo().userInfos;

const indexPage = defineAsyncComponent(() => import('/src/views/admin/product/goods/index.vue'));
</script>
