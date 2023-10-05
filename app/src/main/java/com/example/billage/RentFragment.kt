package com.example.billage



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.billage.databinding.FragmentRentBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class RentFragment : Fragment() {


    //뷰가 사라질 때 즉 메모리에서 날라갈 때 같이 날리기 위해 따로 빼두기
    private var fragmentRentBinding : FragmentRentBinding? =null
    companion object {
        const val TAG : String = "로그"

        fun newInstance() : RentFragment {
            return RentFragment()
        }
    }



    // 메모리에 올라갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGp1 = fragmentRentBinding!!.radioGroup1
        val radioGp2 = fragmentRentBinding!!.radioGroup2
        val radioGp3 = fragmentRentBinding!!.radioGroup3
        val radioLp = fragmentRentBinding!!.rbtnLaptop
        val radioTpc = fragmentRentBinding!!.rbtnTablet
        val tvQst3 = fragmentRentBinding!!.tvQuestion3
        var rentDate: String
        var returnDate: String
        val btnRent = fragmentRentBinding!!.btnRentAvailable


        val constraintLayout: ConstraintLayout = fragmentRentBinding!!.layout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)


        for (i in 0 until radioGp3.getChildCount()) {
            radioGp3.getChildAt(i).isEnabled=false
            radioGp3.getChildAt(i).isVisible=false
        }

        radioGp1.setOnCheckedChangeListener{radioGroup, checkedId ->
            if(checkedId == radioLp.id){
                for (i in 0 until radioGp2.getChildCount()) {
                    radioGp2.getChildAt(i).isEnabled=true
                    radioGp2.getChildAt(i).isVisible=true

                }
                for (i in 0 until radioGp3.getChildCount()) {
                    radioGp3.getChildAt(i).isEnabled=false
                    radioGp3.getChildAt(i).isVisible=false

                }

                constraintSet.connect(
                    tvQst3.id,
                    ConstraintSet.TOP,
                    radioGp2.id,
                    ConstraintSet.BOTTOM,
                    5
                )

                constraintSet.applyTo(constraintLayout)


            }else if(checkedId == radioTpc.id){
                for (i in 0 until radioGp2.getChildCount()) {
                    radioGp2.getChildAt(i).isEnabled=false
                    radioGp2.getChildAt(i).isVisible=false

                }
                for (i in 0 until radioGp3.getChildCount()) {
                    radioGp3.getChildAt(i).isEnabled=true
                    radioGp3.getChildAt(i).isVisible=true
                }

                constraintSet.connect(
                    tvQst3.id,
                    ConstraintSet.TOP,
                    radioGp3.id,
                    ConstraintSet.BOTTOM,
                    5
                )

                constraintSet.applyTo(constraintLayout)
            }
    }

        btnRent.setOnClickListener{
            rentDate = fragmentRentBinding!!.etRentDate.text.toString()
            returnDate = fragmentRentBinding!!.etReturnDate.text.toString()


            if(compareDates(rentDate, returnDate)) {
                    // 유효한 날짜 형식인 경우
                    val rgp1Checked = radioGp1.checkedRadioButtonId
                    val rgp2Checked = radioGp2.checkedRadioButtonId
                    val rgp3Checked = radioGp3.checkedRadioButtonId
                    var option1 = rgp1Checked //노트북, 태블릿PC
                    var option2: String? = null //os

                    if (rgp1Checked == R.id.rbtn_laptop) {// 노트북
                        option1 = 0
                        option2 = when (rgp2Checked) {
                            R.id.rbtn_macos -> "macOS"
                            R.id.rbtn_i5 -> "i5"
                            R.id.rbtn_i7 -> "i7"
                            else -> ""
                        }
                    } else if (rgp1Checked == R.id.rbtn_tablet) { //태블릿 PC
                        option1 = 1
                        option2 = when (rgp3Checked) {
                            R.id.rbtn_ios -> "ios"
                            R.id.rbtn_android -> "android"
                            else -> ""
                        }
                    }


                    val rentIntent = Intent(context, CheckrentActivity::class.java)

                    rentIntent.putExtra("device", option1)
                    rentIntent.putExtra("cateName", option2)
                    rentIntent.putExtra("rentDate", rentDate)
                    rentIntent.putExtra("returnDate", returnDate)

                    startActivity(rentIntent)

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

        val binding : FragmentRentBinding = FragmentRentBinding.inflate(inflater,container,false)
        fragmentRentBinding = binding

        return fragmentRentBinding!!.root
    }


    override fun onDestroyView() {
        fragmentRentBinding = null
        super.onDestroyView()
    }

    private fun isDateFormatValid(date: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.isLenient = false

        try {
            dateFormat.parse(date)
            return true
        } catch (e: ParseException) {
            return false
        }
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }
    fun compareDates(rentDate: String, returnDate: String) : Boolean {
        val currentDate = getCurrentDate()

        if (!isDateFormatValid(rentDate) || !isDateFormatValid(returnDate)) {
            showToast("유효하지 않은 날짜 형식입니다.")
            return false
        }

        if (rentDate < currentDate) {
            // 수령 예정일이 현재 날짜보다 이전인 경우
            showToast("수령 예정일을 확인해주세요.")
            return false
        } else if (returnDate < rentDate) {
            // 반납 예정일이 수령 예정일보다 이전인 경우
            showToast("반납 예정일을 확인해주세요.")
            return false

        } else {
            return true
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

