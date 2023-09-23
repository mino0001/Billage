package com.example.billage

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataprocessRentalAll(private val u_id: String) {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestDataFromWebsite(callback: (List<Rental>) -> Unit) {
        val service = retrofit.create(ApiService::class.java)
        val call = service.getRentalData(u_id)

        call.enqueue(object : Callback<List<Rental>> {
            override fun onResponse(call: Call<List<Rental>>, response: Response<List<Rental>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    callback(result ?: emptyList())
                } else {
                    Log.e("Response", "Unsuccessful response. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Rental>>, t: Throwable) {
                Log.e("Response", "Error: ${t.message}")
            }
        })
    }
}
