package com.example.supervizor.AdapterClass

import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
                totalWorkeingDays_company,
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
                     totalWorkeingDays_company: String?,
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

                    if (additionByPersentage != null && additionByPersentage!=0) {
                        salaryAddByPersentage = (basicSalary * additionByPersentage) / 100

                    } else {
                        salaryAddByPersentage = 0
                    }
                    var salarySubstractionByPersentage: Long

                    if (subtractionByPersentage != null && subtractionByPersentage!=0) {
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

                    if (attendancecountperemployee.toInt() == 0) {
                        itemView.total_salary_salary_list.text = "0 TK"
                    } else {

                        itemView.total_salary_salary_list.text = "${(basicSalary + additionByTaka + salaryAddByPersentage) -
                                (total_subtractionByTaka + salarySubstractionByPersentage)} Tk"

                    }

                    itemView.setOnClickListener {

                        // custom dialog
                        var dialog = Dialog(itemView.context)
                        dialog.setContentView(R.layout.salary_list_details_item)

                        dialog.setTitle("Salary Details")
                        var attendanceCount = dialog.findViewById<TextView>(R.id.attendance_count_TV_ID)
                        var totalWorkeingDays_companyCount = dialog.findViewById<TextView>(R.id.company_office_Days_TV_ID)
                        var basicSalaryCount = dialog.findViewById<TextView>(R.id.basic_Salary_count_TV_ID)
                        var totalSalaryOfThisMonth = dialog.findViewById<TextView>(R.id.total_Salary_of_this_month_count_TV_ID)
                        var fineSalary = dialog.findViewById<TextView>(R.id.Salary_Fine_of_this_month_count_TV_ID)
                        var salaryOfThisMonth = dialog.findViewById<TextView>(R.id.salary_of_this_month_count_TV_ID)
                        var bonusSalary = dialog.findViewById<TextView>(R.id.Bonus_Salary_of_this_month_count_TV_ID);
                        attendanceCount.append("  $attendancecountperemployee Days")
                        totalWorkeingDays_companyCount.append("  $totalWorkeingDays_company Days")
                        basicSalaryCount.append("  ${addemployeePojoclass.employee_salary} Tk")
                        totalSalaryOfThisMonth.append("  $basicSalary Tk")
                        bonusSalary.append("  +${additionByTaka + salaryAddByPersentage} Tk")
                        fineSalary.append("  -${total_subtractionByTaka + salarySubstractionByPersentage} Tk")
                        salaryOfThisMonth.append("  ${itemView.total_salary_salary_list.text}")
                        dialog.show()

                    }
                }
            })
        }
    }
}