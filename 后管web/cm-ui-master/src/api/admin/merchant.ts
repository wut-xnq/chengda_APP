import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		// @ts-ignore
		url: query?.isCompa ? '/merch/merchant/list' : '/admin/merchant/page',
		method: 'get',
		params: query,
	});
}
export function companyList(query?: Object) {
	return request({
		// @ts-ignore
		url:'/admin/merchant/list',
		method: 'get',
		params: query,
	});
}
export function addObj(obj?: Object) {
	return request({
		url: '/admin/merchant',
		method: 'post',
		data: obj,
	});
}
// 仅商户端可编辑
export function getObj(id?: string) {
	return request({
		url: '/merch/merchant/info',
		method: 'get',
		params: { id },
	});
}

export function delObj(ids?: object) {
	return request({
		url: '/admin/merchant',
		method: 'delete',
		data: ids,
	});
}

export function putObj(obj?: Object) {
	return request({
		url: obj?.isCompa ?'/merch/merchant':'/admin/merchant',
		method: 'put',
		params: obj,
	});
}

export function getCityCodeAll() {
	return request({
		url: '/admin/cityCode/getAll',
		method: 'get',
	});
}

export function getDetails(obj: Object) {
	return request({
		url: '/admin/merchant/getmerchantDetailsById/' + obj,
		method: 'get',
	});
}

export function resetAccount(obj?: Object) {
	return request({
		url: '/admin/customer/resetPwd',
		method: 'put',
		params: obj,
	});
}
export function resetMAccount(obj?: Object) {
	return request({
		url: '/admin/merchant/resetPwd',
		method: 'put',
		params: obj,
	});
}
