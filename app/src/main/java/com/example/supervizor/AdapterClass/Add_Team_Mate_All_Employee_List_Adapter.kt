package com.example.supervizor.AdapterClass

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.R
import com.squareup.picasso.Picasso
import java.util.ArrayList
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment
import com.example.supervizor.Fragment.Employee.Employee_profile_View_By_Leader_F
import kotlinx.android.synthetic.main.item_add_team_mate_all_employee.view.*


class Add_Team_Mate_All_Employee_List_Adapter(var addEmployee_pojoClasses: ArrayList<AddEmployee_PojoClass>)  : RecyclerView.Adapter<Add_Team_Mate_All_Employee_List_Adapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_team_mate_all_employee, parent, false);
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

            var image_link = addEmployee_PojoClass.employee_profile_image_link
            var name = addEmployee_PojoClass.employee_name
            var employee_position = addEmployee_PojoClass.employee_designation

            if (!image_link.equals("null")) {

                Picasso.get().load(Uri.parse(image_link)).into(itemView.profile_photo_item_imageView_add_team_mate)
            } else {
                itemView.profile_photo_item_imageView_add_team_mate.setImageResource(R.drawable.profile)
            }

            itemView.employee_name_item_add_team_mate.text = name
            itemView.employee_designation_add_team_mate.text = employee_position

            itemView.setOnClickListener {

                var bundle=Bundle()
                bundle.putString("user_id", addEmployee_PojoClass.employee_User_id)
                load_Profile_Fragment(bundle)

            }

        }


        private fun load_Profile_Fragment(bundle: Bundle) {


            var fragment: Fragment?
            fragment = Employee_profile_View_By_Leader_F()

            if (fragment != null) {
                fragment.arguments = bundle

                val fragmentTransaction = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment!!)
                fragmentTransaction.commit()
            }
        }


    }

}
