package com.example.billage

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


class DataprocessReserveCancel(
    private val rt_id: String
) {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestReserveCancel(callback: (String?) -> Unit) {
        val service = retrofit.create(ApiService::class.java)

        val call = service.saveReserveCancel(rt_id)

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()
                    callback(result?.status) // "success" 또는 "fail" 반환
                    Log.e("Response", "Error body: ${response.errorBody()?.string()}")

                } else {
                    callback(null)
                    Log.e("Response", "Unsuccessful response. Code: ${response.code()}")
//                    Log.e("Response", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback(null)
                Log.e("Response", "Error: ${t.message}")
            }
        })
    }
}