package com.example.billage

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/getDeviceData.php") // 전체 Device 데이터 불러오기
    fun getDeviceData(): Call<List<Device>>

    @GET("api/getCategoryData.php") // 전체 Category 데이터 불러오기
    fun getCategoryData(): Call<List<Category>>

    @GET("api/getRnetalData.php") // 전체 Category 데이터 불러오기
    fun getRentalData(@Query("u_id") u_id: String): Call<List<Rental>>


}