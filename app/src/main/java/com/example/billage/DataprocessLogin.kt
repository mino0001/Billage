package com.example.billage

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataprocessLogin(private val u_id: String, private val u_pw: String) {
    private val retrofit: Retrofit

    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestLoginData(callback: (User?) -> Unit) {
        val service = retrofit.create(ApiService::class.java)
        val call = service.getUserData(u_id, u_pw)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    callback(result)
                } else {
                    callback(null) // 로그인 실패 시 null을 전달
                    Log.e("Response", "Unsuccessful response. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null) // 로그인 실패 시 null을 전달
                Log.e("Response", "Error: ${t.message}")
            }
        })
    }
}