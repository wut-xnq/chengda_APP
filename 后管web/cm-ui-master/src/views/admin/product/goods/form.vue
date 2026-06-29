<template>
	<el-dialog :title="dataForm.id ? $t('common.editBtn') : $t('common.addBtn')" v-model="visible" :close-on-click-modal="false" draggable>
		<el-form :model="dataForm" :rules="dataRules" label-width="100px" ref="goodsDialogRef" v-loading="loading">
			<el-form-item :label="$t('goods.productName')" prop="productName">
				<el-input :placeholder="$t('goods.inputTip')" clearable v-model="dataForm.productName"></el-input>
			</el-form-item>
			<el-form-item :label="$t('goods.productClassify')" prop="productClassify">
				<el-input :placeholder="$t('goods.inputTip')" clearable v-model="dataForm.productClassify"></el-input>
			</el-form-item>
			<el-form-item label="商品编号" prop="productCode">
				<el-input :placeholder="$t('sku.inputTip')" clearable v-model="dataForm.productCode"></el-input>
			</el-form-item>
			<el-form-item label="上架区域" prop="labels">
				<el-select v-model="dataForm.shelveArea">
					<el-option v-for="i in shelve_area.filter(res=>res.value>0)" :label="i.label" :value="i.value"></el-option>
				</el-select>
			</el-form-item>
			
			<el-form-item :label="$t('goods.brandName')" prop="brandName">
				<el-input :placeholder="$t('goods.inputTip')" clearable v-model="dataForm.brandName"></el-input>
			</el-form-item>
			<el-form-item label="商品标签">
				<el-select v-model="dataForm.labels" multiple>
					<el-option v-for="i in label.list" :label="i.labelName" :value="i.id"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="商品规格">
				<el-select v-model="dataForm.productSpecs" multiple>
					<el-option v-for="i in specs.list" :label="i.specName" :value="i.id"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :label="$t('goods.minPrice')" prop="minPrice">
				<el-input :placeholder="$t('goods.inputTip')" clearable v-model="dataForm.minPrice"></el-input>
			</el-form-item>
		
			
			<el-form-item label="是否包邮" >
				<el-select v-model="dataForm.ifShipped">
					<el-option v-for="i in shipped.list" :label="i.label" :value="i.value"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="物流方案" >
				<el-select v-model="dataForm.logisticId">
					<el-option v-for="i in logisticsOption.list" :label="i.logisticName" :value="i.id"></el-option>
				</el-select>
			</el-form-item>
			<!-- <el-form-item label="商品简介" >
				<Editor :getHtml="dataForm.productText" v-if="dataForm.id"  @update:getHtml="getHtml" @update:getText="getText"></Editor>
				<Editor  v-else  @update:getHtml="getHtml" @update:getText="getText"></Editor>
			</el-form-item> -->
			
			<el-form-item :label="$t('goods.productPics')" prop="productPics">
				<Upload :limit="5" v-model:model-value="fileList" key="img1"/>
			</el-form-item>
			
			<el-form-item label="商品介绍" prop="productText">
				<Upload :limit="5" v-model:model-value="fileList2" key="img2"/>
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
import { addObj, putObj, getObj } from '/@/api/admin/goods';
import { useDict } from '/@/hooks/dict';
import { useMessage } from '/@/hooks/message';
import {useUserInfo} from '/@/stores/userInfo';

import { labelList } from '/@/api/admin/label';
import {getSpecList} from '/@/api/admin/spec';
import { fetchList as  logistics} from '/@/api/admin/logistics';

const uData = useUserInfo().userInfos;
console.log(uData)
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
const { shelve_area } = useDict('shelve_area');


const label= reactive({list: [], query:{size: 10,current:1,pages:1,merchantId:uData.merchantId}});
const specs= reactive({list: [], query:{size: 10,current:1,pages:1,merchantId:uData.merchantId}});
const logisticsOption = reactive({list: [], query:{size: 10,current:1,pages:1,merchantId:uData.merchantId}});
const shipped = reactive({list:[{label:'是',value:"1"},{label:'否',value:"0"}]})
/** 标签 */
function getLabelOpt(){
	if(label.query.current > label.query.pages) return null;
	label.query.isCompa = props.isComp
	labelList(label.query).then(res=>{
		label.list = label.list.concat(res.data);
	})
}
/** 规格 */
function getSpecsOpt(){
	if(specs.query.current > specs.query.pages) return null;
	getSpecList(specs.query).then(res=>{
		if(res.code === 0){
			specs.list = specs.list.concat(res.data);
		}
	})
}
function getLogisticsOpt(){
	if(logisticsOption.query.current > logisticsOption.query.pages) return null;
	logistics(logisticsOption.query).then(res=>{
		logisticsOption.list = logisticsOption.list.concat(res.data.records);
	})
}
getLogisticsOpt()
getLabelOpt()
getSpecsOpt()

let dataForm = reactive({
	id: '',
	productName: '',
	fileType: '',
	productClassify: '',
	labels: [],
	fileTitle: '',
	content: '',
	relationFileIds: '',
	merchantId: '',
	productCode: '',
	ifShipped:'',
	logisticId: '',
	shelveArea: '',
	productPics: '',
	productText: '',
	productSpecs: []
});

const dataRules = reactive({
	productName: [{ required: true, message: '商品名称不能为空', trigger: 'blur' }],
	productClassify: [{ required: true, message: '商品分类不能为空', trigger: 'blur' }],
	fileType: [{ required: true, message: '发布类型不能为空', trigger: 'blur' }],
	fileTitle: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
	content: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
	
});
// 定义变量内容
const visible = ref(false);
const fileList = ref([]);
const fileList2 = ref([]);

// 打开弹窗
const openDialog = async (id: string) => {
	fileList.value = [];
	visible.value = true;
	dataForm = reactive({
		id: '',
		productName: '',
		fileType: '',
		productClassify: '',
		labels: [],
		fileTitle: '',
		content: '',
		relationFileIds: '',
		merchantId: '',
		productCode: '',
		ifShipped:'',
		logisticId: '',
		shelveArea: '',
		productPics: '',
		productText: '',
		productSpecs: []
	});
	// 重置表单数据
	nextTick(() => {
		goodsDialogRef.value?.resetFields();
	});
	// 获取sysOauthmerchantDetails信息
	if (id) {
		// dataForm.id = id;
		getDetail(id);
	}
};
// 获取图片
function getImage(data:any){
	dataForm.productPics =data
}
// 获取富文本
function getHtml(data:any){
	dataForm.productText = data
}
function getText(data:any){
	// console.log(data)
}
// 初始化表单数据
const getDetail = (id: string) => {
	// 获取数据
	getObj(id,props.isComp).then((res: any) => {
		Object.assign(dataForm, res.data,{
			labels:res.data.labels?res.data.labels.split(','):[],
			productSpecs:res.data.productSpecs?res.data.productSpecs.split(','):[],
			shelveArea:res.data.shelveArea+'',
			});
			fileList.value = dataForm.productPics?dataForm.productPics.split(","):[]
			fileList2.value = dataForm.productText?dataForm.productText.split(","):[]
		console.log(dataForm,res.data)
	});
};
// 定义变量内容
const goodsDialogRef = ref();
const loading = ref(false);
// 提交
const onSubmit = async () => {
	const valid = await goodsDialogRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
		loading.value = true;
		// console.log(444, fileList.value);
		let params = Object.assign({}, dataForm, {
			// relationFileIds: fileList.value,
			labels: dataForm.labels.join(),
			productPics: fileList.value.map(res=>res.url || res).join(),
			productText: fileList2.value.map(res=>res.url || res).join(),
			productSpecs: dataForm.productSpecs.join(),
		});
		console.log(params)
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
