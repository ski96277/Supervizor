package com.example.supervizor.AdapterClass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass
import com.example.supervizor.R
import com.kinda.alert.KAlertDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.event_list_item_view.view.*

class Event_List_Adapter(var context: Context, var event_date_list: MutableList<Event_details_PojoClass>) : RecyclerView.Adapter<Event_List_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.event_list_item_view, parent, false);
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return event_date_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        return holder.setData(event_date_list[position],position)
    }


    class ViewHolder(var itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun setData(event_details_PojoClass: Event_details_PojoClass, position: Int){

            itemview.event_title_TV_item_ID.text=event_details_PojoClass.event_title
            itemview.event_Date_TV_item_ID.text=event_details_PojoClass.date
            itemview.event_time_TV_item_ID.text=event_details_PojoClass.event_time

            itemview.setOnClickListener {
                var kAlertDialog=KAlertDialog(itemview.context,KAlertDialog.BUTTON_NEUTRAL)

            }
        }


    }


}