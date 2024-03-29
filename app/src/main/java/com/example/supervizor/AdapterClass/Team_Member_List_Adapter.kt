package com.example.supervizor.AdapterClass

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.R
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment
import com.example.supervizor.Activity.EmployeeActivity.Team_Member_List_Activity
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.Java_Class.Check_User_information
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kinda.alert.KAlertDialog
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.item_all_employee.view.*
import kotlinx.android.synthetic.main.item_all_team_member.view.*
import kotlinx.android.synthetic.main.item_team_name_list.view.*
import java.security.AccessController.getContext
import java.util.ArrayList


class Team_Member_List_Adapter(var addEmployee_pojoClasses: ArrayList<AddEmployee_PojoClass>) : RecyclerView.Adapter<Team_Member_List_Adapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_team_member, parent, false);
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {

        return addEmployee_pojoClasses.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        return holder.setData(addEmployee_pojoClasses[position])
    }


    class ViewHolderClass(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun setData(addEmployee_PojoClass: AddEmployee_PojoClass) {

            itemView.employee_name_item_team_member.text = addEmployee_PojoClass.employee_name
            itemView.employee_designation_team_member.text = addEmployee_PojoClass.employee_designation

            if (!addEmployee_PojoClass.employee_profile_image_link.equals("null")) {

                Picasso.get().load(Uri.parse(addEmployee_PojoClass.employee_profile_image_link)).into(itemView.profile_photo_item_imageView_team_member)
            } else {
                itemView.profile_photo_item_imageView_team_member.setImageResource(R.drawable.profile)
            }

            itemView.setOnClickListener {

                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)

                val team_name = sharedPreferences.getString("team_name","defaultname")

                Log.e("Tag", "Team name = $team_name")

                var kAlertDialog = KAlertDialog(itemView.context, KAlertDialog.WARNING_TYPE);
                kAlertDialog.titleText = "Do you want to Remove"
                kAlertDialog.contentText = " This user ?"
                kAlertDialog.cancelText="Cancel"
                kAlertDialog.showCancelButton(true)
                kAlertDialog.show()
                kAlertDialog.setConfirmClickListener {

                    var check_User_information = Check_User_information()
                    var databaseReference = FirebaseDatabase.getInstance().reference

                    databaseReference.child("my_team_request")
                            .child(check_User_information.userID)
                            .child(team_name).child(addEmployee_PojoClass.employee_User_id)
                            .removeValue()
                            .addOnSuccessListener {
                                kAlertDialog.dismissWithAnimation()
                                Toasty.info(itemView.context, "Removed").show()

                             /*   itemView.context.startActivity(
                                        Intent(itemView.context,Team_Member_List_Activity::class.java)
                                        .putExtra("team_name",team_name)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                           */ }

                }
            }

        }


     /*   private fun load_Team_Member_List_Fragment(bundle: Bundle) {


            var fragment: Fragment?
            fragment = Team_Member_List_F()

            if (fragment != null) {

                fragment.arguments = bundle
                val fragmentTransaction = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment!!)
                fragmentTransaction.commit()
            }
        }*/


    }

}
