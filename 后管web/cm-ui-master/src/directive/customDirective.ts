import type { App } from 'vue';

/**
 * 按钮波浪指令
 * @directive 默认方式：v-waves，如 `<div v-waves></div>`
 * @directive 参数方式：v-waves=" |light|red|orange|purple|green|teal"，如 `<div v-waves="'light'"></div>`
 */
export function wavesDirective(app: App) {
	app.directive('waves', {
		mounted(el, binding) {
			el.classList.add('waves-effect');
			binding.value && el.classList.add(`waves-${binding.value}`);
			function setConvertStyle(obj: { [key: string]: unknown }) {
				let style: string = '';
				for (let i in obj) {
					if (obj.hasOwnProperty(i)) style += `${i}:${obj[i]};`;
				}
				return style;
			}
			function onCurrentClick(e: { [key: string]: unknown }) {
				let elDiv = document.createElement('div');
				elDiv.classList.add('waves-ripple');
				el.appendChild(elDiv);
				let styles = {
					left: `${e.layerX}px`,
					top: `${e.layerY}px`,
					opacity: 1,
					transform: `scale(${(el.clientWidth / 100) * 10})`,
					'transition-duration': `750ms`,
					'transition-timing-function': `cubic-bezier(0.250, 0.460, 0.450, 0.940)`,
				};
				elDiv.setAttribute('style', setConvertStyle(styles));
				setTimeout(() => {
					elDiv.setAttribute(
						'style',
						setConvertStyle({
							opacity: 0,
							transform: styles.transform,
							left: styles.left,
							top: styles.top,
						})
					);
					setTimeout(() => {
						elDiv && el.removeChild(elDiv);
					}, 750);
				}, 450);
			}
			el.addEventListener('mousedown', onCurrentClick, false);
		},
		unmounted(el) {
			el.addEventListener('mousedown', () => {});
		},
	});
}

import { debounce } from "lodash";
export function selectLoadmore(app: App) {
	app.directive('loadmore', {
		mounted(el, binding) {
		    // 不插入body时，以下方式可获取元素
		    // 插入body时，需要以document.querySelector('.el-scrollbar__wrap')获取
		    let scrollWrap = el.querySelector('.el-scrollbar__wrap')
		    // 把监听的方法防抖一下
		    const handle = (e) => {
		      let scrollDistance = scrollWrap.scrollHeight - scrollWrap.scrollTop
		      // 比如此处预留10个像素的位置用于触底
		      if (scrollWrap.clientHeight + 10 > scrollDistance) {
		        binding.value() // 触底通知一下，外界
		      }
		    }
		    // 绑定监听滚动事件
		    scrollWrap?.addEventListener('scroll', handle)
		    // 方法挂载到元素身上便于解绑时使用
		    el._hanlde = handle
		 
		  },
		  unmounted(el, binding) {
		    let scrollWrap = document.querySelector('.el-scrollbar__wrap')
		    scrollWrap?.removeEventListener('scroll', el._hanlde)
		    el._hanlde = null
		 
		  }
	})	
}
