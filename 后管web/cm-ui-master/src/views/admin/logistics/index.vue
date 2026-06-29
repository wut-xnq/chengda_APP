<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('logistics.logisticName')" prop="name">
						<el-input clearable :placeholder="$t('logistics.logisticName')" style="max-width: 180px" v-model="state.queryForm.logisticName" />
					</el-form-item>
					<el-form-item v-if="!isComp" :label="$t('logistics.merchantName')" prop="merchantId">
						<el-select clearable v-model="state.queryForm.merchantId">
							<el-option v-for="(item, index) in companyList" :key="index" :label="item.merchantName" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item :label="$t('logistics.state')" prop="state">
						<el-select clearable v-model="state.queryForm.state">
							<el-option v-for="(item, index) in stateDict" :key="index" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button @click="getDataList" icon="search" type="primary">
							{{ $t('common.queryBtn') }}
						</el-button>
						<el-button @click="resetQuery" icon="Refresh">{{ $t('common.resetBtn') }}</el-button>
					</el-form-item>
				</el-form>
			</el-row>
			<el-row>
				<div class="mb8" style="width: 100%">
					<el-button v-if="isComp" @click="formDialogRef.openDialog()" class="ml10" icon="folder-add" type="primary">
						{{ $t('common.addBtn') }}
					</el-button>
					<el-button plain :disabled="multiple" @click="handleDelete(selectObjs)" class="ml10" icon="Delete" type="primary">
						{{ $t('common.delBtn') }}
					</el-button>
				</div>
			</el-row>
			<el-table
				:data="state.dataList"
				@selection-change="handleSelectionChange"
				style="width: 100%"
				v-loading="state.loading"
				border
				:cell-style="tableStyle.cellStyle"
				:header-cell-style="tableStyle.headerCellStyle"
			>
				<el-table-column align="center" type="selection" width="40" />
				<el-table-column :label="t('logistics.index')" type="index" width="60" />
				<el-table-column :label="t('logistics.id')" prop="id" show-overflow-tooltip width="185" />
				<el-table-column :label="t('logistics.logisticName')" min-width="100" prop="logisticName" show-overflow-tooltip />
				<el-table-column :label="t('logistics.region')" prop="regionNameList" min-width="280" show-overflow-tooltip>
					<!-- regionNameList -->
					<template #default="scope">
						<el-button @click="openArea(scope.row.region)" text type="primary">
							查看
						</el-button>
						
					<!-- 	<div style="display: inline-block" v-for="(item, ind) in scope.row.regionNameList" :key="item.cityId">
							{{ item.cityName }}
							<span v-show="ind != scope.row.regionNameList.length - 1">、</span>
						</div> -->
					</template>
				</el-table-column>
				<el-table-column :label="t('logistics.chargeType')" prop="chargeType" show-overflow-tooltip width="110px">
					<template #default="scope">
						<!-- 状态（0-禁用，1-启用） -->
						{{ getLabelByArr(scope.row.chargeType, logistic_charge_type) }}
						<!-- {{ scope.row.chargeType + '' === '1' ? '按重量' : '按数量' }} -->
					</template>
				</el-table-column>
				<el-table-column :label="t('logistics.weight')" prop="weight" min-width="130px" show-overflow-tooltip />
				<el-table-column :label="t('logistics.price')" prop="price" show-overflow-tooltip min-width="120px"> </el-table-column>
				<el-table-column width="90px" :label="t('logistics.secondWeight')" prop="secondWeight" show-overflow-tooltip> </el-table-column>
				<el-table-column :label="t('logistics.secondPrice')" prop="secondPrice" show-overflow-tooltip min-width="120px"> </el-table-column>
				<el-table-column v-if="!isComp" :label="t('logistics.merchantName')" prop="merchantName" show-overflow-tooltip min-width="120px">
				</el-table-column>
				<el-table-column :label="t('logistics.state')" prop="state" show-overflow-tooltip min-width="120px">
					<template #default="scope">
						<!-- 状态（0-禁用，1-启用） -->
						<div :class="'state_' + scope.row.state">
							{{ scope.row.state + '' === '1' ? '启用' : '禁用' }}
						</div>
					</template>
				</el-table-column>

				<el-table-column :label="t('logistics.createTime')" prop="createTime" width="160" show-overflow-tooltip />

				<el-table-column fixed="right" :label="$t('common.action')" width="160">
					<template #default="scope">
						<el-button icon="edit" v-show="isComp" type="primary" text @click="formDialogRef.openDialog(scope.row.id)">{{
							$t('common.editBtn')
						}}</el-button>
						<el-button icon="delete" @click="handleDelete([scope.row.id])" text type="primary">
							{{ $t('common.delBtn') }}
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<pagination @current-change="currentChangeHandle" @size-change="sizeChangeHandle" v-bind="state.pagination" />
		</div>
		<!-- 编辑、新增  -->
		<form-dialog :isComp="isComp" @refresh="getDataList()" ref="formDialogRef" />
		
		<el-dialog title="物流覆盖区域" v-model="areaVisible" v-if="areaVisible">
			<el-tree
			disabled
				:props="{
					label: 'cityName',
					children: 'childrenList',
					disabled: 'cityId',
					value: 'cityId'}"
			  :data="cityCodeList"
			  show-checkbox
			  node-key="cityId"
			  :default-checked-keys="actvieKeys">
			</el-tree>
		</el-dialog>
	</div>
</template>

<script lang="ts" name="systemSysOauthlogisticsDetails" setup>
import { BasicTableProps, useTable } from '/@/hooks/table';
import { delObj, fetchList } from '/@/api/admin/logistics';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useDict } from '/@/hooks/dict';
import { useI18n } from 'vue-i18n';
import { fetchList as getCompanyList,getCityCodeAll } from '/@/api/admin/merchant';
import { getLabelByArr } from '/@/utils/other';
// 定义是否商户端，默认false
const props = defineProps({
	isComp: {
		type: Boolean,
		default: false,
	},
	merchantId: {
		type: String,
		default: '',
	},
});
// 所属公司列表
let companyList = ref([]);

const getCompany = () => {
	getCompanyList().then((res) => {
		companyList.value = res.data.records;
	});
};
if (!props.isComp) {
	getCompany();
}

const areaVisible = ref(false)
let cityCodeList = reactive([]);
const actvieKeys = ref([])
// 获取省市区
const getCityCode = () => {
	getCityCodeAll().then((res) => {
		cityCodeList = res.data;
	});
};
getCityCode();

function openArea(regin){
	areaVisible.value = true
	actvieKeys.value = sortArea(regin)
}
function sortArea(list:any){
	if(list){
		const ids = [];
		list = list.split(',')
		// for(let i of list){
		// 	ids.push([Number(`${i.slice(0,3)}000`),Number(`${i.slice(0,4)}00`),Number(i)])
		// }
		return list
	}
	return [];
}

// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
const { t } = useI18n();
// 定义查询字典
const stateDict = useDict('state').state;
const { logistic_charge_type } = useDict('logistic_charge_type');
// 定义变量内容
const formDialogRef = ref();
const queryRef = ref();
// 搜索变量
const showSearch = ref(true);
// 多选变量
const selectObjs = ref([]) as any;
const multiple = ref(true);

const state: BasicTableProps = reactive<BasicTableProps>({
	queryForm: {},
	pageList: fetchList,
	descs: ['id'],
	isCompa: props.isComp,
});

//  table hook
const { getDataList, currentChangeHandle, sizeChangeHandle, downBlobFile, tableStyle } = useTable(state);

const resetQuery = () => {
	queryRef.value.resetFields();
	state.queryForm = {}
	selectObjs.value = [];
	getDataList();
};

// 多选事件
const handleSelectionChange = (objs: { id: string }[]) => {
	selectObjs.value = objs.map(({ id }) => id);
	multiple.value = !objs.length;
};

// 删除操作
const handleDelete = async (ids: string[]) => {
	try {
		await useMessageBox().confirm(t('common.delConfirmText'));
	} catch {
		return;
	}

	try {
		await delObj(ids, props.isComp);
		getDataList();
		useMessage().success(t('common.delSuccessText'));
	} catch (err: any) {
		useMessage().error(err.msg);
	}
};
</script>
<style scoped style="scss">
.state_1 {
	/* color: red; */
	color: green;
}
.state_0 {
	color: red;
}
</style>
