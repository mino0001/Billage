package com.example.billage


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.billage.databinding.FragmentMoreBinding
import com.example.billage.databinding.FragmentRentBinding

class MoreFragment : Fragment() {

    private var fragmentMoreBinding : FragmentMoreBinding? =null

    companion object {
        const val TAG : String = "로그"

        fun newInstance() : MoreFragment {
            return MoreFragment()
        }

    }

    // 메모리에 올라갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ProfileFragment - onCreate() called")
    }

    // 프레그먼트를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "ProfileFragment - onAttach() called")
    }

    // 뷰가 생성되었을 때
    // 프레그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        Log.d(TAG, "ProfileFragment - onCreateView() called")

        val binding : FragmentMoreBinding = FragmentMoreBinding.inflate(inflater,container,false)
        fragmentMoreBinding = binding

        return fragmentMoreBinding!!.root

        /***
         *  사용자가 대여한 제품 -> 예약 현황
         *
         *  state 어떻게 분류하는지
         *
         */



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val btnCancelReserve = fragmentMoreBinding!!.btnCancelReserve
//        val btnDetailReserve = fragmentMoreBinding!!.btnRentDetail
//        val btnChgPwd = fragmentMoreBinding!!.btnChangePw

        fragmentMoreBinding!!.btnChangePw.setOnClickListener(){

            val intent = Intent(context, ChpwdActivity::class.java)

            startActivity(intent)

        }


    }


}