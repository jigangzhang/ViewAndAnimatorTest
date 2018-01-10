package com.example.administrator.learntask.net;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/9/12 14:01
 */

import com.example.administrator.learntask.entity.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.learntask.net
 * @version: V1.0
 * @author: 张吉岗
 * @date: 2017/9/12 14:01
 * @description: <p>
 * <p>
 * 参考：
 * http://blog.csdn.net/lmj623565791/article/details/51304204
 * http://blog.csdn.net/carson_ho/article/details/73732076
 * 注解作用于方法的参数和注解作用于方法 的区别
 * 注解类型：
 * 一、网络请求方法--@GET、@POST、@HTTP
 * 二、标记类--@FormUrlEncoded、@Multipart、@Streaming
 * 三、网络请求参数--@Header & @Headers、@Body（作用于非表单请求类）、@Field & @FieldMap（与 @FormUrlEncoded 注解配合使用）
 * 、@Part & @PartMap（与@Field的区别：功能相同，但携带的参数类型更加丰富，包括数据流，所以适用于 有文件上传 的场景，与 @Multipart 注解配合使用）
 * 、@Query和@QueryMap（用于 @GET 方法的查询参数（Query = Url 中 ‘?’ 后面的 key-value））
 * 、@Path URL地址的缺省值、@Url 直接传入一个请求的 URL变量 用于URL设置
 * 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
 * <p>
 * 下载文件直接使用OKhttp,避免创建多个OKhttpclient，最好与retrofit.client使用同一个
 * </p>
 */

public interface NetUrlConnectionService {
    @GET(value = "{userUrl}/")
    Call<User> getUser(@Path("userUrl") String userUrl,
                       @Query("id") int id);

    @Streaming //streaming 表示返回的数据以流的形式返回，适用于数据量较大的场景；如果没有该注解，默认将数据全部载入内存再从内存中读取数据
    @HTTP(method = "POST", path = "{fullUrl}", hasBody = true)
    Call<User> getUserData(@Path("fullUrl") String fullUrl,     //直接返回实体类需要Gson转换，及addConverterFactory(GsonConverterFactory.create())
                           @Body Object body);

    @HTTP(method = "POST", path = "{Url}", hasBody = true)
    @FormUrlEncoded
        //FormUrlEncoded 请求体是一个form表单，表示请求正文将使用表单URL编码。 字段应声明为参数，并用@Field注释。
        //使用此注释进行的请求将具有应用程序/ x-www-form-urlencoded MIME类型。 根据RFC-3986，字段名称和值将被UTF-8编码，然后进行URI编码。
    Call<String> getDataFromNet(@Path("Url") String url,
                                @Field("userName") String name,
                                @Field("userAge") int age);

    @GET("{url}")
    Call<ResponseBody> requestWebPage(@Path("url") String url);

    @GET
    Call<ResponseBody> getWebData(@Url String url);

    @POST("url")
    @Multipart
        //Multipart 表示请求体是一个支持文件上传的Form表单
    Call<ResponseBody> upLoadFile(@Part RequestBody name,
                                  MultipartBody.Part file);
}
