package com.example.billage

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billage.databinding.ActivityRentDetailBinding
import com.example.billage.databinding.ListReservationBinding
import java.text.SimpleDateFormat
import java.util.Locale

class RentDetailActivity : AppCompatActivity() {



    lateinit var AlarmPageListAdapter: NotiListAdapter
    lateinit var binding: ActivityRentDetailBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")



        binding.btnBack.setOnClickListener{
            finish()
        }

        val dataProcessor = DataprocessRentalUser(userId.toString())

        dataProcessor.requestDataForUser ({ rentalList ->
            if (rentalList.isNotEmpty()) {
                val filteredList = mutableListOf<Noti>()
                // rentalList를 사용하여 예약 현황을 처리합니다.
                for (rental in rentalList.reversed()) {
                    val rtState = rental.rt_state.toInt()?: 5
                    val rtBook : String? = rental.rt_book?: ""
                    val rtStart : String? = rental.rt_start?: ""
                    val rtDeadline : String? = rental.rt_deadline ?: ""
                    val rtReturn : String? = rental.rt_return ?: ""
                    val dvId : String = rental.d_id?: ""

                    val notiTitle: String
                    var notiOverdue: String? = null

                    when (rtState) {
                        0 -> {
                            notiTitle = "예약 완료"
                        }
                        1 -> {
                            notiTitle = "대여 중"
                        }
                        2 -> {
                            notiTitle = "예약 취소"
                        }
                        3 -> {
                            notiTitle = "반납 완료"
                            notiOverdue = if (checkForOverdue(rtReturn, rtDeadline)) {
                                "연체되었습니다."
                            } else {
                                null
                            }
                        }4 -> {
                        notiTitle = "연체 알림"
                        notiOverdue = "연체되었습니다. \n 반납해주세요."
                    }
                        else -> {
                            // 다른 상태에 대한 처리 필요
                            continue
                        }
                    }

                    val rtNoti = Noti(
                        notiTitle,
                        "기기 ID : $dvId",
                        "예약일 : $rtBook",
                        "수령일 : $rtStart",
                        "반납 기한 : $rtDeadline",
                        "반납일 : $rtReturn",
                        notiOverdue
                    )

                    filteredList.add(rtNoti)


                }
                alarmRecycler(filteredList)

            }else{ }
        },{})

    }

    private fun alarmRecycler(noti: MutableList<Noti>){
        AlarmPageListAdapter = NotiListAdapter(noti)
        binding.alarmRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.alarmRecyclerView.adapter = AlarmPageListAdapter


        AlarmPageListAdapter.notifyDataSetChanged()
    }

    fun checkForOverdue(dtReturn: String?, dtDeadline: String?): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            val returnDate = dateFormat.parse(dtReturn)
            val deadlineDate = dateFormat.parse(dtDeadline)

            // 두 날짜를 비교하여 연체 여부 확인
            return returnDate.after(deadlineDate)
        } catch (e: Exception) {
            // 날짜 파싱 오류 처리
            e.printStackTrace()
        }
        // 오류가 발생하거나 날짜 형식이 잘못된 경우 연체로 처리
        return true
    }
}