import request from '/@/utils/request';

/**
 * 获取 设置
 * @param {Object} query 
 * @return 
 */ 
export function select(query?: Object) {
	return request({
		url: '/admin/setting',
		method: 'get',
		params: query,
	});
}
/**
 * 新增
 * @param {Object} query 
 * @return 
 */ 
export function add(query?: Object) {
	return request({
		url: '/admin/setting',
		method: 'post',
		data: query,
	});
}
export function del(query?: Object) {
	return request({
		url: '/admin/setting',
		method: 'del',
		data: query,
	});
}
export function update(query?: Object) {
	return request({
		url: '/admin/setting',
		method: 'put',
		data: query,
	});
}
