package com.example.supervizor.AdapterClass

import android.app.Dialog
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.NOtification_Firebase.MySingleton
import com.example.supervizor.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.custom_alert_dialog_leave_application_pending_show_company.*
import kotlinx.android.synthetic.main.item_all_pending_leave_application_company.view.*
import org.json.JSONException
import org.json.JSONObject
import java.security.AccessController.getContext
import java.util.HashMap
import java.util.concurrent.Executor

class Leave_Application_Pending_Adapter_Company(val leaveApplication_pojoClasses: MutableList<LeaveApplication_PojoClass>) : RecyclerView.Adapter<Leave_Application_Pending_Adapter_Company.viewHolder>() {

//     val FCM_API = "https://fcm.googleapis.com/fcm/send"
//     val serverKey = "key=" + "AAAAXfQqZOg:APA91bEktl8FWv0s4gALfJ5-Y5vTj4no54F5NQ5CAgAqIoyvE1uJMDSXHfOgDmtlHyCX_jZIRduGFSFLi2PmQRUEoBkv6pZvR-2gHcymDXeQNyXSCkCb_3bPQ8EA_2Lbq_Myx34-Wj0i"
//     val contentType = "application/json"
//    internal val TAG = "NOTIFICATION TAG"
//
//     lateinit var NOTIFICATION_TITLE: String
//    lateinit var NOTIFICATION_MESSAGE: String
//    lateinit var TOPIC: String
//    lateinit var TOPIC_NAME: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_pending_leave_application_company, parent, false);
        return viewHolder(view)
    }

    override fun getItemCount(): Int {

        return leaveApplication_pojoClasses.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        return holder.setview(leaveApplication_pojoClasses[position])
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setview(leaveApplication_PojoClas: LeaveApplication_PojoClass) {

            if (!leaveApplication_PojoClas.profile_image_link.equals("null")) {

                Picasso.get().load(Uri.parse(leaveApplication_PojoClas.profile_image_link))
                        .into(itemView.leave_item_profile_photo_imageView_leave)

            } else {
                itemView.leave_item_profile_photo_imageView_leave.setImageResource(R.drawable.profile)
            }
            itemView.leave_title_item_application.text = leaveApplication_PojoClas.leave_Title
            itemView.leave_details_item_application.text = leaveApplication_PojoClas.leave_description

            itemView.setOnClickListener {
                showDialog_details(leaveApplication_PojoClas)
            }
        }

        fun showDialog_details(leaveApplication_PojoClas: LeaveApplication_PojoClass) {


            var dialog = Dialog(itemView.context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_dialog_leave_application_pending_show_company)
            dialog.leave_applicant_name_date_company.text = leaveApplication_PojoClas.leave_employee_name
            dialog.leave_title_TV_ID_company.text = leaveApplication_PojoClas.leave_Title
            dialog.leave_duration_TV_ID_company.text = "${leaveApplication_PojoClas.leave_start_date} to ${leaveApplication_PojoClas.leave_End_Date}"
            dialog.leave_details_details_TV_ID_company.text = leaveApplication_PojoClas.leave_description

            if (!leaveApplication_PojoClas.profile_image_link.equals("null")) {

                Picasso.get().load(Uri.parse(leaveApplication_PojoClas.profile_image_link))
                        .into(dialog.leave_applicant_profile_image)

            } else {
                dialog.leave_applicant_profile_image.setImageResource(R.drawable.profile)
            }

            dialog.cancel_btn_alert_show_ID_company.setOnClickListener {
                dialog.dismiss()
            }

            dialog.accept_btn_alert_show_ID_company.setOnClickListener {


                var firebseDatabase = FirebaseDatabase.getInstance()
                var check_User_information = Check_User_information()
                var databaseReference = firebseDatabase.getReference()
                        .child("leave_application")
                        .child(check_User_information.userID)
                        .child("pending")
                        .child(leaveApplication_PojoClas.leave_Title)
                        .child("leave_seen").setValue(true)


                //send the notification data
                var TOPIC_NAME = leaveApplication_PojoClas.user_ID_Employee + "leave_approved"

                sendDataToFireabase(TOPIC_NAME, leaveApplication_PojoClas.leave_Title, leaveApplication_PojoClas.leave_description)

                dialog.dismiss()

            }

            dialog.show()

        }

        //Notification Data send
        private fun sendDataToFireabase(topicName: String, event_title: String, event_details: String) {

            var TOPIC = "/topics/$topicName" //topic must match with what the receiver subscribed to
            var NOTIFICATION_TITLE = event_title
            var NOTIFICATION_MESSAGE = event_details

            val notification = JSONObject()
            val notifcationBody = JSONObject()
            try {
                notifcationBody.put("title", NOTIFICATION_TITLE)
                notifcationBody.put("message", NOTIFICATION_MESSAGE)

                notification.put("to", TOPIC)
                notification.put("data", notifcationBody)

            } catch (e: JSONException) {
                Log.e("TAG", "onCreate: " + e.message)
            }

            sendNotification(notification)

        }

        private fun sendNotification(notification: JSONObject) {

            val jsonObjectRequest = object : JsonObjectRequest("https://fcm.googleapis.com/fcm/send", notification,
                    Response.Listener { response ->
                        Log.i("TAG", "onResponse: $response")
                    },
                    Response.ErrorListener {
                        Log.i("TAG", "onErrorResponse: Didn't work")
                    }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["Authorization"] = "key=" + "AAAAXfQqZOg:APA91bEktl8FWv0s4gALfJ5-Y5vTj4no54F5NQ5CAgAqIoyvE1uJMDSXHfOgDmtlHyCX_jZIRduGFSFLi2PmQRUEoBkv6pZvR-2gHcymDXeQNyXSCkCb_3bPQ8EA_2Lbq_Myx34-Wj0i"
                    params["Content-Type"] = "application/json"
                    return params
                }
            }
            MySingleton.getInstance(itemView.context).addToRequestQueue(jsonObjectRequest)
        }


    }


}