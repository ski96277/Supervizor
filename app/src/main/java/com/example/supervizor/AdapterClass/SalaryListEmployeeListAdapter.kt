package com.example.supervizor.AdapterClass

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.salary_view_list_employee_item_view.view.*

class SalaryListEmployeeListAdapter(var addemployeePojoclassList: ArrayList<AddEmployee_PojoClass>) : RecyclerView.Adapter<SalaryListEmployeeListAdapter.ViewHolderClass>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.salary_view_list_employee_item_view, parent, false)

        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {

        return addemployeePojoclassList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        return holder.setValue(addemployeePojoclassList[position])
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setValue(addemployeePojoclass: AddEmployee_PojoClass) {

//            Picasso.get().load(Uri.parse(addemployeePojoclass.employee_profile_image_link))
//                    .error(R.drawable.profile).into(itemView.profile_photo_item_imageView_salaryList)




        }



    }
}