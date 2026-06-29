import request from '/@/utils/request';
function getPrefix(isCompa?: boolean) {
	return isCompa ? '/merch' : '/admin';
}
export function fetchList(query?: Object) {
	let prefix = getPrefix(query?.isCompa);
	return request({
		url: prefix + '/spec/getPage',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		// @ts-ignore
		url: `/merch/spec?id=&merchantId=${obj.merchantId}&groupId=${obj.groupId}&specName=${obj.specName}&state=${obj.state}`, // url: prefix + '/spec?',
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/spec/detail/' + id,
		method: 'get',
	});
}
// 平台端和商户端都-可删除
export function delObj(ids?: object, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/spec',
		method: 'delete',
		data: ids,
	});
}

// 修改商品规格
export function putObj(obj?: Object) {
	// let prefix = getPrefix(isCompa);
	return request({
		url: `/merch/spec?id=&merchantId=${obj.merchantId}&groupId=${obj.groupId}&specName=${obj.specName}&state=${obj.state}`, // url: prefix + '/spec?',
		// url: prefix + '/spec',
		method: 'put',
		data: obj,
	});
}

// 规格组相关接口
export function getSpecGroup(merchantId?: string) {
	return request({
		url: `/merch/specGroup/getListByMerchantId/${merchantId}`,
		method: 'get',
	});
}

export function editSpecGroup(data: object) {
	// specGroup?id=1851853571541331969&merchantId=1847476322969456641&groupName=4567
	return request({
		url: `/merch/specGroup?id=${data.id}&merchantId=${data.merchantId}&groupName=${data.groupName}`,
		method: 'put',
		data,
	});
}
interface addObj {
	merchantId: string;
	groupName: string;
}
export function addSpecGroup(data: addObj) {
	return request({
		url: `/merch/specGroup?merchantId=` + data.merchantId + '&groupName=' + data.groupName,
		method: 'post',
		data,
	});
}

export function delGroup(data: any) {
	return request({
		url: `/merch/specGroup`,
		method: 'delete',
		data,
	});
}


// 规格列表
export function getSpecList(data?: object) {
	return request({
		url: `/merch/spec/getList?merchantId=${data.merchantId}`,
		method: 'get',
		data
	});
}
