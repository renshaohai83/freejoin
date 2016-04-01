package com.ui.freejion.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.ui.freejion.common.CBXManageLog;

public class CBXHttpRequest {
	private static final String TAG = "CBXHttpRequest";

	public static InputStream executeHttpGet(String requestUrl) {
		CBXManageLog.D(TAG, "executeHttpGet:" + requestUrl);

		InputStream result = null;
		try {
			URL url = new URL(requestUrl);
			// 打开连接,会返回conn 对象
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// 设置 超时时间 为15秒
			conn.setConnectTimeout(15 * 1000);

			int responseCode = conn.getResponseCode();
			CBXManageLog.D(TAG, "responseCode:" + responseCode);

			// 取得输入流
			result = conn.getInputStream();
		} catch (ClientProtocolException e) {
			CBXManageLog.E(TAG, "ClientProtocolException:" + e.getMessage());
		} catch (IOException e) {
			CBXManageLog.E(TAG, "IOException:" + e.getMessage());
		} catch (Exception e) {
			CBXManageLog.E(TAG, "Exception:" + e.getMessage());
		}

		return result;
	}

	/**
	 * 
	 * @param requestUrl
	 *            请求路径
	 * @param postData
	 *            post数据
	 * @return
	 * @throws Exception
	 */
	public static InputStream executeHttpPost(String requestUrl, String postData) {

		CBXManageLog.D(TAG, "executeHttpPost:" + requestUrl);
		CBXManageLog.D(TAG, "postData:" + postData);

		InputStream result = null;
		URL url = null;
		try {
			url = new URL(requestUrl);
			// 打开连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置提交方式
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			// post方式不能使用缓存
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设置连接超时时间
			conn.setConnectTimeout(15 * 1000);
			conn.setReadTimeout(15 * 1000);
			//设置connect-type
			conn.setRequestProperty("Content-type","application/json");

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			// 将请求参数数据向服务器端发送
			dos.write(postData.getBytes("UTF8"));
			dos.flush();
			dos.close();

			int responseCode = conn.getResponseCode();
			CBXManageLog.D(TAG, "responseCode：" + responseCode);

			if (responseCode == 200) {
				// 获得服务器端输出流
				result = conn.getInputStream();
			}
		} catch (IOException e) {
			CBXManageLog.E(TAG, "e:" + e.getMessage());
		} catch (Exception e) {
			CBXManageLog.E(TAG, "e:" + e.getMessage());
		}

		return result;
	}



    /**
     *
     * @param requestUrl
     *            请求路径
     *
     * @return
     * @throws Exception
     */
    public static InputStream executeHttpPostNotPostData(String requestUrl) {

        CBXManageLog.D(TAG, "executeHttpPost:" + requestUrl);

        InputStream result = null;
        URL url = null;
        try {
            url = new URL(requestUrl);
            // 打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置提交方式
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // post方式不能使用缓存
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            // 设置连接超时时间
            conn.setConnectTimeout(15 * 1000);
            conn.setReadTimeout(15 * 1000);
            //设置connect-type
            conn.setRequestProperty("Content-type","application/json");

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            // 将请求参数数据向服务器端发送
            dos.flush();
            dos.close();

            int responseCode = conn.getResponseCode();
            CBXManageLog.D(TAG, "responseCode：" + responseCode);

            if (responseCode == 200) {
                // 获得服务器端输出流
                result = conn.getInputStream();
            }
        } catch (IOException e) {
            CBXManageLog.E(TAG, "e:" + e.getMessage());
        } catch (Exception e) {
            CBXManageLog.E(TAG, "e:" + e.getMessage());
        }

        return result;
    }


}
