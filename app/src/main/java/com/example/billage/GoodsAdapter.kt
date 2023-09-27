package com.example.billage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.billage.databinding.ListItemBinding
import java.util.Locale

private val wholeList : MutableList<Goods> = goodsList

class GoodsAdapter(var goodsList: MutableList<Goods>) : RecyclerView.Adapter<GoodsViewHolder>(),
    Filterable {

    private lateinit var binding: ListItemBinding
//    private var filteredList = ArrayList<Goods>()
    var itemFilter = ItemFilter()

//    init {
//        filteredList.addAll(goodsList)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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


    //private var files: MutableList<Goods> = goodsList
    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString().trim { it <= ' ' }
            val results = FilterResults()
//            var filteredList = ArrayList<Goods>()


            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.isEmpty()) {
                results.values = wholeList
                results.count = wholeList.size
                //results.count = goodsList.size
                //filteredList.clear()
//                filteredList.addAll(wholeList)
            }
            //alias로 검색
            else {
                //val filteredList = ArrayList<Goods>()
//                for (goods in wholeList) {
//                    if (goods.alias.lowercase(Locale.ROOT).contains(filterString.lowercase(Locale.ROOT))) {
//                        filteredList.add(goods)
//                    }
//                }
                val filteredList = wholeList.filter { goods ->
                    goods.alias.lowercase(Locale.ROOT).contains(filterString.lowercase(Locale.ROOT))
                }
                results.values = filteredList
                results.count = filteredList.size
                //results.count = filteredList.size
            }

//            results.values = filteredList
//            results.count = filteredList.size

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//            if (results != null && results.count > 0) {
//                goodsList.clear()
//                goodsList.addAll(results.values as ArrayList<Goods>) // 필터링된 결과를 대입해야 합니다.
//            } else {
//                goodsList.clear()
//                goodsList.addAll(filteredList)
//            }
//            // filteredList를 업데이트해야 함
//            filteredList.clear()
//            filteredList.addAll(goodsList)
//            notifyDataSetChanged()

            if (results != null) {
                goodsList.clear()
                goodsList.addAll(results.values as MutableList<Goods>)
            }else {
                // 결과가 null이거나 값이 null인 경우 전체 목록으로 되돌립니다.
                goodsList.clear()
                goodsList.addAll(wholeList)
            }
            notifyDataSetChanged()


        }

    }
}