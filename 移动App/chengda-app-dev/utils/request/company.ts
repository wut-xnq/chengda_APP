import {request} from 'utils/request/request';

 /**
  * 企业列表
  */
 export function getCompany(data:object = {}){
 	return request(`/consu/merchant/nameList`,"GET",data);
 }
