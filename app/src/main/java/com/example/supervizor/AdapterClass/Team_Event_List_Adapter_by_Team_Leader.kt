package com.example.supervizor.AdapterClass

import android.app.Dialog
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.Event_Details_Team_PojoClass
import com.example.supervizor.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.custom_alert_dialog_event_show_by_team_leader.*
import kotlinx.android.synthetic.main.event_list_item_view_team_leader.view.*

class Team_Event_List_Adapter_by_Team_Leader(var event_details_team_pojoClasses: MutableList<Event_Details_Team_PojoClass>) : RecyclerView.Adapter<Team_Event_List_Adapter_by_Team_Leader.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.event_list_item_view_team_leader, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return event_details_team_pojoClasses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        return holder.setData(event_details_team_pojoClasses[position])
    }


    class ViewHolder(var itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun setData(event_details_team_pojoClasses: Event_Details_Team_PojoClass) {

            itemview.event_title_TV_item_ID_view_by_team_leader.text = event_details_team_pojoClasses!!.event_title
            itemview.event_Date_TV_item_ID_view_by_team_leader.text = event_details_team_pojoClasses!!.date
            itemview.event_time_TV_item_ID_view_by_team_leader.text = event_details_team_pojoClasses!!.event_time


            itemview.setOnClickListener {

                show_Event_Information(itemview, event_details_team_pojoClasses)

            }
        }

        private fun show_Event_Information(itemview: View,event_details_team_pojoClasses: Event_Details_Team_PojoClass) {

            var dialog = Dialog(itemview.context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_dialog_event_show_by_team_leader)

            dialog.event_date_TV_ID_by_team_leader.text = event_details_team_pojoClasses.date
            dialog.event_title_TV_ID_by_team_leader.text = event_details_team_pojoClasses.event_title
            dialog.time_title_TV_ID_by_team_leader.text = event_details_team_pojoClasses.event_time
            dialog.event_details_TV_ID_by_team_leader.text = event_details_team_pojoClasses.event_details

            dialog.cancel_btn_alert_show_ID_by_team_leader.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}
