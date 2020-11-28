package com.rosseti.network;

import com.rosseti.models.BaseModel;
import com.rosseti.models.Suggestions;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("auth/phone")
    Call<BaseModel> auth(@Field("phone") String phone);


    @FormUrlEncoded
    @POST("auth/verify-code")
    Call<BaseModel> verifyCode(@Field("phone") String phone, @Field("code") String code);

    @GET("suggestions/index")
    Call<Suggestions> getSuggestions();

    @FormUrlEncoded
    @POST("suggestions/rating/store")
    Call<BaseModel> setRating(@Field("suggestion_id") Integer id, @Field("value") Integer value);


    @FormUrlEncoded
    @POST("suggestions/expert-judgment")
    Call<BaseModel> expertJudgment(@Field("suggestion_id") Integer id,
                                   @Field("accepted") Integer accepted);
}
