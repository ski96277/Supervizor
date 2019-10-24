package com.example.supervizor.Activity.CompanyActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.AdapterClass.TeamMemberListViewByAdminAdapter
import com.example.supervizor.Java_Class.CheckInternet
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
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

                var name_employee=p0.child("employee_list").child(team_leader_User_ID)
                        .child("employee_name").getValue(String::class.java)

                var profileImage=p0.child("employee_list").child(team_leader_User_ID)
                        .child("employee_profile_image_link").getValue(String::class.java)
                name_TeamDetails_TV_ID.text=name_employee
                Picasso.get().load(profileImage).error(R.drawable.profile).into(image_circleImageView__TeamDetails)



              for (userid in p0.child("my_team_request")
                        .child(team_leader_User_ID)
                        .child(team_name).children){
                    userIDList.add(userid.key.toString())
                }
                if (userIDList.size>0){
                    loader_teamMemberList.visibility=View.GONE
                    teamMemberList_details_recyclerView_ID.visibility=View.VISIBLE
                    teamMemberListViewByAdminAdapter= TeamMemberListViewByAdminAdapter(userIDList,team_leader_User_ID)

                    val resId = R.anim.layout_animation_fall_down
                    val animation = AnimationUtils.loadLayoutAnimation(this@TeamRequestDetailsActivity, resId)
                    teamMemberList_details_recyclerView_ID.setLayoutAnimation(animation) /*(https://proandroiddev.com/enter-animation-using-recyclerview-and-layoutanimation-part-1-list-75a874a5d213)*/


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

            if (!CheckInternet.isInternet(this)){
                Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

                return@setOnClickListener
            }

            var checkUserInformation=Check_User_information();

            databaseReference.child("my_team_request_pending").child(checkUserInformation.userID)
                    .child(team_leader_User_ID).child(team_name).child("status").setValue("1")
            Toast.makeText(this, "Approved", Toast.LENGTH_SHORT).show();
            startActivity(Intent(this,Team_RequestListActivity::class.java).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            ))

        }


    }


}
