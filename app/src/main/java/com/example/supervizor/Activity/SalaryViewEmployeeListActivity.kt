package com.example.supervizor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.AdapterClass.SalaryListEmployeeListAdapter
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_salary_view_employee_list.*
import kotlinx.android.synthetic.main.content_receptionist_main.*

class SalaryViewEmployeeListActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    var addemployeePojoclassList = ArrayList<AddEmployee_PojoClass>()
    lateinit var checkUserInformation: Check_User_information

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_view_employee_list)
        supportActionBar?.title = "Salary List"
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initView()

        loading_spin_kit_salary_list.visibility = View.VISIBLE

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                loading_spin_kit_salary_list.visibility = View.GONE

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.child("employee_list_by_company").child(checkUserInformation.userID)
                        .children.forEach() {
                    var addemployeePojoclass = snapshot.getValue(AddEmployee_PojoClass::class.java)
                    addemployeePojoclassList.add(addemployeePojoclass!!)
                }

                if (addemployeePojoclassList.isEmpty()) {

                    loading_spin_kit_salary_list.visibility = View.GONE
                    noUserFoundtoDisplay_TV.visibility = View.VISIBLE

                } else {

                    loading_spin_kit_salary_list.visibility = View.GONE
                    noUserFoundtoDisplay_TV.visibility = View.GONE
                    salary_view_list_employee.visibility=View.VISIBLE
                    var layoutManager = LinearLayoutManager(applicationContext)
                    layoutManager.orientation = RecyclerView.VERTICAL
                    salary_view_list_employee.layoutManager = layoutManager
                    var salaryListEmployeeListAdapter = SalaryListEmployeeListAdapter(addemployeePojoclassList)

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
