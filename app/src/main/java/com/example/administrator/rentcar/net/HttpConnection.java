package com.example.administrator.rentcar.net;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/8/9 11:18
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.rentcar.net
 * @version: V1.0
 * @author: 张吉岗
 * @date: 2017/8/9 11:18
 * @description: <p>
 * <p>
 * </p>
 */

public class HttpConnection {
    private static HttpConnection instance;

    private HttpConnection() {
    }

    public static HttpConnection getInstance() {
        if (instance == null) {
            synchronized (HttpConnection.class) {
                if (instance == null) {
                    instance = new HttpConnection();
                }
            }
        }
        return instance;
    }

    private HttpURLConnection getHttpUrlConnection(String url, String requestMethod) {
        HttpURLConnection http = null;
        try {
            URL uri = new URL(url);
            http = (HttpURLConnection) uri.openConnection();
            http.setRequestMethod(requestMethod);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setReadTimeout(10000);
            http.setConnectTimeout(3000);
            //     http.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            http.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return http;
    }

    private void setPostParams(OutputStream stream, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if (params == null) {
            return;
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!TextUtils.isEmpty(sb.toString())) {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode(entry.getKey(), "utf-8")).append("=");
                sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(stream));
        try {
            bw.write(sb.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(String url, String requestMethod, Map<String, String> params) {
        try {
            HttpURLConnection connection = getHttpUrlConnection(url, requestMethod);
            OutputStream outputStream = connection.getOutputStream();
            setPostParams(outputStream, params);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            String response = readResponse(inputStream);

            Map<String, List<String>> headerFields = connection.getHeaderFields();
//            CookieManager.getDefault().
            Log.e("tag", "code:" + responseCode + "\nmessage:" + responseMessage + "\nread:" + response + "\nheader:" + headerFields.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doGet(String url) {
        HttpURLConnection connection = getHttpUrlConnection(url, "GET");
        try {
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
//                Bitmap bitmap = loadImg(connection);
            }
            String responseMessage = connection.getResponseMessage();
            Log.e("tag", "code:" + responseCode + "\nmessage:" + responseMessage);

            Map<String, List<String>> headerFields = connection.getHeaderFields();
            Log.e("tag", "\nheader:" + headerFields.toString());
            String response = readResponse(connection.getInputStream());
            Log.e("tag", "read:" + response + "\nheader:" + headerFields.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readResponse(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer();
        try {
            while (br.readLine() != null) {
                sb.append(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private Bitmap loadImg(HttpURLConnection connection) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
