import {request} from 'utils/request/request';



 /**
  * 用户详情
  */
 export function getUserInfo(data:object){
 	return request(`/consu/user/details/${data.id}`,"GET",data)
 }
 
 /**
  * 用户 默认地址
  */
 export function getUserAddress(data:object){
 	return request(`/consu/userAddress/default/${data.userId}`,"GET",data)
 }
 
 /**
  * 用户 企业认证
  */
 export function identApply(data:object){
 	return request(`/consu/user/identApply`,"PUT",data)
 }
 /**
  * 用户 查询认证
  */
 export function identResult(data:object){
 	return request(`/consu/user/identResult/${data.userId}`,"GET",data)
 }
 /**
  * 好友详情
  */
 export function firend(data:any){
 	return request(`/consu/user/details`,"GET",data)
 }
 /**
  * 修改个人信息  同步到服务器
  */
 export function updateUser(data:any){
	 return new Promise(async (result,reject)=>{
		 const userInfo = uni.getStorageSync("s_userInfo");
		 if(!data.userId) data.userId = userInfo.userId
		 const res:any =await request(`/consu/user`,"put",data,true)
		 if(res.data.code === 1) reject(res.data.msg)
		 
		 for(let i in data){
			 userInfo[i] = data[i]
		 }
		 console.log("同步缓存信息------------")
		 uni.setStorageSync('s_userInfo',userInfo)
		 result(res)
	 })
	
 }
 
 
 /**
  * 获取 缓存中的 用户详情
  * @return userInfo  用户详情 
  */ 
 export function getSessinUserInfo(isGet = false){
 	return new Promise(async (result,reject)=>{
 		const userInfo = uni.getStorageSync("s_userInfo");
		console.log("---------------------------------------------",userInfo)
 		if(!isGet && userInfo) result(userInfo);
		else {
			const userLogin= uni.getStorageSync("firstLoginUser");
			const res:any =await getUserInfo({id:userLogin.user_id});
			if(res.data.code === 1) reject(res.data.msg)
			console.log("--------------------s_userInfo 不存在， 新s_userInfo----------------------------",res.data.data)
			uni.setStorageSync('s_userInfo',res.data.data)
			result(res.data.data)
		}
 		
 	})
 }
 
 
 