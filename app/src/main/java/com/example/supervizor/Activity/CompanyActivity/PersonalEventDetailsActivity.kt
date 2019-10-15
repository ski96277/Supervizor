package com.example.supervizor.Activity.CompanyActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass
import com.example.supervizor.R
import kotlinx.android.synthetic.main.activity_personal_event_details.*

class PersonalEventDetailsActivity : AppCompatActivity() {

    lateinit var eventDetailpojo:Event_details_PojoClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_personal_event_details)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.title="Event Details"

        eventDetailpojo = intent.getSerializableExtra("eventDetails") as Event_details_PojoClass
        Log.e("TAG - - : ", ": ${eventDetailpojo.event_details}")


        titleEventIDTV.text="Title : ${eventDetailpojo.event_title}"
        eventDateIDTV.text="Date : ${eventDetailpojo.date}"
        detailsIDTV.text="Details: ${eventDetailpojo.event_details}"

    }
}
