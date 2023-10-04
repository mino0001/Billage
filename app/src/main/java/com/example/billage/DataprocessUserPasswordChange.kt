package com.example.billage

import android.util.Log
import com.example.billage.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class DataprocessUserPasswordChange(
    private val u_id: String,
    private val u_pwd: String
) {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // 기본 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestUserPasswordChange(callback: (String?) -> Unit) {
        val service = retrofit.create(ApiService::class.java)

        val call = service.saveUserPasswordChange(u_id, u_pwd)

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()
                    callback(result?.status) // "success" 또는 "fail" 반환
                    Log.e("Response", "Error body: ${response.body()}")
                } else {
                    callback(null)
                    Log.e("Response", "응답 실패. 코드: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback(null)
                Log.e("Response", "에러: ${t.message}")
            }
        })
    }
}
