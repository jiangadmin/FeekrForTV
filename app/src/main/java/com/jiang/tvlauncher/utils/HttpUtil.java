package com.jiang.tvlauncher.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jiangmac
 * on 15/12/23.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO HTTP 工具类
 * update：
 */
public class HttpUtil {
    private static final String TAG = "HttpUtil";
    private static final int TIMEOUT_IN_MILLIONS = 20 * 1000;

    /**
     * Get 请求
     *
     * @param params 请求数据
     * @return 返回des加密后数据
     */
    public static String doGet(String urls, Map<String, String> params) {
        String paramsStr = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (TextUtils.isEmpty(entry.getValue())) {
                continue;
            }
            paramsStr += (entry.getKey() + "=" + entry.getValue() + "&");
        }

        HttpsURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {

            URL url = new URL(urls + "?" + paramsStr);

            conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            LogUtil.e(TAG, "网页结果：" + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();

                return baos.toString();

            } else {
                LogUtil.e(TAG, " responseCode is not 200 ... is" + conn.getResponseCode() + conn.getResponseMessage());
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                LogUtil.e(TAG, e.getMessage());
            }

            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                LogUtil.e(TAG, e.getMessage());
            }

        }
        return null;
    }

    /**
     * post 请求 DES加密发送
     *
     * @param url   请求地址
     * @param param 请求内容
     * @return 返回DES加密数据
     */

    public static String doPost(String url, Map<String, String> param) {
        StringBuilder paramStr = new StringBuilder();
        for (Map.Entry<String, String> para : param.entrySet()) {
            if (TextUtils.isEmpty(para.getValue())) {
                continue;
            }
            try {
                paramStr.append(para.getKey()).append("=").append(URLEncoder.encode(para.getValue(), "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        // 发送请求参数
        Log.e(TAG, "http发送 " + url + "?" + paramStr.toString());
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //超时时间
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            out.print(paramStr);
            // flush输出流的缓冲
            out.flush();

            try {
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (FileNotFoundException e) {
                result = null;
            }
        } catch (SocketTimeoutException e) {
            LogUtil.e(TAG, "发送请求超时！");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            return null;
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (result != null) {

            return result;
        } else {
            return null;
        }
    }

}
