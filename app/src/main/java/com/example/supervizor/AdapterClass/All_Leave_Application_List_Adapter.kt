package com.example.supervizor.AdapterClass

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.R
import java.util.ArrayList
import androidx.fragment.app.FragmentActivity
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.custom_alert_dialog_event_show.*
import kotlinx.android.synthetic.main.custom_alert_dialog_event_show.cancel_btn_alert_show_ID
import kotlinx.android.synthetic.main.custom_alert_dialog_my_leave_application_show_employee.*
import kotlinx.android.synthetic.main.item_all_leave_application.view.*
import kotlin.math.log


class All_Leave_Application_List_Adapter(var leaveApplication_pojoClasses_list: List<LeaveApplication_PojoClass>, image_link: String?) : RecyclerView.Adapter<All_Leave_Application_List_Adapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_leave_application, parent, false)
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {

        return leaveApplication_pojoClasses_list?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        return holder.setData(leaveApplication_pojoClasses_list!!.get(position))
    }


    class ViewHolderClass(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun setData(leaveApplication_PojoClass: LeaveApplication_PojoClass) {
//            Toasty.info(itemView.context, leaveApplication_PojoClass.leave_End_Date).show()
            itemView.leave_title_TV.text = leaveApplication_PojoClass.leave_Title
            itemView.date_TV.text = leaveApplication_PojoClass.leave_description

            itemView.setOnClickListener {
                showDialog_details(itemView,leaveApplication_PojoClass)
            }

        }

        private fun showDialog_details(itemView: View, leaveApplication_PojoClass: LeaveApplication_PojoClass) {


            var dialog = Dialog(itemView.context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_dialog_my_leave_application_show_employee)

            dialog.leave_apply_date_TV_ID.text=leaveApplication_PojoClass.leave_applying_Date
           dialog.leave_title_TV_ID.text=leaveApplication_PojoClass.leave_Title
            dialog.leave_duration_TV_ID.text="${leaveApplication_PojoClass.leave_start_date} to ${leaveApplication_PojoClass.leave_End_Date}"
            dialog.leave_details_details_TV_ID.text=leaveApplication_PojoClass.leave_description

            dialog.cancel_btn_alert_show_ID.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }


    }
}
