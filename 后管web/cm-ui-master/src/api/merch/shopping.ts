import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		url: '/merch/shoppingOrder/getPage',
		method: 'get',
		params: query,
	});
}
export function logistics(query?: Object) {
	return request({
		url: '/merch/logistics/getCompanyList',
		method: 'get',
		params: query,
	});
}
export function addObj(obj?: Object) {
	return request({
		url: '/merch/shoppingOrder',
		method: 'post',
		data: obj,
	});
}
export function deliverGoods(obj?: Object) {
	return request({
		url: '/merch/shoppingOrder/deliver',
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string) {
	return request({
		url: '/merch/shoppingOrder/detail/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object) {
	return request({
		url: '/merch/shoppingOrder',
		method: 'delete',
		data: ids,
	});
}

// 上下架
export function putShelveStateObj(obj?: Object) {
	return request({
		url: '/merch/shoppingOrder/shelve',
		method: 'put',
		data: obj,
	});
}
// 修改商品
export function putObj(obj?: Object) {
	return request({
		url: '/merch/shoppingOrder',
		method: 'put',
		data: obj,
	});
}

export function getDetails(obj: Object) {
	return request({
		url: '/merch/shoppingOrder/getshoppingOrderDetailsById/' + obj,
		method: 'get',
	});
}
