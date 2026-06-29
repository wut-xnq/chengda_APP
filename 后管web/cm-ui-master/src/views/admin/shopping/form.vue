<template>
	<el-dialog :close-on-click-modal="false" :title="form.userId ? $t('common.editBtn') : $t('common.addBtn')" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="120px" ref="dataFormRef" v-loading="loading">
			<el-form-item :label="t('customer.lockFlag')" prop="lockFlag">
				<el-select collapse-tags-tooltip v-model="form.lockFlag">
					<el-option :key="index" :label="item.label" :value="item.value" v-for="(item, index) in lock_flag"></el-option>
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
import { addObj, getObj, putObj } from '/@/api/admin/customer';
import { useI18n } from 'vue-i18n';
import { rule } from '/@/utils/validate';

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

const { t } = useI18n();

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false);
const loading = ref(false);

// 定义字典
const { lock_flag } = useDict('lock_flag');

// 提交表单数据
const form = reactive({
	userId: '',
	lockFlag: '',
});

// 定义校验规则
const dataRules = ref({
	userId: [{ required: true, message: '编号不能为空', trigger: 'blur' }],
	lockFlag: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
});

// 打开弹窗
const openDialog = (id: string) => {
	visible.value = true;
	form.userId = '';
	// 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

	// 获取sysOauthcustomerDetails信息
	if (id) {
		form.userId = id;
		// getsysOauthcustomerDetailsData(id);
	}
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
		let params = {
			userId: Number(form.userId),
			lockFlag: form.lockFlag,
		};
		form.userId ? await putObj(params) : await addObj(params);
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
