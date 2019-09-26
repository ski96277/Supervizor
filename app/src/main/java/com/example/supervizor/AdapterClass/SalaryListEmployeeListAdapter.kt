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

class SalaryListEmployeeListAdapter(var addemployeePojoclassList: ArrayList<AddEmployee_PojoClass>,
                                    var totalWorkeingDays_company: String?,
                                    var basicSalaryList: ArrayList<Long>,
                                    var attendancecountperemployeeList: ArrayList<Long>,
                                    var additionByTaka: Int,
                                    var additionByHour: Int,
                                    var additionByPersentage: Int,
                                    var subtractionByTaka: Int,
                                    var subtractionByPersentage: Int,
                                    var subtractionByHour: Int
) : RecyclerView.Adapter<SalaryListEmployeeListAdapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.salary_view_list_employee_item_view, parent, false)

        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {

        return addemployeePojoclassList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        return holder.setValue(
                addemployeePojoclassList[position],
                basicSalaryList[position],
                additionByHour,
                additionByPersentage,
                additionByTaka,
                subtractionByHour,
                subtractionByPersentage,
                subtractionByTaka)
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setValue(addemployeePojoclass: AddEmployee_PojoClass, basicSalary: Long, additionByHour: Int, additionByPersentage: Int, additionByTaka: Int, subtractionByHour: Int, subtractionByPersentage: Int, subtractionByTaka: Int) {


            Picasso.get().load(Uri.parse(addemployeePojoclass.employee_profile_image_link))
                    .error(R.drawable.profile).into(itemView.profile_photo_item_imageView_salaryList)

            itemView.employee_name_item_salaryList.text = addemployeePojoclass.employee_name
            itemView.employee_designation_salaryList.text = addemployeePojoclass.employee_designation

            var salaryAddByPersentage = (basicSalary * additionByPersentage) / 100
            var salarySubstractionByPersentage = (basicSalary * subtractionByPersentage) / 100


            itemView.total_salary_salary_list.text =
                    "${(basicSalary + additionByTaka + salaryAddByPersentage) -
                    (subtractionByTaka + salarySubstractionByPersentage)} Tk"

        }


    }
}