package com.example.administrator.learntask.net;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/9/12 15:47
 */

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.learntask.net
 * @version: V1.0
 * @author: 张吉岗
 * @date: 2017/9/12 15:47
 * @description: <p>
 * <p>
 * </p>
 */

public class RetrofitUsage {
    private static NetUrlConnectionService service;

    private RetrofitUsage() {
    }

    private static NetUrlConnectionService retrofitHttp() {
        if (service == null) {
            synchronized (NetUrlConnectionService.class) {
                if (service == null) {
                    service = RetrofitConfig.generateRetrofit().create(NetUrlConnectionService.class);
                }
            }
        }
        return service;
    }

    public static void requestWebPage(String url) {
        Call<ResponseBody> call = retrofitHttp().requestWebPage(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();

                try {
                    Log.e("tag", "body-->" + body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void upLoadFile(String userName, String filePath) {
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(mediaType, userName);
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), new File(filePath));
        MultipartBody.Part data = MultipartBody.Part.createFormData("file", "file.txt", file);
        Call<ResponseBody> call = retrofitHttp().upLoadFile(name, data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
