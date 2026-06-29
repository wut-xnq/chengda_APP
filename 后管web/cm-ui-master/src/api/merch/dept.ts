import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		url: '/merch/dept/page',
		method: 'get',
		params: query,
	});
}
interface putObj {
	deptId?: string;
	name: string;
	deptNumber: string;
	merchantId: string;
}
export function addObj(obj: putObj) {
	return request({
		url: `/merch/dept?deptId=${obj.deptId}&name=${obj.name}&deptNumber=${obj.deptNumber}&merchantId=${obj.merchantId}`,
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string) {
	return request({
		url: '/merch/dept/details/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object) {
	return request({
		url: '/merch/dept',
		method: 'delete',
		data: ids,
	});
}

// 编辑用户信息
export function putObj(obj: putObj) {
	return request({
		url: `/merch/dept?deptId=${obj.deptId}&name=${obj.name}&deptNumber=${obj.deptNumber}&merchantId=${obj.merchantId}`,
		method: 'put',
		data: obj,
	});
}
