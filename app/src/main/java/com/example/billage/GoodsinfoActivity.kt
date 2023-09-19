package com.example.billage

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.billage.databinding.ActivityGoodsinfoBinding


class GoodsinfoActivity : ComponentActivity(){
    private var binding: ActivityGoodsinfoBinding? = null
    //lateinit var datas : Nft
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityGoodsinfoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        // 주소랑 시각 등등 정보 바인


        val data = intent.getParcelableExtra("data", Goods::class.java)

        binding!!.tvInfoTitle.text = data!!.alias //변경하기**, 주소, 시각, 보낸 사람, 해시 등
        binding!!.btnBack.setOnClickListener{
            finish()
            //intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
        }
        if (buttonFlag==0){
            binding!!.btnSubmit.isEnabled = false //비활성화
            binding!!.btnSubmit.visibility= View.GONE
        } else {
            binding!!.btnSubmit.setOnClickListener{
                //binding!!.nftcategory.toString()

                showRentConfirmationDialog()


//            카테고리 설정, 별명 -> 처리
            }
        }



    }

    private fun showRentConfirmationDialog() {
        val builder = AlertDialog.Builder(this)

        // 대화상자 제목과 메시지 설정
        builder.setTitle("예약 확인")
        builder.setMessage("대여 예약을 하시겠습니까?")

        // "네" 버튼 추가 및 클릭 리스너 설정
        builder.setPositiveButton("네") { dialog, which ->
            // "네" 버튼을 클릭한 경우 실행될 코드
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            showToast("예약이 완료되었습니다.")

            /**
             * 예약 완료 처리
             */
        }

        // "아니오" 버튼 추가 및 클릭 리스너 설정
        builder.setNegativeButton("아니오") { dialog, which ->
            // "아니오" 버튼을 클릭한 경우 실행될 코드
            dialog.dismiss() // 대화상자 닫기
        }

        // 대화상자 표시
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



}