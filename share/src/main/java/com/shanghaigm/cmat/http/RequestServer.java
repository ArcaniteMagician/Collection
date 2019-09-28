package com.shanghaigm.cmat.http;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 网络请求类
 * Created by darren foung on 2016/6/18.
 */
public class RequestServer {

	private static final String TAG = "RequestServer";

	private Context context;
	private String urlStr;
	private Map<String, String> headers = null;
	private Map<String, Object> params = null;
	private boolean isJson = false;
	private int responseCode = -1; // 记录请求返回的状态码
	private int requestType;
	private OnServerRequestComplete listener;
	private String requestData;
	private static final int CONNECT_TIME_OUT = 20 * 1000; //连接延时
	private static final int READ_TIME_OUT = 20 * 1000;//读取延时

	public static final int MESSAGE_CODE = 100001; // 消息代码
	/**
	 * 其他错误
	 */
	public static final int FAIL_CODE = 110001; // 服务器错误代码
	/**
	 * 内部异常
	 */
	public static final int FAIL_CODE_EXCEPTION_ERROR = 110002; // 异常错误
	/**
	 * 其他错误
	 */
	public static final int FAIL_CODE_OTHER = 110003; // 其他错误
	/**
	 * 401 未授权
	 */
	public static final int FAIL_CODE_UNAUTHORIZED = 110401; // 未授权401
	/**
	 * 403 拒绝请求
	 */
	public static final int FAIL_CODE_FORBIDDEN = 110403; // 服务器拒绝请求403

	/**
	 * 构建服务器请求  请求参数已key-value方式
	 *
	 * @param context     上下文
	 * @param url         请求的URL
	 * @param requestType 0：GET 从服务器取出资源（一项或多项）
	 *                    1：POST 在服务器新建一个资源
	 *                    2: PUT 在服务器更新资源（客户端提供改变后的完整资源）
	 *                    3: PATCH 在服务器更新资源（客户端提供改变的属性）
	 *                    4: DELETE 从服务器删除资源
	 * @param headers     头部参数 没有则null
	 * @param params      请求参数 没有则null
	 */
	public RequestServer(Context context, String url, String requestType,
						 Map<String, String> headers, Map<String, Object> params) {
		this(context, url, requestType, headers, params, false);
	}

	/**
	 * 构建服务器请求  根据isJson值，判断采取json形式或key-value形式
	 *
	 * @param context     上下文
	 * @param url         请求的URL
	 * @param requestType 0：GET 从服务器取出资源（一项或多项）
	 *                    1：POST 在服务器新建一个资源
	 *                    2: PUT 在服务器更新资源（客户端提供改变后的完整资源）
	 *                    3: PATCH 在服务器更新资源（客户端提供改变的属性）
	 *                    4: DELETE 从服务器删除资源
	 * @param headers     头部参数 没有则null
	 * @param params      请求参数 没有则null
	 * @param isJson      请求参数是否是以json的形式，true为json，false为key-value
	 */
	public RequestServer(Context context, String url, String requestType,
						 Map<String, String> headers, Map<String, Object> params, boolean isJson) {
		this.context = context;
		this.urlStr = url;
		this.headers = headers;
		this.params = params;
		this.isJson = isJson;
		if (requestType.toUpperCase().equals("GET")) {
			this.requestType = 0;
		} else if (requestType.toUpperCase().equals("POST")) {
			this.requestType = 1;
		} else if (requestType.toUpperCase().equals("PUT")) {
			this.requestType = 2;
		} else if (requestType.toUpperCase().equals("PATCH")) {
			this.requestType = 3;
		} else if (requestType.toUpperCase().equals("DELETE")) {
			this.requestType = 4;
		}
	}

	/**
	 * 设置请求返回监听器
	 *
	 * @param listener 请求回调监听器
	 */
	public void setOnServerRequestCompleteListener(OnServerRequestComplete listener) {
		this.listener = listener;
	}

	/**
	 * 请求服务器
	 */
	public void requestService() {

		final Handler handler = new RequestHandler(urlStr, listener);

		// 检查是否有网络
		boolean isAvailable = RequestServerHelper.isNetworkAvailable(context);
		if(!isAvailable){
			Message msg = handler.obtainMessage();
			msg.what = FAIL_CODE_OTHER;
			handler.sendMessage(msg);
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				HttpURLConnection urlConnection;
				String response = null;
				try {
					// 请求参数，分Json和key-value两种形式
					requestData = getParamString(params);
					Log.d(TAG, "request params string ：" + requestData);

					switch (requestType) {
						case 0:
							if (requestData != null && requestData.length() > 0) {
								urlStr = urlStr + "?" + requestData;
							}
							urlConnection = initHTTPConnection(urlStr, "GET");
							if (urlConnection != null) {
								response = doGetRequest(urlConnection);
							}
							break;
						case 1:
							urlConnection = initHTTPConnection(urlStr, "POST");
							if (urlConnection != null) {
								response = doPostRequest(urlConnection);
							}
							break;
						case 2:
							urlConnection = initHTTPConnection(urlStr, "PUT");
							if (urlConnection != null) {
								response = doPutRequest(urlConnection);
							}
							break;
						case 3:
							urlConnection = initHTTPConnection(urlStr, "PATCH");
							if (urlConnection != null) {
								response = doPatchRequest(urlConnection);
							}
							break;
						case 4:
							if (requestData != null && requestData.length() > 0) {
								urlStr = urlStr + "?" + requestData;
							}
							urlConnection = initHTTPConnection(urlStr, "DELETE");
							if (urlConnection != null) {
								response = doDeleteRequest(urlConnection);
							}
							break;
					}

					if(response != null){
						Log.d(TAG, "response data length: " + response.length());

						Message msg = handler.obtainMessage();
						msg.what = MESSAGE_CODE;
						Bundle bundle = new Bundle();
						bundle.putInt("responseCode", responseCode);
						bundle.putString("response", response);
						msg.setData(bundle);
						handler.sendMessage(msg);
					} else {
						Message msg = handler.obtainMessage();
						msg.what = FAIL_CODE_EXCEPTION_ERROR;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 初始化HTTP请求
	 *
	 * @param urlStr      请求URL
	 * @param requestType 请求类型
	 * @return 返回Connecton
	 */
	private HttpURLConnection initHTTPConnection(String urlStr, String requestType) {
		try {
			Log.d(TAG, "url = " + urlStr + ", requestType = " + requestType);

			URL url = new URL(urlStr);

			HttpURLConnection conn;
//			System.setProperty("http.keepAlive", "false");
			// 忽略 https 证书验证
			if (url.getProtocol().toUpperCase().equals("HTTPS")) {
				// 设置信任所有主机
//				trustAllHosts();
				trustEveryone();

				conn = (HttpsURLConnection) url.openConnection();
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}

			conn.setRequestMethod(requestType); // 请求类型
			conn.setConnectTimeout(CONNECT_TIME_OUT);
			conn.setReadTimeout(READ_TIME_OUT);
//			conn.setDoInput(true);
//			conn.setDoOutput(false);
			conn.setUseCaches(false);

			// 设置请求头
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setUseCaches(false);

			if (headers != null) {
				Log.d(TAG, "response header: ");
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					Log.d(TAG, entry.getKey() + ": " + entry.getValue());
					conn.setRequestProperty(entry.getKey().trim(), entry.getValue().trim());
				}
			}

			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行PUT请求
	 *
	 * @param conn http连接
	 * @return 返回请求结果
	 */
	private String doPutRequest(HttpURLConnection conn) {

		OutputStream os = null;
		InputStream is = null;
		String response = null;

		try {
			conn.connect();

			os = new BufferedOutputStream(conn.getOutputStream());
			os.write(requestData.getBytes());
			os.flush();

			// 请求反馈
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				response = getDataFromInputStream(is);

				Log.d(TAG, "server response code: " + conn.getResponseCode()
						+ ", message: " + conn.getResponseMessage()+",response="+response);
			} else {
				response = "";
				responseCode = conn.getResponseCode();
				Log.d(TAG, "HTTP PUT Request Failed with Error code : "
						+ conn.getResponseCode()
						+ ", message:" + conn.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * 执行PATCH请求
	 *
	 * @param conn http连接
	 * @return 返回请求结果
	 */
	private String doPatchRequest(HttpURLConnection conn) {

		OutputStream os = null;
		InputStream is = null;
		String response = null;

		try {
			conn.connect();

			os = new BufferedOutputStream(conn.getOutputStream());
			os.write(requestData.getBytes());
			os.flush();

			// 请求反馈
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				response = getDataFromInputStream(is);

				Log.d(TAG, "server response code: " + conn.getResponseCode()
						+ ", message: " + conn.getResponseMessage());
			} else {
				response = "";
				responseCode = conn.getResponseCode();
				Log.d(TAG, "HTTP PATCH Request Failed with Error code : "
						+ conn.getResponseCode()
						+ ", message:" + conn.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * 执行DELETE请求
	 *
	 * @param conn http连接
	 * @return 返回请求结果
	 */
	private String doDeleteRequest(HttpURLConnection conn) {

//		OutputStream os = null;
		InputStream is = null;
		String response = null;

		try {

			conn.connect();

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				response = getDataFromInputStream(is);

				Log.d(TAG, "server response code: " + conn.getResponseCode()
						+ ", message: " + conn.getResponseMessage());
			} else {
				response = "";
				responseCode = conn.getResponseCode();
				Log.d(TAG, "HTTP DELETE Request Failed with Error code : "
						+ conn.getResponseCode()
						+ ", message:" + conn.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
//				if (os != null) {
//					os.close();
//				}
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * 执行POST请求
	 *
	 * @param conn http连接
	 * @return 返回请求结果
	 */
	private String doPostRequest(HttpURLConnection conn) {

		OutputStream os = null;
		InputStream is = null;
		String response = null;

		try {

			conn.connect();

			os = new BufferedOutputStream(conn.getOutputStream());
			os.write(requestData.getBytes());
			os.flush();

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();

				response = getDataFromInputStream(is);

				Log.d(TAG, "server response code: " + conn.getResponseCode()
						+ ", message: " + conn.getResponseMessage());
			} else {
				response = "";
				responseCode = conn.getResponseCode();
				Log.d(TAG, "HTTP POST Request Failed with Error code : "
						+ conn.getResponseCode()
						+ ", message:" + conn.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * 执行GET请求
	 *
	 * @param conn http连接
	 * @return 返回请求结果
	 */
	private String doGetRequest(HttpURLConnection conn) {

//		OutputStream os = null;
		InputStream is = null;
		String response = null;

		try {

			conn.connect();

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				response = getDataFromInputStream(is);

				Log.d(TAG, "server response code: " + conn.getResponseCode()
						+ ", message: " + conn.getResponseMessage());
			} else {
				response = "";
				responseCode = conn.getResponseCode();
				Log.d(TAG, "HTTP GET Request Failed with Error code : "
						+ conn.getResponseCode()
						+ ", message:" + conn.getResponseMessage());

			}
		} catch (IOException e) {
			/*
			* 有时会报java.io.IOException: No authentication challenges found 错误
			* This error happens beause the server sends a 401 (Unauthorized)
			* but does not give a WWW-Authenticate header which is a hint for the client what to do next.
			* */
			try {
				if(conn.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
					response = "";
					responseCode = conn.getResponseCode();
					Log.d(TAG, "server response code: " + conn.getResponseCode()
							+ ", message: " + conn.getResponseMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
//				if (os != null) {
//					os.close();
//				}
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * 获取请求参数字符串
	 *
	 * @param params 请求参数Map
	 * @return 返回json或key-value形式
	 */
	private String getParamString(Map<String, Object> params) {
		if (params == null) {
			return "";
		}

		StringBuilder result = new StringBuilder();
		if (!isJson) {
			// key-value形式
			boolean first = true;
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (first) {
					first = false;
				} else {
					result.append("&");
				}
				try {
					result.append(URLEncoder.encode(entry.getKey().trim(), "UTF-8"));
					result.append("=");
					String valueStr = (String.valueOf(entry.getValue())).trim();
					result.append(URLEncoder.encode(stringEscape(valueStr), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new RuntimeException("param encode error: "
							+ entry.getKey()
							+ "=" + entry.getValue());
				}
			}
		} else {
			// json形式
			try {
				String json = mapToJson( params);
				result.append(json);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("param type error");
			}

		}
		return result.toString();
	}

	/**
	 * 转义特殊字符
	 * @param str 原字符
	 * @return 转义后字符
	 */
	private String stringEscape(String str){
		return str.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\r", "\\r")
				.replace("\n", "\\n");
	}

	/**
	 * 转义&amp;等字符
	 * @param str 原字符串
	 * @return 转义后字符串
	 */
	private static String stringEscapeOther(String str){

		return str.replace("&amp;","&").replace("&quot;", "\\\"");
	}

	/**
	 * 获取返回数据
	 *
	 * @param inputStream http连接输入流
	 * @return 服务器返回内容
	 */
	private String getDataFromInputStream(InputStream inputStream) {
		String line;
		String response = "";
		StringBuilder sb = new StringBuilder();
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			response = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}


	/**
	 * 消息处理Handler
	 */
	static class RequestHandler extends Handler {

		private OnServerRequestComplete listener;
		private String url;

		public RequestHandler(String url, OnServerRequestComplete listener) {
			this.url = url;
			this.listener = listener;
		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == MESSAGE_CODE && listener != null) {
				Bundle bundle = msg.getData();
				String response = bundle.getString("response");
				int responseCode = bundle.getInt("responseCode");
				if (response != null && response.length() > 0) {
					// 请求成功 200
					listener.onSuccess(stringEscapeOther(response));
				} else {
					// 请求错误 非200
					switch (responseCode){
						case HttpURLConnection.HTTP_UNAUTHORIZED: // 未授权错误
							listener.onFail(FAIL_CODE_UNAUTHORIZED, "request service unauthorized 401 ", url);
							break;
						case HttpURLConnection.HTTP_FORBIDDEN: // 拒绝请求 （如：当sign错误）
							listener.onFail(FAIL_CODE_FORBIDDEN, "request service forbidden 403 ", url);
							break;
						default:
							listener.onFail(FAIL_CODE, "request service fail " + responseCode, url);
					}
				}
			} else if(msg.what == FAIL_CODE_EXCEPTION_ERROR && listener != null){
				// 内部异常
				listener.onFail(FAIL_CODE_EXCEPTION_ERROR, "request service inner exception error", url);
			} else if(listener != null){
				// 其他错误
				listener.onFail(FAIL_CODE_OTHER, "request service other error", url);
			} else {
				System.out.printf(TAG + "：request error，"
						+ msg.what + " or not set OnServerRequestCompleteListener");
			}
		}
	}


	/**
	 * 信任所有人
	 */
	private void trustEveryone() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[]{new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			}}, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(
					context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 将Map转换成Json
	 *
	 * @param jsonMap 要转json的map数据
	 * @return 转换后的json
	 * @throws Exception
	 */
	private String mapToJson(Map<String, Object> jsonMap) throws Exception {
		StringBuilder result = new StringBuilder();
		result.append("{");
		Iterator<Map.Entry<String, Object>> entryIterator = jsonMap.entrySet().iterator();

		for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Map) {
//				Log.d(TAG, "\n Object Key : " + key);
				String mapJson = mapToJson((Map<String, Object>) value);
				result.append("\"").append(key).append("\":").append(mapJson);
			} else if (value instanceof List) {
//				Log.d(TAG, "\n Array Key : " + key);
				String listJson = listToJson((List) value);
				result.append("\"").append(key).append("\":").append(listJson);
			} else {
//				Log.d(TAG, "key : " + key + " value : " + String.valueOf(value));
				result.append("\"").append(key).append("\":\"").append(stringEscape(String.valueOf(value))).append("\"");
			}
			// 最后一个去掉 逗号
			entryIterator.next();
			if (entryIterator.hasNext()) {
				result.append(",");
			}
		}
		result.append("}");
		return result.toString();
	}

	/**
	 * 将List转成Json
	 *
	 * @param jsonList 要转换成json的list
	 * @return json
	 * @throws Exception
	 */
	private String listToJson(List jsonList) throws Exception {
		StringBuilder result = new StringBuilder();
		result.append("[");
		JSONArray jsonArray = new JSONArray(jsonList);
		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.opt(i) instanceof JSONObject) {
				String mapJson = mapToJson((Map<String, Object>) jsonList.get(i));
				result.append(mapJson);
			} else if (jsonArray.opt(i) instanceof JSONArray) {
				String listJson = listToJson((List) jsonList.get(i));
				result.append(listJson);
			} else {
				result.append("\"").append(stringEscape(String.valueOf(jsonArray.opt(i)))).append("\"");
			}

			// 最后一个去掉 逗号
			if (i < jsonArray.length() - 1) {
				result.append(",");
			}
		}
		result.append("]");
		return result.toString();
	}

	/**
	 * 请求服务器回调接口
	 */
	public interface OnServerRequestComplete {

		/**
		 * 请求服务器成功
		 *
		 * @param response 服务器返回值
		 */
		void onSuccess(String response);

		/**
		 * 请求服务器失败
		 *
		 * @param statusCode 失败状态码
		 * @param message 失败信息
		 * @param url 失败url
		 */
		void onFail(int statusCode, String message, String url);
	}

}

