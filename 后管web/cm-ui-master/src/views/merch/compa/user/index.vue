<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('merchUser.name')" prop="name">
						<el-input clearable :placeholder="$t('merchUser.name')" style="max-width: 180px" v-model="state.queryForm.name" />
					</el-form-item>
					<el-form-item :label="$t('merchUser.verified')" prop="verified">
						<el-select clearable v-model="state.queryForm.verified">
							<el-option v-for="(item, index) in verified" :key="index" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item :label="$t('merchUser.deptName')" prop="phone">
						<el-select clearable v-model="state.queryForm.deptId">
							<el-option v-for="(item, index) in deptList" :key="index" :label="item.name" :value="item.deptId"></el-option>
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
				<el-table-column :label="t('merchUser.index')" type="index" width="60" />
				<el-table-column :label="t('merchUser.userId')" prop="userId" min-width="100" />
				<el-table-column :label="t('merchUser.name')" prop="name" show-overflow-tooltip />
				<el-table-column :label="t('merchUser.age')" prop="age" show-overflow-tooltip width="80px"> </el-table-column>
				<el-table-column :label="t('merchUser.gender')" prop="gender" show-overflow-tooltip width="80px">
					<template #default="scope">
						{{ getLabelByArr(scope.row.gender, user_gender) }}
						<!-- {{ scope.row.gender === 'M' ? $t('merchUser.man') : $t('merchUser.woman') }} -->
					</template>
				</el-table-column>
				<el-table-column :label="t('merchUser.phone')" prop="phone" show-overflow-tooltip />
				<el-table-column :label="t('merchUser.deptName')" prop="deptName" show-overflow-tooltip>
					<!-- <template #default="scope">
						{{ getLabelByArr(scope.row.deptName, deptList) }}
					</template> -->
				</el-table-column>
				<el-table-column :label="t('merchUser.verified')" prop="verified" show-overflow-tooltip>
					<template #default="scope">
						{{ scope.row.verified === "2" ? '认证通过' : scope.row.verified === '1' ? "认证不通过":"未认证" }}
					</template>
				</el-table-column>
				<!-- <el-table-column :label="t('merchUser.createTime')" prop="createTime" show-overflow-tooltip /> -->
				<el-table-column :label="$t('common.action')" width="150">
					<template #default="scope">
						<!-- 只有在商户端，商户自己编辑自己的信息 -->
						<el-button icon="edit-pen" @click="formDialogRef.openDialog(scope.row.userId)" text type="primary">{{ $t('common.editBtn') }} </el-button>
						<el-button icon="delete" @click="handleDelete([scope.row.userId])" text type="primary">
							{{ $t('common.delBtn') }}
						</el-button>
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
import { delObj, fetchList, putObj } from '/@/api/merch/user';
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

const { verified, user_gender } = useDict('verified', 'user_gender');
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
const handleSelectionChange = (objs: { userId: string }[]) => {
	selectObjs.value = objs.map(({ userId }) => userId);
	multiple.value = !objs.length;
};

// 删除操作
const handleDelete = async (ids: string[]) => {
	// console.log(333, ids);
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
