import request from '/@/utils/request';

/**
 * 获取商户信息
 * @param {Object} query 
 * @return 
 */ 
export function company(query?: Object) {
	return request({
		url: '/merch/statistics/merchantInfo',
		method: 'get',
		params: query,
	});
}
/**
 * 订单收益统计
 * @param {Object} query 
 * @return 
 */ 
export function benefit(query?: Object) {
	return request({
		url: '/merch/statistics/order',
		method: 'get',
		params: query,
	});
}
/**
 * 商品销量统计
 * @param {Object} query 
 * @return 
 */ 
export function statistics(query?: Object) {
	return request({
		url: '/merch/statistics/product',
		method: 'get',
		params: query,
	});
}
/**
 * 员工积分统计
 * @param {Object} query 
 * @return 
 */ 
export function score(query?: Object) {
	return request({
		url: '/merch/statistics/staffScore',
		method: 'get',
		params: query,
	});
}
/**
 * 发起积分转化
 * @param {Object} query 
 * @return 
 */ 
export function trans(query?: Object) {
	return request({
		url: '/merch/statistics/tranScore',
		method: 'put',
		data: query,
	});
}