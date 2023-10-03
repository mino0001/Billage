package com.example.billage


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.billage.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    private var fragmentMoreBinding : FragmentMoreBinding? =null
    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var sharedPreferences: SharedPreferences


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

//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

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

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "").toString()
        val userName = sharedPreferences.getString("userName", "")
        val dataProcessor = DataprocessRentalUser(userId)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val ivState = fragmentMoreBinding!!.ivState
        val btnCancelReserve = fragmentMoreBinding!!.btnCancelReserve
        val btnDetailReserve = fragmentMoreBinding!!.btnRentDetail
        val btnChgPwd = fragmentMoreBinding!!.btnChangePw
        val btnLogout = fragmentMoreBinding!!.btnLogout
        val tvStateTitle = fragmentMoreBinding!!.tvStateTitle
        val tvStateMore = fragmentMoreBinding!!.tvStateMore
//        val btnDeviceRt = fragmentMoreBinding!!.btnDeviceReturn

        // 사용자 정보 사용
        if (isLoggedIn) {
            // 로그인된 경우 처리
            fragmentMoreBinding!!.tvMoreUserName.text = userName
            fragmentMoreBinding!!.tvMoreUserId.text = userId
        }

        dataProcessor.requestDataForUser { rentalList ->
            // rentalList에는 해당 사용자의 예약 현황 데이터가 포함됩니다.
            if (rentalList.isNotEmpty()) {
                val latestRental = rentalList.last()
                val rentalState = latestRental.rt_state.toInt()

                if (rentalState == 0 ){ // 예약완료
                    val rtBook = latestRental.rt_book
                    tvStateTitle.text = "예약완료"
                    tvStateMore.text = rtBook + " 예약 완료"
                    btnCancelReserve.isVisible = true
                    btnCancelReserve.isEnabled = true


                    btnCancelReserve.setOnClickListener{//예약취소 버튼
                        performCancelReserve()
                    }
                } else if (rentalState == 1){ //대여 중
                    val rtDeadline = latestRental.rt_deadline
                    val rtStart = latestRental.rt_start

                    tvStateTitle.text = "사용 중"
                    tvStateMore.text = "$rtStart ~ $rtDeadline "
                    btnCancelReserve.isVisible = false
                    btnCancelReserve.isEnabled = false

                }else if (rentalState == 2){ // 예약취소
                    tvStateTitle.text = "예약 취소"
                    btnCancelReserve.isVisible = false
                    btnCancelReserve.isEnabled = false

                }else if (rentalState == 3){ // 반납 완료
                    val rtReturn = latestRental.rt_return
                    tvStateTitle.text = "반납 완료"
                    tvStateMore.text = "$rtReturn 반납 완료"
                    btnCancelReserve.isVisible = false
                    btnCancelReserve.isEnabled = false

                }else if (rentalState == 4){ // 연체
                    tvStateTitle.text = "연체"
                    tvStateMore.text = "연체되었습니다. 반납해주세요."
                    ivState.setImageResource(R.drawable.icon_warning)
                    btnCancelReserve.isVisible = false
                    btnCancelReserve.isEnabled = false


                }else { //사용 기록 x
                    tvStateTitle.text = "기록 없음"
                    tvStateMore.text = "기기를 예약 해주세요."
                    ivState.setImageResource(R.drawable.icon_none)
                    btnCancelReserve.isVisible = false
                    btnCancelReserve.isEnabled = false
                    btnDetailReserve.isVisible = false
                    btnDetailReserve.isEnabled = false

                }
            } else {
                // 해당 사용자의 예약 현황 데이터가 없을 경우 처리
            }
        }

        btnChgPwd.setOnClickListener(){

            val intent = Intent(context, ChpwdActivity::class.java)

            startActivity(intent)

        }



        btnDetailReserve.setOnClickListener{

            val intent = Intent(context, MainActivity::class.java)

            startActivity(intent)
        }

        btnLogout.setOnClickListener{
            performLogout()
        }


    }
    private fun performCancelReserve() {

    }


    private fun performLogout() {
        // 로그아웃 작업을 수행
        // 예를 들어, SharedPreferences를 사용하여 로그인 상태를 관리하고 로그아웃 처리

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()



        // 필요하다면 현재의 Fragment를 백 스택에서 제거
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()

        // 로그아웃 후 원하는 동작 수행 (예: 로그인 화면으로 이동)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }




    }