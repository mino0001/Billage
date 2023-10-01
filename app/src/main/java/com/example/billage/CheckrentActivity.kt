package com.example.billage

import android.os.Bundle
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


        val rentDevice = intent.getIntExtra("device",0)
        val rentOs = intent.getIntExtra("os",0)
        val rentDate = intent.getStringExtra("rentDate")
        val returnDate = intent.getStringExtra("returnDate")

        val rentCateory = if (rentDevice == 0) {
            //노트북
            if(rentOs == 0 ){
                "macOS"
            } else if ( rentOs == 1){
                "i5"
            } else{
                "i7"
            }
        } else {
            //태블릿pc
            if(rentOs == 0) "ios"
            else "android"
        }

        val filteredList = goodsList.filter { it.c_name == rentCateory}
            //.filter { it.c_id == rentOs }

        binding!!.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding!!.rvList.adapter = GoodsAdapter(filteredList.toMutableList())
        binding!!.rvList.setHasFixedSize(true)

        /**
         * 사용자가 이미 대여한 기기가 없을 경우에만 buttonFlag = 1
         */
        buttonFlag=1








    }

}