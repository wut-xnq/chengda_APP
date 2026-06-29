<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('label.labelName')" prop="name">
						<el-input clearable :placeholder="$t('label.labelName')" style="max-width: 180px" v-model="state.queryForm.labelName" />
					</el-form-item>
					<el-form-item :label="$t('label.id')" prop="labelCode">
						<el-input clearable :placeholder="$t('label.id')" style="max-width: 180px" v-model="state.queryForm.labelCode" />
					</el-form-item>
					<el-form-item :label="$t('label.state')" prop="state">
						<el-select clearable v-model="state.queryForm.state">
							<el-option v-for="(item, index) in stateDict" :key="index" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item v-if="!isComp" :label="$t('label.merchantName')" prop="merchantId">
						<el-select clearable v-model="state.queryForm.merchantId">
							<el-option v-for="(item, index) in companyList" :key="index" :label="item.merchantName" :value="item.id"></el-option>
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
				<el-table-column :label="t('label.index')" type="index" width="60" />
				<el-table-column :label="t('label.id')" prop="labelCode" show-overflow-tooltip width="85" />
				<el-table-column :label="t('label.labelName')" min-width="100" prop="labelName" show-overflow-tooltip />
				<el-table-column v-if="!isComp" :label="t('label.merchantName')" prop="merchantName" min-width="130px" show-overflow-tooltip />
				<el-table-column width="90px" :label="t('label.state')" prop="state" show-overflow-tooltip>
					<template #default="scope">
						<!-- 状态（0-禁用，1-启用） -->
						{{ scope.row.state + '' === '1' ? $t('label.yes') : $t('label.no') }}
					</template>
				</el-table-column>
				<el-table-column :label="t('label.createTime')" prop="createTime" width="160" show-overflow-tooltip />

				<el-table-column fixed="right" :label="$t('common.action')" :width="isComp ? 150 : 100">
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
		<form-dialog :isComp="isComp" :stateDict="stateDict" @refresh="getDataList()" ref="formDialogRef" />
	</div>
</template>

<script lang="ts" name="Goodslabel" setup>
import { BasicTableProps, useTable } from '/@/hooks/table';
import { delObj, fetchList } from '/@/api/admin/label';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useDict } from '/@/hooks/dict';
import { useI18n } from 'vue-i18n';

import { fetchList as getCompanyList } from '/@/api/admin/merchant';

// 定义是否商户端，默认false
const props = defineProps({
	isComp: {
		type: Boolean,
		default: false,
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

// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
const { t } = useI18n();
// 定义查询字典
const stateDict = useDict('state').state;
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
		await delObj(ids);
		getDataList();
		useMessage().success(t('common.delSuccessText'));
	} catch (err: any) {
		useMessage().error(err.msg);
	}
};
</script>
