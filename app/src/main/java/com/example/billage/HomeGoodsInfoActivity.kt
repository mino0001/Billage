package com.example.billage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.billage.databinding.ActivityHomeGoodsinfoBinding

/**
 * homeGoodsInfo 랑 goodsInfo 중에 하나 정리하기
 * ->중복
 */
class HomeGoodsInfoActivity : ComponentActivity(){
    private var binding: ActivityHomeGoodsinfoBinding? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityHomeGoodsinfoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        val data = intent.getParcelableExtra("data", Goods::class.java)

        binding!!.tvInfoTitle.text = data!!.alias //변경하기**, 주소, 시각, 보낸 사람, 해시 등
        binding!!.btnBack.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }


}