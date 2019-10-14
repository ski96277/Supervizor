package com.example.supervizor.Activity.CompanyActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.AdapterClass.EventListAdapterPersonal
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_all_event_to_this_employee.*
import kotlinx.android.synthetic.main.event_list_and_add_by_team_leader.*
import org.apache.commons.lang3.builder.ToStringBuilder


class AllEventToThisEmployee : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var userID_employee: String
    lateinit var userID_company: String
    lateinit var profileImageLink_employee: String
    lateinit var checkUserInformation: Check_User_information
    var eventDetailsPojoclasList = ArrayList<Event_details_PojoClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_event_to_this_employee)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.title = "All Event List"


        databaseReference = FirebaseDatabase.getInstance().reference
        checkUserInformation = Check_User_information()
        userID_employee = intent?.getStringExtra("user_ID_employee").toString()
        profileImageLink_employee = intent?.getStringExtra("profileimage_link").toString()
        userID_company = checkUserInformation.userID

        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        eventList_recyclerView.layoutManager = layoutManager


        //get Data
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {

            // Read from the database
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (data in
                p0.child("personal_event")
                        .child(userID_company)
                        .child(userID_employee)
                        .children) {
                    var eventDetailsPojoclass = data.getValue(Event_details_PojoClass::class.java)

                    eventDetailsPojoclasList.add(eventDetailsPojoclass!!)

                }//for Loop End
                if (eventDetailsPojoclasList.size > 0) {
                    eventList_recyclerView.visibility = View.VISIBLE
                    var eventListAdapterPersonal = EventListAdapterPersonal(eventDetailsPojoclasList, profileImageLink_employee)
                    eventList_recyclerView.adapter = eventListAdapterPersonal
                } else {
                    noEventID_TV.visibility = View.VISIBLE
                }


            }

        })


    }
}
