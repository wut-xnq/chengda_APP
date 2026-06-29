package com.jiaolong.cm.consu.api.express;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * desc: 查询物流跟踪工具类
 * user: pan
 * date: 2024-10-17 18:31
 */
public class HttpUtils {

	private final static String CHARSET_DEFAULT = "UTF-8";

	/**
	 * post请求  编码格式默认UTF-8
	 *
	 * @param url 请求url
	 * @return
	 */
	public static HttpResult doPost(String url, Object obj) throws IOException, IllegalAccessException {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse resp = null;

		HttpResult result = new HttpResult();
		try {
			Map<String, String> params = ObjectToMapUtils.objectToMap(obj);
			HttpPost httpPost = new HttpPost(url);

			if (params != null && params.size() > 0) {
				List<NameValuePair> list = new ArrayList<>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(list, CHARSET_DEFAULT));
			}

			resp = httpClient.execute(httpPost);
			String body = EntityUtils.toString(resp.getEntity(), CHARSET_DEFAULT);
			int statusCode = resp.getStatusLine().getStatusCode();
			result.setStatus(statusCode);
			result.setBody(body);
		} finally {
			if (null != resp) {
				resp.close();
			}
		}
		return result;
	}

	/**
	 * post请求  编码格式默认UTF-8
	 *
	 * @param url 请求url
	 * @return
	 */
	public static HttpResult doPostFile(String url, File file, int connectTimeout, int socketTimeout) throws IOException {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse resp = null;

		HttpResult result = new HttpResult();
		try {

			HttpPost httpPost = new HttpPost(url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(connectTimeout)
					.setSocketTimeout(socketTimeout).build();
			httpPost.setConfig(requestConfig);
			// 把文件加到HTTP的post请求中
			builder.addBinaryBody(
					"file",
					new FileInputStream(file),
					ContentType.MULTIPART_FORM_DATA,
					file.getName()
			);
			//HttpEntity
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			resp = httpClient.execute(httpPost);
			result.setStatus(resp.getStatusLine().getStatusCode());
			result.setBody(EntityUtils.toString(resp.getEntity(), CHARSET_DEFAULT));
		} finally {
			if (null != resp) {
				resp.close();
			}
		}
		return result;
	}

}
