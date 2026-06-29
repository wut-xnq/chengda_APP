import { baseUrl } from 'utils/request/request';

/**
 * 上传文件
 */
export function uploadFile(file : any) {
	return new Promise(result => {
		const firstLoginUser = uni.getStorageSync("firstLoginUser");
		const token = firstLoginUser.access_token
		console.log(file)
		uni.uploadFile({
			url: baseUrl+'/consu/file/upload',
			// files: [file],
			filePath: file,
			name: 'file',
			header: {
				"Authorization": 'Bearer ' + token
			},
			success: (uploadFileRes) => {
				console.log("上传文件 ----------------",uploadFileRes, JSON.parse(uploadFileRes.data));
				result(JSON.parse(uploadFileRes.data))
			},
			fail: (err) => {
				console.error('上传失败：', err);
			}
		});
	})

}