package com.example.supervizor.AdapterClass

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.Activity.CompanyActivity.AllEventToThisEmployee
import com.example.supervizor.Activity.CompanyActivity.PersonalEventDetailsActivity
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass
import com.example.supervizor.Java_Class.CheckInternet
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.firebase.database.FirebaseDatabase
import com.kinda.alert.KAlertDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_personal_event_list.view.*

class EventListAdapterPersonal(var useridEmployee: String,
                               var eventDetailsPojoclasList: ArrayList<Event_details_PojoClass>,
                               var profileimagelinkEmployee: String) : RecyclerView.Adapter<EventListAdapterPersonal.ViewHolderClass>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_personal_event_list, parent, false)

        return ViewHolderClass(view)

    }

    override fun getItemCount(): Int {
        return eventDetailsPojoclasList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {


        return holder.setDataToItem(eventDetailsPojoclasList[position], profileimagelinkEmployee, useridEmployee)
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setDataToItem(eventDetails: Event_details_PojoClass, profileimagelinkEmployee: String, useridEmployee: String) {

            Picasso.get().load(Uri.parse(profileimagelinkEmployee))
                    .error(R.drawable.profile)
                    .into(itemView.profile_image_ID_personal_event)
            itemView.title_personal_event_TV_ID.text = eventDetails.event_title
            itemView.details_personal_event_ID_TV.text = eventDetails.event_details
            itemView.date_personal_event_ID.text = eventDetails.date

            if (CheckInternet.isInternet(itemView.context)) {

                itemView.setOnClickListener {
                    itemView.context.startActivity(
                            Intent(itemView.context, PersonalEventDetailsActivity::class.java)
                                    .putExtra("eventDetails", eventDetails)

                    )

                }


                itemView.setOnLongClickListener {

                    var deleteDialog = KAlertDialog(itemView.context, KAlertDialog.WARNING_TYPE)
                    deleteDialog.showCancelButton(true)
                    deleteDialog.cancelText = "Cancel"
                    deleteDialog.confirmText = "Delete"
                    deleteDialog.contentText = "Do You Want To Delete?"
                    deleteDialog.show()
                    deleteDialog.setCancelClickListener { deleteDialog.dismissWithAnimation() }
                    deleteDialog.setConfirmClickListener {

                        var checkUserInformation = Check_User_information()

                        var databaseReference = FirebaseDatabase.getInstance().reference
                        databaseReference.child("personal_event")
                                .child(checkUserInformation.userID)
                                .child(useridEmployee).child(eventDetails.date)
                                .removeValue()
                                .addOnSuccessListener {
                                    deleteDialog.dismissWithAnimation()
                                    Toast.makeText(itemView.context, "Deleted", Toast.LENGTH_SHORT).show();
                                    itemView.context.startActivity(Intent(itemView.context, AllEventToThisEmployee::class.java)
                                            .putExtra("user_ID_employee", useridEmployee)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                    }

                    return@setOnLongClickListener true
                }

            }//check internet end
            else {
                Toast.makeText(itemView.context, "Check Internet Connection", Toast.LENGTH_SHORT).show()
                return
            }


        }


    }
}
