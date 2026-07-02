const baseUrl = 'https://chengboda.com/api'
// const baseUrl = 'http://192.168.31.99:9999'

const commonHeaders = {
    'content-type': 'application/x-www-form-urlencoded',
    Authorization: 'Basic ' + btoa('custom:custom'),
    skipToken: true,
    'Access-Control-Allow-Origin': '*'
};

// 验证码检验
export async function verifyCode(phone: string, smsCode: string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/sms/verifyCode?phone=${encodeURIComponent(phone)}&code=${encodeURIComponent(smsCode)}`,
            method: 'POST'
        });
        return response;
    } catch (error) {
        console.error('验证短信验证码失败:', error);
        throw error;
    }
}


// 用户端登录

export async function authenticate(phone: string, inviteCode: string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + '/auth/oauth2/token',
            method: 'POST',
            data: {
                mobile: phone,
                inviteCode: inviteCode,
                grant_type: 'mobile',
                scope: 'server'
            },
            header: commonHeaders,
        });
		uni.setStorageSync('firstLoginUser', response.data);
		console.log('缓存数据',response.data)
        return response;
    } catch (error) {
        console.error('登录失败:', error);
        throw error;
    }
}
// 网易云信注册/登录
export async function refreshToken(phone: string, access_token: string): Promise<any> {
    try {
		const firstLoginUser = uni.getStorageSync("firstLoginUser");
		const token = firstLoginUser?.access_token;
		if (!token) {
			console.error('未找到 access_token，请先登录');
			throw new Error('登录信息丢失，请重新登录');
		}
		
        const response = await uni.request({
            // url: baseUrl + '/consu/sms/refreshToken?phone='+phone,
            url: baseUrl + '/consu/sms/refreshToken',
            method: 'POST',
            data: {
                phone: phone
            },
            header: {
				"Authorization": 'Bearer ' + token,
				"content-type": 'application/x-www-form-urlencoded',
			}
        });
        return response;
    } catch (error) {
        console.error('刷新网易云信令牌失败:', error);
        throw error;
    }
}


const composeRequest = (url: string, method: any, params: {} | undefined, header = {}) => {
    const {access_token} = uni.getStorageSync('firstLoginUser');
       // 检查 access_token 是否存在
    //    if (!access_token) {
    //     console.log('access_token 不存在，请先登录！');
    //     uni.navigateTo({
    //         url: '/pages/Login/index'
    //     });
    //     return; 
    // }
    return uni.request({
        url: `${baseUrl}${url}`,
        header: {
            "Authorization": `Bearer ${access_token}`,
            "content-type": 'application/json',
            ...header
        },
        method,
        data: params
    });
};

// 我的订单-所有订单
export async function queryUserOrders(params): Promise<any> {
    const {current, size, paymentState, state} = params;
    try {
        return await composeRequest(
            `/consu/order/userOrderPage?size=${size}&current=${current}&paymentState=${paymentState}&state=${state}`,
            'GET'
        );
    } catch (error) {
        console.error('查询用户订单列表失败:', error);
        throw error;
    }
}

// 查看订单详情
export async function queryOrderInfoById(orderId): Promise<any> {
    try {
        return await composeRequest(
            `/consu/order/details/${orderId}`,
            'GET'
        );
    } catch (error) {
        console.error('查询订单详情失败:', error);
        throw error;
    }
}


// 我的订单-删除订单
export async function deleteUserOrder(orders): Promise<any> {
    try {
        return await composeRequest(
            '/consu/order',
            'DELETE',
            orders
        )
    } catch (error) {
        console.error('删除用户订单失败:', error);
        throw error;
    }
}

// 我的订单-确认收货
export async function confirmReceipt(orderId): Promise<any> {
    try {
        return await composeRequest(
            `/consu/order/receive/${orderId}`,
            'PUT',
            {}
        );
    } catch (error) {
        console.error('确认收货失败:', error);
        throw error;
    }
}

// 我的订单-订单详情
export async function queryOrderDetails(orderId): Promise<any> {
    try {
        return await composeRequest(
            `/consu/order/details/${orderId}`,
            'GET'
        );
    } catch (error) {
        console.error('查询订单详情失败:', error);
        throw error;
    }
}


/**
 * 设置>>收货地址
 */
export async function resDeliveryAddress(userId): Promise<any> {
    try {
        return await composeRequest(
            `/consu/userAddress/list/${userId}`,
            'GET',
        );
    } catch (error) {
        console.error('接收收货地址分页失败:', error);
        throw error;
    }
}

/**
 * 修改地址
 * @param {string} access_token
 * @param {any} pageForms
 * @return
 */
export async function editAddress ( pageForms: any ): Promise<any> {
	try{
        return await composeRequest(
            '/consu/userAddress',
            'PUT',
            pageForms
        )
	}catch(error){
		console.error('修改地址提交失败:', error);
		throw error;
	}
}

/**
 * 新增地址/post
 * @param {string} access_token
 * @param {any} newCardLists
 * @return
 */
export async function newAddress ( newCardLists: any ): Promise<any> {
	try{
        return await composeRequest(
            '/consu/userAddress',
            "POST",
            newCardLists
        )
	}catch(error){
		console.error('接收收货地址分页失败:', error);
		throw error;
	}
}

/**
 * 级联列表
 * 全部城市数据
 * FullCities
 */
export async function FullCities ( access_token:string): Promise<any> {
	try{
		const respone = await uni.request({
			url: baseUrl + '/consu/cityCode',
			method:"GET",
			header: {
			    "Authorization": 'Bearer ' + access_token ,
			    "content-type": 'application/x-www-form-urlencoded',
			}
		})
		return respone
	}catch(error){
		console.error('接收城市失败:', error);
		throw error;
	}
}


/**
 * 城市编码管理
 * 三级选项城市的的接口
 * resTertiaryOptionCity
 */
export async function resTertiaryOptionCity ( cityPid:number,access_token:string): Promise<any> {
	try{
		const respone = await uni.request({
			url: baseUrl + `/consu/cityCode/getLowerList/${encodeURIComponent(cityPid)}`,
			method:"GET",
			header: {
			    "Authorization": 'Bearer ' + access_token ,
			    "content-type": 'application/x-www-form-urlencoded',
			}
		})
		return respone
	}catch(error){
		console.error('接收三级选项城市失败:', error);
		throw error;
	}
}



/**
 * 购物模块
 * resShoppingHomepage/分页
 * GET
 */
export async function resShoppingHomepage(size:number,
											current:number,
											shelveArea:string,
											productName:string,
											access_token: string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/product/page?size=${encodeURIComponent(size)}&current=${encodeURIComponent(current)}&shelveArea=${encodeURIComponent(shelveArea)}&productName=${encodeURIComponent(productName)}`,
            method: "GET",
            header: {
                "Authorization": 'Bearer ' + access_token ,
                "content-type": 'application/x-www-form-urlencoded',
            }
        });
        return response;
    } catch (error) {
        console.error('接收购物模块分页失败:', error);
        throw error;
    }
}
/**
 * 质检单独接口
 * resQualityInspection
 */
export async function resQualityInspection(size:number,
											current:number,
												fileTitle:string,
												access_token: string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/exam/page?size=${encodeURIComponent(size)}&current=${encodeURIComponent(current)}&fileTitle=${encodeURIComponent(fileTitle)}`,
            method: "GET",
            header: {
                "Authorization": 'Bearer ' + access_token ,
                "content-type": 'application/x-www-form-urlencoded',
            }
        });
        return response;
    } catch (error) {
        console.error('接收质检详情页失败:', error);
        throw error;
    }
}

// 商品最新质检
//NewQualityInspection
export async function resNewQualityInspection(productId:number): Promise<any> {
    try {
        return await composeRequest (
            `/consu/exam/listByProduct/${encodeURIComponent(productId)}`,
            "GET"
        )
    } catch (error) {
        console.error('接收质检详情页失败:', error);
        throw error;
    }
}


/**
 * 商品详情
 * resProductDetails/详情页面
 */
export async function resProductDetails( id:string ): Promise<any> {
    try {
        return await composeRequest(
            `/consu/product/${encodeURIComponent(id)}`,
            "GET"
        )
    } catch (error) {
        console.error('接收商品详情页失败:', error);
        throw error;
    }
}

/**
 * 商品规格组管理
 * CommodityManagement
 * @param {string} merchantId
 * @param {string} access_token
 * @return
 */
export async function CommodityManagement(merchantId: string , access_token: string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/specGroup/list/${encodeURIComponent(merchantId)}`,
            method: "GET",
            header: {
                "Authorization": 'Bearer ' + access_token ,
                "content-type": 'application/x-www-form-urlencoded',
            }
        });
        return response;
    } catch (error) {
        console.error('接收商品规格组管理失败:', error);
        throw error;
    }
}

/**
 * 商品规格管理
 * CommoditySpecification
 * @param {string} productId
 * @param {string} access_token
 * @return
 */
export async function CommoditySpecification(productId: string , access_token: string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/spec/getListByProductId/${encodeURIComponent(productId)}`,
            method: "GET",
            header: {
                "Authorization": 'Bearer ' + access_token ,
                "content-type": 'application/x-www-form-urlencoded',
            }
        });
        return response;
    } catch (error) {
        console.error('接收商品规格管理失败:', error);
        throw error;
    }
}

/**
 * 商品SKU管理
 * SkuManagement
 * @param {string} productId
 * @param {string} access_token
 * @return
 */
export async function SkuManagement(productId: string , access_token: string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/sku/getListByProductId/${encodeURIComponent(productId)}`,
            method: "GET",
            header: {
                "Authorization": 'Bearer ' + access_token ,
                "content-type": 'application/x-www-form-urlencoded',
            }
        });
        return response;
    } catch (error) {
        console.error('接收商品SKU管理失败:', error);
        throw error;
    }
}

/**
 * 购物订单管理商品页面点击购买操作
 * resClickBuy
 * @param {string} access_token
 * @return
 */
export async function resClickBuy(access_token:string , data:any): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/order`,
            method: 'POST',
            header: {
                "Authorization": 'Bearer ' + access_token ,
                // "content-type": 'application/x-www-form-urlencoded',
            },
			data: data
        });
        return response;
    } catch (error) {
        console.error('商品选购之后购买的接口失败:', error);
        throw error;
    }
}


/**
 * 积分转换
 * @param {number} id
 * @param {string} access_token
 * @return
 */
export async function resIntegralConversion(id:number , access_token:string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/user/listForTranScore/${encodeURIComponent(id)}`,
            method: "GET",
            header: {
                "Authorization": 'Bearer ' + access_token ,
                "content-type": 'application/x-www-form-urlencoded',
            },
        });
        return response;
    } catch (error) {
        console.error('商品选购之后购买的接口失败:', error);
        throw error;
    }
}
// 发起积分转换
// initiatingIntegralConversion
export async function initiatingIntegralConversion(access_token:string , data:any): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/user/tranScore`,
            method: 'PUT',
            header: {
                "Authorization": 'Bearer ' + access_token ,
                // "content-type": 'application/x-www-form-urlencoded',
            },
			data: data
        });
        return response;
    } catch (error) {
        console.error('发起积分转换接口失败:', error);
        throw error;
    }
}

// 处理积分
// processingIntegral
export async function processingIntegral(access_token:string , data:any): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/user/tranScore/handle`,
            method: 'PUT',
            header: {
                "Authorization": 'Bearer ' + access_token ,
                // "content-type": 'application/x-www-form-urlencoded',
            },
			data: data
        });
        return response;
    } catch (error) {
        console.error('处理积分接口失败:', error);
        throw error;
    }
}

// 查看物流
// LogisticsView
export async function resLogisticsView( orderId:number, access_token:string): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/logistic/trace?orderId=${encodeURIComponent(orderId)}`,
            method: "POST",
            header: {
                "Authorization": 'Bearer ' + access_token ,
                "content-type": 'application/x-www-form-urlencoded',
            },
        });
        return response;
    } catch (error) {
        console.error('查看物流接口通信失败:', error);
        throw error;
    }
}

// 修改订单收货地址
// modifyShippingAddress
export async function modifyShippingAddress( orderId:string , userAddressId:string , access_token:string ): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/order/receive/${encodeURIComponent(orderId)}/${encodeURIComponent(userAddressId)}`,
            method: 'PUT',
            header: {
                "Authorization": 'Bearer ' + access_token ,
                // "content-type": 'application/x-www-form-urlencoded',
            },
        });
        return response;
    } catch (error) {
        console.error('修改订单收货地址接口通信失败:', error);
        throw error;
    }  
}




// 发起付款
// oderPay
export async function oderPay( orderNo:string, subject:string ,totalAmount:number, access_token:string ): Promise<any> {
    try {
        const response = await uni.request({
            url: baseUrl + `/consu/alipay/pay?orderNo=${encodeURIComponent(orderNo)}&subject=${encodeURIComponent(subject)}&totalAmount=${encodeURIComponent(totalAmount)}`,
            method: 'POST',
            header: {
                "Authorization": 'Bearer ' + access_token ,
                // "content-type": 'application/x-www-form-urlencoded',
            },
        });
        return response;
    } catch (error) {
        console.error('修改订单收货地址接口通信失败:', error);
        throw error;
    }  
}
