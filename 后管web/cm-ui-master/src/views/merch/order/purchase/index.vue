<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item label="商户订单号" prop="paymentNo">
						<el-input clearable placeholder="商户订单号" style="max-width: 180px"
							v-model="state.queryForm.paymentNo" />
					</el-form-item>
					<el-form-item :label="$t('charge.paymentType')" prop="paymentType">
						<el-select clearable v-model="state.queryForm.paymentType">
							<el-option v-for="(item, index) in payment_type" :key="index" :label="item.label"
								:value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item :label="$t('charge.createTime')" prop="createTime">
						<el-date-picker type="datetime" clearable :placeholder="'请选择'"
							value-format="YYYY-MM-DD HH:mm:ss" style="width: 190px"
							v-model="state.queryForm.createTimeStart" />
						-
						<el-date-picker type="datetime" clearable :placeholder="'请选择'"
							value-format="YYYY-MM-DD HH:mm:ss" style="width: 190px"
							v-model="state.queryForm.createTimeEnd" />
					</el-form-item>
					<el-form-item :label="$t('charge.totalPrice')" prop="totalPrice">
						<el-input-number clearable :placeholder="'￥'" style="max-width: 150px"
							v-model="state.queryForm.amountMin" />
						-
						<el-input-number clearable :placeholder="'￥'" style="max-width: 150px"
							v-model="state.queryForm.amountMax" />
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
					<el-button plain :disabled="multiple" @click="handleDelete(selectObjs)" class="ml10" icon="Delete"
						type="primary">
						{{ $t('common.delBtn') }}
					</el-button>
				</div>
			</el-row>
			<el-table :data="state.dataList" @selection-change="handleSelectionChange" style="width: 100%"
				v-loading="state.loading" border :cell-style="tableStyle.cellStyle"
				:header-cell-style="tableStyle.headerCellStyle">
				<el-table-column align="center" type="selection" width="40" />
				<el-table-column :label="t('charge.index')" type="index" width="60" />
				<el-table-column label="商户订单号" prop="paymentNo" show-overflow-tooltip />
				<el-table-column :label="t('charge.createTime')" width="200" prop="createTime"
					show-overflow-tooltip />
				<el-table-column :label="t('charge.userChargeAccount')" prop="username" show-overflow-tooltip> </el-table-column>
				<!-- <el-table-column :label="t('charge.chargePics')" prop="chargePics" show-overflow-tooltip min-width="120px">
					<template #default="scope">
						<el-image :src="scope.row.chargePics" :fit="'cover'"></el-image>
					</template>
				</el-table-column> -->
				<el-table-column :label="t('charge.amount')" prop="amount" width="140" show-overflow-tooltip />
				<el-table-column :label="t('charge.paymentType')" prop="paymentType" width="140" show-overflow-tooltip>
					<template #default="scope">
						{{ getLabelByArr(scope.row.paymentType, payment_type) }}
					</template>
				</el-table-column>

				<el-table-column width="90px" :label="t('charge.paymentState')" prop="paymentState"
					show-overflow-tooltip>
					<template #default="scope">
						{{ getLabelByArr(scope.row.paymentState, payment_state) }}
					</template>
				</el-table-column>
				<el-table-column label="支付宝交易号" prop="tradeFlowNo" 
					show-overflow-tooltip />
				<el-table-column :label="$t('common.action')" width="160">
					<template #default="scope">
					<!-- 	<el-button v-if="scope.row.paymentState === 3 && scope.row.state === 1"
							@click="sendDialogRef.openDialog(scope.row.id,scope.row.shoppingOrderNo)" text
							type="primary">发货</el-button> -->

						<el-button icon="delete" @click="handleDelete([scope.row.id])" text type="primary">
							{{ $t('common.delBtn') }}
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<pagination @current-change="currentChangeHandle" @size-change="sizeChangeHandle"
				v-bind="state.pagination" />
		</div>
		<!-- 编辑、新增  -->
		<form-dialog @refresh="getDataList()" ref="formDialogRef" />

		<SendDialog @refresh="getDataList()" ref="sendDialogRef" />
	</div>
</template>

<script lang="ts" name="systemSysOauthchargeDetails" setup>
	import { BasicTableProps, useTable } from '/@/hooks/table';
	import { delObj, fetchList } from '/@/api/merch/charge';
	import { useMessage, useMessageBox } from '/@/hooks/message';
	import { useDict } from '/@/hooks/dict';
	import { useI18n } from 'vue-i18n';
	import { getLabelByArr } from '/@/utils/other';

	const values = ref()

	// 引入组件
	const FormDialog = defineAsyncComponent(() => import('./form.vue'));
	const SendDialog = defineAsyncComponent(() => import('./send.vue'));
	const sendDialogRef = ref();
	const { t } = useI18n();
	// 定义查询字典
	const { payment_type, payment_state } = useDict('payment_type', 'payment_state');
	// 定义变量内容
	const formDialogRef = ref();
	const queryRef = ref();
	// 搜索变量
	const showSearch = ref(true);
	// 多选变量
	const selectObjs = ref([]) as any;
	const multiple = ref(true);

	const state : BasicTableProps = reactive<BasicTableProps>({
		queryForm: {},
		pageList: fetchList,
		descs: ['id'],
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
	const handleSelectionChange = (objs : { id : string }[]) => {
		selectObjs.value = objs.map(({ id }) => id);
		multiple.value = !objs.length;
	};

	// 删除操作
	const handleDelete = async (ids : string[]) => {
		try {
			await useMessageBox().confirm(t('common.delConfirmText'));
		} catch {
			return;
		}

		try {
			await delObj(ids);
			getDataList();
			useMessage().success(t('common.delSuccessText'));
		} catch (err : any) {
			useMessage().error(err.msg);
		}
	};
</script>