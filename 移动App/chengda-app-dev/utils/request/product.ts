import {request} from 'utils/request/request';

 /**
  * 商品详情
  */
 export function getProduct(data:object){
 	return request(`/consu/product/${data.id}`,"GET",data)
 }
 /**
  * 商品运费
  */
 export function productFreight(data:object){
 	return request(`/consu/logistic/fee`,"POST",data)
 }
 /**
  * 生成订单
  */
 export function createOrder(data:object){
 	return request(`/consu/order`,"POST",data,true)
 }
 /**
  * 交易订单
  */
 export function ailiPayment(data:object){
 	return request(`/consu/alipay/pay`,"POST",data)
 }
 /**
  * 交易成功
  */
 export function paymentSuccess(data:object){
 	return request(`/consu/alipay/payResult`,"POST",data)
 }
 /**
  * 催发货
  */
 export function warn(data:object){
 	return request(`/consu/order/promot/${data.orderId}`,"put",data)
 }
 
 /**
  * 质检详情
  */
 export function quality(data:object){
 	return request(`/consu//exam/${data.id}`,"get",data)
 }
 
 
