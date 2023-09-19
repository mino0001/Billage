package com.example.billage


import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billage.databinding.FragmentHomeBinding


var goodsList = mutableListOf<Goods>()
var categoryArray = arrayOf("노트북","태블릿pc")
var count = 0
var buttonFlag = 0
lateinit var goodsAdapter : GoodsAdapter

class HomeFragment : Fragment() {


    //뷰가 사라질 때 즉 메모리에서 날라갈 때 같이 날리기 위해 따로 빼두기
    private var fragmentHomeBinding : FragmentHomeBinding? =null
    private var intent :Intent? = null
    companion object {
        const val TAG : String = "로그"

        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }

    // 메모리에 올라갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentHomeBinding!!.toolbar.inflateMenu(R.menu.toolbar_menu)
       initRecycler()
        setupCategorySpinnerHandler()
        buttonFlag=0


        fragmentHomeBinding!!.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btn_tb_user -> {
                    intent = Intent(context, HomeuserActivity::class.java)
                    startActivity(intent)

                    true
                }
                R.id.btn_tb_noti -> {
                    intent = Intent(context, NotiActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.tb_nft_new -> { //등록
                    intent = Intent(context, Send2Activity::class.java)
                    startActivity(intent)
                    true
                }
//                R.id.tb_nft_send -> { //대여
//                    intent = Intent(context, Send2Activity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                R.id.tb_category_edit -> { //반납
//                    intent = Intent(context, MainActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
                else -> false
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val binding : FragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        fragmentHomeBinding = binding

        return fragmentHomeBinding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onDestroyView() {
        fragmentHomeBinding = null
        super.onDestroyView()
    }

    fun initRecycler(){


        fragmentHomeBinding!!.rvNftList.setHasFixedSize(true)
        val dividerItemDecoration =
            DividerItemDecoration(fragmentHomeBinding!!.rvNftList.context, LinearLayoutManager(context).orientation)
        fragmentHomeBinding!!.rvNftList.addItemDecoration(dividerItemDecoration)
        fragmentHomeBinding!!.rvNftList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        goodsAdapter  = GoodsAdapter(goodsList)
        fragmentHomeBinding!!.rvNftList.adapter = goodsAdapter
        goodsAdapter.notifyDataSetChanged()


    }

    private fun setupCategorySpinnerHandler(){
        fragmentHomeBinding!!.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                if(count==0){
                    count++
                    goodsList.apply {
                        add(Goods(1,R.drawable.icon_laptop, "sm-12wq31", "more_1", "노트북",0,false))
                        add(Goods(2,R.drawable.icon_tablet, "sm-12wq32", "more_2", "태블릿pc",1,false))
                        add(Goods(3,R.drawable.icon_laptop, "sm-12wq33", "more_3", "노트북",2,false))
                        add(Goods(4,R.drawable.icon_laptop, "sm-12wq34", "more_4", "노트북",1,false))
                        add(Goods(5,R.drawable.icon_tablet, "sm-12wq35", "more_5", "태블릿pc",0,false))
                        add(Goods(6,R.drawable.icon_tablet, "sm-12wq36", "more_6", "태블릿pc",1,false))
                        add(Goods(7,R.drawable.icon_laptop, "sm-12wq37", "more_7", "노트북",2,false))
                        add(Goods(8,R.drawable.icon_laptop, "sm-12wq38", "more_8", "노트북",1,false))
                    }
                }


                if(fragmentHomeBinding!!.spinnerCategory.getItemAtPosition(position).equals("전체")){
                    goodsAdapter=GoodsAdapter(goodsList)
                }
                else {
                    val filteredList = goodsList.filter { it.category == fragmentHomeBinding!!.spinnerCategory.getItemAtPosition(position)}
                    goodsAdapter =GoodsAdapter(filteredList.toMutableList())
                }

                fragmentHomeBinding!!.rvNftList.adapter = goodsAdapter
                goodsAdapter.notifyDataSetChanged()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

}

