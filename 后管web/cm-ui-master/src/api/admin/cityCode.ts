import request from '/@/utils/request';

export function fetchList(query?: Object) {
	return request({
		url: '/admin/cityCode/getAll',
		method: 'get',
		params: query,
	});
}
