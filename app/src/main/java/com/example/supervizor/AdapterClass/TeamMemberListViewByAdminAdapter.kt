package com.example.supervizor.AdapterClass

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.team_member_list_view_by_admin_adapter.view.*

class TeamMemberListViewByAdminAdapter(var userIDList: ArrayList<String>, var teamLeaderUserId: String) : RecyclerView.Adapter<TeamMemberListViewByAdminAdapter.HolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.team_member_list_view_by_admin_adapter, parent, false)

        return HolderClass(view)
    }

    override fun getItemCount(): Int {

        return userIDList.size
    }

    override fun onBindViewHolder(holder: HolderClass, position: Int) {


        return holder.setData(userIDList[position],teamLeaderUserId)
    }

    class HolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(userID: String, teamLeaderUserId: String) {

            var databaseReference=FirebaseDatabase.getInstance().reference
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(itemView.context, "Data Load Failed", Toast.LENGTH_SHORT).show();
                }

                override fun onDataChange(p0: DataSnapshot) {


                    var profile_image=p0.child("employee_list").child(userID)
                            .child("employee_profile_image_link").getValue(String::class.java)
                    var name_employee=p0.child("employee_list").child(userID)
                            .child("employee_name").getValue(String::class.java)
                    var designation_employee=p0.child("employee_list").child(userID)
                            .child("employee_designation").getValue(String::class.java)


                    Log.e("TAG - - : ", ":Test "+profile_image );
                    Log.e("TAG - - : ", ": "+name_employee );
                    Log.e("TAG - - : ", ": "+designation_employee );
                    Picasso.get().load(profile_image)
                            .error(R.drawable.profile)
                            .into(itemView.profile_photo_item_team_pending)

                    itemView.employee_name_item_team_pending.text=name_employee
                    itemView.employee_designation_item_pending.text=designation_employee

                }
            })




        }

    }
}