<template>
	<el-dialog :close-on-click-modal="false" :title="form.userId ? $t('common.editBtn') : $t('common.addBtn')" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="130px" ref="dataFormRef" v-loading="loading">
			<el-form-item :label="t('merchUser.name')" prop="name">
				<el-input :placeholder="t('merchUser.inputResourceIdsTip')" v-model="form.name" />
			</el-form-item>
			<el-form-item :label="t('merchUser.age')" prop="age">
				<el-input :placeholder="t('merchUser.inputResourceIdsTip')" v-model="form.age" />
			</el-form-item>
			<el-form-item :label="t('merchUser.gender')" prop="gender">
				<el-select collapse-tags collapse-tags-tooltip v-model="form.gender">
					<el-option :key="index" :label="item.label" :value="item.value" v-for="(item, index) in user_gender"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :label="t('merchUser.phone')" prop="phone">
				<el-input :placeholder="t('merchUser.inputResourceIdsTip')" :disabled="!!form.userId" v-model="form.phone" />
			</el-form-item>
			<!-- <el-form-item :label="t('merchUser.address')" prop="address">
				<el-cascader
					:props="{
						label: 'cityName',
						children: 'childrenList',
						value: 'cityId',
					}"
					style="width: 100%"
					v-model="form.address"
					:options="cityCodeList"
					:check-strictly="false"
				/>
			</el-form-item> -->
			<!-- <el-form-item :label="t('merchUser.address')" prop="address">
				<el-input :placeholder="t('merchUser.inputResourceIdsTip')" v-model="form.address" />
			</el-form-item> -->
			<el-form-item :label="t('merchUser.verifiedName')" prop="verified">
				<el-select collapse-tags collapse-tags-tooltip v-model="form.verified">
					<el-option :key="index" :label="item.label" :value="item.value" v-for="(item, index) in verified"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :label="t('merchUser.deptName')" prop="deptId">
				<el-select collapse-tags collapse-tags-tooltip v-model="form.deptId">
					<el-option :key="index" :label="item.name" :value="item.deptId" v-for="(item, index) in deptList"></el-option>
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
import { addObj, getObj, putObj } from '/@/api/merch/user';
import { useI18n } from 'vue-i18n';
import { rule } from '/@/utils/validate';
import { getCityCodeAll } from '/@/api/admin/merchant';

const props = defineProps({
	deptList: {
		type: Array,
		default: [],
	},
});
// const Upload = defineAsyncComponent(() => import('/@/components/Upload/index.vue'));
// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

const { t } = useI18n();

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false);
const loading = ref(false);

const imageUrl = ref('');

// 定义字典
const { verified, user_gender } = useDict('verified', 'user_gender');

// 提交表单数据
const form = reactive({
	userId: '',
	name: '',
	age: '',
	gender: '',
	phone: '',
	// address: '',
	verified: '',
	deptId: '',
});
let cityCodeList = reactive([]);
// 定义校验规则
const dataRules = ref({
	name: [{ required: true, message: '员工姓名不能为空', trigger: 'blur' }],
	// age: [{ required: true, message: '年龄不能为空', trigger: 'blur' }],
	// gender: [{ required: true, message: '性别不能为空', trigger: 'blur' }],
	phone: [{ required: true, message: '手机号码不能为空', trigger: 'blur' }],
	// address: [{ required: true, message: '地址不能为空', trigger: 'blur' }],
	verified: [{ required: true, message: '认证状态不能为空', trigger: 'blur' }],
});

// 打开弹窗
const openDialog = (id: string) => {
	visible.value = true;
	form.userId = '';
	// 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

	// 获取sysOauthmerchantDetails信息
	if (id) {
		form.userId = id;
		getsysOauthmerchantDetailsData(id);
	}
};
const getCityCode = () => {
	getCityCodeAll().then((res) => {
		console.log(444, res);
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
		// if (form.cityCodeArr.length) {
		// 	form.provinceCode = form.cityCodeArr[0];
		// 	form.cityCode = form.cityCodeArr[1];
		// 	form.countryCode = form.cityCodeArr[2];
		// }
		form.userId ? await putObj(form) : await addObj(form);
		useMessage().success(t(form.userId ? 'common.editSuccessText' : 'common.addSuccessText'));
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
		Object.assign(form, res.data, {});
	});
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>
