package com.example.billage

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.billage.databinding.ActivityHomeuserBinding


private var binding: ActivityHomeuserBinding? = null

class HomeuserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeuserBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnBack.setOnClickListener{
            finish()
        }



    }
}


