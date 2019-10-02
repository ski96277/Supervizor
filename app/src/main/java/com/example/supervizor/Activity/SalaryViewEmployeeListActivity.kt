package com.example.supervizor.Activity

import android.app.ProgressDialog
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supervizor.AdapterClass.SalaryListEmployeeListAdapter
import com.example.supervizor.DataBase.DataBaseHelper
import com.example.supervizor.DataBase.DataInsert
import com.example.supervizor.DataBase.SalaryDataBasePojoClass
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass
import com.example.supervizor.JavaPojoClass.SalaryPolicyPojoClass
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.*
import com.opencsv.CSVWriter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_salary_view_employee_list.*
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
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

    private lateinit var sqliteDatabse: SQLiteDatabase
    private lateinit var dataInsert: DataInsert
    private lateinit var dataBaseHelper: DataBaseHelper
    private lateinit var salaryDataBasePojoClass: SalaryDataBasePojoClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_view_employee_list)
        supportActionBar?.title = "Salary List"
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initView()


        /*floatbtnPrintXlsSheet.setOnClickListener {

            Toast.makeText(applicationContext, "Toast", Toast.LENGTH_SHORT).show();

        }*/
        //get year month
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1

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

                    //get Working Days monthly
                    total_workeing_Days_Compnay = p0.child("company_list").child(checkUserInformation.userID).child("company_working_day")
                            .getValue(String::class.java).toString()

                    //get attendance count for employee
                    var attendance_count = p0.child("Attendance")
                            .child(checkUserInformation.userID)
                            .child(addEmployeePojoClass!!.employee_User_id)
                            .child(year.toString())
                            .child(month.toString())
                            .childrenCount

                    //get the leave approve day count Start
                    var format = SimpleDateFormat("dd/MM/yyyy")
                    var approveLeaveCount = 0
                    if (attendance_count.toInt() < total_workeing_Days_Compnay.toInt()) {
                        for (snapshot in p0.child("leave_application")
                                .child(addEmployeePojoClass.company_User_id)
                                .child(addEmployeePojoClass.employee_User_id)
                                .children) {

                            var leaveapplicationPojoclass = snapshot.getValue(LeaveApplication_PojoClass::class.java)
                            if (leaveapplicationPojoclass!!.isLeave_seen &&
                                    leaveapplicationPojoclass.month.toInt() == month &&
                                    leaveapplicationPojoclass.year.toInt() == year) {

                                var date1 = format.parse(leaveapplicationPojoclass.leave_start_date)
                                var date2 = format.parse(leaveapplicationPojoclass.leave_End_Date)
                                val diff = date2.getTime() - date1.getTime()
                                val diffDays = diff / (24 * 60 * 60 * 1000)
                                Log.e("Tag", "Days $diffDays")
                                approveLeaveCount += diffDays.toInt()

                            }
                        }

                    }

                    attendance_count += approveLeaveCount.toLong()
                    //get the leave approved day END

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
//insert data to Database POJO class Start
                    var i = 0
                    for (addemployeePojoClass in addemployeePojoclassList) {

                        if (addemployeePojoClass.user_phone_number==null) {
                            addemployeePojoClass.user_phone_number = "No Number"
                        }


                        if (attendanceCountPerEmployee_List[i].toInt() == 0) {

                            salaryDataBasePojoClass = SalaryDataBasePojoClass(
                                    addemployeePojoClass.employee_name,
                                    addemployeePojoClass.employee_email,
                                    addemployeePojoClass.user_phone_number,
                                    total_workeing_Days_Compnay,
                                    attendanceCountPerEmployee_List[i].toString(),
                                    addemployeePojoClass.employee_salary,
                                    totalSalaryList[i].toString(),
                                    (additionByTaka + additionByPersentage).toString(),
                                    (subtractionByTaka + subtractionByPersentage).toString(),
                                    "0"

                            )
                        } else {
                            salaryDataBasePojoClass = SalaryDataBasePojoClass(
                                    addemployeePojoClass.employee_name,
                                    addemployeePojoClass.employee_email,
                                    addemployeePojoClass.user_phone_number,
                                    total_workeing_Days_Compnay,
                                    attendanceCountPerEmployee_List[i].toString(),
                                    addemployeePojoClass.employee_salary,
                                    totalSalaryList[i].toString(),
                                    (additionByTaka + additionByPersentage).toString(),
                                    (subtractionByTaka + subtractionByPersentage).toString(),
                                    ((totalSalaryList[i].toInt() + additionByTaka.toInt() + additionByPersentage.toInt()) -
                                            (subtractionByTaka + subtractionByPersentage)).toString()

                            )
                        }

                        i++
                        //Insert Data Start

                        var status = dataInsert.insertData(salaryDataBasePojoClass)

                        if (status) {
                            Log.e("TAG", "Sqlite Data Saved")
//                            Toast.makeText(applicationContext, "data insert", Toast.LENGTH_SHORT).show();
                        } else {

                            Log.e("TAG", "Sqlite Data Not Saved")
//                            Toast.makeText(applicationContext, "Sorry", Toast.LENGTH_SHORT).show();

                        }
                        //insert Data end

                        Log.e("TAG - - : ", "Name = : ${salaryDataBasePojoClass.name}");
                        Log.e("TAG - - : ", "Email = : ${salaryDataBasePojoClass.email}");
                        Log.e("TAG - - : ", "Phone = : ${salaryDataBasePojoClass.phone}");
                        Log.e("TAG - - : ", "company office days = : ${salaryDataBasePojoClass.companyiesWorkingDays}");
                        Log.e("TAG - - : ", "Attendance count = : ${salaryDataBasePojoClass.attendanceCount}");
                        Log.e("TAG - - : ", "Basic salary = : ${salaryDataBasePojoClass.basicSalary}");
                        Log.e("TAG - - : ", "Total salary of this month = : ${salaryDataBasePojoClass.totalSalary}");
                        Log.e("TAG - - : ", "Bonus salary = : ${salaryDataBasePojoClass.bonusSalary}");
                        Log.e("TAG - - : ", "Fine salary = : ${salaryDataBasePojoClass.salaryFine}");
                        Log.e("TAG - - : ", "Payable Salary  = : ${salaryDataBasePojoClass.payAbleSalary}");

                    }
//insert data to Database POJO class END

                    var salaryListEmployeeListAdapter = SalaryListEmployeeListAdapter(
                            addemployeePojoclassList,
                            total_workeing_Days_Compnay,
                            totalSalaryList,
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

//make the database table
        dataInsert = DataInsert(applicationContext)
        dataBaseHelper = DataBaseHelper(this)

        //initialize the sqlite database and delete the Data from Table
        sqliteDatabse = dataBaseHelper.readableDatabase
        sqliteDatabse.delete(DataBaseHelper.DATABASE_TABLE_NAME, null, null)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.salary_view_in_xls_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.xlsMenu -> {
                val exportDatabaseCSVTask = ExportDatabaseCSVTask()
                exportDatabaseCSVTask.execute()
            }
        }

        return true
    }


    inner class ExportDatabaseCSVTask : AsyncTask<String, Void, Boolean>() {
//        private val dialog = ProgressDialog(applicationContext)


        override fun onPreExecute() {
//            this.dialog.setMessage("Exporting database...")
//            this.dialog.show()
        }

        override fun doInBackground(vararg args: String): Boolean? {
            val dbFile = getDatabasePath(DataBaseHelper.DATABASE_NAME)
            //            File dbFile = getDatabasePath("Information.db");
            Log.e("MainActivity", "doInBackground:  dbfile $dbFile")
            println(dbFile)  // displays the data base path in your logcat
            val exportDir = File(Environment.getExternalStorageDirectory(), "")

            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }

            val file = File(exportDir, "SalarySheet.csv")
            try {
                file.createNewFile()
                val csvWrite = CSVWriter(FileWriter(file))
                val dataBaseHelperyh = DataBaseHelper(applicationContext)
                sqliteDatabse = dataBaseHelperyh.readableDatabase
                val curCSV = sqliteDatabse.rawQuery("select * from " + DataBaseHelper.DATABASE_TABLE_NAME, null)
                csvWrite.writeNext(curCSV.getColumnNames())
                while (curCSV.moveToNext()) {

                    val arrStr = arrayOf<String>(
                            curCSV.getString(0),
                            curCSV.getString(1),
                            curCSV.getString(2),
                            curCSV.getString(3),
                            curCSV.getString(4),
                            curCSV.getString(5),
                            curCSV.getString(6),
                            curCSV.getString(7),
                            curCSV.getString(8),
                            curCSV.getString(9),
                            curCSV.getString(10))
                    csvWrite.writeNext(arrStr)
                }
                csvWrite.close()
                curCSV.close()
                return true
            } catch (sqlEx: SQLException) {
                Log.e("MainActivity", sqlEx.message, sqlEx)
                return false
            } catch (e: IOException) {
                Log.e("MainActivity", e.message, e)
                return false
            }

        }

        override fun onPostExecute(success: Boolean?) {
//            if (this.dialog.isShowing) {
//                this.dialog.dismiss()
//            }
            if (success!!) {
                Toast.makeText(applicationContext, "Export successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Export failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
