import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		url: '/merch/ident/page',
		method: 'get',
		params: query,
	});
}

export function delObj(id?: string) {
	return request({
		url: `/merch/ident/recovery/${id}`,
		method: 'delete',
	});
}

// 审核（认证）
export function putObj(obj: object) {
	return request({
		url: '/merch/ident/audit',
		method: 'put',
		data: obj,
	});
}
