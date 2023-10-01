package com.example.billage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.billage.databinding.ActivityChpwdBinding


private var binding: ActivityChpwdBinding? = null

class ChpwdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChpwdBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



    }




    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
