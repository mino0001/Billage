package com.example.billage



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.billage.databinding.FragmentRentBinding


class RentFragment : Fragment() {


    //뷰가 사라질 때 즉 메모리에서 날라갈 때 같이 날리기 위해 따로 빼두기
    private var fragmentRentBinding : FragmentRentBinding? =null
    private var intent :Intent? = null
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
        val etDate = fragmentRentBinding!!.etRentDate
        val btnRent = fragmentRentBinding!!.btnRentAvailable

        val constraintLayout: ConstraintLayout = fragmentRentBinding!!.layout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        //fragmentRentBinding!!.toolbar.inflateMenu(R.menu.toolbar_menu)

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

            // 체크된 값, 대여일 기준으로 대여 가능한 기기 조회

            val rgp1Checked = radioGp1.checkedRadioButtonId
            val rgp2Checked = radioGp2.checkedRadioButtonId
            val rgp3Checked = radioGp3.checkedRadioButtonId

            var option1=rgp1Checked
            var option2=0

            if (rgp1Checked==R.id.rbtn_laptop){// 노트북
                option1=0
                if (rgp2Checked==R.id.rbtn_macos){
                    option2=0
                }else if (rgp2Checked==R.id.rbtn_windows){
                    option2=1
                }else if (rgp2Checked==R.id.rbtn_etc){
                    option2=2
                }
            }
            else if (rgp1Checked==R.id.rbtn_tablet){ //태블릿 PC
                option1=1
                if (rgp2Checked==R.id.rbtn_ios){
                    option2=0
                }else if (rgp2Checked==R.id.rbtn_android){
                    option2=1
                }
            }


            val intent = Intent(context, CheckrentActivity::class.java)
            // 정보 넘겨주기
            intent.putExtra("device", option1)
            intent.putExtra("os", option2)
            intent.putExtra("date", etDate.text)


            startActivity(intent)


        }




    }

    // 프레그먼트를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // 뷰가 생성되었을 때
    // 프레그먼트와 레이아웃을 연결시켜주는 부분이다.
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
}

