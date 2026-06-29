<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('customer.customerId')" prop="name">
						<el-input clearable :placeholder="$t('customer.customerId')" style="max-width: 180px" v-model="state.queryForm.name" />
					</el-form-item>
					<el-form-item :label="$t('customer.phone')" prop="phone">
						<el-input clearable :placeholder="$t('customer.phone')" style="max-width: 180px" v-model="state.queryForm.phone" />
					</el-form-item>
					<el-form-item :label="$t('customer.lockFlag')" prop="lockFlag">
						<el-select clearable v-model="state.queryForm.lockFlag">
							<el-option v-for="(item, index) in lock_flag" :key="index" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="所属公司" >
						<el-select clearable v-model="state.queryForm.merchantId">
							<el-option v-for="(item, index) in company.list" :key="index" :label="item.merchantName" :value="item.merchantId"></el-option>
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
			<!-- <el-row>
				<div class="mb8" style="width: 100%">
					<el-button v-auth="'sys_customer_add'" @click="formDialogRef.openDialog()" class="ml10" icon="folder-add" type="primary">
						{{ $t('common.addBtn') }}
					</el-button>
					<el-button v-auth="'sys_customer_del'" plain @click="handleRefreshCache()" class="ml10" icon="refresh-left" type="primary">
						{{ $t('common.refreshCacheBtn') }}
					</el-button>

					<el-button
						plain
						:disabled="multiple"
						@click="handleDelete(selectObjs)"
						class="ml10"
						icon="Delete"
						type="primary"
						v-auth="'sys_customer_del'"
					>
						{{ $t('common.delBtn') }}
					</el-button>

					<right-toolbar
						:export="'sys_customer_del'"
						@exportExcel="exportExcel"
						@queryTable="getDataList"
						class="ml10"
						style="float: right; margin-right: 20px"
						v-model:showSearch="showSearch"
					></right-toolbar>
				</div>
			</el-row> -->
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
				<el-table-column :label="t('customer.index')" type="index" width="60" />
				<el-table-column :label="t('customer.customerId')" prop="name" show-overflow-tooltip />
				<el-table-column :label="t('customer.phone')" prop="phone" show-overflow-tooltip />
				<el-table-column :label="t('customer.gender')" prop="gender" show-overflow-tooltip width="80px">
					<template #default="scope">
						{{ scope.row.gender === 'M' ? $t('customer.man') : $t('customer.woman') }}
					</template>
				</el-table-column>
				<el-table-column :label="t('customer.age')" prop="age" show-overflow-tooltip width="80px"> </el-table-column>
				<el-table-column :label="t('customer.merchantName')" prop="merchantName" show-overflow-tooltip />
				<el-table-column :label="t('customer.verified')" prop="verified" show-overflow-tooltip>
					<template #default="scope">
						{{ scope.row.verified === "2" ? '认证通过' : scope.row.verified === '1' ? "认证不通过":"未认证" }}
					</template>
				</el-table-column>
				<el-table-column :label="t('customer.lockFlag')" prop="lockFlag" show-overflow-tooltip>
					<template #default="scope">
						<!-- <el-switch
							v-auth="'admin_customer_edit'"
							v-model="scope.row.lockFlag"
							inline-prompt
							active-value="0"
							inactive-value="9"
							active-text="启用"
							inactive-text="禁用"
							@change="lockFlagChange(scope.row.userId, scope.row.lockFlag)"
						></el-switch> -->
						{{scope.row.lockFlag == 0?"启用":"禁用"}}
					</template>
				</el-table-column>
				<el-table-column :label="t('customer.createTime')" prop="createTime" show-overflow-tooltip />

				<el-table-column :label="$t('common.action')" width="150" v-auth="'admin_customer_edit'">
					<!-- <template #default="scope">
						<el-button icon="view" @click="handleDelete([scope.row.id])" text type="primary">
							{{ $t('common.detailBtn') }}
						</el-button>
					</template> -->
					
					<template #default="scope">
						<el-switch
							v-model="scope.row.lockFlag"
							inline-prompt
							active-value="0"
							inactive-value="9"
							active-text="启用"
							inactive-text="禁用"
							@change="lockFlagChange(scope.row.userId, scope.row.lockFlag)"
						></el-switch>
						
						
						<!-- <el-button @click="saveResetAccount(scope.row.id)" text type="primary">
							重置密码
						</el-button> -->
					</template>
				</el-table-column>
			</el-table>
			<pagination @current-change="currentChangeHandle" @size-change="sizeChangeHandle" v-bind="state.pagination" />
		</div>
		<!-- 编辑、新增  -->
		<form-dialog @refresh="getDataList()" ref="formDialogRef" />
	</div>
</template>

<script lang="ts" name="systemSysOauthcustomerDetails" setup>
import { BasicTableProps, useTable } from '/@/hooks/table';
import { delObj, fetchList, refreshCache ,putObj } from '/@/api/admin/customer';
import { resetAccount,companyList } from '/@/api/admin/merchant';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useDict } from '/@/hooks/dict';
import { useI18n } from 'vue-i18n';

// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
const { t } = useI18n();
// 定义查询字典

const { lock_flag } = useDict('lock_flag');
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
});

const company = reactive({
	list: []
})
function getCompanyList(){
	companyList().then(res=>{
		if(res.code === 0){
			company.list = res.data
		}
	})
}
getCompanyList()

//  table hook
const { getDataList, currentChangeHandle, sizeChangeHandle, downBlobFile, tableStyle } = useTable(state);
const lockFlagChange = (userId: string, lockFlag: string) => {
	let params = {
		userId: userId,
		lockFlag: lockFlag,
	};
	putObj(params);
	useMessage().success(t('common.editSuccessText'));
};
// 删除缓存
const handleRefreshCache = () => {
	refreshCache().then(() => {
		useMessage().success('同步成功');
	});
};

const resetQuery = () => {
	queryRef.value.resetFields();
	state.queryForm = {};
	selectObjs.value = [];
	getDataList();
};

// 导出excel
const exportExcel = () => {
	downBlobFile('/admin/customer/export', state.queryForm, 'customer.xlsx');
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

/**
 * 重置密码
 */
function saveResetAccount(id:string){
	resetAccount({userId:id}).then(res=>{
		if(res.code === 0){
			useMessage().success(res.msg)
		}else{
			useMessage().wraning(res.msg)
		}
	}).catch(err=>{
		useMessage().wraning(err.msg)
		console.log(err)
	})
}
</script>
