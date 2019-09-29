package com.example.supervizor.AdapterClass

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.salary_view_list_employee_item_view.view.*
import kotlin.collections.ArrayList

class SalaryListEmployeeListAdapter(var addemployeePojoclassList: ArrayList<AddEmployee_PojoClass>,
                                    var totalWorkeingDays_company: String?,
                                    var basicSalaryList: ArrayList<Long>,
                                    var attendancecountperemployeeList: ArrayList<Long>,
                                    var additionByTaka: Int,
                                    var additionByHour: Int,
                                    var additionByPersentage: Int,
                                    var subtractionByTaka: Int,
                                    var subtractionByPersentage: Int,
                                    var subtractionByHour: Int) : RecyclerView.Adapter<SalaryListEmployeeListAdapter.ViewHolderClass>() {


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
                subtractionByTaka,
                attendancecountperemployeeList[position])
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setValue(addemployeePojoclass: AddEmployee_PojoClass,
                     basicSalary: Long,
                     additionByHour: Int,
                     additionByPersentage: Int,
                     additionByTaka: Int,
                     subtractionByHour: Int,
                     subtractionByPersentage: Int,
                     subtractionByTaka: Int,
                     attendancecountperemployee: Long
        ) {


            Picasso.get().load(Uri.parse(addemployeePojoclass.employee_profile_image_link))
                    .error(R.drawable.profile).into(itemView.profile_photo_item_imageView_salaryList)

            itemView.employee_name_item_salaryList.text = addemployeePojoclass.employee_name
            itemView.employee_designation_salaryList.text = addemployeePojoclass.employee_designation

            //get the late count from employee
            var databaseReference = FirebaseDatabase.getInstance().reference

            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                @SuppressLint("SetTextI18n")
                override fun onDataChange(p0: DataSnapshot) {


                    var latecount = p0.child("late_count")
                            .child(addemployeePojoclass.employee_User_id)
                            .child("TotalDays")
                            .getValue(String::class.java)

                    var salaryAddByPersentage: Long

                    if (additionByPersentage != null) {
                        salaryAddByPersentage = (basicSalary * additionByPersentage) / 100

                    } else {
                        salaryAddByPersentage = 0
                    }
                    var salarySubstractionByPersentage: Long

                    if (subtractionByPersentage != null) {
                        salarySubstractionByPersentage = (basicSalary * subtractionByPersentage) / 100

                    } else {
                        salarySubstractionByPersentage = 0
                    }
                    var total_subtractionByTaka: Int
                    if (latecount != null) {
                        total_subtractionByTaka = subtractionByTaka * latecount!!.toInt()
                    } else {
                        total_subtractionByTaka = 0
                    }

                    Log.e("TAG - - : ", "attendancecountperemployee : $attendancecountperemployee");
                    if (attendancecountperemployee.toInt() == 0) {
                        itemView.total_salary_salary_list.text = "0 TK"
                    } else {

                        itemView.total_salary_salary_list.text = "${(basicSalary + additionByTaka + salaryAddByPersentage) -
                                (total_subtractionByTaka + salarySubstractionByPersentage)} Tk"

                    }

                    itemView.setOnClickListener {
                        Toast.makeText(itemView.context, "$adapterPosition", Toast.LENGTH_SHORT).show();
                    }
                }

            })

        }


    }
}