<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('merchDept.name')" prop="name">
						<el-input clearable :placeholder="$t('merchDept.name')" style="max-width: 180px" v-model="state.queryForm.name" />
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
					<el-button @click="formDialogRef.openDialog()" class="ml10" icon="folder-add" type="primary">
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
				<el-table-column :label="t('merchDept.index')" type="index" width="60" />
				<el-table-column :label="t('merchDept.deptNumber')" prop="deptNumber" min-width="100" />
				<el-table-column :label="t('merchDept.name')" prop="name" show-overflow-tooltip />
				<el-table-column :label="t('merchDept.createTime')" prop="createTime" show-overflow-tooltip />
				<el-table-column :label="$t('common.action')" width="150">
					<template #default="scope">
						<!-- 只有在商户端，商户自己编辑自己的信息 -->
						<el-button icon="edit-pen" @click="formDialogRef.openDialog(scope.row.deptId)" text type="primary">{{ $t('common.editBtn') }} </el-button>
						<el-button icon="delete" @click="handleDelete([scope.row.deptId])" text type="primary">
							{{ $t('common.delBtn') }}
						</el-button>
					</template>
				</el-table-column>
				<!-- <el-table-column :label="$t('common.action')" width="150">
					<template #default="scope">
						<el-button icon="view" @click="handleDelete([scope.row.id])" text type="primary">
							{{ $t('common.detailBtn') }}
						</el-button>
					</template>
				</el-table-column> -->
			</el-table>
			<pagination @current-change="currentChangeHandle" @size-change="sizeChangeHandle" v-bind="state.pagination" />
		</div>
		<!-- 编辑、新增  -->
		<form-dialog @refresh="getDataList()" ref="formDialogRef" />
	</div>
</template>

<script lang="ts" name="systemSysOauthcustomerDetails" setup>
import { BasicTableProps, useTable } from '/@/hooks/table';
import { delObj, fetchList, putObj } from '/@/api/merch/dept';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useDict } from '/@/hooks/dict';
import { useI18n } from 'vue-i18n';
import { getLabelByArr } from '/@/utils/other';

// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
const { t } = useI18n();
// 定义查询字典

const { verified } = useDict('verified');
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
	descs: [''],
});

//  table hook
const { getDataList, currentChangeHandle, sizeChangeHandle, downBlobFile, tableStyle } = useTable(state);

const resetQuery = () => {
	queryRef.value.resetFields();
	// state.queryForm = {};
	selectObjs.value = [];
	getDataList();
};

// 导出excel
const exportExcel = () => {
	downBlobFile('/admin/customer/export', state.queryForm, 'merchDept.xlsx');
};

// 多选事件
const handleSelectionChange = (objs: { deptId: string }[]) => {
	selectObjs.value = objs.map(({ deptId }) => deptId);
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
