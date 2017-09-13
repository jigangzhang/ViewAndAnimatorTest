package com.example.administrator.rentcar.net;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/9/12 15:37
 */

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.rentcar.net
 * @version: V1.0
 * @author: 张吉岗
 * @date: 2017/9/12 15:37
 * @description: <p>
 * <p>
 * retrofit详解：包含源码
 * http://blog.csdn.net/lmj623565791/article/details/51304204
 * </p>
 */

public class RetrofitConfig {
    private RetrofitConfig() {
    }

    public static Retrofit generateRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://www.baidu.com");
        builder.addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(Java8CallAdapterFactory.create())
//                .addCallAdapterFactory(GuavaCallAdapterFactory.create())
//                .callbackExecutor()
                .client(client);
        return builder.build();
    }
}
