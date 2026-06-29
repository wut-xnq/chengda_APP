<template>
	<el-dialog :close-on-click-modal="false" :title="form.id ? $t('common.editBtn') : $t('common.addBtn')" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" label-width="120px" ref="dataFormRef" v-loading="loading">
			<el-form-item :label="$t('spec.groupName') + '名称'" prop="groupName">
				<el-input :placeholder="$t('spec.inputTip')" clearable v-model="form.groupName"></el-input>
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

<script lang="ts" name="specGroupDetailsDialog" setup>
import { useDict } from '/@/hooks/dict';
import { useMessage } from '/@/hooks/message';
import { addSpecGroup, getSpecGroup, editSpecGroup } from '/@/api/admin/spec';
import { useI18n } from 'vue-i18n';
import { rule } from '/@/utils/validate';
import { useUserInfo } from '/@/stores/userInfo';
const uData = useUserInfo().userInfos;

const props = defineProps({
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
const stateDict = useDict('state').state;
// 提交表单数据
const form = reactive({
	id: '',
	groupName: '',
	merchantId: '',
});

// 定义校验规则
const dataRules = ref({
	groupName: [{ required: true, message: '规格名称不能为空', trigger: 'blur' }],
});

// 打开弹窗
const openDialog = (row: object) => {
	visible.value = true;
	// 重置表单数据
	form.id = '';
	form.groupName = '';
	form.merchantId = uData.merchantId;
	// // 获取Details信息
	if (row?.id) {
		// form.id = row.id;
		// form.groupName = row.groupName;
		Object.assign(form, row);
	}
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
		form.id ? await editSpecGroup(form) : await addSpecGroup(form);
		useMessage().success(t(form.id ? 'common.editSuccessText' : 'common.addSuccessText'));
		visible.value = false;
		emit('refresh');
	} catch (err: any) {
		useMessage().error(err.msg);
	} finally {
		loading.value = false;
	}
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>
