package com.rosseti.network;

import com.rosseti.models.BaseModel;
import com.rosseti.models.Suggestions;
import com.rosseti.models.TopicsResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("suggestions/store")
    Call<BaseModel> sendApplication(
            @Part("title") String title,
            @Part("topic_id") Integer topic,
            @Part("existing_solution_text") String existingSolutionText,
            @Part("proposed_solution_text") String proposedSolutionText,
            @Part("positive_effect") String positiveEffect,
            @Part MultipartBody.Part existingSolutionImage,
            @Part MultipartBody.Part proposedSolutionImage,
            @Part MultipartBody.Part existingSolutionVideo,
            @Part MultipartBody.Part proposedSolutionVideo
    );

    @GET("topics")
    Call<TopicsResponse> getTopics();
}
