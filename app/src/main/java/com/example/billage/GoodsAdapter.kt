package com.example.billage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.billage.databinding.ListItemBinding
import java.util.Locale


class GoodsAdapter(var goodsList: MutableList<Goods>) : RecyclerView.Adapter<GoodsViewHolder>(),
    Filterable {

    private lateinit var binding: ListItemBinding
    var filteredGoods = ArrayList<Goods>()
    var itemFilter = ItemFilter()

    init {
        filteredGoods.addAll(goodsList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoodsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val item = filteredGoods[position]
        holder.bind(item)

        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 300
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int {
        return filteredGoods.size
    }


    //private var files: MutableList<Goods> = goodsList
    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString().trim { it <= ' ' }
            val results = FilterResults()
            val filteredList: ArrayList<Goods> = ArrayList<Goods>()
//            var filteredList = ArrayList<Goods>()


            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.isEmpty()) {
                results.values = goodsList
                return results


            }
            else {
                //val filteredList = ArrayList<Goods>()
                for (goods in goodsList) {
                    if (goods.name.lowercase(Locale.ROOT).contains(filterString.lowercase(Locale.ROOT)) ||
                        goods.id.lowercase(Locale.ROOT).contains(filterString.lowercase(Locale.ROOT))) {
                        filteredList.add(goods)
                    }
                }

            }

            results.values = filteredList
//            results.count = filteredList.size

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {


            filteredGoods.clear()
            filteredGoods.addAll(results?.values as MutableList<Goods>)
            notifyDataSetChanged()


        }

    }
}