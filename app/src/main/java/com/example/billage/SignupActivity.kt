package com.example.billage

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.billage.databinding.ActivitySignupBinding

private var binding: ActivitySignupBinding? = null

class SignupActivity : ComponentActivity() {

    var isExistBlank = false
    var isPWSame = false
//    private lateinit var userId: EditText
//    private lateinit var userPw: EditText
//    private lateinit var userPhone: EditText
//    private lateinit var userEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

    }

    fun onSignupClick(view: View) {
        val userId = binding!!.etNewUserId.text.toString()
        val userPw = binding!!.etNewUserPw.text.toString()
        val userPwRe = binding!!.etNewUserPwRe.text.toString()
        val userName = binding!!.etNewUserName.text.toString()
        val userPhone = binding!!.etNewUserPhone.text.toString()
        val userEmail = binding!!.etNewUserEmail.text.toString()


        if(userId.isEmpty() || userPw.isEmpty() || userPwRe.isEmpty()|| userPhone.isEmpty()||userEmail.isEmpty()||userName.isEmpty()){
            isExistBlank = true
        } else{
            if(userPw == userPwRe){
                isPWSame = true
            }
        }

        if (isExistBlank
        ) {
            showToast("입력 정보를 모두 입력해주세요.")
        } else if(!isPWSame){
            showToast("비밀번호가 일치하지 않습니다.")
        } else if(!isValidEmail(userEmail)){
            showToast("올바른 이메일을 입력해주세요.")
        } else if(!isValidPassword(userPw)){
            showToast("비밀번호를 8자 이상 입력해주세요.")
        } else {
            val dataProcessor = DataprocessAddUser(userId, userPw, userName, userPhone, userEmail)

            dataProcessor.requestAddUserData { result ->
                if (result == "success") {
                    showToast("회원가입이 완료되었습니다.")
                } else {
                    showToast("회원가입에 실패하였습니다.")
                }
            }

            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }


    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}