package com.example.billage

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.billage.databinding.ActivityGoodsinfoBinding


class GoodsinfoActivity : ComponentActivity(){
    private var binding: ActivityGoodsinfoBinding? = null
    private var deviceId : String = ""

    //lateinit var datas : Nft
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityGoodsinfoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        // 주소랑 시각 등등 정보 바인

        var sharedPreferences= this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val data = intent.getParcelableExtra("data", Goods::class.java)
        deviceId = data!!.id
        val userRtState = sharedPreferences.getInt("userRtState", 5)
        val btnToRent = binding!!.btnSubmit

        binding!!.tvInfoTitle.text = data!!.name
        binding!!.tvModel.text = data!!.model
        binding!!.tvDetailInfo.text = data!!.more
//        binding!!.tvRentNum.text = data!!.



        binding!!.btnBack.setOnClickListener{
            finish()
        }
        if ((userRtState == 2 || userRtState == 3) && buttonFlag == 1){
            btnToRent.text ="대여하기"
            btnToRent.isEnabled = true
            btnToRent.isVisible = true

            btnToRent.setOnClickListener{
                showRentConfirmationDialog()
            }

        } else if (buttonFlag == 0 ) {
            btnToRent.isEnabled = false
            btnToRent.isVisible = false
        }
        else {
            btnToRent.text ="대여 불가"
            btnToRent.isEnabled = false //비활성화
            btnToRent.setBackgroundResource(R.drawable.btn_no_border)
//            btnToRent.setBackgroundColor(R.drawable.btn_no_border)
//            binding!!.btnSubmit.visibility= View.GONE
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
            performReserve()
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            showToast("예약이 완료되었습니다.")

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

    private fun performReserve() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "") ?: ""
        val rentDate = intent.getStringExtra("rentDate").toString()
        val returnDate = intent.getStringExtra("returnDate").toString()


        // DataprocessReserveNew 인스턴스 생성
        val reserveProcessor = DataprocessReserveNew(userId, deviceId, rentDate, returnDate)

        // 예약 요청 및 결과 처리
        reserveProcessor.requestReservation { result ->
            if (result == "success") {
                // 예약 성공 시 처리
                showToast("예약이 완료되었습니다.")
            } else {
                // 예약 실패 시 처리
                showToast("예약에 실패했습니다.")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



}