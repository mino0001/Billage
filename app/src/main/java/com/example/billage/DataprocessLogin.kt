package com.example.billage

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class DataprocessLogin(private val u_id: String, private val u_pw: String) {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestLoginData(callback: (User?) -> Unit) {
        val service = retrofit.create(ApiService::class.java)

        // Create RequestBody for u_id and u_pw
        val mediaType = "text/plain".toMediaTypeOrNull()

        val u_idRequestBody = u_id.toRequestBody(mediaType)
        val u_pwRequestBody = u_pw.toRequestBody(mediaType)

        val call = service.getUserData(u_id, u_pw)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()
                    callback(result)
                    //Log.e("Response", "Error body: " + response.body())
                } else {
                    callback(null) // 로그인 실패 시 null을 전달
                    Log.e("Response", "Unsuccessful response. Code: ${response.code()}")
                    Log.e("Response", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null) // 로그인 실패 시 null을 전달
                Log.e("Response", "Error: ${t.message}")
            }
        })
    }
}
