<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('merchUser.phone')" prop="phone">
						<el-input clearable :placeholder="$t('merchUser.phone')" style="max-width: 180px" v-model="state.queryForm.phone" />
					</el-form-item>
					<el-form-item :label="$t('merchUser.verified')" prop="verified">
						<el-select clearable v-model="state.queryForm.verifiedState">
							<el-option v-for="(item, index) in verified" :key="index" :label="item.label" :value="item.value"></el-option>
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
				<el-table-column :label="t('merchUser.index')" type="index" width="60" />
				<el-table-column :label="t('merchUser.name') + '（最后一个字）'" prop="username" show-overflow-tooltip />
				<el-table-column :label="t('merchUser.phone')" prop="phone" show-overflow-tooltip />
				<el-table-column :label="t('merchUser.verified')" prop="verified" show-overflow-tooltip>
					<template #default="scope">
						{{ getLabelByArr(scope.row.verifiedState, verified) }}
					</template>
				</el-table-column>
				<el-table-column label="备注" prop="remarks" show-overflow-tooltip />
				<el-table-column label="操作时间" prop="updateTime" show-overflow-tooltip />
				<el-table-column :label="$t('common.action')" width="150">
					<template #default="scope">
						<el-button icon="edit-pen" v-if='scope.row.verifiedState != 2' @click="formDialogRef.openDialog(scope.row)" text type="primary">审核 </el-button>
						<!-- <el-button icon="delete" @click="handleDelete([scope.row.id])" text type="primary"> -->
						<!-- {{ $t('common.delBtn') }} -->
						<!-- </el-button> -->
					</template>
				</el-table-column>
			</el-table>
			<pagination @current-change="currentChangeHandle" @size-change="sizeChangeHandle" v-bind="state.pagination" />
		</div>
		<!-- 编辑、新增  -->
		<form-dialog :deptList="deptList" @refresh="getDataList()" ref="formDialogRef" />
	</div>
</template>

<script lang="ts" name="systemSysOauthcustomerDetails" setup>
import { BasicTableProps, useTable } from '/@/hooks/table';
import { delObj, fetchList, putObj } from '/@/api/merch/ident';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useDict } from '/@/hooks/dict';
import { useI18n } from 'vue-i18n';
import { getLabelByArr } from '/@/utils/other';
import { fetchList as getDeptList } from '/@/api/merch/dept';

// 所属公司列表
let deptList = ref([]);

const getCompany = () => {
	getDeptList({ size: 100 }).then((res) => {
		deptList.value = res.data.records;
	});
};
getCompany();
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
	state.queryForm = {};
	selectObjs.value = [];
	getDataList();
};

// 导出excel
const exportExcel = () => {
	downBlobFile('/admin/customer/export', state.queryForm, 'merchUser.xlsx');
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
