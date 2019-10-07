package com.example.supervizor.Activity.CompanyActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.supervizor.R

class SalaryReportAllActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_report_all)
        supportActionBar?.title="Salary Report"
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
