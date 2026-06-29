<template>
  <view class="complaint-page">
    <view class="form-container">
      <!-- 问题类型（可选） -->
      <view class="form-item">
        <text class="label">问题类型</text>
        <picker mode="selector" :range="complaintTypes" @change="onTypeChange">
          <view class="picker-value">
            {{ complaintTypes[form.typeIndex] || '请选择问题类型' }}
            <text class="arrow">></text>
          </view>
        </picker>
      </view>

      <!-- 联系方式（可选） -->
      <view class="form-item">
        <text class="label">联系方式</text>
        <input
          class="input"
          type="text"
          placeholder="手机号/邮箱（便于我们联系您）"
          v-model="form.contact"
          maxlength="50"
        />
      </view>

      <!-- 问题描述（必填） -->
      <view class="form-item">
        <text class="label required">问题描述</text>
        <textarea
          class="textarea"
          v-model="form.description"
          placeholder="请详细描述您遇到的问题，包括发生时间、操作步骤等（20~500字）"
          maxlength="500"
          show-count
        />
        <text class="word-count">{{ form.description.length }}/500</text>
      </view>

      <!-- 提交按钮 -->
      <button
        class="submit-btn"
        :disabled="isSubmitting"
        :loading="isSubmitting"
        @click="handleSubmit"
      >
        {{ isSubmitting ? '提交中...' : '提交投诉' }}
      </button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      // 投诉类型选项
      complaintTypes: [
        '界面问题',
        '账号问题',
        '支付问题',
		'功能异常',
		'内容违规',
        '其他'
      ],
      form: {
        typeIndex: 0,     // 默认选中第一个
        contact: '',
        description: ''
      },
      isSubmitting: false
    };
  },
  methods: {
    onTypeChange(e) {
      this.form.typeIndex = e.detail.value;
    },

    async handleSubmit() {
      const { description, contact } = this.form;

      // 校验
      if (description.trim().length < 20) {
        uni.showToast({
          title: '问题描述不少于20字',
          icon: 'none'
        });
        return;
      }

      if (contact && !this.validateContact(contact)) {
        uni.showToast({
          title: '请输入有效的手机号或邮箱',
          icon: 'none'
        });
        return;
      }

      this.isSubmitting = true;

      try {
        // TODO: 替换为你的实际 API 接口
        /* const res = await uni.request({
          url: 'https://your-api.com/api/complaint', // 替换为你的后端地址
          method: 'POST',
          data: {
            type: this.complaintTypes[this.form.typeIndex],
            contact: contact.trim() || null,
            description: description.trim(),
            appVersion: plus.runtime.version, // 获取 App 版本（App 端）
            platform: uni.getSystemInfoSync().platform
          },
          header: {
            'Content-Type': 'application/json'
          }
        });

        if (res[1].statusCode === 200) {
          uni.showToast({
            title: '投诉提交成功！',
            icon: 'success'
          });

          // 提交成功后清空并返回
          setTimeout(() => {
            uni.navigateBack();
          }, 1500);
        } else {
          throw new Error('提交失败');
        } */
		uni.showToast({
		  title: '投诉提交成功！',
		  icon: 'success'
		});
		
		// 提交成功后清空并返回
		setTimeout(() => {
		  uni.navigateBack();
		}, 1500);
      } catch (error) {
        console.error('投诉提交失败:', error);
        uni.showToast({
          title: '提交失败，请重试',
          icon: 'none'
        });
      } finally {
        this.isSubmitting = false;
      }
    },

    // 简单校验手机号或邮箱
    validateContact(str) {
      const phoneReg = /^1[3-9]\d{9}$/;
      const emailReg = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      return phoneReg.test(str) || emailReg.test(str);
    }
  }
};
</script>

<style scoped>
.complaint-page {
  background-color: #f5f5f5;
  min-height: 100vh;
  padding: 20rpx;
}

.form-container {
  background: #ffffff;
  border-radius: 20rpx;
  padding: 40rpx 30rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.form-item {
  margin-bottom: 40rpx;
}

.label {
  display: block;
  font-size: 32rpx;
  color: #333;
  margin-bottom: 16rpx;
  font-weight: 500;
}

.required::after {
  content: '*';
  color: #ff4d4f;
  margin-left: 6rpx;
}

.picker-value {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20rpx 24rpx;
  background: #f8f8f8;
  border-radius: 12rpx;
  font-size: 30rpx;
  color: #666;
}

.arrow {
  font-size: 28rpx;
  color: #999;
}

.input {
  width: 100%;
  min-height: 80rpx; /* 最小高度，确保能显示一行以上 */
  padding: 20rpx 24rpx;
  background: #f8f8f8;
  border-radius: 12rpx;
  font-size: 30rpx;
  color: #333;
  box-sizing: border-box;
  line-height: 1.4; /* 行高更舒适 */
  display: flex;
  align-items: center; /* 垂直居中（部分平台支持） */
}

/* 针对 input 的内部文本容器（H5/小程序兼容） */
.input ::v-deep input,
.input input {
  width: 100%;
  min-height: 40rpx;
  line-height: 1.4;
  outline: none;
  background: transparent;
  border: none;
  font-size: 30rpx;
  color: #333;
}

.textarea {
  width: 100%;
  padding: 20rpx 24rpx;
  background: #f8f8f8;
  border-radius: 12rpx;
  font-size: 30rpx;
  color: #333;
  box-sizing: border-box;
}

.textarea {
  min-height: 200rpx;
  line-height: 1.6;
  resize: none;
}

.word-count {
  text-align: right;
  font-size: 24rpx;
  color: #999;
  margin-top: 10rpx;
}

.submit-btn {
  width: 100%;
  height: 90rpx;
  background: #007aff;
  color: #ffffff;
  font-size: 34rpx;
  border-radius: 12rpx;
  margin-top: 20rpx;
}

.submit-btn:disabled {
  background: #ccc;
}
</style>