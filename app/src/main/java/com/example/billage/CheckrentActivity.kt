package com.example.billage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billage.databinding.ActivityCheckrentBinding
import kotlin.math.log

class CheckrentActivity : ComponentActivity() {
    private var binding: ActivityCheckrentBinding? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityCheckrentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


//        val rentDevice = intent.getIntExtra("device", 0)
        val cateName = intent.getStringExtra("cateName").toString()
        val rentDate = intent.getStringExtra("rentDate").toString()
        val returnDate = intent.getStringExtra("returnDate").toString()
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("startDate", rentDate)
        editor.putString("endDate", returnDate)
        editor.apply()

        buttonFlag=1


        val cateId = when (cateName) {
            "i5" -> "c12345-003"
            "i7" -> "c12345-004"
            "macOS" -> "c12345-005"
            "android" -> "c12345-007"
            "ios" -> "c12345-006"
            else -> ""
        }
        val dataProcessor = DataprocessDeviceAvailable(cateId, rentDate, returnDate)


        dataProcessor.requestDeviceAvailableData { devices ->
            val filteredList: MutableList<Goods> = mutableListOf() // 빈 리스트로 초기화
            devices?.forEach {  device ->
                val d_state = device.d_state?.toIntOrNull() ?: 0
                val d_token = device.d_token?.toIntOrNull() ?: 0
                val rental_count = device.rental_count
                val deviceCateId = device.c_id
                val iconResId = when (deviceCateId) {
                    "c12345-007", "c12345-006" -> R.drawable.icon_tablet
                    else -> R.drawable.icon_laptop
                }
                Log.e("Response", "***category: $deviceCateId")


                val goods = Goods(
                    device.d_id ?: "",
                    iconResId,
                    device.d_name ?: "",
                    device.d_model ?: "",
                    device.d_info ?: "",
                    d_state,
                    device.c_name ?: "",
                    device.c_id ?: "",
                    d_token,
                    rental_count ?:""
                )

                filteredList.add(goods)
            }

            binding!!.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding!!.rvList.adapter = GoodsAdapter(filteredList)
            binding!!.rvList.setHasFixedSize(true)
        }
    }

}

