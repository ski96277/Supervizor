package com.example.supervizor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_policy_form.*

class PolicyFormActivity : AppCompatActivity(), View.OnClickListener {

    var permanent = "Permanent"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_form)
        //hide notificationbar
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.title = "Add salary Policy"
        submit_salary_information_btn.setOnClickListener(this)
        permanent_condiation_btn.setOnClickListener(this)
        monthly_conditaion_btn.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.submit_salary_information_btn -> {
                submitDataMethod()

            }
            R.id.permanent_condiation_btn -> {
                permanent = "Permanent"

                permanent_condiation_btn.background = resources.getDrawable(R.drawable.background_border)

                monthly_conditaion_btn.background = resources.getDrawable(R.drawable.button_background_employee_type)

            }
            R.id.monthly_conditaion_btn -> {
                permanent = "Monthly"

                permanent_condiation_btn.setBackground(resources.getDrawable(R.drawable.button_background_employee_type))

                monthly_conditaion_btn.setBackground(resources.getDrawable(R.drawable.background_border))

            }

        }
    }

    private fun submitDataMethod() {
        var policyTitleSt = policy_name_ET_ID.text.toString()
        var amountSt = amountET_ID.text.toString()
        var calculateTypeSpinner_plus = calculateTypeSpinner_plus_minus.selectedItem
        var calculateTypeSpinner = calculateTypeSpinner.selectedItem

        if (policyTitleSt.isEmpty()) {
            policy_name_ET_ID.error = "Policy Title?"
            policy_name_ET_ID.requestFocus()
            return
        }
        if (amountSt.isEmpty()) {
            amountET_ID.error = "Amount ?"
            amountET_ID.requestFocus()
            return
        }

        var checkUserInformation = Check_User_information()
        var user_ID = checkUserInformation.userID

      var databaseReference =FirebaseDatabase.getInstance().reference
        databaseReference.child("Salary_Policy").child(user_ID).child("test").setValue("test")




    }
}
