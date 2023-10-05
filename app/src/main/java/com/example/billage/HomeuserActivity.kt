package com.example.billage

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.billage.databinding.ActivityHomeuserBinding



class HomeuserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val userName = sharedPreferences.getString("userName", "")
        val userPhone = sharedPreferences.getString("userPhone", "")
        val userEmail = sharedPreferences.getString("userEmail", "")
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        var binding = ActivityHomeuserBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        // 사용자 정보 사용
        if (isLoggedIn) {
            // 로그인된 경우 처리
            binding!!.tvUserName.setText(userName)
            binding!!.tvUserId.setText(userId)
            binding!!.tvUserEmail.setText(userEmail)
            binding!!.tvUserPhone.setText(userPhone)
        }


        binding!!.btnBack.setOnClickListener{
            finish()
        }

    }
}



