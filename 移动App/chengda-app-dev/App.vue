<script lang="ts">
import RootStore, { type IMMessage } from "@xkit-yx/im-store";
// @ts-ignore
import { NimKitCore } from "@xkit-yx/core-kit/dist/uniapp-nim-core";
import {
  customRedirectTo,
  customReLaunch,
  customSwitchTab,
} from "./utils/customNavigate";
import { getMsgContentTipByType } from "./utils/msg";
import { STORAGE_KEY } from "./utils/constants";
import { isWxApp } from "./utils";

export default {
  onLaunch() {
    console.log("App Launch===========");
	this.checkPrivacyAgreement();
	// this.initializeApp();
  },
  onShow() {
    // #ifdef APP-PLUS
    // @ts-ignore
    const nimSdk = uni?.$UIKitNIM?.getNIM();
    nimSdk?.offlinePush?.updateAppBackground({
      isBackground: false,
    });
    // #endif
  },
  onHide() {
    // #ifdef APP-PLUS
    // @ts-ignore
    const nimSdk = uni?.$UIKitNIM?.getNIM();
    nimSdk?.offlinePush?.updateAppBackground({
      isBackground: true,
    });
    // #endif
  },
  methods: {
	checkPrivacyAgreement() {
		 // if(!plus.runtime.isAgreePrivacy()){
		 //    //弹出自定义隐私政策提示框
			// console.log("不同意隐私政策重定向至中间页");
			// uni.reLaunch({
			//   url: '/pages/PrivacyAgreementEntry/index'
			// });
		 //  } else {
			//   // 已同意，进行正常的初始化流程
			//   this.initializeApp();
		 //  }
	  const agreed = uni.getStorageSync('privacy_agreed');
	  console.log("agreed===========",agreed);
	  if (agreed !== true) {
		// 如果未同意隐私政策，重定向至中间页
		console.log("不同意隐私政策重定向至中间页");
		uni.reLaunch({
		  url: '/pages/PrivacyAgreementEntry/index'
		});
	  } else {
		// 已同意，进行正常的初始化流程
		this.initializeApp();
	  }
	},
	initializeApp() {
		// #ifdef APP-PLUS
		// 关闭启动画面，锁定竖屏
		plus.navigator.closeSplashscreen();
		plus.screen.lockOrientation("portrait-primary");
		// #endif
		if (
		  // @ts-ignore
		  uni.$UIKitStore &&
		  uni.$UIKitStore?.connectStore?.connectState === "connected"
		) {
		  return;
		}
		
		const imOptions = uni.getStorageSync(STORAGE_KEY) || {};
		// 检查 imOptions 是否有效
		if (!imOptions.account || !imOptions.token) {
		  console.error("imOptions账户或令牌无效，请检查参数");
		  customRedirectTo({
		    url: "/pages/Login/index",
		  });
		  return;
		}
		// 处理修改文件app无法正常登出
		this.initNim(imOptions);
		console.log("启动成功", imOptions);
	},
    initNim(opts: { account: string; token: string }) {
		// #ifdef APP-PLUS
		// @ts-ignore
		// 推送插件
		const nimPushPlugin = uni.requireNativePlugin("NIMUniPlugin-PluginModule");
		// 音视频通话插件
		//@ts-ignore
		const nimCallKit = (uni.$UIKitCallKit = uni.requireNativePlugin("netease-CallKit"));
		// #endif
		
      // 保存登录信息
      // @ts-ignore
      uni.setStorage({
        key: STORAGE_KEY,
        data: opts,
        success: function () {
          console.log("保存登录信息success");
        },
        fail: function (err) {
          console.error("保存登录信息失败:", err);
          // 这里可以添加更多失败时的处理逻辑，比如提示用户或记录日志
        },
      });
      // @ts-ignore
      const nim = (uni.$UIKitNIM = new NimKitCore({
        initOptions: {
          appkey: STORAGE_KEY, // 请填写你的appkey
          account: opts.account, // 请填写你的account
          token: opts.token, // 请填写你的token
          lbsUrls: isWxApp
            ? ["https://lbs.netease.im/lbs/wxwebconf.jsp"]
            : ["https://lbs.netease.im/lbs/webconf.jsp"],
          linkUrl: isWxApp ? "wlnimsc0.netease.im" : "weblink.netease.im",
          needReconnect: true,
          /**
           * 使用固定设备ID，
           */
          isFixedDeviceId: true,
          // "reconnectionAttempts": 5,
          debugLevel: "debug",
          // ...opts,
        },
        platform: "UniApp",
      }));

      // @ts-ignore
      const store = (uni.$UIKitStore = new RootStore(nim, {
        addFriendNeedVerify: false,
        teamBeInviteMode: "noVerify",
        teamJoinMode: "noVerify",
        teamUpdateExtMode: "manager",
        teamUpdateTeamMode: "manager",
        teamInviteMode: "manager",
        sendMsgBefore: async (options: any, type: IMMessage["type"]) => {
          const pushContent = getMsgContentTipByType({
            body: options.body,
            type,
          });
          const yxAitMsg = options.ext
            ? options.ext.yxAitMsg
            : { forcePushIDsList: "[]", needForcePush: false };

          // 如果是 at 消息，需要走离线强推
          // @ts-ignore
          const { forcePushIDsList, needForcePush } = yxAitMsg
            ? // @ts-ignore
              store.msgStore._formatExtAitToPushInfo(yxAitMsg, options.body)
            : { forcePushIDsList: "[]", needForcePush: false };

          console.log("forcePushIDsList: ", forcePushIDsList);

          // 不同产商的推送消息体
          const { scene, to } = options;
          const pushPayload = JSON.stringify({
            // oppo
            oppoField: {
              click_action_type: 4, // 参考 oppo 官网
              click_action_activity: "", // 各端不一样 TODO
              action_parameters: { sessionId: scene, sessionType: to }, // 自定义
            },

            // vivo
            vivoField: {
              pushMode: 0, //推送模式 0：正式推送；1：测试推送，不填默认为0
            },

            // huawei
            hwField: {
              click_action: {
                type: 1,
                action: "", // 各端不一样 TODO
              },
              androidConfig: {
                category: "IM",
                data: JSON.stringify({ sessionId: to, sessionType: scene }),
              },
            },

            // 通用
            sessionId: to,
            sessionType: scene,
          });

          const pushInfo: any = {
            needPush: true,
            needPushBadge: true,
            pushPayload: "{}",
            pushContent,
            needForcePush,
            forcePushIDsList,
            forcePushContent: pushContent,
          };
          return { ...options, pushInfo };
        },
      }));

      // #ifdef APP-PLUS
      // 注册推送
      nim.getNIM().offlinePush.setOfflinePushConfig({
        plugin: nimPushPlugin,
        authConfig: {
          // xiaomi
          xmAppId: "",
          xmAppKey: "",
          xmCertificateName: "",

          // huawei
          hwAppId: "",
          hwCertificateName: "",

          // oppo
          oppoAppId: "",
          oppoAppKey: "",
          oppoAppSecret: "",
          oppoCertificateName: "",

          /** vivo
           * 注意vivo的appid和appkey需要同时在此处，以及manifest.json(即插件参数配置)中配置
           */
          vivoAppId: "",
          vivoAppKey: "",
          vivoCertificateName: "",

          // fcm
          fcmCertificateName: "",

          // meizu
          mzAppId: "",
          mzAppKey: "",
          mzCertificateName: "",

          // iOS
          apnsCertificateName: "",
        },
      });

      // 初始化音视频通话插件
      if (nimCallKit) {
        nimCallKit.initConfig(
          {
            appKey: "", // 请填写你的appkey
            account: "", // 请填写你的account
            token: "", // 请填写你的token
            // account: opts.account, // 请填写你的account
            // token: opts.token, // 请填写你的token
            apnsCername: "",
            pkCername: "",
          },
          (ret: any) => {
            if (ret.code != 200) {
              console.log("-------------callkit init失败\n错误码：", ret);
            } else {
              console.log("-------------callkit 开始登录------------");
              //@ts-ignore
              nimCallKit.login(
                {
                  account: "", // 请填写你的account
                  token: "", // 请填写你的token
                },
                function (ret: any) {
                  if (ret.code != 200) {
                    console.log(
                      "-------------callkit 登录失败------------",
                      ret
                    );
                  } else {
                    console.log(
                      "-------------callkit 登录成功------------ ",
                      ret
                    );
                  }
                }
              );
            }
          }
        );
      }
      // #endif
      nim.connect();
      customSwitchTab({
        url: "/pages/Conversation/index",
      });
    },

    logout() {
      // @ts-ignore
      uni.removeStorageSync(STORAGE_KEY);
      try {
        // @ts-ignore
        nimCallKit.logout({}, (ret) => {
          if (ret.code != 200) {
            console.log("音视频通话插件退出失败");
          } else {
            console.log("音视频通话插件退出成功");
          }
        });
      } catch (error) {
        console.log("音视频通话插件退出失败", error);
      }
      // @ts-ignore
      uni.$UIKitNIM.disconnect();
      // @ts-ignore
      uni.$UIKitStore.destroy();
      customReLaunch({
        url: "/pages/Login/index",
      });
    },
  },
};
</script>

<style>
@import '@/static/style/font/iconfont.css';
uni-page-body {
  height: 100%;
}
uni-page-body > uni-view {
  height: 100%;
}
</style>
