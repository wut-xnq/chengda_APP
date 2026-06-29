<template>
	<el-dialog :close-on-click-modal="false" :title="form.id ? $t('common.editBtn') : $t('common.addBtn')" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="120px" ref="dataFormRef" v-loading="loading">
			<!-- <el-form-item :label="$t('spec.id')" prop="id">
				<el-input :placeholder="$t('spec.id')" clearable v-model="form.id"></el-input>
			</el-form-item> -->
			<el-form-item :label="$t('spec.specName')" prop="specName">
				<el-input :placeholder="$t('spec.inputTip')" clearable v-model="form.specName"></el-input>
			</el-form-item>
			<el-form-item :label="$t('spec.groupName')" prop="groupId">
				<el-select clearable v-model="form.groupId">
					<el-option v-for="(item, index) in groupSpecList" :key="index" :label="item.groupName" :value="item.id"></el-option>
				</el-select>
				<!-- <el-input :placeholder="$t('spec.inputTip')" clearable v-model="form.groupId"></el-input> -->
			</el-form-item>
			<el-form-item :label="$t('spec.state')" prop="state">
				<el-select clearable v-model="form.state">
					<el-option v-for="(item, index) in stateDict" :key="index" :label="item.label" :value="item.value"></el-option>
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

<script lang="ts" name="specDetailsDialog" setup>
import { useDict } from '/@/hooks/dict';
import { useMessage } from '/@/hooks/message';
import { addObj, getObj, putObj } from '/@/api/admin/spec';
import { useI18n } from 'vue-i18n';
import { rule } from '/@/utils/validate';
import { useUserInfo } from '/@/stores/userInfo';
const uData = useUserInfo().userInfos;

const props = defineProps({
	isComp: {
		type: Boolean,
		default: false,
	},
	groupSpecList: {
		type: Array,
		default: () => [],
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
const stateDict = useDict('state').state;
// 提交表单数据
const form = reactive({
	// id: '',
	specName: '',
	groupName: '',
	state: undefined,
	merchantId: '',
});

// 定义校验规则
const dataRules = ref({
	id: [{ required: true, message: '规格编号不能为空', trigger: 'blur' }],
	specName: [{ required: true, message: '规格名称不能为空', trigger: 'blur' }],
	groupId: [{ required: true, message: '规格组不能为空', trigger: 'blur' }],
	state: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
});

// 打开弹窗
const openDialog = (row: object) => {
	visible.value = true;
	Object.assign(form, {
		id: '',
		specName: '',
		groupId: '',
		state: '',
	});

	// 重置表单数据
	// nextTick(() => {
	// 	dataFormRef.value?.resetFields();
	// });

	// 获取sysOauthcustomerDetails信息
	if (row?.id) {
		Object.assign(form, row);
		// form.id = id;
		// getsysOauthcustomerDetailsData(id);
	}
};
// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
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
	getObj(id).then((res: any) => {
		Object.assign(form, res.data);
	});
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>
