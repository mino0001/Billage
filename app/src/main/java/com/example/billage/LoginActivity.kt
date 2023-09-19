import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.billage.MainActivity
import com.example.billage.databinding.ActivityLoginBinding
import com.example.billage.loginFlag

private var binding: ActivityLoginBinding? = null

class LoginActivity : AppCompatActivity() {
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnLogin.setOnClickListener(){


        }

        binding!!.btnSignup.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding!!.btnSignup.setOnClickListener(){
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



//        usernameEditText = findViewById(R.id.usernameEditText)
//        passwordEditText = findViewById(R.id.passwordEditText)
    }

    fun onLoginClick(view: View) {
        val userId = binding!!.etUserId.text.toString()
        val userPw = binding!!.etUserPw.text.toString()

        // 여기에서 로그인 처리를 진행하면 됩니다.
        // 실제로는 사용자의 입력과 데이터베이스 등을 비교하여 로그인 결과를 판단하게 될 것입니다.

        loginFlag = 1


        if (isValidCredentials(userId, userPw)) {
            // 로그인 성공
            //loginFlag = 1
            showToast("로그인 성공!")

            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // 로그인 실패
            showToast("로그인 실패. 사용자 이름과 비밀번호를 확인하세요.")
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        // 여기에서 사용자 인증을 수행하고 결과를 반환합니다.
        // 데이터베이스 조회, 서버 요청 등
        // 이 예제에서는 간단히 "user"라는 사용자 이름과 "password"라는 비밀번호로 로그인 성공하는 것으로 가정합니다.
        return username == "user" && password == "password"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
