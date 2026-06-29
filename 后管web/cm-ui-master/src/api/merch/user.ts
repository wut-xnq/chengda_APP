import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		url: '/merch/user/page',
		method: 'get',
		params: query,
	});
}

export function addObj(obj?: Object) {
	// http://123.56.97.60:9999/merch/user?userId=&name=%E5%BC%A0%E4%B8%89&gender=F&age=4&phone=18409209898&userNumber=&verified=0&deptId=1
	return request({
		url: 'merch/user',
		// TODO:
		// url: `/merch/user?userId=&name=%E5%BC%A0%E4%B8%89&gender=F&age=4&phone=18409209898&userNumber=&verified=0&deptId=1`,
		method: 'post',
		data: obj,
	});
}

export function getObj(id?: string) {
	return request({
		url: '/merch/user/details/' + id,
		method: 'get',
	});
}

export function delObj(ids?: object) {
	return request({
		url: '/merch/user',
		method: 'delete',
		data: ids,
	});
}

// 编辑用户信息
export function putObj(obj: object) {
	return request({
		url: '/merch/user',
		method: 'put',
		data: obj,
	});
}
