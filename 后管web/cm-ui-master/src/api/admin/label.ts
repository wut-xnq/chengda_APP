import request from '/@/utils/request';
function getPrefix(isCompa?: boolean) {
	return isCompa ? '/merch' : '/admin';
}
export function fetchList(query?: Object) {
	let prefix = '/admin';
	if (query && query.isCompa) {
		prefix = getPrefix(true);
	}
	return request({
		url: prefix + '/label/getPage',
		method: 'get',
		params: query,
	});
}
export function labelList(query?: Object) {
	let prefix = '/admin';
	if (query && query.isCompa) {
		prefix = getPrefix(true);
	}
	return request({
		url: prefix + '/label/getList',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/label',
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/label/detail/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/label',
		method: 'delete',
		data: ids,
	});
}
// 修改商品
export function putObj(obj?: Object, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/label',
		method: 'put',
		data: obj,
	});
}
export function getDetails(id: string, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + `/label/detail/${id}`,
		method: 'get',
	});
}
