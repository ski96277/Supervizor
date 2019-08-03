package com.example.supervizor.AdapterClass

import android.app.Dialog
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass
import com.example.supervizor.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_alert_dialog_leave_application_show_company.*
import kotlinx.android.synthetic.main.item_all_leave_applycation_company.view.*
import kotlinx.android.synthetic.main.leave_application_employee_f.*

class Leave_Application_Pending_Adapter(val leaveApplication_pojoClasses: MutableList<LeaveApplication_PojoClass>) : RecyclerView.Adapter<Leave_Application_Pending_Adapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_leave_applycation_company, parent, false);
        return viewHolder(view)
    }

    override fun getItemCount(): Int {

        return leaveApplication_pojoClasses.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        return holder.setview(leaveApplication_pojoClasses[position])
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setview(leaveApplication_PojoClas: LeaveApplication_PojoClass) {

            Log.e("Tag - ", "image = " + leaveApplication_PojoClas.profile_image_link)
            Log.e("Tag - ", "Title = " + leaveApplication_PojoClas.leave_Title)
            Log.e("Tag - ", "dis = " + leaveApplication_PojoClas.leave_description)
            if (!leaveApplication_PojoClas.profile_image_link.equals("null")) {

                Picasso.get().load(Uri.parse(leaveApplication_PojoClas.profile_image_link))
                        .into(itemView.leave_item_profile_photo_imageView_leave)

            } else {
                itemView.leave_item_profile_photo_imageView_leave.setImageResource(R.drawable.profile)
            }
            itemView.leave_title_item_application.text = leaveApplication_PojoClas.leave_Title
            itemView.leave_details_item_application.text = leaveApplication_PojoClas.leave_description

            itemView.setOnClickListener {
                showDialog_details(leaveApplication_PojoClas)
            }
        }

        private fun showDialog_details(leaveApplication_PojoClas: LeaveApplication_PojoClass) {


            var dialog = Dialog(itemView.context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_dialog_leave_application_show_company)
            dialog.leave_applicant_name_date_company.text = leaveApplication_PojoClas.leave_employee_name
            dialog.leave_title_TV_ID_company.text = leaveApplication_PojoClas.leave_Title
            dialog.leave_duration_TV_ID_company.text = "${leaveApplication_PojoClas.leave_start_date} to ${leaveApplication_PojoClas.leave_End_Date}"
            dialog.leave_details_details_TV_ID_company.text = leaveApplication_PojoClas.leave_description

            if (!leaveApplication_PojoClas.profile_image_link.equals("null")) {

                Picasso.get().load(Uri.parse(leaveApplication_PojoClas.profile_image_link))
                        .into(dialog.leave_applicant_profile_image)

            } else {
                dialog.leave_applicant_profile_image.setImageResource(R.drawable.profile)
            }

            dialog.cancel_btn_alert_show_ID_company.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }


    }


}