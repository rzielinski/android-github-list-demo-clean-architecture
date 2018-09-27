package com.dreddi.android.githublist.presentation.di;

import com.dreddi.android.githublist.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit apiClient = null;

    public static Retrofit getClient() {
        if (apiClient == null) {
            apiClient = getRetrofitClient(BuildConfig.API_REST_URL, BuildConfig.API_REST_TIMEOUT);
        }
        return apiClient;
    }

    private static Retrofit getRetrofitClient(String url, long timeout) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .readTimeout(timeout, TimeUnit.SECONDS).build();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
