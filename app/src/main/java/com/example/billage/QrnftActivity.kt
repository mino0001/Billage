package com.example.billage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.billage.databinding.ActivityQrnftBinding

class QrnftActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityQrnftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGohome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}
