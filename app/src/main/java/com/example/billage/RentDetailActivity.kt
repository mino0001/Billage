package com.example.billage

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billage.databinding.ActivityRentDetailBinding
import com.example.billage.databinding.ListReservationBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class RentDetailActivity : AppCompatActivity() {



    lateinit var AlarmPageListAdapter: NotiListAdapter
    lateinit var binding: ActivityRentDetailBinding
//    private val noti = mutableListOf<Noti>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val resBinding = ListReservationBinding.inflate(layoutInflater)
        val tvBook = resBinding.tvDateBook
        val tvStart = resBinding.tvDateStart
        val tvReturn = resBinding.tvDateReturn
        val tvDeadline = resBinding.tvDateDeadline
        val tvExplan = resBinding.tvRentExplan






//        alarmRecycler()


        binding.btnBack.setOnClickListener{
            finish()
        }

        // DataprocessRentalUser 객체를 생성합니다.
        val dataProcessor = DataprocessRentalUser(userId.toString())

        // 예약 현황 데이터를 가져옵니다.
        dataProcessor.requestDataForUser { rentalList ->
            // rentalList에는 해당 사용자의 예약 현황 데이터가 포함됩니다.
            if (rentalList.isNotEmpty()) {
                val filteredList: MutableList<Noti> = mutableListOf() // 빈 리스트로 초기화
                // rentalList를 사용하여 예약 현황을 처리합니다.
                for (rental in rentalList) {
                    val rtState = rental.rt_state.toInt()
                    val rtBook : String? = rental.rt_book.toString()
                    val rtStart : String? = rental.rt_start
                    val rtDeadline : String? = rental.rt_deadline
                    val rtReturn : String? = rental.rt_return
                    val dvId : String = rental.d_id


                    if( rtState == 0 ){
                        val rtNoti = Noti(
                            "예약 완료",
                            "기기 ID : $dvId",
                            "예약일 : $rtBook",
                            "수령일 : $rtStart",
                            "반납 기한 : $rtDeadline",
                            "반납일 : $rtReturn",
                            null
                        )
                        filteredList.add(rtNoti)

                    } else if (rtState == 1){
                        val rtNoti = Noti(
                            "대여 중",
                            "기기 ID : $dvId",
                            "예약일 : $rtBook",
                            "수령일 : $rtStart",
                            "반납 기한 : $rtDeadline",
                            "반납일 : $rtReturn",
                            null
                        )
                        filteredList.add(rtNoti)


                    }else if (rtState == 2){
                        val rtNoti = Noti(
                            "예약 취소",
                            "기기 ID : $dvId",
                            "예약일 : $rtBook",
                            "수령일 : $rtStart",
                            "반납 기한 : $rtDeadline",
                            "반납일 : $rtReturn",
                            null
                        )
                        filteredList.add(rtNoti)


                    }else if (rtState == 3){

                        var notiOverDue =if (checkForOverdue(rtReturn,rtDeadline)){
                             "연체되었습니다."
                        } else {
                             ""
                        }
                        val rtNoti = Noti(
                            "반납 완료",
                            "기기 ID : $dvId",
                            "예약일 : $rtBook",
                            "수령일 : $rtStart",
                            "반납 기한 : $rtDeadline",
                            "반납일 : $rtReturn",
                            notiOverDue
                        )
                        filteredList.add(rtNoti)


                    }else if (rtState == 4){

                        val rtNoti = Noti(
                            "연체 알림",
                            "기기 ID : $dvId",
                            "예약일 : $rtBook",
                            rtStart,
                            rtDeadline,
                            rtReturn,
                            "연체되었습니다. 반납해주세요."
                        )
                        filteredList.add(rtNoti)

                    }else{

                    }

                }
                alarmRecycler(filteredList)
            } else {
                // 해당 사용자의 예약 현황 데이터가 없을 경우 처리
//                val rtNoti = Noti(
//                    "대여 기기 없음",
//                    rtBook,
//                    rtStart,
//                    rtDeadline,
//                    rtReturn,
//                    null
//                )
//                filteredList.add(rtNoti)
            }
        }

    }

    private fun alarmRecycler(noti: MutableList<Noti>){
        AlarmPageListAdapter = NotiListAdapter(noti)
        binding.alarmRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.alarmRecyclerView.adapter = AlarmPageListAdapter

//        noti.apply{
//            add(Noti(title = "예약 확정", subtitle = "sm-12wq31 예약 완료"))
//            add(Noti(title = "연체 알림", subtitle = "sm-12wq31 연체"))
//            add(Noti(title = "반납 완료", subtitle = "sm-12wq31 반납 완료"))
//        }

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