package com.example.supervizor.AdapterClass

import android.app.Dialog
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.Event_Details_Team_PojoClass
import com.example.supervizor.R import kotlinx.android.synthetic.main.custom_alert_dialog_event_show_by_team_member.*
import kotlinx.android.synthetic.main.item_event_request_view_company.view.*

class Team_Event_Request_List_Adapter_View_by_company(var team_name_list: MutableList<String>, var team_leader_user_id: MutableList<String>) : RecyclerView.Adapter<Team_Event_Request_List_Adapter_View_by_company.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_request_view_company, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return team_name_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        return holder.setData(team_name_list[position],team_leader_user_id[position])
    }


    class ViewHolder(var itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun setData(team_name: String, team_leader_user_ID: String) {

            itemview.team_name_TV_Item_ID_as_a_company.text = team_name

            itemview.setOnClickListener {

//                show_Event_Information(itemview, event_details_team_pojoClasses)

            }
        }

        private fun show_Event_Information(itemview: View,event_details_team_pojoClasses: Event_Details_Team_PojoClass) {

            var dialog = Dialog(itemview.context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_dialog_event_show_by_team_member)

            dialog.event_date_TV_ID_by_team_member.text = event_details_team_pojoClasses.date
            dialog.event_title_TV_ID_by_team_member.text = event_details_team_pojoClasses.event_title
            dialog.time_title_TV_ID_by_team_member.text = event_details_team_pojoClasses.event_time
            dialog.event_details_TV_ID_by_team_member.text = event_details_team_pojoClasses.event_details

            dialog.cancel_btn_alert_show_ID_by_team_member.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}
