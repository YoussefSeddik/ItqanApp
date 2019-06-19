package com.alryada.etqan.Retofit;
import com.alryada.etqan.Model.PayFortData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterfacePay {
    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("paymentApi")
    Call<PayFortData> getToken(@Field("service_command") String serviceCommand,
                               @Field("access_code") String access_code ,
                               @Field("merchant_identifier") String merchant_identifier,
                               @Field("language") String language,
                               @Field("device_id") String device_id,
                               @Field("signature") String signature);
}
