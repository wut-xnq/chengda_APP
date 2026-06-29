import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		url: '/admin/shoppingOrder/getPage',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object) {
	return request({
		url: '/admin/shoppingOrder',
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string) {
	return request({
		url: '/admin/shoppingOrder/detail/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object) {
	return request({
		url: '/admin/shoppingOrder',
		method: 'delete',
		data: ids,
	});
}

// 上下架
export function putShelveStateObj(obj?: Object) {
	return request({
		url: '/admin/shoppingOrder/shelve',
		method: 'put',
		data: obj,
	});
}
// 修改商品
export function putObj(obj?: Object) {
	return request({
		url: '/admin/shoppingOrder',
		method: 'put',
		data: obj,
	});
}

export function getDetails(obj: Object) {
	return request({
		url: '/admin/shoppingOrder/getshoppingOrderDetailsById/' + obj,
		method: 'get',
	});
}
