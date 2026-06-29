<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view" style="width: 100%">
			<el-row class="ml10" v-show="showSearch && !isComp">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('merchant.merchantId')" prop="merchant">
						<el-input :placeholder="$t('merchant.placeholder')" style="max-width: 180px" v-model="state.queryForm.merchantId" />
					</el-form-item>
					<el-form-item :label="$t('merchant.merchantName')" prop="clientSecret">
						<el-input :placeholder="$t('merchant.placeholder')" style="max-width: 180px" v-model="state.queryForm.merchantName" />
					</el-form-item>
					<el-form-item>
						<el-button @click="getDataList" icon="search" type="primary">
							{{ $t('common.queryBtn') }}
						</el-button>
						<el-button @click="resetQuery" icon="Refresh">{{ $t('common.resetBtn') }}</el-button>
					</el-form-item>
				</el-form>
			</el-row>
			<el-row v-if="!isComp">
				<div class="mb8" style="width: 100%">
					<el-button v-auth="'admin_merch_add'" @click="formDialogRef.openDialog()" class="ml10" icon="folder-add" type="primary">
						{{ $t('common.addBtn') }}
					</el-button>
					<el-button plain :disabled="multiple" @click="handleDelete(selectObjs)" class="ml10" icon="Delete" type="primary" v-auth="'admin_merch_del'">
						{{ $t('common.delBtn') }}
					</el-button>

					<right-toolbar
						:export="'sys_client_del'"
						@exportExcel="exportExcel"
						@queryTable="getDataList"
						class="ml10"
						style="float: right; margin-right: 20px"
						v-model:showSearch="showSearch"
					></right-toolbar>
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
				<el-table-column :label="t('merchant.index')" type="index" width="60" />
				<el-table-column :label="t('merchant.merchantId')" prop="id" show-overflow-tooltip width="200" />
				<el-table-column :label="t('merchant.merchantName')" prop="merchantName" min-width="120" show-overflow-tooltip />
				<el-table-column :label="t('merchant.chargePersonName')" prop="chargePersonName" show-overflow-tooltip width="100px" />
				<el-table-column :label="t('merchant.creditCode')" prop="creditCode" show-overflow-tooltip width="200px"> </el-table-column>
				<el-table-column :label="t('merchant.address')" prop="address" min-width="140" show-overflow-tooltip />
				<el-table-column label="手机号" prop="chargePersonPhone" min-width="140" show-overflow-tooltip />
				<el-table-column label="账户状态" prop="state" width="120">
					<template #default="scope">
						{{scope.row.state === '0'?'禁用':'启用'}}
					</template>
				</el-table-column>
				<el-table-column :label="$t('common.action')" width="240">
					<template #default="scope">
						<!-- 只有在商户端，商户自己编辑自己的信息 -->
						<!-- <el-button icon="edit-pen" v-if="isComp" v-auth="'merch_edit'" @click="formDialogRef.openDialog(scope.row.id)" text type="primary"
							>{{ $t('common.editBtn') }}
						</el-button> -->
						<el-button v-if="!isComp"  @click="saveResetAccount(scope.row.id)" text type="primary">
							重置密码
						</el-button>
						<el-button v-if="!isComp"  @click="saveDisabledAccount(scope.row)" text type="primary">
							{{scope.row.state === '1'?'禁用':'启用'}}账号
						</el-button>
						
						<el-button v-if="!isComp" v-auth="'admin_merch_del'" icon="delete" @click="handleDelete([scope.row.id])" text type="primary">
							{{ $t('common.delBtn') }}
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<pagination @current-change="currentChangeHandle" @size-change="sizeChangeHandle" v-bind="state.pagination" />
		</div>
		<!-- 编辑、新增  -->
		<form-dialog @refresh="getDataList()" :isComp="isComp" ref="formDialogRef" />
	</div>
</template>

<script lang="ts" name="systemSysOauthClientDetails" setup>
import { BasicTableProps, useTable } from '/@/hooks/table';
import { delObj, fetchList,resetMAccount,putObj } from '/@/api/admin/merchant';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useDict } from '/@/hooks/dict';
import { useI18n } from 'vue-i18n';
import { useUserInfo } from '/@/stores/userInfo';
const uData = useUserInfo().userInfos;
// 定义是否商户端，默认false
const props = defineProps({
	isComp: {
		type: Boolean,
		default: false,
	},
});
// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
const { t } = useI18n();
// 定义查询字典

const { grant_types } = useDict('grant_types');
// 定义变量内容
const formDialogRef = ref();
const queryRef = ref();
// 搜索变量
const showSearch = ref(true);
// 多选变量
const selectObjs = ref([]) as any;
const multiple = ref(true);
const state: BasicTableProps = reactive<BasicTableProps>({
	queryForm: {
		phone: props.isComp ? uData.user.phone : '',
		
	},
	pageList: fetchList,
	descs: ['id'],
	isCompa: props.isComp,
	isPage: props.isComp ? false : true,
});

//  table hook
const { getDataList, currentChangeHandle, sizeChangeHandle, downBlobFile, tableStyle } = useTable(state);

const resetQuery = () => {
	queryRef.value.resetFields();
	state.queryForm.merchantId = '';
	state.queryForm.merchantName = '';
	selectObjs.value = [];
	getDataList();
};

// 导出excel
const exportExcel = () => {
	downBlobFile('/admin/merchant/export', state.queryForm, 'merchant.xlsx');
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
 * 用户 重置密码
 */
function saveResetAccount(id:string){
	resetMAccount({merchantId:id}).then(res=>{
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
/**
 * 禁用账号
 */
function saveDisabledAccount(params:any){
	putObj({merchantId:params.id,state:params.state === '0'?'1':'0'}).then(res=>{
		if(res.code === 0){
			useMessage().success(res.msg)
			resetQuery()
		}else{
			useMessage().wraning(res.msg)
		}
	}).catch(err=>{
		useMessage().wraning(err.msg)
		console.log(err)
	})
}
</script>
