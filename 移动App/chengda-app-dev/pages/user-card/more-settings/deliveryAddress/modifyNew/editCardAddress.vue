<!-- 修改地址 -->
<template>
	<div class="wrapper">
		<NavBar :title="t('editAddress')">
			<!-- 在 NavBar 内部新增删除按钮 -->
			<template v-slot:right>
				<!-- <span class="deleteButton" @click="handleDelete">删除</span> -->
			</template>
		</NavBar>
		<!-- 上 -->
		<div class="namePhone">
			<div class="nameList">
				<span>收货人*</span>
				<input type="text" :placeholder='pageForms.concatPerson' v-model="pageForms.concatPerson"></input>
				<!-- <span>{{pageForms.name}}</span> -->
				<!-- <span @click="getAddressBook">通讯录</span> -->
			</div>
			<div class="phoneList">
				<span>手机号*</span>
				<input type="number" :placeholder='pageForms.contactPhone' v-model="pageForms.contactPhone" />
			</div>
		</div>
		<!-- 下 -->
		<div class="namePhone">
			<div class="detailAddress">
				<span>所在地区*</span>
				<uni-data-picker class='uni-data-picker' placeholder="请选择省市区" popup-title="请选择所在地区" :localdata="dataTree"
					v-model="selectedCities" @change="onchange" @nodeclick="onnodeclick" @popupopened="onpopupopened"
					@popupclosed="onpopupclosed">
				</uni-data-picker>
				<!-- <p>{{ selectedCities }}</p> -->
				<!-- <input type="text" :placeholder='pageForms.provinceCode'></input> -->
			</div>
			<div class="detailAddress">
				<span>详细地址*</span>
				<input type="text" :placeholder='pageForms.address' v-model="pageForms.address"></input>
			</div>
			<div class="acquiesceAddress">
				<span>设为默认收货地址</span>
				<label class="radio">
					<checkbox :value="pageForms.ifDefault" :checked='false' @click="setAddress" />
				</label>
			</div>
		</div>


		<div class="addAddress">
			<view class="btn" @click="commitChanges()">提交</view>
		</div>

	</div>
</template>

<script lang="ts" setup>
	import { onMounted, reactive, ref } from '@vue/composition-api';
	import NavBar from '../../../../../components/NavBar.vue'
	import { t } from '../../../../../utils/i18n'
	import { onLoad } from "@dcloudio/uni-app";
	import { FullCities, editAddress } from 'utils/ArequestHelper';


	const pageForms = reactive({
		address: '',
		provinceCode: 0,
		cityCode: 0,
		countryCode: 0,
		concatPerson: '',
		contactPhone: '',
		createTime: '',
		fullAddress: '',
		id: '',
		ifDefault: '0',
		state: '1',
		userId: '',
	});

	const dataTree = ref<CityNode[]>([]);
	const selectedCities = ref<string[]>([]);

	const onchange = (e) => {
		console.log('onchange:', e.detail.value);
		// selectedCities.value = e.detail.value;
		const value = e.detail.value;
		// 更新 selectedCities
		selectedCities.value = value;

		// 假设 value 数组的顺序是 [provinceCode, cityCode, countryCode]
		if (Array.isArray(value) && value.length >= 3) {
			pageForms.provinceCode = value[0].value || 0;
			pageForms.cityCode = value[1].value || 0;
			pageForms.countryCode = value[2].value || 0;
		} else {
			// 处理数组长度小于 3 的情况
			pageForms.provinceCode = value[0]?.value || 0;
			pageForms.cityCode = value.length > 1 ? value[1]?.value || 0 : 0;
			pageForms.countryCode = value.length > 2 ? value[2]?.value || 0 : 0;
		}
	}
	const onnodeclick = (e) => {
		console.log(e);
	}
	const onpopupopened = (e) => {
		console.log('popupopened');
	}
	const onpopupclosed = (e) => {
		console.log('popupclosed');
	}


	// onLoad 接受 A 页面传递的参数
	onLoad((options) => {
		console.log('页面参数。。。', options);
		// 使用默认值初始化 pageForms
		if (!options) {
			console.log('options 未定义，使用默认值初始化 pageForms');
		} else {
			// 根据 options 更新 pageForms 的属性
			if (options.userId) pageForms.userId = options.userId;
			if (options.concatPerson) pageForms.concatPerson = options.concatPerson;
			if (options.contactPhone) pageForms.contactPhone = options.contactPhone;
			if (options.address) pageForms.address = options.address;
			if (options.ifDefault) pageForms.ifDefault = options.ifDefault;
			if (options.provinceCode) pageForms.provinceCode = options.provinceCode;
			if (options.cityCode) pageForms.cityCode = options.cityCode;
			if (options.countryCode) pageForms.countryCode = options.countryCode;
			if (options.id) pageForms.id = options.id;

			// 查找并设置选中的城市
			if (options.provinceCode && options.cityCode && options.countryCode) {
				const selectedCityCodes = findSelectedCityNames(dataTree.value, pageForms.provinceCode, pageForms.cityCode, pageForms.countryCode);
				console.log('找到的城市名称:', selectedCityCodes);
				selectedCities.value = selectedCityCodes;
				console.log('selectedCities.value', selectedCities.value)
			}
		}
		// 打印更新后的 pageForms
		console.log('更新后的 pageForms:', pageForms);
		console.log('更新后的 selectedCities:', selectedCities.value);
		// console.log(pageForms.ifDefault);
	});

	interface CityNode {
		text : string;
		value : number;
		children ?: CityNode[];
	}

	const findSelectedCityNames = (data : CityNode[], provinceCode : number, cityCode : number, countryCode : number) : string[] => {
		const result : string[] = [];

		const findCity = (data : CityNode[], code : number) : any => {
			for (const item of data) {
				if (item.value == code) {
					console.log('找到城市:', item.text);
					result.push(item.text);

					if (item.children && item.children.length > 0) {
						uni.setStorageSync('CityNode', item.children);
					}
					return result;
				}
			}
			return;
		};

		findCity(data, provinceCode);
		findCity(uni.getStorageSync('CityNode'), cityCode);
		findCity(uni.getStorageSync('CityNode'), countryCode);
		return result;
	};
	// 定义删除操作的处理函数
	const handleDelete = () => {
		console.log('删除操作');
		// 这里可以添加实际的删除逻辑
	};

	// 定义获取通讯录的处理函数
	const getAddressBook = () => {
		console.log('获取通讯录');

		// 检查是否支持 chooseContact 方法
		if (typeof uni.chooseContact === 'function') {
			uni.chooseContact({
				success: (res) => {
					pageForms.concatPerson = res.userName;
					pageForms.contactPhone = res.mobilePhoneNumber;
				},
				fail: (err) => {
					console.error('选择联系人失败:', err);
					uni.showToast({
						title: '获取联系人失败，请手动输入',
						icon: 'none'
					});
				}
			});
		} else {
			// 如果不支持 chooseContact 方法，提示用户手动输入
			uni.showToast({
				title: '当前环境不支持获取联系人，请手动输入',
				icon: 'none'
			});
		}
	};

	// 设置默认地址
	const setAddress = () => {
		console.log('传入的 ifDefault 值:', pageForms.ifDefault);
		if (pageForms.ifDefault === '' || pageForms.ifDefault === '0') {
			pageForms.ifDefault = '1';
		} else {
			pageForms.ifDefault = '0';
		}
		console.log('当前 ifDefault 的值:', pageForms.ifDefault)
	};

	// 获取缓存Token/公用
	const firstLoginUser = uni.getStorageSync('firstLoginUser')

	// 提交修改地址信息
	const commitChanges = async () => {
		console.log('提交修改地址信息');
		validateAddressInfo();
		try {
			const response = await editAddress(pageForms);
			console.log('修改地址成功', response);
			uni.navigateBack({
				delta: 1,
			});
			uni.showToast({ title: '修改地址成功', icon: 'success' });
		} catch (error) {
			console.error('修改地址失败', error);
			uni.showToast({ title: '修改地址失败', icon: 'none' });
		}
	}

	const validateAddressInfo = () => {
		if (!pageForms.concatPerson) {
			uni.showToast({ title: '请填写收货人姓名', icon: 'none' });
			return;
		}
		if (!pageForms.contactPhone) {
			uni.showToast({ title: '请填写手机号码', icon: 'none' });
			return;
		}
		if (pageForms.contactPhone.length !== 11) {
			uni.showToast({ title: '请填写11位手机号', icon: 'none' });
			return;
		}
		if (!pageForms.provinceCode || !pageForms.cityCode || !pageForms.countryCode) {
			uni.showToast({ title: '请选择所在地区', icon: 'none' });
			return;
		}
		if (!pageForms.address) {
			uni.showToast({ title: '请填写详细地址', icon: 'none' });
			return;
		}
	}

	onMounted(async () => {
		console.log('DOM 已渲染完成');
		const firstLoginUser = uni.getStorageSync('firstLoginUser')
		const resCitysDatas = await FullCities(firstLoginUser.access_token)
		console.log('城市全部列表数据', resCitysDatas)
		// 处理城市列表数据
		const processData = (data : any[]) : CityNode[] => {
			return data.map(item => ({
				text: item.cityName,
				value: item.cityId,
				children: item.childrenList ? processData(item.childrenList) : []
			}))
		}
		dataTree.value = processData(resCitysDatas.data.data);
		console.log('处理后的城市数据:', dataTree.value);

		// 如果有接收到的参数，查找并设置选中的城市
		if (pageForms.provinceCode && pageForms.cityCode && pageForms.countryCode) {
			const selectedCityCodes = findSelectedCityNames(dataTree.value, pageForms.provinceCode, pageForms.cityCode, pageForms.countryCode);
			if (selectedCityCodes) {
				selectedCities.value = [+pageForms.provinceCode, +pageForms.cityCode, +pageForms.countryCode];
			} else {
				// todo 如果没有查询到的话，处理一下
			}
			console.log('是否正常', selectedCities.value)
		}

	});
</script>

<style lang="scss" scoped>
	@import '../../../../styles/common.scss';

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

	// 收货人/手机号
	.namePhone {
		margin-top: 10rpx;
		background-color: #ffffff;
		font-weight: 400;
		font-size: 28rpx;
		color: #000000;
		padding: 0 30rpx;
		
		.nameList {
			display: flex;
			align-items: center;
			// justify-content: space-between;
			height: 100rpx;
			border-bottom: 1px solid  rgba(229, 229, 229, 1);
			span {
				width: 160rpx;
				margin-right: 20rpx;
			}

			input {
				width: 100%;
			}
		}

		.phoneList {
			display: flex;
			align-items: center;
			height: 100rpx;

			span {
				width: 160rpx;
				margin-right: 20rpx;
			}
			input {
				width: 100%;
			}
		}
	}

	.detailAddress {
		display: flex;
		align-items: center;
		height: 100rpx;
		border-bottom: 1px solid  rgba(229, 229, 229, 1);
		span {
			width: 160rpx;
			margin-right: 20rpx;
		}
		input {
			width: 100%;
		}
	}

	.acquiesceAddress {
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 100rpx;

		span {
			margin-right: 20rpx;
		}
	}

	.addAddress {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 10;
		padding: 10rpx 30rpx 30rpx 30rpx;
		background-color: #fff;

		.btn {
			height: 80rpx;
			line-height: 80rpx;
			text-align: center;
			background-color: rgba(0, 140, 234, 1);
			color: white;
			border: none;
			border-radius: 40rpx;
		}
	}
	
	
	::v-deep .uni-data-picker {
		.input-value-border{
			border: none !important;
		}
	
	}
</style>