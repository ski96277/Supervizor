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
    var attendanceCountPerEmployee = ArrayList<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_view_employee_list)
        supportActionBar?.title = "Salary List"
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initView()

        //get year month
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        salary_view_list_employee.setLayoutManager(linearLayoutManager)


        loading_spin_kit_salary_list.visibility = View.VISIBLE

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                loading_spin_kit_salary_list.visibility = View.GONE

            }

            override fun onDataChange(p0: DataSnapshot) {

//get Employee List
                for (snapshot in p0.child("employee_list_by_company").child(checkUserInformation.userID)
                        .children) {
                    val addEmployeePojoClass = snapshot.getValue(AddEmployee_PojoClass::class.java)

                    //get attendance count
                   var attendance_count =  snapshot.child("Attendance").child(checkUserInformation.userID)
                            .child(addEmployeePojoClass!!.employee_User_id)
                            .child(year.toString()).child(month.toString()).childrenCount

                    Log.e("TAG - - : ", ": ${addEmployeePojoClass!!.employee_User_id}"  )
                    Log.e("TAG - - : ", ": $attendance_count"  )
                    attendanceCountPerEmployee.add(attendance_count)

                    addemployeePojoclassList.add(addEmployeePojoClass!!)


                }

      //get Working Days monthly
               var total_workeing_Days =  p0.child("company_list").child(checkUserInformation.userID).child("company_working_day")
                        .getValue(String::class.java)


                //get salary policy for this company
                for (snapshot in p0.child("Salary_Policy")
                        .child(checkUserInformation.userID).children) {
                    val salaryPolicyPojoClass = snapshot.getValue(SalaryPolicyPojoClass::class.java)
                    salaryPolicyPojoClas_list.add(salaryPolicyPojoClass!!)
                }
                //if employee list is empty
                if (addemployeePojoclassList.isEmpty()) {

                    loading_spin_kit_salary_list.visibility = View.GONE
                    noUserFoundtoDisplay_TV.visibility = View.VISIBLE

                } else {

                    loading_spin_kit_salary_list.visibility = View.GONE
                    noUserFoundtoDisplay_TV.visibility = View.GONE
                    salary_view_list_employee.visibility = View.VISIBLE

                    var salaryListEmployeeListAdapter = SalaryListEmployeeListAdapter(addemployeePojoclassList,total_workeing_Days)

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
