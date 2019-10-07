package com.example.supervizor.AdapterClass

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.R
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.Java_Class.CheckInternet
import com.google.firebase.database.FirebaseDatabase
import com.kinda.alert.KAlertDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_all_team_leader.view.*


class Team_Leader_List_Adapter(var addEmployee_pojoClasses_list: MutableList<AddEmployee_PojoClass>) : RecyclerView.Adapter<Team_Leader_List_Adapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_team_leader, parent, false);
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {

        return addEmployee_pojoClasses_list.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        return holder.setData(addEmployee_pojoClasses_list[position])
    }


    class ViewHolderClass(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun setData(addEmployee_PojoClass: AddEmployee_PojoClass) {

            itemView.employee_name_item_team_leader.text = addEmployee_PojoClass.employee_name
            itemView.employee_designation_team_leader.text = addEmployee_PojoClass.employee_designation

            if (!addEmployee_PojoClass.employee_profile_image_link.equals("null")) {

                Picasso.get().load(Uri.parse(addEmployee_PojoClass.employee_profile_image_link)).into(itemView.profile_photo_item_imageView_team_leader)
            } else {
                itemView.profile_photo_item_imageView_team_leader.setImageResource(R.drawable.profile)
            }

            itemView.setOnLongClickListener {
                if (CheckInternet.isInternet(itemView.context)) {

                    var kAlertDialog = KAlertDialog(itemView.context, KAlertDialog.WARNING_TYPE)
                    kAlertDialog.titleText = "Do you want to"
                    kAlertDialog.contentText = "Remove this User ?"
                    kAlertDialog.cancelText = "Cancel"
                    kAlertDialog.showCancelButton(true)
                    kAlertDialog.show()
                    kAlertDialog.setConfirmClickListener {

                        var databaseReference = FirebaseDatabase.getInstance().reference
                        databaseReference.child("my_team_request_pending")
                                .child(addEmployee_PojoClass.company_User_id)
                                .child(addEmployee_PojoClass.employee_User_id)
                                .removeValue()
                        databaseReference.child("my_team_request")
                                .child(addEmployee_PojoClass.employee_User_id)
                                .removeValue()

                        databaseReference.child("team_leader_ID_List")
                                .child(addEmployee_PojoClass.employee_User_id)
                                .removeValue()
                                .addOnSuccessListener {

                                    kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                                    kAlertDialog.showCancelButton(false)
                                    kAlertDialog.setConfirmClickListener { kAlertDialog ->
                                        kAlertDialog.dismissWithAnimation()
                                    }
                                }

                    }
                }//check internet connection END


                return@setOnLongClickListener true
            }


        }


    }

}
