package com.example.billage

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("api/getDeviceData.php") // 전체 Device 데이터 불러오기
    fun getDeviceData(): Call<List<Device>>

    @GET("api/getCategoryData.php") // 전체 Category 데이터 불러오기
    fun getCategoryData(): Call<List<Category>>

    @GET("api/getRentalData.php") // 전체 Rental 데이터 불러오기
    fun getRentalData(): Call<List<Rental>>

    @GET("api/getRentalData.php") // 사용자별 Rental 데이터 불러오기
    fun getRentalData(@Query("u_id") u_id: String): Call<List<Rental>>

    @FormUrlEncoded
    @POST("api/getUserData.php")
    fun getUserData(
        @Field("u_id") u_id: String,
        @Field("u_pw") u_pw: String
    ): Call<User>

    @FormUrlEncoded
    @POST("api/saveUserNew.php")
    fun saveUserNew(
        @Field("u_id") u_id: String,
        @Field("u_pwd") u_pwd: String,
        @Field("u_name") u_name: String,
        @Field("u_phone") u_phone: String,
        @Field("u_email") u_email: String
    ): Call<String>

    @GET("api/getDeviceAvailableData.php") // 대여 가능 기기 정보 불러오기
    fun getDeviceAvailableData(
        @Query("category_id") category_id: String,
        @Query("rental_start") rental_start: String,
        @Query("rental_deadline") rental_deadline: String
    ): Call<List<Device>>

    @GET("api/saveReserveNew.php") // 새로운 예약 저장하기
    fun reserveNew(
        @Query("user_id") user_id: String,
        @Query("device_id") device_id: String,
        @Query("rental_start") rental_start: String,
        @Query("rental_deadline") rental_deadline: String
    ): Call<String>

}