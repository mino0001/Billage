package com.example.billage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.billage.databinding.ListReservationBinding

class NotiListAdapter (val notiList: MutableList<Noti>) : RecyclerView.Adapter<NotiListAdapter.ViewHolder> () {

    private lateinit var binding : ListReservationBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val binding = ListReservationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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
            binding.alarmTitle.text = item.title
            binding.alarmSubtitle.text = item.subtitle

        }

    }
}