package com.alryada.etqan.Retofit;


import com.alryada.etqan.Model.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;



public interface ApiInterface {

    //region authentication
    @FormUrlEncoded
    @POST("login")
    Call<String> login(@Field("email") String email, //not null
                       @Field("password") String password, // not null
                       @Field("platform") String platform,
                       @Field("platform_token") String platformToken,
                       @Field("lang") String lang
    );

    @FormUrlEncoded
    @POST("forget_password")
    Call<String> forgetPassword(@Field("email") String email,
                                @Field("lang") String lang
    );

    @FormUrlEncoded
    @POST("registeration")
    Call<DefaultResponse> registerEmail(@Field("email") String email, //not null
                                        @Field("password") String password,  //not null
                                        @Field("name") String name,  //not null
                                        @Field("gender") String gender, //not null
                                        @Field("mobile") String mobile,  //not null
                                        @Field("lang") String lang,   //not null
                                        @Field("phone") String phone,
                                        @Field("address") String address,
                                        @Field("latitude") String latitude,
                                        @Field("longitude") String longitude,
                                        @Field("platform") String platform,
                                        @Field("platform_token") String platform_token
    );

    @FormUrlEncoded
    @POST("update_client_details")
    Call<String> updateUserInfo(@Field("client_id") String clientId, //not null
                                @Field("email") String email,
                                @Field("nameAR") String nameAR,
                                @Field("nameEN") String nameEN,
                                @Field("gender") String gender,
                                @Field("mobile") String mobile,
                                @Field("phone") String phone,
                                @Field("addressAR") String addressAR,
                                @Field("addressEN") String addressEN,
                                @Field("latitude") String latitude,
                                @Field("longitude") String longitude,
                                @Field("platform") String platform,
                                @Field("platform_token") String platform_token,
                                @Field("lang") String lang          //not null

    );


    @FormUrlEncoded
    @POST("get_services_list")
    Call<String> getServicesList(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("get_nationalities")
    Call<String> getNationalitiesList(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("get_client_main_info")
    Call<String> getUserdata(@Field("client_id") String clientID,
                             @Field("lang") String lang);

    @FormUrlEncoded
    @POST("update_client_password")
    Call<String> updatePassword(@Field("client_id") String clientID,
                                @Field("old_password") String oldPassword,
                                @Field("new_password") String newPassword,
                                @Field("lang") String lang);

    @FormUrlEncoded
    @POST("get_client_main_info")
    Call<String> getProfileInfo(@Field("client_id") String clientID, @Field("lang") String lang);

    @FormUrlEncoded
    @POST("get_price")
    Call<String> getPrice(@Field("service_id") String serviceId,
                          @Field("nationality_id") String nationalityID,
                          @Field("gender") String gender,
                          @Field("employees_num") String employeesNum,
                          @Field("hours_number") String hoursNum,
                          @Field("start_time") String startTime,
                          @Field("lang") String lang);

    @FormUrlEncoded
    @POST("get_subsribe_price")
    Call<String> getSubscribePrice(@Field("service_id") String serviceId,
                                   @Field("gender") String gender,
                                   @Field("employees_num") String employeesNum,
                                   @Field("hours_number") String hoursNum,
                                   @Field("days") String days,
                                   @Field("duration") String duration,
                                   @Field("lang") String lang);

    @FormUrlEncoded
    @POST("get_client_order")
    Call<String> getOrders(@Field("client_id") String client,
                           @Field("lang") String lang);

    @FormUrlEncoded
    @POST("get_order_by_id")
    Call<String> getOrderDetails(@Field("order_id") String client,
                                 @Field("lang") String lang);

    @FormUrlEncoded
    @POST("get_notifcation")
    Call<String> getNotifications(@Field("client_id") String clientID,
                                  @Field("lang") String lang);

    @FormUrlEncoded
    @POST("client_rate_order")
    Call<String> rateOrder(@Field("client_id") String clientID,
                           @Field("order_id") String order_id,
                           @Field("ontime_rate") String ontime_rate,
                           @Field("quality_rate") String quality_rate,
                           @Field("team_rate") String team_rate,
                           @Field("comment") String comment,
                           @Field("lang") String lang
    );

    @FormUrlEncoded
    @POST("make_order")
    Call<String> makeOrder(@Field("client_id") String clientId,
                           @Field("latitude") String latitude,
                           @Field("longitude") String longitude,
                           @Field("hours_number") String hours_number,
                           @Field("date") String date,
                           @Field("time_from") String timeFrom,
                           @Field("service_id") String serviceId,
                           @Field("nationality_id") String nationalityId,
                           @Field("gender") String gender,
                           @Field("team_number") String TeamNumber,
                           @Field("lang") String lang
    );


    @FormUrlEncoded
    @POST("make_subscribe")
    Call<String> makeSubscriptionOrder(@Field("client_id") String clientId,
                                       @Field("latitude") String latitude,
                                       @Field("longitude") String longitude,
                                       @Field("hours_number") String hours_number,
                                       @Field("date") String date,
                                       @Field("time_from") String timeFrom,
                                       @Field("service_id") String serviceId,
//                                       @Field("nationality_id") String nationalityId,
                                       @Field("gender") String gender,
                                       @Field("team_number") String TeamNumber,
                                       @Field("days") String days,
                                       @Field("duration") int duration,
                                       @Field("lang") String lang
    );
}
