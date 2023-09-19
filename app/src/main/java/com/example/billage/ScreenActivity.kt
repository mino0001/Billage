package com.example.billage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 3000 // Splash 화면을 표시할 시간 (3초)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash 화면을 표시할 레이아웃 설정
        setContentView(R.layout.activity_screen)


    }
}
