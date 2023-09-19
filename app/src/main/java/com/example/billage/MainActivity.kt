package com.example.billage


import LoginActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.billage.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

var loginFlag = 0
class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? =null
    private lateinit var homeFragment: HomeFragment
    private lateinit var rentFragment: RentFragment
    private lateinit var moreFragment: MoreFragment

    companion object {

        const val TAG: String = "로그"

    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding =ActivityMainBinding.inflate(layoutInflater)
        activityMainBinding = binding
        // 레이아웃과 연결
        setContentView(activityMainBinding!!.root)

        if (loginFlag== 0) {
            /**
             * 예시
             */
            // 로그인되어 있지 않으면 LoginActivity로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.btmNav.setOnItemSelectedListener(onBottomNavItemSelectedListener)
        binding.btmNav.setItemIconTintList(null);
        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragment_frame, homeFragment).commit()
        //처음에만 add, 나머지는 replace
    }

//    override fun onRestart() {
//        super.onRestart()
//        HomeFragment.kt().initRecycler()
//    }


    // 바텀네비게이션 아이템 클릭 리스너 설정
    private val onBottomNavItemSelectedListener = NavigationBarView.OnItemSelectedListener {

        when(it.itemId){
            R.id.menu_home -> {
                homeFragment = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, homeFragment).commit()
            }
            R.id.menu_rent -> {

                rentFragment = RentFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, rentFragment).commit()

                //cameraFragment = CameraFragment.newInstance()
                //supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, cameraFragment).commit()
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
}