<!-- 商品选购弹出层 -->
<template>
	<div class="op">
		<uni-popup ref="popup" type="bottom">商品选购弹出层</uni-popup>
	</div>
</template>

<script lang="ts">
import { defineComponent } from "@vue/composition-api"
import uniPopup from "../../../uni_modules/uni_modules/uni-popup/components/uni-popup/uni-popup.vue"

export default defineComponent({
	components:{ uniPopup },
	
	setup(){
		
		return {
			
		}
	}
})
	
</script>

<style lang="scss" scoped>
	.op {
		background-color: skyblue;
		height: 200px;
		width: 500px;
	}
</style>