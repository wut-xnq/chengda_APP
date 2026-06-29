<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item label="编号" prop="shoppingNo">
						<el-input clearable placeholder="编号" style="max-width: 180px" v-model="state.queryForm.orderNo" />
					</el-form-item>
					<!-- <el-form-item label="商户订单号" prop="paymentNo">
						<el-input clearable placeholder="商户订单号" style="max-width: 180px" v-model="state.queryForm.paymentNo" />
					</el-form-item> -->
					<el-form-item :label="$t('shopping.createTime')" prop="createTime">
						<el-date-picker type="datetime" clearable :placeholder="'请选择'" style="width: 190px" v-model="state.queryForm.createTimeStart" />
						-
						<el-date-picker type="datetime" clearable :placeholder="'请选择'" style="width: 190px" v-model="state.queryForm.createTimeEnd" />
					</el-form-item>
					<el-form-item label="下单用户" prop="username">
						<el-input clearable placeholder="下单用户" style="max-width: 180px" v-model="state.queryForm.username" />
					</el-form-item>
					<el-form-item :label="$t('shopping.state')" prop="order_state">
						<el-select clearable v-model="state.queryForm.order_state">
							<el-option v-for="(item, index) in order_state" :key="index" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="商品归属" >
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
			<el-row>
				<div class="mb8" style="width: 100%">
					<el-button plain :disabled="multiple" v-auth="'admin_order_del'" @click="handleDelete(selectObjs)" class="ml10" icon="Delete" type="primary">
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
				<el-table-column :label="t('shopping.index')" type="index" width="60" />
				<el-table-column label="编号" prop="orderNo" show-overflow-tooltip />
				<el-table-column :label="t('shopping.createTime')" width="200" prop="createTime" show-overflow-tooltip />
				<el-table-column label="下单用户" prop="username" show-overflow-tooltip width="200"> </el-table-column>
				<!-- <el-table-column :label="t('shopping.shoppingPics')" prop="shoppingPics" show-overflow-tooltip min-width="120px">
					<template #default="scope">
						<el-image :src="scope.row.shoppingPics" :fit="'cover'"></el-image>
					</template>
				</el-table-column> -->
				<el-table-column :label="t('shopping.totalPrice')" width="140" show-overflow-tooltip >
					<template #default="scope">
							{{scope.row.totalPrice || scope.row.realPrice }}
						</template>
				</el-table-column>
				<el-table-column :label="t('shopping.logisticsNo')" prop="logisticsNo" width="140" show-overflow-tooltip />
				<el-table-column :label="t('shopping.paymentType')" prop="paymentType" width="140" show-overflow-tooltip >
					<template #default="scope">
							{{scope.row.paymentType?scope.row.paymentType === 2? '支付宝支付':'微信支付':'' }}
						</template>
					</el-table-column>
				<el-table-column :label="t('shopping.orderProducts')" prop="orderProducts" width="140" show-overflow-tooltip >
					<template #default="scope">
						<!-- 状态（0-禁用，1-启用） -->
						{{ scope.row.orderProductList.map((res:any)=>res.productName).join() }}
					</template>
				</el-table-column>

				<el-table-column :label="t('shopping.merchantId')" prop="merchantName" width="200" show-overflow-tooltip />
				<el-table-column width="90px" :label="t('shopping.state')" prop="state" show-overflow-tooltip>
					<template #default="scope">
						<!-- 状态（0-禁用，1-启用） -->
						{{ scope.row.state + '' === '1' ? $t('shopping.yes') : $t('shopping.no') }}
					</template>
				</el-table-column>
				<el-table-column label="备注" prop="remarks" show-overflow-tooltip />
				<el-table-column label="商户订单号" prop="paymentNo"  show-overflow-tooltip />
				<el-table-column :label="$t('common.action')" width="100">
					<template #default="scope">
						<el-button icon="delete" v-auth="'admin_order_del'" @click="handleDelete([scope.row.id])" disabled text type="primary">
							{{ $t('common.delBtn') }}
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<pagination @current-change="currentChangeHandle" @size-change="sizeChangeHandle" v-bind="state.pagination" />
		</div>
		<!-- 编辑、新增  -->
		<form-dialog @refresh="getDataList()" ref="formDialogRef" />
	</div>
</template>

<script lang="ts" name="systemSysOauthshoppingDetails" setup>
import { BasicTableProps, useTable } from '/@/hooks/table';
import { delObj, fetchList } from '/@/api/admin/shopping';
import { companyList } from '/@/api/admin/merchant';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useDict } from '/@/hooks/dict';
import { useI18n } from 'vue-i18n';

// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
const { t } = useI18n();
// 定义查询字典
const { order_state } = useDict('order_state');
const { payment_type } = useDict('payment_type');
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

//  table hook
const { getDataList, currentChangeHandle, sizeChangeHandle, downBlobFile, tableStyle } = useTable(state);
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
