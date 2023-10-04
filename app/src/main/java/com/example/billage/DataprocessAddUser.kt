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

class DataprocessAddUser(
    private val u_id: String,
    private val u_pwd: String,
    private val u_name: String,
    private val u_phone: String,
    private val u_email: String
) {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://billage.dothome.co.kr/") // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun requestAddUserData(callback: (String?) -> Unit) {
        val service = retrofit.create(ApiService::class.java)

        val call = service.saveUserNew(u_id, u_pwd, u_name, u_phone, u_email)

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
