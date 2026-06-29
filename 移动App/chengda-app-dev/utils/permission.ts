import { t } from './i18n'

export const handleNoPermission = (res: any): void => {
  if (
    uni.getSystemInfoSync().platform == 'android' ||
    uni.getSystemInfoSync().platform == 'ios'
  ) {
    const appAuthorizeSetting = uni.getAppAuthorizeSetting()
    //点击取消时不出现弹窗
    const isCameraDenied = appAuthorizeSetting.cameraAuthorized === 'denied'
    const isUserCancelled =
      res.errMsg === 'chooseImage:fail User cancelled' ||
      res.errMsg === 'chooseImage:fail cancel' ||
      res.errMsg === 'chooseVideo:fail cancel' ||
      res.errMsg === 'chooseVideo:fail User cancelled'
    if (
      (res.code === 11 && isCameraDenied && !isUserCancelled) ||
      (res.code === 12 && isCameraDenied && !isUserCancelled) ||
      (res.code === 2 && !isUserCancelled)
    ) {
      uni.showToast({
        icon: 'none',
        title: t('addPermissionText'),
      })
    }
  }
}

export function requestCameraPermission() {
  return new Promise<boolean>((resolve) => {
    // 1. 先弹出自定义说明
    uni.showModal({
      title: '需要相机权限',
      content: '为了拍摄头像，我们需要访问您的相机。授权后您可在系统设置中随时关闭。',
      confirmText: '去授权',
      cancelText: '暂不',
      success: (res) => {
        if (res.confirm) {
          // 2. 用户同意后，再申请系统权限
          uni.authorize({
            scope: 'scope.camera',
            success: () => resolve(true),
            fail: () => {
              // 用户拒绝，可引导去设置页
              showOpenSetting('相机');
              resolve(false);
            }
          });
        } else {
          resolve(false);
        }
      }
    });
  });
}

// 引导用户去设置页开启权限
function showOpenSetting(permissionName: string) {
  uni.showModal({
    title: '权限被拒绝',
    content: `请在设置中开启“${permissionName}”权限，以正常使用相关功能。`,
    showCancel: true,
    confirmText: '去设置',
    success: (res) => {
      if (res.confirm) {
        uni.openSetting({
          success: (settingRes) => {
            console.log('用户打开了设置页', settingRes);
          }
        });
      }
    }
  });
}
