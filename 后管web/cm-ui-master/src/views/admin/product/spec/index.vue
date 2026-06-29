<template>
	<div class="layout-padding">
		<div class="layout-padding-auto layout-padding-view">
			<el-row class="ml10" v-show="showSearch">
				<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList" ref="queryRef">
					<el-form-item :label="$t('spec.specName')" prop="name">
						<el-input clearable :placeholder="$t('spec.specName')" style="max-width: 180px"
							v-model="state.queryForm.specName" />
					</el-form-item>
					<!-- <el-form-item :label="$t('spec.groupName')" prop="groupName">
						<el-input clearable :placeholder="$t('spec.groupName')" style="max-width: 180px" v-model="state.queryForm.groupName" />
					</el-form-item> -->
					<el-form-item :label="$t('spec.state')" prop="state">
						<el-select clearable v-model="state.queryForm.state">
							<el-option v-for="(item, index) in stateDict" :key="index" :label="item.label"
								:value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item v-if="!isComp" :label="$t('spec.merchantName')" prop="merchantId">
						<el-select clearable v-model="state.queryForm.merchantId">
							<el-option v-for="(item, index) in companyList" :key="index" :label="item.merchantName"
								:value="item.id"></el-option>
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
					<el-button v-if="isComp" @click="formDialogRef.openDialog()" class="ml10" icon="folder-add"
						type="primary"> 新增规格 </el-button>
					<el-button plain :disabled="multiple" @click="handleDelete(selectObjs)" class="ml10" icon="Delete"
						type="primary">
						{{ $t('common.delBtn') }}
					</el-button>
				</div>
			</el-row>
			<!-- 商户端含规格组 -->
			<div class="flex-between">
				<el-button-group class="ml-4">
					<el-button v-for="(item,index) in specGroupData" :key="item.id"
						:type="groupSpecActived === item.id ? 'primary' : ''">
						<!-- handleTabsEdit -->
						<span @click="handleClick(item.id)">{{ item.groupName }}</span>
						<el-icon style="margin-left: 10px" @click="groupFormDialogRef.openDialog(item)">
							<EditPen />
						</el-icon>
						<el-icon style="margin-left: 10px" v-if="index> 0" @click="del(item)">
							<Delete />
						</el-icon>
					</el-button>
				</el-button-group>
				<!-- style="width: 100%" -->
				<!-- <el-tabs closable stretch v-model="groupSpecActived" type="card" class="demo-tabs" @tab-change="handleClick" @edit="handleTabsEdit">
					<template #close-icon>
						<el-icon><EditPen /></el-icon>
					</template>
					<el-tab-pane v-for="item in specGroupData" :key="item.id" :label="item.groupName" :name="item.id"></el-tab-pane>
				</el-tabs> -->
				<el-button v-if="isComp" @click="groupFormDialogRef.openDialog()" class="ml10" icon="folder-add"
					type="primary"> 新增规格组 </el-button>
			</div>
			<!-- {{ specGroupData }} -->
			<el-table :data="state.dataList" @selection-change="handleSelectionChange" style="width: 100%"
				v-loading="state.loading" border :cell-style="tableStyle.cellStyle"
				:header-cell-style="tableStyle.headerCellStyle">
				<el-table-column align="center" type="selection" width="40" />
				<el-table-column :label="t('spec.index')" type="index" width="60" />
				<el-table-column :label="t('spec.id')" prop="id" show-overflow-tooltip width="85" />
				<el-table-column :label="t('spec.specName')" min-width="100" prop="specName" show-overflow-tooltip />
				<el-table-column :label="t('spec.groupName')" prop="groupName" show-overflow-tooltip width="90px">
				</el-table-column>
				<el-table-column width="90px" :label="t('spec.state')" prop="state" show-overflow-tooltip>
					<template #default="scope">
						<!-- 状态（0-禁用，1-启用） -->
						{{ scope.row.state + '' === '1' ? $t('spec.yes') : $t('spec.no') }}
					</template>
				</el-table-column>
				<el-table-column v-if="!isComp" :label="t('spec.merchantName')" prop="merchantName" min-width="130px"
					show-overflow-tooltip />
				<el-table-column :label="t('spec.createTime')" prop="createTime" width="160" show-overflow-tooltip />

				<el-table-column :label="$t('common.action')" :width="isComp ? 160 : 100">
					<template #default="scope">
						<el-button icon="edit" v-show="isComp" type="primary" text
							@click="formDialogRef.openDialog(scope.row)">{{
							$t('common.editBtn')
						}}</el-button>
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
		<form-dialog :groupSpecList="specGroupData" :isComp="isComp" @refresh="getDataList()" ref="formDialogRef" />
		<!-- 规格组新增编辑 -->
		<groupform-dialog :isComp="isComp" @refresh="refreshed()" ref="groupFormDialogRef" />
	</div>
</template>

<script lang="ts" name="systemSysOauthspecDetails" setup>
	import { BasicTableProps, useTable } from '/@/hooks/table';
	import { delObj, fetchList, getSpecGroup, addSpecGroup, editSpecGroup, delGroup } from '/@/api/admin/spec';
	import { useMessage, useMessageBox } from '/@/hooks/message';
	import { useDict } from '/@/hooks/dict';
	import { useI18n } from 'vue-i18n';
	import { useUserInfo } from '/@/stores/userInfo';

	import { fetchList as getCompanyList } from '/@/api/admin/merchant';
	import { ElMessage, ElMessageBox, TabPaneName } from 'element-plus';
	// 定义是否商户端，默认false
	const props = defineProps({
		isComp: {
			type: Boolean,
			default: false,
		},
	});
	// 当前登录用户信息
	const uData = useUserInfo().userInfos;
	let groupSpecActived = ref('');
	// 所属公司列表
	let companyList = ref([]);
	type Item = {
		id : string;
		groupName : string;
	};
	let specGroupData = ref<Item[]>([]);

	const state : BasicTableProps = reactive<BasicTableProps>({
		queryForm: {
			groupId: groupSpecActived.value,
		},
		pageList: fetchList,
		descs: ['id'],
		isCompa: props.isComp,
	});
	const refreshed = () => {
		getGroupDataList();
		getDataList();
	};
	// 引入组件
	const FormDialog = defineAsyncComponent(() => import('./form.vue'));
	const groupformDialog = defineAsyncComponent(() => import('./groupForm.vue'));

	const { t } = useI18n();
	// 定义查询字典
	const stateDict = useDict('state').state;
	// 定义变量内容
	const formDialogRef = ref();
	const groupFormDialogRef = ref();

	const queryRef = ref();
	// 搜索变量
	const showSearch = ref(true);
	// 多选变量
	const selectObjs = ref([]) as any;
	const multiple = ref(true);

	//  table hook
	const getCompany = async () => {
		if (!props.isComp) {
			await getCompanyList().then((res) => {
				companyList.value = res.data.records;
			});
		}
		await getGroupDataList();
	};

	const getGroupDataList = async () => {
		if (props.isComp) {
			await getSpecGroup(uData.merchantId).then((res) => {
				let allItem = [{ id: '', groupName: '全部' }];
				specGroupData.value = allItem.concat(res.data);

				// if (res.data?.length) {
				// 	groupSpecActived.value = res.data[0].id;
				// 	state.queryForm.groupId = groupSpecActived.value;
				// 	getDataList();
				// }
			});
		}
	};
	getCompany();
	const { getDataList, currentChangeHandle, sizeChangeHandle, downBlobFile, tableStyle } = useTable(state);

	const handleClick = (val : string) => {
		console.log(val);
		groupSpecActived.value = val;
		state.queryForm.groupId = groupSpecActived.value;
		getDataList();
	};
	const resetQuery = () => {
		queryRef.value.resetFields();
		selectObjs.value = [];
		state.queryForm = {};
		getDataList();
	};

	// 多选事件
	const handleSelectionChange = (objs : { id : string }[]) => {
		selectObjs.value = objs.map(({ id }) => id);
		multiple.value = !objs.length;
	};

	function del(data : any) {
		try{
			ElMessageBox.confirm(
				'此操作将删除规格组，是否继续?',
				'提示',
				{
					confirmButtonText: '确定',
					cancelButtonText: '关闭',
					type: 'warning',
				}
			)
				.then(() => {
					delGroup([data.id]).then(res => {
						if(res.code === 1) throw new Error(res.msg)
						console.log(res)
						refreshed()
						ElMessage({
						    message: res.msg,
						    type: 'success',
						    plain: true,
						  })
					})
				})
		}catch(err){
			ElMessage({
			    message:err.message || err.msg,
			    type: 'success',
			    plain: true,
			  })
			console.log(err)
		}
		
	}

	// 删除操作
	const handleDelete = async (ids : string[]) => {
		try {
			await useMessageBox().confirm(t('common.delConfirmText'));
		} catch {
			return;
		}

		try {
			await delObj(ids, props.isComp);
			getDataList();
			useMessage().success(t('common.delSuccessText'));
		} catch (err : any) {
			useMessage().error(err.msg);
		}
	};
	const handleTabsEdit = (targetName : TabPaneName, action : 'remove' | 'add') => {
		// console.log(444, targetName);
		// @ts-ignore
		handleGroupDelete([targetName]);
	};
	// 删除规格组
	const handleGroupDelete = async (id : string[]) => {
		try {
			await useMessageBox().confirm(t('common.delConfirmText'));
		} catch {
			return;
		}

		try {
			await editSpecGroup(id);
			refreshed();
			useMessage().success(t('common.delSuccessText'));
		} catch (err : any) {
			useMessage().error(err.msg);
		}
	};
</script>