package com.example.supervizor.AdapterClass

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.R
import com.example.supervizor.Activity.ReceptionistActivity.TeamEventListReceptionActivity
import kotlinx.android.synthetic.main.item_team_name_list_as_a_member.view.*


class Team_Name_List_As_A_Member_Reception_Adapter(var team_name_list: MutableList<String>, var team_leader_id_list: MutableList<String>) : RecyclerView.Adapter<Team_Name_List_As_A_Member_Reception_Adapter.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_name_item_view_receptionist, parent, false)
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {

        return team_name_list.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        return holder.setData(team_name_list[position], team_leader_id_list[position])
    }


    class ViewHolderClass(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun setData(team_name: String, team_leader_id_list: String) {


            itemView.team_name_TV_Item_ID_as_a_member.text = team_name

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "test", Toast.LENGTH_SHORT).show();

                itemView.context.startActivity(Intent(itemView.context, TeamEventListReceptionActivity::class.java)
                        .putExtra("team_name", team_name)
                        .putExtra("team_leader_id", team_leader_id_list))

            }

        }


      /*  private fun load_Team_Member_List_Fragment(bundle: Bundle) {


            var fragment: Fragment?
            fragment = Team_Event_View_As_A_Member()
            if (fragment != null) {

                fragment.arguments = bundle
                val fragmentTransaction = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment!!)
                fragmentTransaction.commit()
            }
        }*/


    }

}
