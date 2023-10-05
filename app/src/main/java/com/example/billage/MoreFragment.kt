package com.example.billage


import android.app.AlertDialog
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

    private var fragmentMoreBinding : FragmentMoreBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userId : String
    private lateinit var rentalId : String


    companion object {
        const val TAG : String = "로그"

        fun newInstance() : MoreFragment {
            return MoreFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ProfileFragment - onCreate() called")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "ProfileFragment - onAttach() called")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        Log.d(TAG, "ProfileFragment - onCreateView() called")
        val binding = FragmentMoreBinding.inflate(inflater,container,false)
        fragmentMoreBinding = binding

        return fragmentMoreBinding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", "").toString()
        val userName = sharedPreferences.getString("userName", "")
        val editor = sharedPreferences.edit()
        val dataProcessor = DataprocessRentalUser(userId)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val ivState = fragmentMoreBinding!!.ivState
        val btnCancelReserve = fragmentMoreBinding!!.btnCancelReserve
        val btnDetailReserve = fragmentMoreBinding!!.btnRentDetail
        val btnChgPwd = fragmentMoreBinding!!.btnChangePw
        val btnLogout = fragmentMoreBinding!!.btnLogout
        val tvStateTitle = fragmentMoreBinding!!.tvStateTitle
        val tvStateMore = fragmentMoreBinding!!.tvStateMore



        if (isLoggedIn) {
            fragmentMoreBinding!!.tvMoreUserName.text = userName
            fragmentMoreBinding!!.tvMoreUserId.text = userId
        }

        dataProcessor.requestDataForUser ({ rentalList ->
            if (rentalList.isNotEmpty()) {
                val latestRental = rentalList.last()
                val rentalState = latestRental.rt_state.toInt() ?: 5
                rentalId = latestRental.rt_id

                editor.putInt("userRtState", rentalState)
                editor.apply()


                if (rentalState == 0 ){ // 예약완료
                    val rtBook = latestRental.rt_book
                    tvStateTitle.text = "예약완료"
                    tvStateMore.text = rtBook + " 예약 완료"
                    btnCancelReserve.isVisible = true
                    btnCancelReserve.isEnabled = true


                    btnCancelReserve.setOnClickListener{//예약취소 버튼
                        showRentConfirmationDialog()
                    }
                } else if (rentalState == 1){ //대여 중
                    val rtDeadline = latestRental.rt_deadline
                    val rtStart = latestRental.rt_start

                    tvStateTitle.text = "대여 중"
                    tvStateMore.text = "$rtStart ~ $rtDeadline "
                    btnCancelReserve.isVisible = false
                    btnCancelReserve.isEnabled = false

                }else if (rentalState == 2){ // 예약취소
                    tvStateTitle.text = "예약 취소"
                    tvStateMore.text = "예약이 취소되었습니다."
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
                    tvStateMore.text = "* 연체 *  반납해주세요."
                    ivState.setImageResource(R.drawable.icon_warning)
                    btnCancelReserve.isVisible = false
                    btnCancelReserve.isEnabled = false


                }else { }
            } else { }
        }, {
            tvStateTitle.text = "기록 없음"
            tvStateMore.text = "기기를 예약 해주세요."
            ivState.setImageResource(R.drawable.icon_none)
            btnCancelReserve.isVisible = false
            btnCancelReserve.isEnabled = false
            btnDetailReserve.isVisible = true
            btnDetailReserve.isEnabled = true

            editor.putInt("userRtState", 5)
            editor.apply()
        })


        btnChgPwd.setOnClickListener(){
            val intent = Intent(context, ChpwdActivity::class.java)
            startActivity(intent)
        }


        btnDetailReserve.setOnClickListener{
            val intent = Intent(context, RentDetailActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener{
            performLogout()
        }


    }
    private fun performCancelReserve() {
        val dataProcessor = DataprocessReserveCancel(rentalId)

        dataProcessor.requestReserveCancel { result ->
            if (result == "success") {
                (activity as MainActivity).showToast("예약이 취소되었습니다.")
            } else {
                (activity as MainActivity).showToast("예약 취소에 실패했습니다.")
            }
        }
    }


    private fun performLogout() {
        // SharedPreferences를 사용하여 로그인 상태를 관리하고 로그아웃 처리

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        // 현재의 Fragment를 백 스택에서 제거
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()

        // 로그아웃 후 로그인 화면으로 이동
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun showRentConfirmationDialog() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("예약 취소 확인")
        builder.setMessage("예약을 취소 하시겠습니까?")

        builder.setPositiveButton("네") { dialog, which ->
            performCancelReserve()
        }

        builder.setNegativeButton("아니오") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}