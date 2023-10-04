package com.example.billage

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.billage.databinding.ListReservationBinding

class NotiListAdapter (val notiList: MutableList<Noti>) : RecyclerView.Adapter<NotiListAdapter.ViewHolder> () {

    private lateinit var binding : ListReservationBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        binding = ListReservationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = notiList.size

    override fun onBindViewHolder(holder: ViewHolder, positoin: Int){
        holder.bind(notiList[positoin])
    }

    inner class ViewHolder( val binding: ListReservationBinding) : RecyclerView.ViewHolder(binding.root){

        //private val txtTitle: TextView = itemView.findViewById(R.id.alarm_title)
        //private val txtSubTitle: TextView = itemView.findViewById(R.id.alarm_subtitle)

        fun bind(item: Noti){
            binding.tvResTitle.text = item.title
            binding.tvDeviceId.text = item.device_id
            binding.tvDateBook.text = item.date_book
            binding.tvDateStart.text = item.date_start
            binding.tvDateDeadline.text = item.date_deadline
            binding.tvDateReturn.text = item.date_return
            var tvExplain = binding.tvRentExplan
            if (item.title == "연체 알림"){
                binding.ivNotiIcon.setImageResource(R.drawable.icon_warning)
            }else {
                binding.ivNotiIcon.setImageResource(R.drawable.icon_checked)
            }

            if (item.rt_explan.isNullOrEmpty()){
                tvExplain.isVisible=false
            }else {
                tvExplain.text = item.rt_explan

            }


            }



    }
}