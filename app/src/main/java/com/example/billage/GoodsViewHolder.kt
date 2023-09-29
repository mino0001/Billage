package com.example.billage

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.billage.databinding.ListItemBinding


class GoodsViewHolder (val binding: ListItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    init{
        binding.root.setOnClickListener(){

        }
    }

    fun bind(data: Goods) {

        binding.ivItem.setImageResource(data.img_goods)

        if (data.name!!.isEmpty()){
            binding.tvItemId.text = data.model
            binding.tvItemMore.text = ""

        }else{
            binding.tvItemId.text = data.name
            binding.tvItemMore.text =data.model
        }


        itemView.setOnClickListener {
            val intent = Intent(context, GoodsinfoActivity::class.java)
            intent.putExtra("data", data);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

   }




}