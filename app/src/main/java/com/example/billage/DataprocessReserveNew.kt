package com.example.billage

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataprocessReserveNew(
    private val user_id: String,
    private val device_id: String,
    private val rental_start: String,
    private val rental_deadline: String
) {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestReservation(callback: (String?) -> Unit) {
        val service = retrofit.create(ApiService::class.java)
        val call = service.reserveNew(
            user_id,
            device_id,
            rental_start,
            rental_deadline
        )

        call.enqueue(object : Callback<ResponseDataforApi> {
            override fun onResponse(call: Call<ResponseDataforApi>, response: Response<ResponseDataforApi>) {
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()
                    callback(result?.status) // "success" 또는 "fail" 반환
                    Log.e("Response", "Error body: ${response.body()}")
                } else {
                    callback(null)
                    Log.e("Response", "Unsuccessful response. Code: ${response.code()}")
//                    Log.e("Response", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseDataforApi>, t: Throwable) {
                callback("fail")
                Log.e("Response", "Error: ${t.message}")
            }
        })
    }
}
