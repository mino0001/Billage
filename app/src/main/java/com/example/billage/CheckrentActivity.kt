package com.example.billage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billage.databinding.ActivityCheckrentBinding

class CheckrentActivity : ComponentActivity() {
    private var binding: ActivityCheckrentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityCheckrentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        /**
         *         이전 Fragment에서 받은 정보 -> db 로 조회해서 해당 데이터 불러오기

         */


        val rentDevice = intent.getIntExtra("device", 0)
        val cateName = intent.getStringExtra("cateName").toString()
        var cateId : String
        val rentDate = intent.getStringExtra("rentDate").toString()
        val returnDate = intent.getStringExtra("returnDate").toString()

        buttonFlag=1

        if (rentDevice == 0) {  //노트북
            cateId = when (cateName) {
                "i5" -> "c12345-003"
                "i7" -> "c12345-004"
                "macOS" -> "c12345-005"
                else -> ""
            }
        } else { //태블릿pc
            cateId = when (cateName) {
                "android" -> "c12345-007"
                "ios" -> "c12345-006"
                else -> ""
            }
        }
        val dataProcessor = DataprocessDeviceAvailable(cateId, rentDate, returnDate)


        dataProcessor.requestDeviceAvailableData { devices ->
            val filteredList: MutableList<Goods> = mutableListOf() // 빈 리스트로 초기화
            devices?.forEach {  device ->
                val d_state = device.d_state?.toIntOrNull() ?: 0
                val d_token = device.d_token?.toIntOrNull() ?: 0
                val deviceCategory = device.c_name

                val iconResId = when (deviceCategory) {
                    "android", "ios" -> R.drawable.icon_tablet
                    else -> R.drawable.icon_laptop
                }

                val goods = Goods(
                    device.d_id ?: "",
                    iconResId,
                    device.d_name ?: "",
                    device.d_model ?: "",
                    device.d_info ?: "",
                    d_state,
                    device.c_name ?: "",
                    device.c_id ?: "",
                    d_token
                )

                filteredList.add(goods)


            }

            binding!!.rvList.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding!!.rvList.adapter = GoodsAdapter(filteredList)
            binding!!.rvList.setHasFixedSize(true)
        }
    }

    //val filteredList = goodsList.filter { it.c_name == cateId}
    //.filter { it.c_id == rentOs }

//        binding!!.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
//        binding!!.rvList.adapter = GoodsAdapter(filteredList)
//        binding!!.rvList.setHasFixedSize(true)

    /**
     * 사용자가 이미 대여한 기기가 없을 경우에만 buttonFlag = 1
     */




}

