<template>
	<el-dialog :title="dataForm.id ? $t('common.editBtn') : $t('common.addBtn')" v-model="visible"
		:close-on-click-modal="false" draggable>
		<el-form :model="dataForm" :rules="dataRules" label-width="100px" ref="examineDialogRef" v-loading="loading">
			<el-form-item :label="$t('examine.fileType')" prop="fileType">
				<el-select style="width: 100%" v-model="dataForm.fileType">
					<el-option v-for="(item, index) in fileType" :key="index" :label="item.label"
						:value="item.value"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :label="$t('examine.agencyName')" prop="agencyName">
				<el-input :placeholder="$t('examine.inputTitleTip')" clearable v-model="dataForm.agencyName"></el-input>
			</el-form-item>
			<el-form-item :label="$t('examine.fileName')" prop="testProductIds">
				<el-select v-model="dataForm.testProductIds" multiple filterable
					:placeholder="$t('examine.inputTitleTip')">
					<el-option v-for="i in examineObj.list" :value="i.procuctId" :label="i.productName"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :label="$t('examine.title')" prop="fileTitle">
				<el-input :placeholder="$t('examine.inputTitleTip')" clearable v-model="dataForm.fileTitle"></el-input>
			</el-form-item>
			
			<el-form-item :label="$t('examine.remarks')" prop="remarks">
				<el-input :placeholder="$t('examine.inputTitleTip')" clearable v-model="dataForm.remarks"></el-input>
			</el-form-item>
			<el-form-item label="质检时间" prop="testTime">
				<el-date-picker
				      v-model="dataForm.testTime"
					  style="width: 100%;"
				      type="datetime"
					  value-format="YYYY-MM-DD hh:mm:ss"
				      placeholder="选择日期时间">
				    </el-date-picker>
			</el-form-item>
			<el-form-item :label="$t('examine.content')" prop="content">
				<el-input type="textarea" maxlength="150" rows="3" :placeholder="$t('examine.inputContentTip')"
					v-model="dataForm.content"></el-input>
			</el-form-item>
			<el-form-item :label="$t('examine.fileList')" prop="fileList">
				<upload @change="uploadSuccess" v-model:model-value="fileList" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="visible = false">{{ $t('common.cancelButtonText') }}</el-button>
				<el-button type="primary" @click="onSubmit">{{ $t('common.confirmButtonText') }}</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script setup lang="ts" name="examineDialog">
	import { useI18n } from 'vue-i18n';

	import { rule } from '/@/utils/validate';
	import { addObj, putObj, getObj,goodsList } from '/@/api/admin/examine';
	import { useDict } from '/@/hooks/dict';
	import { useMessage } from '/@/hooks/message';
	const { t } = useI18n();
	// const Upload = defineAsyncComponent(() => import('/@/components/Upload/index.vue'));
	const Upload = defineAsyncComponent(() => import('/@/views/components/Image.vue'));
	const emit = defineEmits(['refresh']);
	const { fileType } = useDict('fileType');

	const dataForm = reactive({
		fileType: '',
		testProductIds: [],
		fileTitle: '',
		content: '',
		relationFileIds: '',
		agencyName: '',
		testTime:''
	});

	const dataRules = reactive({
		testProductIds: [
			{ required: true, message: '商品不能为空', trigger: 'blur' },
			// { validator: rule.validatorNameCn, trigger: 'blur' },
		],
		fileType: [{ required: true, message: '发布类型不能为空', trigger: 'blur' }],
		fileTitle: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
		content: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
		agencyName: [{ required: true, message: '检机构不能为空', trigger: 'blur' }],
		testTime: [{ required: true, message: '质检时间不能为空', trigger: 'blur' }],
		
	});
	// 定义变量内容
	const visible = ref(false);
	const fileList = ref([]);

	// 打开弹窗
	const openDialog = (id : string) => {
		fileList.value = [];
		visible.value = true;
		dataForm.id = '';
		// 重置表单数据
		nextTick(() => {
			examineDialogRef.value?.resetFields();
		});
		// 获取sysOauthmerchantDetails信息
		if (id) {
			dataForm.id = id;
			getDetail(id);
		}
		
		getgoodsList()
	};

	const examineObj = reactive({
		query: {},
		page:{ pages:1,current:1,size:10},
		loading: false,
		list: []
	})

	function getgoodsList(){
		goodsList().then(res=>{
			console.log(res)
			if(res?.data){
				examineObj.list = res?.data
			}
		})
	}
	// 初始化表单数据
	const getDetail = (id : string) => {
		// 获取数据
		getObj(id).then((res : any) => {
			if(res.data.fileList){
				fileList.value = res.data.fileList.map(item=>{
					item.name = item.fileName
					return item
				})
			}
			Object.assign(dataForm, res.data,{testProductIds:res.data.testProductIds.split(','),fileType: res.data.fileType+'',fileList:[]});
		});
	};
	// 定义变量内容
	const examineDialogRef = ref();
	const loading = ref(false);
	const uploadSuccess = (val : any) => {
		console.log(456, val);
		dataForm.relationFileIds = val;
	};
	// 提交
	const onSubmit = async () => {
		const valid = await examineDialogRef.value.validate().catch(() => { });
		if (!valid) return false;

		try {
			loading.value = true;
			console.log(444, fileList.value);
			let params = Object.assign({}, dataForm, {
				relationFileIds: fileList.value.map((res:any)=>res.id || res.fileId).join(),
				testProductIds: dataForm.testProductIds ?dataForm.testProductIds.join():''
			});
			const result = dataForm.id ? await putObj(params) : await addObj(params);
			useMessage().success(t(dataForm.id ? 'common.editSuccessText' : 'common.addSuccessText'));
			visible.value = false;
			emit('refresh', result.data);
		} catch (err : any) {
			useMessage().error(err.msg);
		} finally {
			loading.value = false;
		}
	};
	const success = () => {
		emit('refresh');
	};

	// 暴露变量
	defineExpose({
		openDialog,
	});
</script>