<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('cityCode.cityPid')" prop="cityPid">
						<el-input clearable :placeholder="$t('cityCode.cityPid')" style="max-width: 180px" v-model="state.queryForm.name" />
					</el-form-item>
					<el-form-item :label="$t('cityCode.cityName')" prop="cityName">
						<el-input clearable :placeholder="$t('cityCode.cityName')" style="max-width: 180px" v-model="state.queryForm.cityName" />
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
					<el-button plain :disabled="multiple" @click="handleDelete(selectObjs)" class="ml10" icon="Delete" type="primary">
						{{ $t('common.delBtn') }}
					</el-button>
				</div>
			</el-row> -->
			<el-table
				:data="state.dataList"
				style="width: 100%"
				v-loading="state.loading"
				border
				:cell-style="tableStyle.cellStyle"
				:header-cell-style="tableStyle.headerCellStyle"
				row-key="cityId"
				:lazy="false"
				:tree-props="{ children: 'childrenList', hasChildren: true }"
			>
				<el-table-column align="center" type="selection" width="40" />
				<el-table-column :label="t('cityCode.index')" type="index" width="60" />
				<el-table-column :label="t('cityCode.cityName')" prop="cityName" show-overflow-tooltip />
				<el-table-column :label="t('cityCode.cityId')" prop="cityId" show-overflow-tooltip> </el-table-column>
				<el-table-column :label="t('cityCode.type')" prop="type" show-overflow-tooltip>
					<template #default="scope">
						{{ getLabelByArr(scope.row.type, city_type) }}
					</template>
				</el-table-column>
				<!-- <el-table-column :label="t('cityCode.createTime')" prop="createTime" width="160" show-overflow-tooltip />

				<el-table-column :label="$t('common.action')" width="100">
					<template #default="scope">
						<el-button icon="delete" @click="handleDelete([scope.row.id])" text type="primary">
							{{ $t('common.delBtn') }}
						</el-button>
					</template>
				</el-table-column> -->
			</el-table>
			<pagination @current-change="currentChangeHandle" @size-change="sizeChangeHandle" v-bind="state.pagination" />
		</div>
	</div>
</template>

<script lang="ts" name="systemSysOauthcityCodeDetails" setup>
import { BasicTableProps, useTable } from '/@/hooks/table';
import { fetchList } from '/@/api/admin/cityCode';
import { useMessage, useMessageBox } from '/@/hooks/message';
import { useDict } from '/@/hooks/dict';
import { useI18n } from 'vue-i18n';
import { getLabelByArr } from '/@/utils/other';
// 引入组件
const { t } = useI18n();
// 定义查询字典
const { city_type } = useDict('city_type');
// 定义变量内容
const formDialogRef = ref();
const queryRef = ref();
// 搜索变量
const showSearch = ref(false);
// 多选变量
const selectObjs = ref([]) as any;
const multiple = ref(true);

const state: BasicTableProps = reactive<BasicTableProps>({
	queryForm: {},
	isPage: false,
	pageList: fetchList,
	props: {
		item: 'data',
	},
});

//  table hook
const { getDataList, currentChangeHandle, sizeChangeHandle, downBlobFile, tableStyle } = useTable(state);

const resetQuery = () => {
	queryRef.value.resetFields();
	selectObjs.value = [];
	getDataList();
};
</script>
