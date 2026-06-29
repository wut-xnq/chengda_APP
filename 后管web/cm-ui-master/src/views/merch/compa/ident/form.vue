<template>
	<el-dialog :close-on-click-modal="false" title="审核" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="130px" ref="dataFormRef"
			v-loading="loading">
			<el-form-item :label="t('merchUser.verifiedName')" prop="verifiedState">
				<el-select collapse-tags collapse-tags-tooltip v-model="form.verifiedState">
					<el-option :key="index" :label="item.label" :value="item.value"
						v-for="(item, index) in verified"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :label="t('merchUser.remarks')" prop="remarks">
				<el-input :placeholder="t('merchUser.inputResourceIdsTip')" v-model="form.remarks" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="visible = false">{{ $t('common.cancelButtonText') }}</el-button>
				<el-button @click="onSubmit" type="primary"
					:disabled="loading">{{ $t('common.confirmButtonText') }}</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script lang="ts" name="SysOauthmerchantDetailsDialog" setup>
	import { useDict } from '/@/hooks/dict';
	import { useMessage } from '/@/hooks/message';
	import { putObj } from '/@/api/merch/ident';
	import { useI18n } from 'vue-i18n';
	import { rule } from '/@/utils/validate';

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
		id: '',
		verifiedState: '',
		remarks: '',
	});
	let cityCodeList = reactive([]);
	// 定义校验规则
	const dataRules = ref({
		verifiedState: [{ required: true, message: '认证状态不能为空', trigger: 'blur' }],
	});
	interface user{
		id: string,
		userId: string
	}
	// 打开弹窗
	const openDialog = (data : user) => {
		visible.value = true;
		// form = {};
		Object.assign(form, {
			id:data.id,
			userId: data.userId,
			verifiedState: '',
			remarks: '',
		})
		// 获取详情信息
		// if (row?.id) {
		// 	form.userId = Object.assign(row);
		// }
	};

	// 提交
	const onSubmit = async () => {
		console.log('9999', imageUrl.value);
		const valid = await dataFormRef.value.validate().catch(() => { });
		if (!valid) return false;

		try {
			loading.value = true;
			await putObj(form);
			useMessage().success(t(form.userId ? 'common.editSuccessText' : 'common.addSuccessText'));
			visible.value = false;
			emit('refresh');
		} catch (err : any) {
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