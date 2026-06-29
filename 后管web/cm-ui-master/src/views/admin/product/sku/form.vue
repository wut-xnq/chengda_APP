<template>
	<el-dialog :title="dataForm.id ? $t('common.editBtn') : $t('common.addBtn')" v-model="visible" destroy-on-close :close-on-click-modal="false" draggable>
		<el-form :model="dataForm" :rules="dataRules" label-width="100px" ref="goodsDialogRef" v-loading="loading">
			<el-form-item :label="$t('sku.productName')" prop="productId">
				<!-- 下拉-请求商品列表数据 -->
				<el-select clearable v-model="dataForm.productId" v-loadmore="getGoods" :teleported="false">
					<el-option v-for="(item, index) in goodsList" :key="index" :label="item.productName" :value="item.id"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="SKU编号" prop="skuCode">
				<el-input :placeholder="$t('sku.inputTip')" clearable v-model="dataForm.skuCode"></el-input>
			</el-form-item>
			<el-form-item :label="$t('sku.skuName')" prop="skuName">
				<el-input :placeholder="$t('sku.inputTip')" clearable v-model="dataForm.skuName"></el-input>
			</el-form-item>
			<el-form-item label="商品数量" prop="amount">
				<el-input-number :placeholder="$t('sku.inputTip')" :min="1" v-model="dataForm.amount"></el-input-number>
			</el-form-item>
			<el-form-item label="限购数量" prop="limitAmount">
				<el-input-number :placeholder="$t('sku.inputTip')" :min="1" v-model="dataForm.limitAmount"></el-input-number>
			</el-form-item>
			<el-form-item :label="$t('sku.currentPrice')" prop="currentPrice">
				<el-input :placeholder="$t('sku.inputTip')" clearable v-model="dataForm.currentPrice"></el-input>
			</el-form-item>
			<el-form-item :label="$t('sku.originPrice')" prop="originPrice">
				<el-input :placeholder="$t('sku.inputTip')" clearable v-model="dataForm.originPrice"></el-input>
			</el-form-item>
			<el-form-item :label="$t('sku.state')" prop="state">
				<el-select clearable v-model="dataForm.state">
					<el-option v-for="(item, index) in stateDict" :key="index" :label="item.label" :value="item.value"></el-option>
				</el-select>
				<!-- <el-input :placeholder="$t('sku.inputTip')" v-model="dataForm.state"></el-input> -->
			</el-form-item>
			<el-form-item :label="$t('sku.remarks')" prop="remarks">
				<el-input :placeholder="$t('sku.inputTip')" v-model="dataForm.remarks"></el-input>
			</el-form-item>
			<el-form-item :label="$t('sku.skuPics')" prop="skuPics">
				<UploadImg :imageUrl="dataForm.skuPics" @update:imageUrl="getImage" v-if="dataForm.id"/>
				<UploadImg :imageUrl="dataForm.skuPics" @update:imageUrl="getImage" v-else/>
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

<script setup lang="ts" name="goodsDialog">
import { useI18n } from 'vue-i18n';

import { rule } from '/@/utils/validate';
import { addObj, putObj, getObj } from '/@/api/admin/sku';
import { useDict } from '/@/hooks/dict';
import { useMessage } from '/@/hooks/message';

import { fetchList as getGoodsList } from '/@/api/admin/goods';

// 商品列表
let goodsList = ref([]);
const query = reactive({pages:1,current:0,size: 10})
const getGoods = () => {
	query.current ++
	if(query.current > query.pages){
		getGoods()
	}
	getGoodsList({ isCompa: true,...query }).then((res) => {
		goodsList.value = goodsList.value.concat(res.data.records);
		query.pages  = res.data.pages
		query.current  = res.data.current
	});
};
getGoods();

const props = defineProps({
	isComp: {
		type: Boolean,
		default: false,
	},
});
const { t } = useI18n();
const Upload = defineAsyncComponent(() => import('/@/views/components/Image.vue'));
const emit = defineEmits(['refresh']);
const { fileType } = useDict('fileType');
const stateDict = useDict('state').state;
let dataForm = reactive({
	id: '',
	productId: '',
	fileType: '',
	skuName: '',
	amount: '',
	labels: '',
	fileTitle: '',
	content: '',
	relationFileIds: '',
	skuPics: '',
	limitAmount:''
});

const dataRules = reactive({
	productId: [{ required: true, message: '商品名称不能为空', trigger: 'blur' }],
	skuCode: [{ required: true, message: 'SKU编号不能为空', trigger: 'blur' }],
	skuName: [{ required: true, message: 'SKU名称不能为空', trigger: 'blur' }],
	amount: [{ required: true, message: '商品数量不能为空', trigger: 'blur' }],
	limitAmount: [{ required: true, message: '限购数量不能为空', trigger: 'blur' }],
	currentPrice: [{ required: true, message: '现价不能为空', trigger: 'blur' }],
	originPrice: [{ required: true, message: '原价不能为空', trigger: 'blur' }],
	
	fileType: [{ required: true, message: '发布类型不能为空', trigger: 'blur' }],
	fileTitle: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
	content: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
});
// 定义变量内容
const visible = ref(false);
const fileList = ref([]);

// 打开弹窗
const openDialog = (id: string) => {
	fileList.value = [];
	visible.value = true;
	dataForm = reactive({
		id: '',
		productId: '',
		fileType: '',
		skuName: '',
		amount: '',
		labels: '',
		fileTitle: '',
		content: '',
		relationFileIds: '',
		skuPics: '',
		limitAmount:''
	})
	// 重置表单数据
	nextTick(() => {
		goodsDialogRef.value?.resetFields();
	});
	// 获取sysOauthmerchantDetails信息
	if (id) {
		// dataForm.id = id;
		getDetail(id);
	}else{
		
	}
};
function getImage(data){
	dataForm.skuPics =data
}
// 初始化表单数据
const getDetail = (id: string) => {
	// 获取数据
	getObj(id).then((res: any) => {
		Object.assign(dataForm, res.data);
	});
};
// 定义变量内容
const goodsDialogRef = ref();
const loading = ref(false);
const uploadSuccess = (val: any) => {
	dataForm.relationFileIds = val;
};
// 提交
const onSubmit = async () => {
	const valid = await goodsDialogRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
		// console.log(444, fileList.value);
		let params = Object.assign({}, dataForm, {
			// relationFileIds: fileList.value,
		});
		const result = dataForm.id ? await putObj(params, props.isComp) : await addObj(params, props.isComp);
		useMessage().success(t(dataForm.id ? 'common.editSuccessText' : 'common.addSuccessText'));
		visible.value = false;
		emit('refresh', result.data);
	} catch (err: any) {
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
