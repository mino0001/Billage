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
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billage.databinding.FragmentHomeBinding


var goodsList = mutableListOf<Goods>()
var categoryArray = arrayOf("노트북","태블릿pc")
var count = 0
var buttonFlag = 0
var loginFlag = 0
lateinit var goodsAdapter : GoodsAdapter

class HomeFragment : Fragment() {


    private var fragmentHomeBinding : FragmentHomeBinding? =null
    private var intent :Intent? = null
    private var searchView: SearchView? = null
    companion object {
        const val TAG : String = "로그"

        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }

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

        searchView = fragmentHomeBinding!!.toolbar.findViewById(R.id.btn_tb_search)
        searchView!!.setOnQueryTextListener(searchViewTextListener)

        fragmentHomeBinding!!.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btn_tb_user -> {
                    intent = Intent(context, HomeuserActivity::class.java)
                    startActivity(intent)

                    true
                }

                else -> false
            }
        }

        val categoryDataProcessor = DataprocessCategoryAll()
        categoryDataProcessor.requestDataFromWebsite { categoryDataList ->
            // 가져온 카테고리 데이터에서 이름만 추출하여 categoryArray에 추가합니다.
            categoryArray = categoryDataList.map { it.c_name }.toTypedArray()

            count = 0
            buttonFlag = 0
            loginFlag = 0
            goodsAdapter = GoodsAdapter(goodsList)

            initRecycler()
            setupCategorySpinnerHandler()

        }

        val dataProcessor = DataprocessDeviceAll()

        dataProcessor.requestDataFromWebsite { deviceDataList ->
            // 가져온 deviceDataList를 활용하여 goodsList를 업데이트합니다.
            goodsList.clear() // 기존 데이터를 초기화합니다.

            // deviceDataList를 순회하면서 Goods 객체를 생성하고 goodsList에 추가합니다.
            deviceDataList.forEach { device ->

                val d_state = device.d_state?.toIntOrNull() ?: 0
                val d_token = device.d_token?.toIntOrNull() ?: 0
                val rental_count = device.rental_count
                val deviceCategory = device.c_name

                val iconResId = when (deviceCategory) {
                    "android", "ios" -> R.drawable.icon_tablet
                    else -> R.drawable.icon_laptop
                }

                val goods = Goods(
                    device.d_id ?: "",
                    iconResId,
                    device.d_name ?: "",
                    device.d_model ?: "",
                    device.d_info ?: "",
                    d_state,
                    device.c_name ?: "",
                    device.c_id ?: "",
                    d_token,
                    rental_count ?:""

                )
                goodsList.add(goods)  // 생성한 Goods 객체를 goodsList에 추가합니다.
            }


            goodsAdapter.notifyDataSetChanged()  // RecyclerView를 갱신합니다.
        }
    }

    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                goodsAdapter.filter.filter(s)
                return false
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


                if(fragmentHomeBinding!!.spinnerCategory.getItemAtPosition(position).equals("전체")){
                    goodsAdapter=GoodsAdapter(goodsList)
                }
                else {
                    val filteredList = goodsList.filter { it.c_name == fragmentHomeBinding!!.spinnerCategory.getItemAtPosition(position)}
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

