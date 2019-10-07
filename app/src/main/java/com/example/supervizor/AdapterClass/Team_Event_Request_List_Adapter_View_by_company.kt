package com.example.supervizor.AdapterClass

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.Activity.CompanyActivity.Team_RequestListActivity
import com.example.supervizor.Fragment.Company.Employee_list_F
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import com.example.supervizor.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_event_request_view_company.view.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.custom_alert_event_request_show.*
import kotlinx.android.synthetic.main.custom_alert_event_request_show.view.*
import kotlinx.android.synthetic.main.custom_alert_event_request_show.view.image_circleImageView_alert_ID


class Team_Event_Request_List_Adapter_View_by_company(var team_name_list: MutableList<String>, var team_leader_user_id: MutableList<String>) : RecyclerView.Adapter<Team_Event_Request_List_Adapter_View_by_company.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_request_view_company, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return team_name_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        return holder.setData(team_name_list[position], team_leader_user_id[position])
    }

    class ViewHolder(var itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun setData(team_name: String, team_leader_user_ID: String) {

            itemview.team_name_TV_Item_ID_as_a_company.text = team_name

            itemview.setOnClickListener {

                show_Event_Information(team_name, team_leader_user_ID)

            }
        }

        private fun show_Event_Information(teamName: String, teamLeaderUserId: String) {

            var dialog = Dialog(itemview.context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_event_request_show)

            var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
            // Read from the database
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val addemployeePojoclass: AddEmployee_PojoClass? = dataSnapshot.child("employee_list")
                            .child(teamLeaderUserId).getValue(AddEmployee_PojoClass::class.java)

                    var image_link = addemployeePojoclass!!.employee_profile_image_link
                    if (!image_link.equals("null")) {

                        Picasso.get().load(Uri.parse(image_link)).into(dialog.image_circleImageView_alert_ID)
                    } else {
//                        itemView.image_circleImageView_alert_ID.setImageResource(R.drawable.profile)
                        /*val imgResId = R.drawable.profile

                        dialog.image_circleImageView_alert_ID.setImageResource(imgResId)*/
                    }
                    dialog.name_TV_ID_custom_alert.text = addemployeePojoclass.employee_name
                    dialog.team_name_ID_custom_alert_dialog.text = teamName

                    dialog.cancel_btn_custom_alert.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.approve_btn_custom_alert.setOnClickListener {

                        databaseReference.child("my_team_request_pending")
                                .child(addemployeePojoclass.company_User_id)
                                .child(teamLeaderUserId)
                                .child(teamName)
                                .child("status")
                                .setValue("1")
                                .addOnCompleteListener {
                                    dialog.dismiss()
                                    itemview.context.startActivity(Intent(itemview.context, Team_RequestListActivity::class.java)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                    Toasty.info(itemview.context, "Approved").show()
                                }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toasty.error(itemview.context, "Failed To Load To Data").show()


                }
            })

            dialog.show()
        }

        /*  private fun loadTeamRequestList() {


              var fragment: Fragment?
              fragment = Team_Request()

              if (fragment != null) {

                  val fragmentTransaction = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                  fragmentTransaction.replace(R.id.company_main_screen, fragment!!)
                  fragmentTransaction.commit()
              }

          }*/
    }
}
