<!-- 注销账号 -->
<template>
  <div class="wrapper">
    <NavBar :title="t('cancelAccount')" />
    <div class="textsTips">
      <div class="textsTipsOne">
        <uni-icons type="info-filled" size="75" color="#87CEEB"></uni-icons>
        <span
          >注销 {{ phone.slice(0, 3) }}****{{ phone.slice(9) }} 绑定的账号</span
        >
      </div>
      <p class="textsTipsP">申请注销的关联事项：</p>
      <div class="textMoudels">
        <span>1. 账号处于安全状态</span>
        <p>账号当前为正常使用状态，不存在被盗、违规、禁用等风险</p>
      </div>
      <div class="textMoudels">
        <span>2. 账号下的订单已完成</span>
        <p>当前账号下的交易均已完成，不存在订单纠纷</p>
      </div>
      <div class="textMoudels">
        <span>3. 账号授权、绑定关系已解除</span>
        <p>账号已解除与其他第三方平台的授权，已解绑银行卡等</p>
      </div>
      <!-- 注销协议/注销按钮 -->
      <div class="cBox">
        <uniDataCheckbox
          :localdata="infoArr"
          v-model="selectedInfo"
          @change="handleChange"
        />
        <button class="logOffButton" @click="logOff">确认注销</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import NavBar from "../../../../../components/NavBar.vue";
import uniDataCheckbox from "../../../../../uni_modules/uni-data-checkbox/components/uni-data-checkbox/uni-data-checkbox.vue";
import { t } from "../../../../../utils/i18n";
import { defineComponent, ref } from "@vue/composition-api";

export default defineComponent({
  components: {
    NavBar,
    uniDataCheckbox,
  },
  setup() {
    let phone = ref(uni.getStorageSync("firstLoginUser").user_info.phone);
    const infoArr = ref([
      { text: "我已阅读并同意《用户注销须知》", value: "Agree" },
    ]);
    // const selectedInfo = ref([]);
    const selectedInfo = ref<string[]>([]); // 确保是字符串数组

    const handleChange = (e: any) => {
      selectedInfo.value = e.detail.value;
      console.log("Selected items:", selectedInfo.value);
    };
    // 确认注销按钮
    const logOff = () => {
      if (!selectedInfo.value.includes("Agree")) {
        uni.showToast({
          title: "请先选择阅读并同意《用户注销须知》",
          icon: "none",
          duration: 3000,
        });
      } else {
        console.log("确认注销");
        // 这里可以添加实际的注销逻辑
        uni.showModal({
          title: t("cancelAccount"),
          content: t("cancelAccountTitle"),
          showCancel: true,
          success: function (res) {
            if (res.confirm) {
              console.log("用户点击确定");
              const app = getApp();
              app.logout();
              // 此处执行数据传输后端注销账号
            } else if (res.cancel) {
              console.log("用户点击取消");
            }
          },
        });
      }
    };

    return {
      t,
      infoArr,
      selectedInfo,
      handleChange,
      logOff,
      phone,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "../../../../styles/common.scss";

page {
  padding-top: var(--status-bar-height);
  height: 100vh;
  background-color: rgb(245, 246, 247);
}

.wrapper {
  background-color: rgb(245, 246, 247);
  width: 100%;
  height: 100vh;
  box-sizing: border-box;
}
.textsTips {
  margin: 5%;
  p {
    color: #9a9a9a;
    margin-bottom: 10px;
    margin-top: 10px;
    font-size: 15px;
  }
  .textsTipsOne {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    // margin-bottom: 40px;
  }
  .textMoudels {
    border-bottom: 2px solid #efefef;
    margin-top: 10px;
    span {
      color: #101010;
      font-weight: 500;
    }
  }
}
.cBox {
  margin-top: 15px;
}
.logOffButton {
  // background-color: #008CEA;
  // border-radius: 20px;
  // height: 45px;
  // line-height: 45px;
  // text-align: center;
  // width: 90%;
  // margin: 20px auto;
  // color: #ffffff;
  // letter-spacing: 10px;
  position: fixed;
  bottom: 20px;
  left: 0;
  right: 0;
  width: 90%;
  height: 40px;
  line-height: 40px;
  text-align: center;
  background-color: #1e70e2;
  color: white;
  border: none;
  z-index: 10;
  border-radius: 20px;
}
</style>
