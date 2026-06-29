<template>
	<el-dialog :close-on-click-modal="false" :title="form.id ? $t('common.editBtn') : $t('common.addBtn')" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="120px" ref="dataFormRef" v-loading="loading">
			<el-form-item :label="$t('label.labelName')" prop="labelName">
				<el-input placeholder="请输入" clearable v-model="form.labelName"></el-input>
			</el-form-item>
			<el-form-item :label="$t('label.id')" prop="labelCode">
				<el-input placeholder="请输入" clearable v-model="form.labelCode"></el-input>
			</el-form-item>
			<el-form-item :label="t('label.state')" prop="state">
				<el-select collapse-tags-tooltip v-model="form.state">
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

<script lang="ts" name="SysOauthcustomerDetailsDialog" setup>
import { useDict } from '/@/hooks/dict';
import { useMessage } from '/@/hooks/message';
import { addObj, getObj, putObj } from '/@/api/admin/label';
import { useI18n } from 'vue-i18n';
import { rule } from '/@/utils/validate';
import { useUserInfo } from '/@/stores/userInfo';
const uData = useUserInfo().userInfos;

const props = defineProps({
	stateDict: {
		type: Array,
		default: [],
	},
	isComp: {
		type: Boolean,
		default: false,
	},
});
// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

const { t } = useI18n();

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false);
const loading = ref(false);

// 定义字典

// 提交表单数据
let form = reactive({
	labelName: '',
	labelCode: '',
	state: '',
	merchantId: '',
	id: '',
});

// 定义校验规则
const dataRules = ref({
	labelName: [{ required: true, message: '标签名称不能为空', trigger: 'blur' }],
	labelCode: [{ required: true, message: '标签编号不能为空', trigger: 'blur' }],
	state: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
});

// 打开弹窗
const openDialog = (id: string) => {
	visible.value = true;
	form = reactive({
		labelName: '',
		labelCode: '',
		state: '',
		merchantId: '',
		id: '',
	});
	// 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

	// 获取sysOauthcustomerDetails信息
	if (id) {
		form.id = id;
		getsysOauthcustomerDetailsData(id);
	}
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
		console.log(345, props.isComp);
		if (props.isComp) {
			form.merchantId = uData.merchantId;
		}
		form.id ? await putObj(form, props.isComp) : await addObj(form, props.isComp);
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
const getsysOauthcustomerDetailsData = (id: string) => {
	// 获取数据
	getObj(id, props.isComp).then((res: any) => {
		Object.assign(form, res.data);
	});
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>
