import request from '/@/utils/request';
function getPrefix(isCompa?: boolean) {
	return isCompa ? '/merch' : '/admin';
}
// @ts-ignore
export function fetchList(query) {
	let prefix = '/admin';
	if (query && query.isCompa) {
		prefix = getPrefix(true);
	}
	return request({
		url: prefix + '/product/getPage',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/product',
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/product/detail/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/product',
		method: 'delete',
		data: ids,
	});
}

// 修改商品
export function putObj(obj?: Object, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/product',
		method: 'put',
		data: obj,
	});
}
export interface ShelveObj {
	id: string;
	shelveArea: string;
	shelveState: string;
}
// 上下架
export function putShelveStateObj(obj: ShelveObj, isCompa?: boolean) {
	let prefix = getPrefix(isCompa);
	return request({
		url: prefix + '/product/shelve?id=' + obj.id + '&shelveArea=' + obj.shelveArea + '&shelveState=' + obj.shelveState,
		method: 'put',
		data: obj,
	});
}
