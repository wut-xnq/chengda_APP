<template>
	<el-dialog :close-on-click-modal="false" :title="form.id ? $t('common.editBtn') : $t('common.addBtn')" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="140px" ref="dataFormRef" v-loading="loading">
			<el-form-item :label="t('merchant.merchantName')" prop="merchantName">
				<el-input :placeholder="t('merchant.placeholder')" v-model="form.merchantName" />
			</el-form-item>
			<el-form-item :label="t('merchant.chargePersonName')" prop="chargePersonName">
				<el-input :placeholder="t('merchant.placeholder')" v-model="form.chargePersonName" />
			</el-form-item>
			<el-form-item :label="t('merchant.chargePersonPhone')" prop="chargePersonPhone" required>
				<el-input :placeholder="t('merchant.placeholder')" :maxlength="11" v-model="form.chargePersonPhone" />
			</el-form-item>
			<el-form-item :label="t('merchant.creditCode')" prop="creditCode">
				<el-input :placeholder="t('merchant.placeholder')" v-model="form.creditCode" />
			</el-form-item>
			<el-form-item :label="t('merchant.cityCode')" prop="cityCode">
				<!-- <el-select collapse-tags collapse-tags-tooltip v-model="form.cityCode">
					<el-option :key="index" :label="item.label" :value="item.value" v-for="(item, index) in cityCodeList"></el-option>
				</el-select> -->
				<el-cascader
					:props="{
						label: 'cityName',
						children: 'childrenList',
						value: 'cityId',
					}"
					style="width: 100%"
					v-model="form.cityCodeArr"
					:options="cityCodeList"
					:check-strictly="false"
				/>
			</el-form-item>
			<el-form-item :label="t('merchant.address')" prop="address">
				<el-input :placeholder="t('merchant.placeholder')" v-model="form.address" />
			</el-form-item>
			<el-form-item :label="t('merchant.verified')" prop="verified">
				<el-select collapse-tags collapse-tags-tooltip v-model="form.verified">
					<el-option :key="index" :label="item.label" :value="item.value" v-for="(item, index) in verified"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :label="t('merchant.staffCount')" prop="staffCount">
				<el-input-number :placeholder="t('merchant.placeholder')" :min="0" v-model="form.staffCount" />
			</el-form-item>
			<el-form-item :label="'logo'" prop="logo">
				<UploadImg :imageUrl="imageUrl" @update:imageUrl="handleImageUrlUpdate" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="visible = false">{{ $t('common.cancelButtonText') }}</el-button>
				<el-button @click="onSubmit" type="primary" :disabled="loading">{{ $t('common.confirmButtonText') }}</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script lang="ts" name="SysOauthmerchantDetailsDialog" setup>
import { useDict } from '/@/hooks/dict';
import { useMessage } from '/@/hooks/message';
import { addObj, getObj, putObj, getCityCodeAll } from '/@/api/admin/merchant';
import { useI18n } from 'vue-i18n';
import { rule } from '/@/utils/validate';

// const Upload = defineAsyncComponent(() => import('/@/components/Upload/index.vue'));
// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

const { t } = useI18n();
const props = defineProps({
	isComp: {
		type: Boolean,
		default: false,
	},
});
// 定义变量内容
const dataFormRef = ref();
const visible = ref(false);
const loading = ref(false);

const imageUrl = ref('');

// 定义字典
const { verified } = useDict('verified');

// 提交表单数据
const form = reactive({
	id: '',
	merchantId: '',
	merchantName: '',
	creditCode: '',
	chargePersonName: '',
	chargePersonPhone: null,
	staffCount: undefined,
	address: '',
	logo: '',
	cityCodeArr: [],
	provinceCode: '', //省
	cityCode: '',
	countryCode: '',
});
let cityCodeList = reactive([]);

function validatorPhone(rule:any, value:any, callback:any){
	if(value === ''){
		callback(new Error("负责人联系方式不能为空"))
	}
	const regex = /^1[3-9]\d{9}$/;
	if(regex.test(value)){
		callback()
	}else{
		callback(new Error("请输入正确手机号"))
	}
}

// 定义校验规则
const dataRules = ref({
	merchantName: [{ required: true, message: '商户名称不能为空', trigger: 'blur' }],
	creditCode: [{ required: true, message: '统一信用代码不能为空', trigger: 'blur' }],
	chargePersonName: [{ required: true, message: '负责人不能为空', trigger: 'blur' }],
	chargePersonPhone: [{validator:validatorPhone, trigger: 'blur' }],
	// cityCode: [{ required: true, message: '省市区不能为空', trigger: 'blur' }],
	// logo: [{ required: true, message: 'logo不能为空', trigger: 'blur' }],
});
const handleImageUrlUpdate = (val: any) => {
	console.log(234, val);
	imageUrl.value = val;
	form.logo = val;
};

// 打开弹窗
const openDialog = (id: string) => {
	visible.value = true;
	form.id = '';
	// 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

	// 获取sysOauthmerchantDetails信息
	if (id) {
		form.id = id;
		getsysOauthmerchantDetailsData(id);
	}
};
const getCityCode = () => {
	getCityCodeAll().then((res) => {
		cityCodeList = res.data;
	});
};
getCityCode();
// 提交
const onSubmit = async () => {
	console.log('9999', imageUrl.value);
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
		if (form.cityCodeArr.length) {
			form.provinceCode = form.cityCodeArr[0];
			form.cityCode = form.cityCodeArr[1];
			form.countryCode = form.cityCodeArr[2];
			form.merchantId = form.id;
		}
		form.isCompa = props.isComp;
		console.log(form)
		form.id ? await putObj(form) : await addObj(form);
		useMessage().success(t(form.id ? 'common.editSuccessText' : 'common.addSuccessText'));
		visible.value = false;
		emit('refresh');
	} catch (err: any) {
		useMessage().error(err.msg);
	} finally {
		loading.value = false;
	}
};

// 初始化表单数据
const getsysOauthmerchantDetailsData = (id: string) => {
	// 获取数据
	getObj(id).then((res: any) => {
		Object.assign(form, res.data, {
			cityCodeArr: [res.data.provinceCode, res.data.cityCode, res.data.countryCode],
		});
	});
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>
