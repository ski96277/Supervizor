package com.example.supervizor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.supervizor.JavaPojoClass.SalaryPolicyPojoClass
import com.example.supervizor.Java_Class.CheckInternet
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.*
import com.kinda.alert.KAlertDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_policy_form.*

class PolicyFormActivity : AppCompatActivity(), View.OnClickListener {

    var salaryStatus = "Permanent"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_form)
        //hide notificationbar
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        supportActionBar?.title = "Add salary Policy"

        if (!CheckInternet.isInternet(this@PolicyFormActivity)) {
            Toasty.info(this@PolicyFormActivity, "Check Internet Connection").show()
            return
        }
        submit_salary_information_btn.setOnClickListener(this)
        permanent_condiation_btn.setOnClickListener(this)
        monthly_conditaion_btn.setOnClickListener(this)
        loading_spin_kit_ID.visibility = View.GONE

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.submit_salary_information_btn -> {
                submitDataMethod()

            }
            R.id.permanent_condiation_btn -> {
                salaryStatus = "Permanent"

                permanent_condiation_btn.background = resources.getDrawable(R.drawable.button_background_employee_type_dark)

                monthly_conditaion_btn.background = resources.getDrawable(R.drawable.button_background_employee_type)

            }
            R.id.monthly_conditaion_btn -> {
                salaryStatus = "Monthly"

                permanent_condiation_btn.background = resources.getDrawable(R.drawable.button_background_employee_type)

                monthly_conditaion_btn.background = resources.getDrawable(R.drawable.button_background_employee_type_dark)

            }

        }
    }

    private fun submitDataMethod() {

        if (!CheckInternet.isInternet(this@PolicyFormActivity)) {
            Toasty.info(this@PolicyFormActivity, "Check Internet Connection").show()
            return
        }

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
        loading_spin_kit_ID.visibility = View.VISIBLE

        var checkUserInformation = Check_User_information()
        var user_ID = checkUserInformation.userID

        var salaryPolicyPojoClass = SalaryPolicyPojoClass(policyTitleSt, amountSt, calculateTypeSpinner_plus.toString(), calculateTypeSpinner.toString(), salaryStatus)
        var databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

                loading_spin_kit_ID.visibility = View.GONE

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.child("Salary_Policy").child(user_ID).hasChild(policyTitleSt)) {
                    loading_spin_kit_ID.visibility = View.GONE
                    var kAlertDialog = KAlertDialog(this@PolicyFormActivity, KAlertDialog.WARNING_TYPE)
                    kAlertDialog.show()
                    kAlertDialog.titleText = "You Can't Set Same Name"
                    kAlertDialog.contentText = "Change Your Policy Name"
                    kAlertDialog.confirmText = "OK"


                    kAlertDialog.setConfirmClickListener {
                        kAlertDialog.dismissWithAnimation()
                    }

                } else {
                    databaseReference.child("Salary_Policy").child(user_ID)
                            .child(policyTitleSt).setValue(salaryPolicyPojoClass)
                            .addOnSuccessListener {

                                loading_spin_kit_ID.visibility = View.GONE

                                var kAlertDialog = KAlertDialog(this@PolicyFormActivity, KAlertDialog.WARNING_TYPE)
                                kAlertDialog.show()
                                kAlertDialog.titleText = "Submit Done"

                                kAlertDialog.setConfirmClickListener {
                                    policy_name_ET_ID.setText("")
                                    amountET_ID.setText("")
                                    kAlertDialog.dismissWithAnimation()

                                }
                            }


                }

            }

        })

    }
}

