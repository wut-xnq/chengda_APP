<!-- 新增地址 -->
<template>
	<div class="wrapper">
		<NavBar :title="t('newAddress')"></NavBar>
		<!-- 上 -->
			<div class="namePhone">
				<div class="nameList">
					<span>收货人*</span>
					<input type="text" v-model="newCardLists.concatPerson" placeholder='请填写收货人姓名'></input>
					<!-- <span>{{pageForms.name}}</span> -->
					<!-- <span @click="getAddressBook">通讯录</span> -->
				</div>
				<div class="phoneList">
					<span>手机号*</span>
					<input type="text" v-model="newCardLists.contactPhone" placeholder='请填写手机号码'></input>
				</div>
			</div>
			<!-- 下 -->
			<div class="namePhone">
				<div class="detailAddress">
					<span>所在地区*</span>
					<!-- <input type="text" placeholder='省、市、区'></input> -->
					<uni-data-picker class='uni-data-picker' placeholder="请选择省市区"
										popup-title="请选择所在地区"
											:localdata="dataTree"
												v-model="selectedCities"
													@change="onchange"
														@nodeclick="onnodeclick"
															@popupopened="onpopupopened"
																@popupclosed="onpopupclosed">
					</uni-data-picker>
				</div>
				<div class="detailAddress">
					<span>详细地址*</span>
					<input type="text" v-model="newCardLists.address" placeholder='小区，门牌号等'></input>
				</div>
				<div class="acquiesceAddress">
					<span>设为默认收货地址</span>
					<label class="radio">
						<checkbox :value="newCardLists.ifDefault" :checked="false" @click="setAddress()" />
					</label>
				</div>
			</div>
			
			<div class="addAddress">
				<view class="btn" @click="commitChanges()">保存地址</view>
			</div>
	</div>
</template>

<script lang="ts" setup>
import { onMounted , ref } from '@vue/composition-api';
import NavBar from '../../../../../components/NavBar.vue'
import { t } from '../../../../../utils/i18n'
import uniDataPicker from '../../../../../uni_modules/uni-data-picker/components/uni-data-picker/uni-data-picker.vue'
import { FullCities, newAddress } from 'utils/ArequestHelper';

const newCardLists = {
	provinceCode: null,		//省
	cityCode: null,		//市
	countryCode: null,			//区
	address: '',
	concatPerson: '',
	contactPhone: '',
	createTime: '',
	fullAddress: '',
	ifDefault: '0',
	state: '',
	id: null,
	userId: ''
};

const dataTree = ref([]);
const selectedCities = ref([]);

// 定义获取通讯录的处理函数
const getAddressBook = () => {
    console.log('获取通讯录');

};

// 设置默认地址
const setAddress = () => {
	console.log('传入的 ifDefault 值:', newCardLists.ifDefault);
	if(newCardLists.ifDefault === '' || newCardLists.ifDefault === '0'){
		newCardLists.ifDefault = '1';
	}else{
		newCardLists.ifDefault = '0';
	}
	console.log('当前 ifDefault 的值:', newCardLists.ifDefault)
};

// 保存新的地址信息
const commitChanges = async () => {
	console.log('保存新的地址信息');
	console.info(newCardLists.ifDefault);
	  // 检查必填字段
	  if (!newCardLists.concatPerson) {
	    uni.showToast({ title: '请填写收货人姓名', icon: 'none' });
	    return;
	  }
	  if (!newCardLists.contactPhone) {
	    uni.showToast({ title: '请填写手机号码', icon: 'none' });
	    return;
	  }
	  if (!/^[1][3-9][0-9]{9}$/.test(newCardLists.contactPhone)) {
  		uni.showToast({ title: '请填写正确的手机号码', icon: 'none' });
  		return;
	}
	  if (!newCardLists.provinceCode || !newCardLists.cityCode || !newCardLists.countryCode) {
	    uni.showToast({ title: '请选择所在地区', icon: 'none' });
	    return;
	  }
	  if (!newCardLists.address) {
	    uni.showToast({ title: '请填写详细地址', icon: 'none' });
	    return;
	  }
	const firstLoginUser = uni.getStorageSync('firstLoginUser')
		console.log('打印id',firstLoginUser.user_id)
		newCardLists.userId = firstLoginUser.user_id
		console.log('打印newCardLists',newCardLists.userId)
	try{
		const response = await newAddress( newCardLists );
		console.log('新增地址成功', response);
		    // 返回上一级页面
		    uni.navigateBack({
		      delta: 1,
		    });
	}catch(error){
		console.error('新增地址失败', error);
		uni.showToast({ title: '新增地址失败', icon: 'none' });
	}
	console.log('最新的数据',newCardLists)
}

const onchange = (e) => {
  console.log('选择改变', e);
  console.log('选择改变', e.detail.value);

	// 初始化 newCardLists 的地区代码为 null
	newCardLists.provinceCode = null;
	newCardLists.cityCode = null;
	newCardLists.countryCode = null;

  e.detail.value.forEach(( item:any , index:number ) => {
	  console.log(`第 ${index + 1} 个对象的值:`, item.value)
	    switch (index) {
	      case 0:
	        newCardLists.provinceCode = item.value;
	        break;
	      case 1:
	        newCardLists.cityCode = item.value;
	        break;
	      case 2:
	        newCardLists.countryCode = item.value;
	        break;
	    }
  })
    // 清空 selectedCities
    selectedCities.value = [];
    console.log('打印newCardLists',newCardLists)
};

const onnodeclick = (e) => {
  console.log('节点点击', e);
  // 处理节点点击的逻辑
};

const onpopupopened = (e) => {
  console.log('弹窗打开', e);
  // 处理弹窗打开的逻辑
};

const onpopupclosed = (e) => {
  console.log('弹窗关闭', e);
  // 处理弹窗关闭的逻辑
};


// 确保 DOM 渲染完成后进行操作
onMounted( async() => {
  console.log('DOM 已渲染完成');
  const firstLoginUser = uni.getStorageSync('firstLoginUser')
  const resCitysDatas = await FullCities (firstLoginUser.access_token)
  console.log('城市全部列表数据',resCitysDatas)
  // 处理城市列表数据
  const processData = (data) => {
	  return data.map(item => ({
		  text: item.cityName,
		  value: item.cityId,
		  children: item.childrenList ? processData(item.childrenList) : []
	  }))
  }
  dataTree.value = processData(resCitysDatas.data.data);
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

