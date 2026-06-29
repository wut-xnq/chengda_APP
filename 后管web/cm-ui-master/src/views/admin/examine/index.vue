<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row v-show="showSearch">
				<el-form :model="state.queryForm" ref="queryRef" :inline="true" @keyup.enter="getDataList">
					<el-form-item :label="$t('examine.createTime')" prop="original">
						<el-date-picker
							:end-placeholder="$t('examine.inputEndPlaceholderTip')"
							:start-placeholder="$t('examine.inputStartPlaceholderTip')"
							range-separator="To"
							type="datetimerange"
							v-model="state.queryForm.createTime"
							value-format="YYYY-MM-DD hh:mm:ss"
						/>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" icon="Search" @click="getListByTime">{{ $t('common.queryBtn') }}</el-button>
						<el-button icon="Refresh" @click="resetQuery">{{ $t('common.resetBtn') }}</el-button>
					</el-form-item>
				</el-form>
			</el-row>
			<el-row>
				<div class="mb8" style="width: 100%">
					<el-button icon="folder-add" type="primary" class="ml10" v-auth="'sys_file_del'" @click="formDialogRef.openDialog()">
						{{ $t('examine.addBtn') }}
					</el-button>
					<el-button :disabled="multiple" icon="Delete" type="primary" class="ml10" v-auth="'sys_file_del'" @click="handleDelete(selectObjs)">
						{{ $t('common.delBtn') }}
					</el-button>
					<right-toolbar
						v-model:showSearch="showSearch"
						class="ml10"
						style="float: right; margin-right: 20px"
						@queryTable="getDataList"
					></right-toolbar>
				</div>
			</el-row>
			<el-table
				:data="state.dataList"
				v-loading="state.loading"
				style="width: 100%"
				@selection-change="handleSelectionChange"
				border
				:cell-style="tableStyle.cellStyle"
				:header-cell-style="tableStyle.headerCellStyle"
			>
				<el-table-column type="selection" width="40" align="center" />
				<el-table-column type="index" :label="t('examine.index')" width="80" />
				<el-table-column prop="id" :label="t('examine.fileId')" show-overflow-tooltip />
				<el-table-column prop="fileTitle" :label="t('examine.title')" show-overflow-tooltip />
				<el-table-column prop="content" :label="t('examine.content')" show-overflow-tooltip />
				<!-- <el-table-column prop="images" :label="t('examine.images')" show-overflow-tooltip /> -->
				<el-table-column prop="publishDate" width="100" :label="t('examine.publishDate')" show-overflow-tooltip />
				<el-table-column prop="updateBy" width="100" :label="t('examine.operator')" show-overflow-tooltip />

				<el-table-column prop="fileList" :label="t('examine.fileList')" min-width="160px" show-overflow-tooltip>
					<template #default="scope">
						{{scope.row.fileList?.map((res:any)=>res.fileName).join()}}
					</template>
					<!-- fileList -->
				</el-table-column>
				<el-table-column prop="createTime" width="160" :label="t('examine.createTime')" show-overflow-tooltip />
				<!-- publishDate -->
				<el-table-column :label="$t('common.action')" width="150">
					<template #default="scope">
						<el-button icon="edit" type="primary" text @click="formDialogRef.openDialog(scope.row.id)">{{ $t('common.editBtn') }}</el-button>
						<el-button icon="delete" text type="primary" @click="handleDelete([scope.row.id])">{{ $t('common.delBtn') }}</el-button>
					</template>
				</el-table-column>
			</el-table>
			<pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" v-bind="state.pagination" />
		</div>
		<!-- 编辑、新增  -->
		<form-dialog ref="formDialogRef" @refresh="getDataList()" />
	</div>
</template>

<script setup lang="ts" name="examine">
import { BasicTableProps, useTable } from '/@/hooks/table';
import { fetchList, delObj } from '/@/api/admin/examine';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useI18n } from 'vue-i18n';
import { downBlobFile } from '/@/utils/other';

// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
const { t } = useI18n();

// 定义变量内容
const queryRef = ref();
const formDialogRef = ref();
// 搜索变量
const showSearch = ref(true);
// 多选变量
const selectObjs = ref([]) as any;
const multiple = ref(true);

const state: BasicTableProps = reactive<BasicTableProps>({
	queryForm: {},
	pageList: fetchList,
	descs: ['create_time'],
});

//  table hook
const { getDataList, currentChangeHandle, sizeChangeHandle, tableStyle } = useTable(state);

const getListByTime = () => {
	if (state.queryForm.createTime?.length) {
		state.queryForm.startTime = state.queryForm.createTime[0];
		state.queryForm.endTime = state.queryForm.createTime[1];
	}
	getDataList();
};
// 清空搜索条件
const resetQuery = () => {
	state.queryForm.createTime = [];
	state.queryForm.startTime = '';
	state.queryForm.endTime = '';
	queryRef.value.resetFields();
	getDataList();
};

// 文件下载
// const download = (row: any) => {
// 	// downBlobFile('/admin/sys-file/' + row.bucketName + '/' + row.fileName, null, row.fileName);
// };
// 修改

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
