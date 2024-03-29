package com.example.supervizor.AdapterClass

import android.app.Dialog
import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass
import com.example.supervizor.R
import kotlinx.android.synthetic.main.custom_alert_dialog_event_show.*
import kotlinx.android.synthetic.main.event_list_item_view.view.*

class All_Event_List_Adapter_For_Employee(var context: Context?, var event_date_list: MutableList<Event_details_PojoClass>) : RecyclerView.Adapter<All_Event_List_Adapter_For_Employee.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.event_list_item_view, parent, false);
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return event_date_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        return holder.setData(event_date_list[position], position)
    }


    class ViewHolder(var itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun setData(event_details_PojoClass: Event_details_PojoClass, position: Int) {

            itemview.event_title_TV_item_ID.text = event_details_PojoClass.event_title
            itemview.event_Date_TV_item_ID.text = event_details_PojoClass.date
            itemview.event_time_TV_item_ID.text = event_details_PojoClass.event_time


            itemview.setOnClickListener {

                show_Event_Information(itemview, event_details_PojoClass)

            }
        }

        private fun show_Event_Information(itemview: View, event_details_PojoClass: Event_details_PojoClass) {

            var dialog = Dialog(itemview.context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_dialog_event_show_for_employee)

            dialog.event_date_TV_ID.text = event_details_PojoClass.date
            dialog.event_title_TV_ID.text = event_details_PojoClass.event_title
            dialog.time_title_TV_ID.text = event_details_PojoClass.event_time
            dialog.event_details_TV_ID.text = event_details_PojoClass.event_details

            dialog.cancel_btn_alert_show_ID.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}
