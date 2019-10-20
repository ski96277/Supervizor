package com.example.supervizor.Activity.CompanyActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.supervizor.R

class TeamRequestDetailsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_request_details)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        var intent=intent;
        var team_name = intent.getStringExtra("team_name")
        var team_leader_User_ID = intent.getStringExtra("team_leader_user_ID")






    }
}
