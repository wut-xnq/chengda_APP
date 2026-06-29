<template>
	<!-- 原>图片＋按钮＋图片 -->
  <!-- <div>
    <image src="https://yx-web-nosdn.netease.im/common/da126b0fcc419b142224ced81456e179/yx-welcome.png" class="welcome-img" />
    <button class="login-btn" @click="onClick">注册/登录</button>
    <div class="bottom-box">
      <image src="https://yx-web-nosdn.netease.im/common/9303d9be2ea5f90c48397326ae5dfd45/welcome-bottom.png" class="welcome-img-bottom" />
    </div>
  </div> -->
  <!-- 现<针对图片路径编译时候出现问题，渲染> -->
  <div>
    <image src="../static/DcImage/StartPageImage@1x.png" class="welcome-img" />
	<div class="textScheduling">
		<span>{{ i18n.welComeGoTo }}</span>
		<span>{{ i18n.InterpretationPlatform }}</span>
	</div>
    <button class="login-btn" @click="onClick">注册/登录</button>
	<!-- 此处可放公司logo加名字 -->
    <div class="bottom-box">
      <image src="https://yx-web-nosdn.netease.im/common/9303d9be2ea5f90c48397326ae5dfd45/welcome-bottom.png" class="welcome-img-bottom" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import i18n from '../i18n/zh-cn'

const onClick = () => {
  uni.$emit('login')
}

// const url = 'https://yiyong-qa.netease.im/yiyong-static/statics/uniapp-vue2-h5'

</script>

<style lang="scss" scoped>
page {
  height: 100vh;
  overflow: hidden;
}

.welcome-img {
  width: 110px;
  height: 110px;
  display: block;
  margin: 20vh auto 0;
  border-radius: 8px;
}

.login-btn {
  height: 50px;
  width: 90%;
  background: #337EFF;
  border-radius: 8px;
  color: #fff;
  margin-top: 80px;
}

.bottom-box {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);

  .welcome-img-bottom {
    width: 82px;
    height: 20px;
  }
}

// 新增
.textScheduling {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-top: 10px;
}
/* 选择第一个 span 并应用样式 */
.textScheduling span:first-child {
  color: black;
  font-weight: bold;
  font-size: 20px;
}

/* 选择第二个 span 并应用样式 */
.textScheduling span:nth-child(2) {
  color: #8f939c;
  // font-style: italic;//斜体
  font-size: 15px;
  margin-top: 10px;
}
</style>
