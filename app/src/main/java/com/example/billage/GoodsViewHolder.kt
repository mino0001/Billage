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

        if (data.alias!!.isEmpty()){
            binding.tvItemId.text = data.more
            binding.tvItemMore.text = ""

        }else{
            binding.tvItemId.text = data.alias
            binding.tvItemMore.text =data.more
        }


        itemView.setOnClickListener {
            val intent = Intent(context, GoodsinfoActivity::class.java)
            intent.putExtra("data", data);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

   }




}