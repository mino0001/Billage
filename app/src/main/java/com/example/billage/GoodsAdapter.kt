package com.example.billage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.billage.databinding.ListItemBinding

class GoodsAdapter(val goodsList: MutableList<Goods>) : RecyclerView.Adapter<GoodsViewHolder>() {

    private lateinit var binding : ListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GoodsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val item = goodsList[position]
        holder.bind(item)

        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 300
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int {
        return goodsList.size
    }
}