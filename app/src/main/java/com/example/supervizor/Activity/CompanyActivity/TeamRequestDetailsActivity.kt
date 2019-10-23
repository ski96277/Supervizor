package com.example.supervizor.Activity.CompanyActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.AdapterClass.TeamMemberListViewByAdminAdapter
import com.example.supervizor.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_team_request_details.*

class TeamRequestDetailsActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var teamMemberListViewByAdminAdapter:TeamMemberListViewByAdminAdapter

    var userIDList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_request_details)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        title = "Team Request Details"

        var intent = intent
        var team_name = intent.getStringExtra("team_name")
        var team_leader_User_ID = intent.getStringExtra("team_leader_user_ID")

//        name_TeamDetails_TV_ID.text=team_name
        team_name_TeamDetails_ID.text=team_name






        var linearLayoutManager=LinearLayoutManager(this)
        linearLayoutManager.orientation=RecyclerView.VERTICAL
        teamMemberList_details_recyclerView_ID.layoutManager=linearLayoutManager

        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Data loading Cancel", Toast.LENGTH_SHORT).show();
            }

            override fun onDataChange(p0: DataSnapshot) {


              for (userid in p0.child("my_team_request")
                        .child(team_leader_User_ID)
                        .child(team_name).children){
                    userIDList.add(userid.key.toString())
                }
                if (userIDList.size>0){
                    loader_teamMemberList.visibility=View.GONE
                    teamMemberList_details_recyclerView_ID.visibility=View.VISIBLE
                    teamMemberListViewByAdminAdapter= TeamMemberListViewByAdminAdapter(userIDList,team_leader_User_ID)
                    teamMemberList_details_recyclerView_ID.adapter=teamMemberListViewByAdminAdapter

                }else{
                    loader_teamMemberList.visibility=View.GONE
                    Toast.makeText(this@TeamRequestDetailsActivity, "No Team Member", Toast.LENGTH_SHORT).show();
                }

            }
        })

        cancel_btn__TeamDetails.setOnClickListener {
            startActivity(Intent(this, Team_RequestListActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
        approve_btn__TeamDetails.setOnClickListener {
            Toast.makeText(this, "test Approved", Toast.LENGTH_SHORT).show();

        }


    }


}
