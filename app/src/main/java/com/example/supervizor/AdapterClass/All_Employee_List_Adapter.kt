package com.example.supervizor.AdapterClass

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.R
import com.kinda.alert.KAlertDialog
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.item_all_employee.view.*
import java.util.ArrayList
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.supervizor.Fragment.Company.User_Attendance_F
import com.example.supervizor.Fragment.Company.User_Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class All_Employee_List_Adapter(var addEmployee_pojoClasses: ArrayList<AddEmployee_PojoClass>) : RecyclerView.Adapter<All_Employee_List_Adapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_employee, parent, false);
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {

        return addEmployee_pojoClasses.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        return holder.setData(addEmployee_pojoClasses[position])
    }


    class ViewHolderClass(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var i = 0
        fun setData(addEmployee_PojoClass: AddEmployee_PojoClass) {

            var image_link = addEmployee_PojoClass.employee_profile_image_link
            var name = addEmployee_PojoClass.employee_name
            var employee_position = addEmployee_PojoClass.employee_designation

            if (!image_link.equals("null")) {

                Picasso.get().load(Uri.parse(image_link)).into(itemView.profile_photo_item_imageView)
            } else {
                itemView.profile_photo_item_imageView.setImageResource(R.drawable.profile)
            }

            itemView.employee_name_item.text = name
            itemView.employee_designation.text = employee_position

            //profile View By company
            itemView.profile_view_item_layout.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("user_id", addEmployee_PojoClass.employee_User_id)
                load_Profile_Fragment(bundle)
            }
            //Attendance View By company
            itemView.attendance_item_layout.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("user_id", addEmployee_PojoClass.employee_User_id)
                load_attandence_Fragment(bundle)
            }
            //remove user by Company
            itemView.remove_item_layout.setOnClickListener {
                //                Toasty.info(itemView.context,"Remove",Toasty.LENGTH_LONG).show()


                val kAlertDialog = KAlertDialog(itemView.context, KAlertDialog.WARNING_TYPE)
                kAlertDialog.titleText = "Delete ? "
                kAlertDialog.contentText = "Do you want to remove this user?"
                kAlertDialog.cancelText = "Cancel"
                kAlertDialog.confirmText = "Delete"
                kAlertDialog.setCancelClickListener {
                    kAlertDialog.dismissWithAnimation()

                }
                kAlertDialog.setConfirmClickListener {
                    kAlertDialog.dismissWithAnimation()

                    var firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth

                    var firebaseDatabase = FirebaseDatabase.getInstance()
                    var databaseReference = firebaseDatabase.reference
//remove value from employee_list_by_company
                    databaseReference.child("employee_list_by_company")
                            .child(addEmployee_PojoClass.company_User_id)
                            .child(addEmployee_PojoClass.employee_User_id)
                            .removeValue()

//remove value from receptionist_list
                    databaseReference.child("receptionist_list")
                            .child(addEmployee_PojoClass.employee_User_id)
                            .removeValue()
                            .addOnSuccessListener {
                            }

//remove value from receptionist_list_by_company
                    databaseReference.child("receptionist_list_by_company")
                            .child(addEmployee_PojoClass.company_User_id)
                            .child(addEmployee_PojoClass.employee_User_id)
                            .removeValue()
                            .addOnSuccessListener {
                            }
//remove value from employee_list
                    databaseReference.child("employee_list")
                            .child(addEmployee_PojoClass.employee_User_id)
                            .removeValue()
                            .addOnSuccessListener {
                                Toasty.info(itemView.context, "Removed").show()

                            }
                }
                kAlertDialog.show()
            }
        }

        private fun load_attandence_Fragment(bundle: Bundle) {


            var fragment: Fragment?
            fragment = User_Attendance_F()

            if (fragment != null) {
                fragment.arguments = bundle

                val fragmentTransaction = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.company_main_screen, fragment!!)
                fragmentTransaction.commit()
            }
        }

        private fun load_Profile_Fragment(bundle: Bundle) {


            var fragment: Fragment?
            fragment = User_Profile()

            if (fragment != null) {
                fragment.arguments = bundle

                val fragmentTransaction = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.company_main_screen, fragment!!)
                fragmentTransaction.commit()
            }
        }

        init {
            itemview.setOnClickListener {
                i++
                if (i % 2 == 0) {

                    itemview.item_action_layout.visibility = View.GONE
                    YoYo.with(Techniques.FadeOutUp)
                            .duration(500)
                            .repeat(0)
                            .playOn(itemview.item_action_layout)
                } else {

                    itemview.item_action_layout.visibility = View.VISIBLE
                    YoYo.with(Techniques.FadeInDown)
                            .duration(500)
                            .repeat(0)
                            .playOn(itemview.item_action_layout)
                }

            }
        }

    }
}
