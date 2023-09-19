package com.example.billage

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.billage.databinding.ActivitySend2Binding

class Send2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySend2Binding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnTransmit.setOnClickListener {
//            val intent = Intent(this, NotiActivity::class.java)
//            startActivity(intent)
//
//        }
    }
}
