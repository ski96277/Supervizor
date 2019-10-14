package com.example.supervizor.AdapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass
import com.example.supervizor.R

class EventListAdapterPersonal(var eventDetailsPojoclasList: ArrayList<Event_details_PojoClass>, var profileimagelinkEmployee: String) : RecyclerView.Adapter<EventListAdapterPersonal.ViewHolderClass>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.event_list_item_view__by_reception, parent, false)

        return ViewHolderClass(view)

    }

    override fun getItemCount(): Int {
        return eventDetailsPojoclasList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {


        return holder.setDataToItem(eventDetailsPojoclasList[position].event_title,eventDetailsPojoclasList[0].date,eventDetailsPojoclasList[0].event_details,profileimagelinkEmployee)
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setDataToItem(eventTitle: String, date: String, eventDetails: String, profileimagelinkEmployee: String) {
            Toast.makeText(itemView.context, ""+profileimagelinkEmployee, Toast.LENGTH_SHORT).show();

        }

    }
}