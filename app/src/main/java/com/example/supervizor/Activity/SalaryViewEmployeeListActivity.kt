package com.example.supervizor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supervizor.AdapterClass.SalaryListEmployeeListAdapter
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.JavaPojoClass.SalaryPolicyPojoClass
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_salary_view_employee_list.*
import java.util.*
import kotlin.collections.ArrayList

class SalaryViewEmployeeListActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    var addemployeePojoclassList = ArrayList<AddEmployee_PojoClass>()
    lateinit var checkUserInformation: Check_User_information
    var salaryPolicyPojoClas_list = ArrayList<SalaryPolicyPojoClass>()
    var attendanceCountPerEmployee_List = ArrayList<Long>()
    var totalSalaryList = ArrayList<Long>()
    lateinit var total_workeing_Days_Compnay: String

    var additionByPersentage = 0
    var additionByHour = 0
    var additionByTaka = 0

    var subtractionByPersentage = 0
    var subtractionByHour = 0
    var subtractionByTaka = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_view_employee_list)
        supportActionBar?.title = "Salary List"
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initView()

        //get year month
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
//        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        salary_view_list_employee.setLayoutManager(linearLayoutManager)


        loading_spin_kit_salary_list.visibility = View.VISIBLE

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                loading_spin_kit_salary_list.visibility = View.GONE

            }

            override fun onDataChange(p0: DataSnapshot) {


                //get salary policy for this company
                for (snapshot in p0.child("Salary_Policy")
                        .child(checkUserInformation.userID).children) {
                    val salaryPolicyPojoClass = snapshot.getValue(SalaryPolicyPojoClass::class.java)
                    salaryPolicyPojoClas_list.add(salaryPolicyPojoClass!!)
                }

                for (salaryPolicy in salaryPolicyPojoClas_list) {
                    if (salaryPolicy.calculateTypeSpinner_plus == "Addition"
                            && salaryPolicy.calculateTypeSpinner == "TK") {

                        additionByTaka = salaryPolicy.amountSt.toInt().plus(additionByTaka)

                        Log.e("TAG - - : ", "Addition TK $additionByTaka")

                    }
                    if (salaryPolicy.calculateTypeSpinner_plus == "Addition"
                            && salaryPolicy.calculateTypeSpinner == "Percentage") {

                        additionByPersentage = salaryPolicy.amountSt.toInt().plus(additionByPersentage)
                        Log.e("TAG - - : ", "Addition Percentage " + salaryPolicy.amountSt);

                    }
                    if (salaryPolicy.calculateTypeSpinner_plus == "Addition"
                            && salaryPolicy.calculateTypeSpinner == "By hour") {

                        additionByHour = salaryPolicy.amountSt.toInt().plus(additionByHour)
                        Log.e("TAG - - : ", "Addition By hour $additionByHour")


                    }
                    if (salaryPolicy.calculateTypeSpinner_plus == "Subtraction"
                            && salaryPolicy.calculateTypeSpinner == "TK") {

                        subtractionByTaka = salaryPolicy.amountSt.toInt().plus(subtractionByTaka)
                        Log.e("TAG - - : ", "Subtraction TK " + subtractionByTaka);
                    }

                    if (salaryPolicy.calculateTypeSpinner_plus == "Subtraction"
                            && salaryPolicy.calculateTypeSpinner == "Percentage") {

                        subtractionByPersentage = salaryPolicy.amountSt.toInt().plus(subtractionByPersentage)
                        Log.e("TAG - - : ", "Subtraction Percentage " + subtractionByPersentage);


                    }

                    if (salaryPolicy.calculateTypeSpinner_plus == "Subtraction"
                            && salaryPolicy.calculateTypeSpinner == "By hour") {

                        subtractionByHour = salaryPolicy.amountSt.toInt().plus(subtractionByHour)
                        Log.e("TAG - - : ", "Subtraction By hour " + subtractionByHour);

                    }


                }


//get Employee List
                for (snapshot in p0.child("employee_list_by_company").child(checkUserInformation.userID)
                        .children) {

                    val addEmployeePojoClass = snapshot.getValue(AddEmployee_PojoClass::class.java)

                    //get attendance count for employee
                    var attendance_count = p0.child("Attendance").child(checkUserInformation.userID)
                            .child(addEmployeePojoClass!!.employee_User_id)
                            .child(year.toString()).child(month.toString()).childrenCount

                    //get Working Days monthly
                    total_workeing_Days_Compnay = p0.child("company_list").child(checkUserInformation.userID).child("company_working_day")
                            .getValue(String::class.java).toString()


                    var totalSalary: Int


//if attendance is zero
                    if (attendance_count.toInt() == 0) {

                        totalSalary = 0

                    } else {
                        var perDay_salary = (addEmployeePojoClass.employee_salary.toInt()) / (total_workeing_Days_Compnay!!.toInt())

                        Log.e("TAG - - : ", "Per Day: $perDay_salary");
                        Log.e("TAG - - : ", "Per Day: $attendance_count");

                        totalSalary = perDay_salary * attendance_count.toInt()
                    }

                    totalSalaryList.add(totalSalary.toLong())
                    attendanceCountPerEmployee_List.add(attendance_count)

                    addemployeePojoclassList.add(addEmployeePojoClass!!)


                }


                //if employee list is empty
                if (addemployeePojoclassList.isEmpty()) {

                    loading_spin_kit_salary_list.visibility = View.GONE
                    noUserFoundtoDisplay_TV.visibility = View.VISIBLE

                } else {

                    loading_spin_kit_salary_list.visibility = View.GONE
                    noUserFoundtoDisplay_TV.visibility = View.GONE
                    salary_view_list_employee.visibility = View.VISIBLE

                    Log.e("TAG - - : ", ": $total_workeing_Days_Compnay");
                    Log.e("TAG - - : ", ": $totalSalaryList")

                    var salaryListEmployeeListAdapter = SalaryListEmployeeListAdapter(
                            addemployeePojoclassList,
                            total_workeing_Days_Compnay
                            , totalSalaryList,
                            attendanceCountPerEmployee_List,
                            additionByTaka,
                            additionByHour,
                            additionByPersentage,
                            subtractionByTaka,
                            subtractionByPersentage,
                            subtractionByHour)

                    salary_view_list_employee.adapter = salaryListEmployeeListAdapter

                }


            }

        })


    }

    private fun initView() {

        databaseReference = FirebaseDatabase.getInstance().reference

        checkUserInformation = Check_User_information()


    }
}
