<template>
	<el-dialog :close-on-click-modal="false" :title="form.id ? $t('common.editBtn') : $t('common.addBtn')" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="140px" ref="dataFormRef" v-loading="loading">
			<el-form-item :label="t('logistics.logisticName')" prop="logisticName">
				<el-input :placeholder="t('logistics.placeholder')" v-model="form.logisticName" />
			</el-form-item>
			<el-form-item :label="t('logistics.region')" prop="region">
				<el-cascader
					:props="{
						label: 'cityName',
						children: 'childrenList',
						value: 'cityId',
						multiple: true,
					}"
					collapse-tags
					collapse-tags-tooltip
					:max-collapse-tags="3"
					clearable
					style="width: 100%"
					v-model="form.region"
					:options="cityCodeList"
					:check-strictly="false"
				/>
			</el-form-item>
			<el-form-item :label="t('logistics.chargeType')" prop="chargeType">
				<el-select filterable collapse-tags collapse-tags-tooltip v-model="form.chargeType">
					<el-option :key="index" :label="item.label" :value="item.value" v-for="(item, index) in logistic_charge_type"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :label="t('logistics.weight')" prop="weight">
				<el-input :placeholder="t('logistics.placeholder')" v-model="form.weight" />
			</el-form-item>
			<el-form-item :label="t('logistics.price')" prop="price">
				<el-input :placeholder="t('logistics.placeholder')" v-model="form.price" />
			</el-form-item>
			<el-form-item :label="t('logistics.secondWeight')" prop="secondWeight">
				<el-input :placeholder="t('logistics.placeholder')" v-model="form.secondWeight" />
			</el-form-item>
			<el-form-item :label="t('logistics.secondPrice')" prop="secondPrice">
				<el-input :placeholder="t('logistics.placeholder')" v-model="form.secondPrice" />
			</el-form-item>
			<el-form-item :label="t('logistics.state')" prop="state">
				<el-select collapse-tags collapse-tags-tooltip v-model="form.state">
					<el-option :key="index" :label="item.label" :value="item.value" v-for="(item, index) in stateDict"></el-option>
				</el-select>
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
import { addObj, getObj, putObj } from '/@/api/admin/logistics';
import { useI18n } from 'vue-i18n';
import { rule } from '/@/utils/validate';
import { getCityCodeAll } from '/@/api/admin/merchant';
import { useUserInfo } from '/@/stores/userInfo';
const uData = useUserInfo().userInfos;

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

const { t } = useI18n();

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false);
const loading = ref(false);

// 定义字典
const { logistic_charge_type } = useDict('logistic_charge_type');
const stateDict = useDict('state').state;
// 提交表单数据
let form = reactive({
	id: '',
	merchantId: '',
	logisticName: '',
	chargeType: '',
	weight: '',
	price: '',
	secondPrice: '',
	secondWeight: '',
	region: [],
	ifDefault: '',
	state: '',
});
let cityCodeList = reactive([]);
// 定义校验规则
const dataRules = ref({
	logisticName: [{ required: true, message: '方案名称不能为空', trigger: 'blur' }],
	price: [{ required: true, message: '首重价格不能为空', trigger: 'blur' }],
	chargeType: [{ required: true, message: '计费方式不能为空', trigger: 'blur' }],
	weight: [{ required: true, message: '首重不能为空', trigger: 'blur' }],
	region: [{ required: true, message: '物流覆盖区域不能为空', trigger: 'blur' }],
	
});

// 打开弹窗
const openDialog = (id: string) => {
	visible.value = true;
	form = reactive({
		id: '',
		merchantId: '',
		logisticName: '',
		chargeType: '',
		weight: '',
		price: '',
		secondPrice: '',
		secondWeight: '',
		region: [],
		ifDefault: '',
		state: '',
	});
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
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
		form.merchantId = uData.merchantId;
		const query = Object.assign({},form,{region:form.region.map(res=>res[res.length-1]).join()})
		form.id ? await putObj(query) : await addObj(query);
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
		Object.assign(form, res.data, {region: sortArea(res.data.region)});
	});
};

function sortArea(list:any){
	if(list){
		const ids = [];
		list = list.split(',')
		for(let i of list){
			ids.push([Number(`${i.slice(0,3)}000`),Number(`${i.slice(0,4)}00`),Number(i)])
		}
		return ids
	}
	return [];
}

// 暴露变量
defineExpose({
	openDialog,
});
</script>
