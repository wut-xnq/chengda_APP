<template>
  <span 
    @click="handleClick"
    :class="{ active: isActive }"
    class="option-tab"
  >
    {{ label }}
    <span v-if="isActive" class="underline"></span>
  </span>
</template>

<script lang="ts">
import { defineComponent, ref, PropType,computed } from '@vue/composition-api';

export default defineComponent({
  name: 'OptionTab',
  props: {
    label: {
      type: String as PropType<string>,
      required: true
    },
    isSelected: {
          type: Boolean as PropType<boolean>,
          default: false
        },
    selectedOption: {
          type: String as PropType<string>,
          default: ''
        }
  },
  setup(props, { emit }) {
    const isActive = computed(() => props.selectedOption === props.label);
	// console.log(isActive);

    const handleClick = () => {
      emit('click', props.label);
    };

    return {
      isActive,
      handleClick
    };
  }
});
</script>

<style scoped>
.option-tab {
  cursor: pointer;
  padding-bottom: 5px;
  position: relative;
  letter-spacing: 3px;
  font-weight: 600;
  transition: all 0.3s ease; /* 添加过渡效果 */
}

.active::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 130%;
  height: 4px;
  border-radius: 2px;
  background-color: skyblue;
}
.active {
  color: #000000;
  font-weight: bold;
}
</style>