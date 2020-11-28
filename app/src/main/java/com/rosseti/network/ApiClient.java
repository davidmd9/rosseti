package com.rosseti.network;

import android.content.Context;
import com.rosseti.Application;
import com.rosseti.Storage;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String URL = "https://msofter.com/rosseti/public/api/";

    public static Api getApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", getToken())
                            .build();
                    return chain.proceed(request);
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .client(client)
                .build();

        return retrofit.create(Api.class);
    }

    private static String getToken() {
        Context context = com.rosseti.Application.getAppContext();
        if(context != null){
            Storage storage = new Storage(Application.getAppContext());
            if (storage.getToken() != null) {
                return storage.getToken();
            }
        }
        return "";
    }
}
