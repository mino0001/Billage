package com.example.billage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.billage.databinding.ActivityLoginBinding



class LoginActivity : ComponentActivity() {
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText

    private var binding: ActivityLoginBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    var userName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        //binding!!.btnSignup.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding!!.btnSignup.setOnClickListener(){
            intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }



//        usernameEditText = findViewById(R.id.usernameEditText)
//        passwordEditText = findViewById(R.id.passwordEditText)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    fun onLoginClick(view: View) {
        val userId = binding!!.etUserId.text.toString()
        val userPw = binding!!.etUserPw.text.toString()

        // 여기에서 로그인 처리를 진행하면 됩니다.
        // 실제로는 사용자의 입력과 데이터베이스 등을 비교하여 로그인 결과를 판단하게 될 것입니다.

//        loginFlag = 1
//
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)

        isValidCredentials(userId, userPw) { isValid ->
            if (isValid) {
                // 로그인 성공
                showToast(userName+"님 환영합니다!")


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // 로그인 실패
                Log.e("Login", "로그인 실패")
                showToast("로그인 실패. 사용자 아이디와 비밀번호를 확인하세요.")
            }
        }



    }

    private fun isValidCredentials(username: String, password: String, callback: (Boolean) -> Unit) {
        val dataProcessor = DataprocessLogin(username, password)

        dataProcessor.requestLoginData { user ->
            if (user != null) {
                // 로그인 성공한 경우 사용자 정보를 사용하세요
                userName = user.u_name
                val userId = user.u_id
                val userEmail = user.u_email
                val userPhone = user.u_phone
//                loginFlag = 1

                val editor = sharedPreferences.edit()
                editor.putString("userName", userName)
                editor.putString("userId", userId)
                editor.putString("userEmail", userEmail)
                editor.putString("userPhone", userPhone)
                editor.putBoolean("isLoggedIn", true)
                editor.apply()
                // 로그인 성공을 콜백에 알립니다.
                callback(true)
            } else {
                // 로그인 실패한 경우 콜백에 알립니다.
                callback(false)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
