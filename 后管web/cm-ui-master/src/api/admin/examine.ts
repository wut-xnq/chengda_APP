import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		url: '/admin/examine/page',
		method: 'get',
		params: query,
	});
}
export function goodsList(query?: Object) {
	return request({
		url: '/admin/examine/goodsList',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object) {
	return request({
		url: '/admin/examine',
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string) {
	return request({
		url: '/admin/examine/' + id,
		method: 'get',
	});
}

export function delObj(ids?: Object) {
	return request({
		url: '/admin/examine',
		method: 'delete',
		data: ids,
	});
}

export function putObj(obj?: Object) {
	return request({
		url: '/admin/examine',
		method: 'put',
		data: obj,
	});
}
