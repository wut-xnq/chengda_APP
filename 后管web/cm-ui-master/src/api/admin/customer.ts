import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		url: '/admin/customer/page',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object) {
	return request({
		url: '/admin/customer',
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string) {
	return request({
		url: '/admin/customer/details/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object) {
	return request({
		url: '/admin/customer',
		method: 'delete',
		data: ids,
	});
}
interface disabledObj {
	lockFlag: string;
	userId: string;
}
// 禁用或启用
export function putObj(obj: disabledObj) {
	return request({
		url: '/admin/customer/disabled?userId=' + obj.userId + '&lockFlag=' + obj.lockFlag,
		method: 'put',
		data: obj,
	});
}

export function refreshCache() {
	return request({
		url: '/admin/customer/sync',
		method: 'put',
	});
}

export function getDetails(obj: Object) {
	return request({
		url: '/admin/customer/getcustomerDetailsById/' + obj,
		method: 'get',
	});
}

export function validatecustomerId(rule: any, value: any, callback: any, isEdit: boolean) {
	if (isEdit) {
		return callback();
	}
	getDetails(value).then((res) => {
		const result = res.data;
		if (result !== null) {
			callback(new Error('编号已经存在'));
		} else {
			callback();
		}
	});
}
