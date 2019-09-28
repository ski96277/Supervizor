package com.example.supervizor.AdapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.SalaryPolicyPojoClass
import com.example.supervizor.Java_Class.CheckInternet
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kinda.alert.KAlertDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.salary_policy_list_item.view.*

class SalaryPolicyListAdapter(var salaryPolicyPojoClassList: MutableList<SalaryPolicyPojoClass>) : RecyclerView.Adapter<SalaryPolicyListAdapter.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.salary_policy_list_item, parent, false)
        return ViewHolderClass(view)

    }

    override fun getItemCount(): Int {

        return salaryPolicyPojoClassList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {


        return holder.setDataToItem(salaryPolicyPojoClassList[position])

    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setDataToItem(salaryPolicyPojoClass: SalaryPolicyPojoClass) {

            itemView.policyTitle_TV_ID.text = salaryPolicyPojoClass.policyTitleSt
            itemView.policyDetails_TV_ID.text = ":   " + salaryPolicyPojoClass.amountSt + " " + salaryPolicyPojoClass.calculateTypeSpinner+" "+salaryPolicyPojoClass.calculateTypeSpinner_plus
            itemView.setOnLongClickListener {
                showDialogToDelete(salaryPolicyPojoClass)

            }
        }

        private fun showDialogToDelete(salaryPolicyPojoClass: SalaryPolicyPojoClass): Boolean {

            if (!CheckInternet.isInternet(itemView.context)) {
                Toasty.error(itemView.context, "No Internet Connection").show()
                return true
            }

            var checkUserInformation = Check_User_information()
            var kAlertDialog = KAlertDialog(itemView.context, KAlertDialog.WARNING_TYPE)
            kAlertDialog.cancelText = "Cancel"
            kAlertDialog.showCancelButton(true)
            kAlertDialog.titleText = "Delete This Item ?"
            kAlertDialog.confirmText = "Delete"
            kAlertDialog.show()
            kAlertDialog.setConfirmClickListener {
                var databaseReference = FirebaseDatabase.getInstance().reference
                databaseReference.child("Salary_Policy")
                        .child(checkUserInformation.userID)
                        .child(salaryPolicyPojoClass.policyTitleSt)
                        .removeValue()
                        .addOnSuccessListener {
                            kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                            kAlertDialog.titleText="Remove Done"
                            kAlertDialog.showCancelButton(false)
                            kAlertDialog.confirmText="OK"
                            kAlertDialog.setConfirmClickListener {
                                kAlertDialog -> kAlertDialog.dismissWithAnimation()

                            }
                        }
            }
            return true
        }


    }

}