package com.example.billage


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.billage.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? =null
    private lateinit var homeFragment: HomeFragment
    private lateinit var rentFragment: RentFragment
    private lateinit var moreFragment: MoreFragment
    private lateinit var sharedPreferences: SharedPreferences

    companion object {

        const val TAG: String = "로그"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding =ActivityMainBinding.inflate(layoutInflater)
        activityMainBinding = binding
        setContentView(activityMainBinding!!.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
//            val savedUserName = sharedPreferences.getString("userName", "")

        } else {
            // 로그인되지 않은 경우
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.btmNav.setOnItemSelectedListener(onBottomNavItemSelectedListener)
        binding.btmNav.setItemIconTintList(null);
        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragment_frame, homeFragment).commit()
    }



    private val onBottomNavItemSelectedListener = NavigationBarView.OnItemSelectedListener {

        when(it.itemId){
            R.id.menu_home -> {
                homeFragment = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, homeFragment).commit()
            }
            R.id.menu_rent -> {
                rentFragment = RentFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, rentFragment).commit()
            }
            R.id.menu_more -> {
                moreFragment = MoreFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, moreFragment).commit()
            }
        }

        true
    }

    // 뒤로가기 2번
    private var backPressedTime : Long = 0
    override fun onBackPressed() {
        Log.d("TAG", "뒤로가기")

        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
            return
        }
        // 처음 클릭 메시지
        Toast.makeText(this, "한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}