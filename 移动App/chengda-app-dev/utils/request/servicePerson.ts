import {request} from 'utils/request/request';
/**
 * 企业员工列表
 */
export function staff(data:object){
	return request("/consu/merchant/getUserPage","GET",data)
}
 /**
  * 用户详情
  */
 export function getUserInfo(data:object){
	 console.log(data)
 	return request(`/consu/merchant/getUserDetails/${data.userId}`,"GET",data)
 }