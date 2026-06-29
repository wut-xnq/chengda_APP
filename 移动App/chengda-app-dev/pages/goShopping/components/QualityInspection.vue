	<template>
	  <div>
	    <div class="textBoxs" v-for="item in items" :key="item.id">
	      <div class="textsTips">
	        <span>{{ item.fileTitle }}</span>
	        <span>{{ item.publishDate }}</span>
	      </div>
	      <!-- <p>{{ item.content }}</p> -->
	      <p>{{ item.fileTitle }}</p>
	    </div>
	  </div>
	</template>
	
	<script lang="ts">
	import { defineComponent, PropType, onMounted } from '@vue/composition-api';
	
	export default defineComponent({
	  props: {
	    items: {
	      type: Array as PropType<{ id: number; fileTitle: string; publishDate: string; content: string }[]>,
	      required: true,
	      default: () => [] // 添加默认值
	    }
	  },
	  setup(props) {
	    onMounted(() => {
	      console.log('Items received:', props.items);
	    });
	
	    return {};
	  }
	});
	</script>
	
	<style lang="scss" scoped>
	.textBoxs {
	  background-color: #ffffff;
	  padding: 15px;
	  border-radius: 10px;
	  box-shadow:
	    inset 0 0 5px rgba(0, 0, 0, 0.1),
	    inset 0 0 10px rgba(0, 0, 0, 0.05),
	    2px 2px 5px rgba(0, 0, 0, 0.15),
	    -2px -2px 5px rgba(255, 255, 255, 0.5);
	  margin-bottom: 20px;
	  p {
	    display: -webkit-box;
	    -webkit-box-orient: vertical;
	    -webkit-line-clamp: 3;
	    overflow: hidden;
	  }
	}
	.textsTips {
	  display: flex;
	  justify-content: space-between;
	  align-items: center;
	  margin-bottom: 10px;
	  span {
	    font-weight: 600;
	    color: #000000;
	  }
	  span + span {
	    color: #9A9A9A;
	    font-size: 12px;
	  }
	}
	</style>