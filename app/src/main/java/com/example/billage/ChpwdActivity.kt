package com.example.billage

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.billage.databinding.ActivityChpwdBinding



class ChpwdActivity : ComponentActivity() {

    private var binding: ActivityChpwdBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChpwdBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnChgPw.setOnClickListener{
            var isExistBlank = false
            var isPWSame = false
            val etNewPw = binding!!.etChgPwd.text.toString()
            val etNewPwCheck = binding!!.etChgPwdCheck.text.toString()
            val etExPw = binding!!.etPwdEx.text.toString()
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getString("userId", "") ?: ""

            if(etNewPw.isEmpty() || etNewPwCheck.isEmpty() || etExPw.isEmpty()){
                isExistBlank = true
            } else{
                if(etNewPw == etNewPwCheck){
                    isPWSame = true
                }
            }

            if (isExistBlank) {
                showToast("입력 정보를 모두 입력해주세요.")
            } else if(!isPWSame){
                showToast("새 비밀번호가 일치하지 않습니다.")
            } else if(!isValidPassword(etNewPw)){
                showToast("비밀번호를 8자 이상 입력해주세요.")
            } else {
                val dataProcessor = DataprocessUserPasswordChange(userId, etExPw, etNewPw)

                dataProcessor.requestUserPasswordChange { result ->
                    if (result == "success") {
                        showToast("비밀번호가 변경되었습니다.")
                        finish()
                    } else if (result == "fail_old") {
                        showToast("현재 비밀번호가 일치하지 않습니다.")
                    }
                    else {
                        showToast("비밀번호 변경에 실패. 다시 시도해주세요.")
                    }
                }

            }
        }



    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
