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
		url: prefix + '/logistics/getPage',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object) {
	return request({
		url: '/merch/logistics',
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string) {
	return request({
		url: '/merch/logistics/detail/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object, isComp?: boolean) {
	return request({
		url: isComp ? '/merch/logistics' : '/admin/logistics',
		method: 'delete',
		data: ids,
	});
}

// 修改商品
export function putObj(obj?: Object) {
	return request({
		url: '/merch/logistics',
		method: 'put',
		data: obj,
	});
}
