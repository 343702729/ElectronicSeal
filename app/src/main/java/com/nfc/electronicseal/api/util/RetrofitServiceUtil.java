package com.nfc.electronicseal.api.util;

import com.nfc.electronicseal.api.APIService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by $wu on 2017-08-09 上午 9:21.
 * 创建单例获取RetrofitService
 */

public class RetrofitServiceUtil {
    private static Retrofit mRetrofit;
    private static APIService mAPIService;

    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(180, TimeUnit.SECONDS)
                    .readTimeout(180, TimeUnit.SECONDS)
                    .writeTimeout(180, TimeUnit.SECONDS)
                    .addInterceptor(logging).build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASEURL_IP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return mRetrofit;
    }

    public static APIService getAPIService() {
        if (mAPIService == null) {
            mAPIService = getRetrofit().create(APIService.class);
        }
        return mAPIService;
    }


}
