package com.example.billage

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataprocessDeviceAll {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestDataFromWebsite(callback: (List<Device>) -> Unit) {
        val service = retrofit.create(ApiService::class.java)
        val call = service.getDeviceData()

        call.enqueue(object : Callback<List<Device>> {
            override fun onResponse(call: Call<List<Device>>, response: Response<List<Device>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    callback(result ?: emptyList())
                } else {
                    Log.e("Response", "Unsuccessful response. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Device>>, t: Throwable) {
                Log.e("Response", "Error: ${t.message}")
            }
        })
    }
}

