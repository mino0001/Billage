import android.util.Log
import com.example.billage.ApiService
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

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()
                    callback(result) //취소 성공 시 'success' 실패 시 'fail' 받아옴, if문으로 처리하기
                } else {
                    callback("fail") // 실패 시 "fail"을 전달
                    Log.e("Response", "Unsuccessful response. Code: ${response.code()}")
                    Log.e("Response", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callback(null) // 실패 시 "fail"을 전달
                Log.e("Response", "Error: ${t.message}")
            }
        })
    }
}
