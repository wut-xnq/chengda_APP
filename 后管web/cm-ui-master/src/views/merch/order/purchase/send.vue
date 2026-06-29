<template>
	<el-dialog :close-on-click-modal="false" title="发货" width="600" draggable v-model="visible">
		<el-form :model="form" :rules="dataRules" formDialogRef label-width="120px" ref="dataFormRef"
			v-loading="loading">
			<el-form-item label="订单编号" >
				{{form.orderNo}}
			</el-form-item>
			<el-form-item label="物流公司名称" prop="logisticsCode" >
				<el-select collapse-tags-tooltip v-model="form.logisticsCode" filterable  @change="onchange">
					<el-option :key="index" :label="item.name" :value="item.code"
						v-for="(item, index) in logis"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="物流单编号" prop="logisticsNo">
				<el-input v-model="form.logisticsNo"></el-input>
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

<script lang="ts" name="SysOauthcustomerDetailsDialog" setup>
	import { useDict } from '/@/hooks/dict';
	import { useMessage } from '/@/hooks/message';
	import { addObj, getObj, putObj } from '/@/api/admin/customer';
	import { useI18n } from 'vue-i18n';
	import { rule } from '/@/utils/validate';


	import { deliverGoods,logistics } from '/@/api/merch/shopping';

	// 定义子组件向父组件传值/事件
	const emit = defineEmits(['refresh']);

	const { t } = useI18n();


	// 定义变量内容
	const dataFormRef = ref();
	const visible = ref(false);
	const loading = ref(false);
	const logis = ref()

	// 提交表单数据
	const form = reactive({
		id: '',
		"logisticsName": "",
		"logisticsCode": "",
		"logisticsNo": "",
		"orderId": '',
		"orderNo": ""
	});
	

	// 定义校验规则
	const dataRules = ref({
		logisticsCode: [{ required: true, message: '物流公司不能为空', trigger: 'blur' }],
		logisticsNo: [{ required: true, message: '物流单编号不能为空', trigger: 'blur' }],
	});

	// 打开弹窗
	const openDialog = (id : string, oderNo : string) => {
		visible.value = true;
		form.orderId = '';
		form.orderNo = '';
		// 重置表单数据
		nextTick(() => {
			dataFormRef.value?.resetFields();
		});

		// 获取sysOauthcustomerDetails信息
		if (id) {
			form.orderId = id;
			form.orderNo = oderNo;
			// getsysOauthcustomerDetailsData(id);
		}
	};
    
	async function getLogistics(){
		try{
			logistics().then(res=>{
				logis.value = res.data
			})
		}catch(err){
			useMessage().error(err.msg);
		}
	}
	getLogistics()
	function onchange(e){
		const item = logis.value.filter(res=>res.code === e)
		if(item.length === 1) form.logisticsName = item[0].name
	}
	// 提交
	const onSubmit = async () => {
		const valid = await dataFormRef.value.validate().catch(() => { });
		if (!valid) return false;
		try {
			loading.value = true;
			await deliverGoods(form).then(res => {
				useMessage().success('发货成功')
				emit('refresh');
			})
		} catch (err : any) {
			useMessage().error(err.msg);
		} finally {
			visible.value = false;
			loading.value = false;
		}
	};

	// 初始化表单数据
	const getsysOauthcustomerDetailsData = (id : string) => {
		// 获取数据
		getObj(id).then((res : any) => {
			Object.assign(form, res.data);
		});
	};

	// 暴露变量
	defineExpose({
		openDialog,
	});
</script>