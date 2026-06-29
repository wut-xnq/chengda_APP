<template>
	<el-dialog :close-on-click-modal="false" :title="form.deptId ? $t('common.editBtn') : $t('common.addBtn')" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="130px" ref="dataFormRef" v-loading="loading">
			<el-form-item :label="t('merchDept.name')" prop="name">
				<el-input :placeholder="t('merchDept.inputResourceIdsTip')" v-model="form.name" />
			</el-form-item>
			<el-form-item :label="t('merchDept.deptNumber')" prop="deptNumber">
				<el-input :placeholder="t('merchDept.inputResourceIdsTip')" v-model="form.deptNumber" />
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
import { addObj, getObj, putObj } from '/@/api/merch/dept';
import { useI18n } from 'vue-i18n';
import { rule } from '/@/utils/validate';
import { useUserInfo } from '/@/stores/userInfo';
const uData = useUserInfo().userInfos;

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

// 提交表单数据
const form = reactive({
	deptId: '',
	name: '',
	deptNumber: '',
	merchantId: '',
});
let cityCodeList = reactive([]);
// 定义校验规则
const dataRules = ref({
	name: [{ required: true, message: '员工姓名不能为空', trigger: 'blur' }],
	deptNumber: [{ required: true, message: '年龄不能为空', trigger: 'blur' }],
});

// 打开弹窗
const openDialog = (id: string) => {
	visible.value = true;
	form.deptId = '';
	// 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

	// 获取sysOauthmerchantDetails信息
	if (id) {
		form.deptId = id;
		getsysOauthmerchantDetailsData(id);
	}
};
// const getCityCode = () => {
// 	getCityCodeAll().then((res) => {
// 		console.log(444, res);
// 		cityCodeList = res.data;
// 	});
// };
// getCityCode();
// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
		form.merchantId = uData.merchantId;
		form.deptId ? await putObj(form) : await addObj(form);
		useMessage().success(t(form.deptId ? 'common.editSuccessText' : 'common.addSuccessText'));
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
