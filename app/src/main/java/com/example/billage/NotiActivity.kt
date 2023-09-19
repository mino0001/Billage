package com.example.billage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billage.databinding.ActivityNotiBinding

class NotiActivity : AppCompatActivity() {



    lateinit var AlarmPageListAdapter: NotiListAdapter
    lateinit var binding: ActivityNotiBinding
    private val noti = mutableListOf<Noti>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmRecycler()


        binding.btnBack.setOnClickListener{
            finish()
        }

    }

    private fun alarmRecycler(){
        AlarmPageListAdapter = NotiListAdapter(noti)
        binding.alarmRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.alarmRecyclerView.adapter = AlarmPageListAdapter

        noti.apply{
            add(Noti(title = "예약 확정", subtitle = "sm-12wq31 예약 완료"))
            add(Noti(title = "연체 알림", subtitle = "sm-12wq31 연체"))
            add(Noti(title = "반납 완료", subtitle = "sm-12wq31 반납 완료"))
        }

        AlarmPageListAdapter.notifyDataSetChanged()
    }
}
