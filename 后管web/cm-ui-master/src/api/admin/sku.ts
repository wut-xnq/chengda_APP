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
		url: prefix + '/sku/getPage',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object) {
	return request({
		url: '/merch/sku',
		method: 'post',
		data: obj,
	});
}

// 仅商户端可编辑,sku详情
export function getObj(id?: string) {
	return request({
		url: '/merch/sku/detail/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object, isComp?: boolean) {
	return request({
		url: isComp ? '/merch/sku' : '/admin/sku',
		method: 'delete',
		data: ids,
	});
}

// 上下架
export function putShelveStateObj(obj?: Object) {
	return request({
		url: '/admin/sku/shelve',
		method: 'put',
		data: obj,
	});
}
// 修改商品
export function putObj(obj?: Object) {
	return request({
		url: '/merch/sku',
		method: 'put',
		data: obj,
	});
}

export function getDetails(obj: Object) {
	return request({
		url: '/admin/sku/getskuDetailsById/' + obj,
		method: 'get',
	});
}
