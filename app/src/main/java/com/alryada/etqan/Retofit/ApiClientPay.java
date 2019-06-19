package com.alryada.etqan.Retofit;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Abd El-Sattar
 * on 8/4/2017.
 */

public class ApiClientPay {

    //public static final String BASE_URL = "http://ur-business.net/majed/admin/Api/";
    public static final String BASE_URL = "https://sbpaymentservices.payfort.com/FortAPI/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(180, TimeUnit.SECONDS)
//                .connectTimeout(180, TimeUnit.SECONDS)
//                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    .build();
        }

        return retrofit;
    }
}