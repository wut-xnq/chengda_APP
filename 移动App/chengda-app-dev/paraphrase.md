设置主页面----more-settings
				|----deliveryAddress	收货地址
					|----modifyNew	修保文件夹
						|----editCardAddress.vue	修改地址
						|----newCardAddress.vue		保存新的地址
						
				|----personalCertification		个人认证
						|----index.vue
						
				|----AccountsSecurity		账号安全
						|----index.vue
					|----cryptographicDeviceLogout		密码、设备、注销
						|----cryptographic.vue		密码修改
						|----device.vue				设备查看
						|----logout.vue				注销账号
						
				|----integralConversion				积分转换
						|----index.vue				积分转换页面
购物模块
	----pages
		|----goShopping		购物模块
		|	|----components		组件
		|		|----OptionTab.vue		头部组件（搜索栏/标签切换）
		|		|----FavorAble.vue		优惠
		|		|----QualityProduct.vue		正品>>>与优惠封装组件相同注释
		|		|----NationalStandard.vue		国标>>>与优惠封装组件相同注释
		|		|----QualityInspection.vue		质检
		|		|----commodityCard.vue		我的订单下商品卡片
		|--------index.vue		主页
		|
		|--------myOrder		我的订单
		|		|----index.vue		我的订单页面
		|
		|--------productDetailsPage
		|		|----productDetailsPage.vue		商品详情页
		|
		|--------itemStatusDetailsPage		商品状态详情页文件
		|		|----index.vue			具体页面
		|
		|----protocolPages		诸多协议页面