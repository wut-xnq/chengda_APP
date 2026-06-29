export const baseUrl = 'https://chengboda.com/api';
// export const baseUrl = 'http://192.168.31.99:9999';
/**
 * 
 * @param {string} url 		请求地址
 * @param {any} method 		请求方式
 * @param {object} data 	请求数据
 * @param {boolean} type	请求参数类型 false 表单格式	true json格式
 * @return 
 */ 
export function request(url:string,method:any,data?:object,type:boole = false){
	return new Promise((result,reject)=>{
		const firstLoginUser = uni.getStorageSync("firstLoginUser");
		const token = firstLoginUser.access_token
		console.log("token ",token)
		if(!token){
			console.log("token 不存在 退出登录!");
			uni.reLaunch({url:'/pages/Login/index'})
			reject("请求令牌已过期")
		}
		const response = uni.request({
		    url:baseUrl+ url,
		    method: method,
		    header: {
		        "Authorization": 'Bearer ' + token ,
		        'Content-Type': type?'application/json; charset=UTF-8':'application/x-www-form-urlencoded; charset=UTF-8',
		    },
			data: data
		});
		response.then(res=>{
			const result = res.data;
			if(result.msg === '请求令牌已过期'){
				console.log("token 已过期 退出登录",res)
				uni.reLaunch({url:'/pages/Login/index'})
				reject("请求令牌已过期")
			}
			
		})
		result(response)
	})
}