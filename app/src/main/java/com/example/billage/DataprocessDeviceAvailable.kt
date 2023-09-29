package com.example.billage

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataprocessDeviceAvailable(private val category_id: String, private val rental_start: String, private val rental_deadline: String) {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestDeviceAvailableData(callback: (List<Device>?) -> Unit) {
        val service = retrofit.create(ApiService::class.java)
        val call = service.getDeviceAvailableData(category_id, rental_start, rental_deadline)

        call.enqueue(object : Callback<List<Device>> {
            override fun onResponse(call: Call<List<Device>>, response: Response<List<Device>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    callback(result)
                } else {
                    callback(null) // 데이터 가져오기 실패 시 null을 전달
                    Log.e("Response", "Unsuccessful response. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Device>>, t: Throwable) {
                callback(null) // 데이터 가져오기 실패 시 null을 전달
                Log.e("Response", "Error: ${t.message}")
            }
        })
    }
}
