package com.patelheggere.committeelection.apiClasses;


import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    /*@POST("/api/v1/send_otp")
    Call<SendOTP> sendOTP(@Body HashMap<String, Object> body);

    @POST("/api/v1/verify_otp?")
    Call<VerifyOTP> verifyOTP(@Body HashMap<String, Object> body);
    //Call<VerifyOTP> verifyOTP(@Field("mob") String name, @Field("Details") String Details, @Field("otp_input") String otp);

   *//* @FormUrlEncoded
    @POST("/api/v1/register_user?")
    Call<User> createUser(@Field("mob") String mobile, @Field("dob") String dob, @Field("gender") String gender,
                          @Field("email") String email,
                          @Field("full_name") String full_name);
*//*
    //@FormUrlEncoded
    @POST("/api/v1/register_user?")
    Call<VerifyOTP> createUser(@Body HashMap<String, Object> body);


    @POST("/api/v1/get_profile?")
    Call<UserProfile> getProfile(@Body HashMap<String, Object> body);

    @POST("/api/v1/edit_profile?")
    Call<ReplyModel> editProfile(@Body HashMap<String, Object> body);

    @POST("/api/v1/get_tickets")
    Call<List<LotteryTicketModel>> getTickets(@Body HashMap<String, Object> body);

    @Multipart
    @POST("/api/v1/edit_profile/upload_image")
    Call<PhotoUploadReplyModel> updateProfile(@Part MultipartBody.Part image);

    @POST("/api/v1/submit_ticket")
    Call<List<LotteryTicketModel>> submitTicket(@Body HashMap<String, Object> body);
*/

}
