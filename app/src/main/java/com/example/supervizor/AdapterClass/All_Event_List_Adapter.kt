package com.example.supervizor.AdapterClass

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.Editable
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass
import com.example.supervizor.Java_Class.CheckInternet
import com.example.supervizor.Java_Class.Check_User_information
import com.example.supervizor.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.kinda.alert.KAlertDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.custom_alert_dialog_event_show.*
import kotlinx.android.synthetic.main.event_list_item_view.view.*
import java.util.*

class All_Event_List_Adapter(var context: Context?, var event_date_list: MutableList<Event_details_PojoClass>) : RecyclerView.Adapter<All_Event_List_Adapter.ViewHolder>()  {

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
            dialog.setContentView(R.layout.custom_alert_dialog_event_show)

            dialog.event_date_TV_ID.text = event_details_PojoClass.date
            dialog.event_title_TV_ID.text = event_details_PojoClass.event_title
            dialog.time_title_TV_ID.text = event_details_PojoClass.event_time
            dialog.event_details_TV_ID.text = event_details_PojoClass.event_details

            dialog.cancel_btn_alert_show_ID.setOnClickListener {
                dialog.dismiss()
            }
            dialog.edit_btn_alert_show_ID.setOnClickListener {

                var check_User_information = Check_User_information()
                var user_ID = check_User_information.userID

                var date_child = event_details_PojoClass.date
                var date = event_details_PojoClass.date
                var day = event_details_PojoClass.day
                var event_details = event_details_PojoClass.event_details
                var event_title = event_details_PojoClass.event_title
                var event_time = event_details_PojoClass.event_time
                var event_month = event_details_PojoClass.month
                var event_year = event_details_PojoClass.year

                dialog.dismiss()
                showEditEvent_Alert(itemview.context, date_child, date, day,
                        event_details,
                        event_time, event_title, event_month, event_year, user_ID)


            }

            val window = dialog.getWindow()
            val wlp = window.getAttributes()
            wlp.gravity = Gravity.CENTER
            wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
            wlp.windowAnimations = R.style.DialogAnimation_right_TO_Center
            window.setAttributes(wlp)

            dialog.show()
        }


        fun showEditEvent_Alert(context: Context, date_child: String, date: String,
                                day: String, event_details: String,
                                event_time_st: String,
                                event_title_st: String, event_month:
                                String, event_year: String, user_ID: String) {

            val dialog = Dialog(context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_dialog_event_add)
//set animation
            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation_Left_TO_Center

            val add_Event_button = dialog.findViewById<Button>(R.id.button_ok_dialog)
            add_Event_button.text="Save it"
            val cross_btn = dialog.findViewById<Button>(R.id.cross_image_button_ID)
            val textView = dialog.findViewById<TextView>(R.id.event_date_TV_ID)
            val event_time_ET = dialog.findViewById<TextView>(R.id.event_Time_ET_ID)
            val title_event_ET = dialog.findViewById<EditText>(R.id.event_title_ET_ID)
            val details_event_ET = dialog.findViewById<EditText>(R.id.event_details_ET_ID)

            textView.text = date
            event_time_ET.text = event_time_st
            details_event_ET.text = Editable.Factory.getInstance().newEditable(event_details)
            title_event_ET.text = Editable.Factory.getInstance().newEditable(event_title_st)


            textView.text = "Event : " + date

            event_time_ET.setOnClickListener { v -> setTime(v, event_time_ET as EditText?) }

            cross_btn.setOnClickListener { dialog.dismiss() }

            add_Event_button.setOnClickListener { v ->
                val event_title = title_event_ET.text.toString()
                val event_details = details_event_ET.text.toString()
                val event_time_set = event_time_ET.text.toString()

                //check the alert input field is empty ?
                if (event_title.isEmpty() || event_details.isEmpty() || event_time_set.isEmpty()) {
                    Toasty.info(context, "Fill up the input field").show()
                    dialog.dismiss()
                    return@setOnClickListener
                }

                val event_details_pojoClass = Event_details_PojoClass(date_child, day, event_month, event_year
                        , event_title, event_details, event_time_set)

                if (!CheckInternet.isInternet(context)) {
                    Toasty.error(context, "Internet Connection Error")
                    return@setOnClickListener
                }
                dialog.dismiss()

                val kAlertDialog1 = KAlertDialog(context, KAlertDialog.PROGRESS_TYPE)
                kAlertDialog1.titleText = "Saving Data to Database"
                kAlertDialog1.show()

                var databaseReference = FirebaseDatabase.getInstance().getReference()

                databaseReference.child("Event_list").child(user_ID).child(date_child).setValue(event_details_pojoClass).addOnSuccessListener(OnSuccessListener<Void> {
                    Toasty.success(context, "Event Saved").show()
                    kAlertDialog1.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                    kAlertDialog1.titleText = "Done"
                    kAlertDialog1.setConfirmClickListener { kAlertDialog1.dismissWithAnimation() }
                })
            }
            dialog.show()

        }


            fun setTime(v: View?, event_time: EditText?) {

                var CalendarHour: Int
                var CalendarMinute: Int
                var format: String
                var timepickerdialog: TimePickerDialog?


                var calendar = Calendar.getInstance()
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY)
                CalendarMinute = calendar.get(Calendar.MINUTE)


                timepickerdialog = TimePickerDialog(v!!.context,
                        { _, hourOfDay, minute ->

                            var hour_of_day = when (hourOfDay) {

                                13->"01"
                                14->"02"
                                15->"03"
                                16->"04"
                                17->"05"
                                18->"06"
                                19->"07"
                                20->"08"
                                21->"09"
                                22->"10"
                                23->"11"
                                else -> hourOfDay
                            }

                            format = if(hourOfDay<12){
                                "AM"
                            }else{
                                "PM"
                            }


                            if (minute < 10) {
                                event_time!!.text = Editable.Factory.getInstance().newEditable("$hour_of_day : 0$minute $format");

                            } else {
                                event_time!!.text = Editable.Factory.getInstance().newEditable("$hour_of_day : $minute $format")

                            }

                        }, CalendarHour, CalendarMinute, false)
                timepickerdialog.show()
            }



    }


}
/*

private fun show_Event_Information(itemview: View, event_details_PojoClass: Event_details_PojoClass) {


    var dialog = Dialog(itemview.context)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.custom_alert_dialog_event_show)

    dialog.event_date_TV_ID.text = event_details_PojoClass.date
    dialog.event_title_TV_ID.text = event_details_PojoClass.event_title
    dialog.time_title_TV_ID.text = event_details_PojoClass.event_time
    dialog.event_details_TV_ID.text = event_details_PojoClass.event_details

    dialog.cancel_btn_alert_show_ID.setOnClickListener {
        dialog.dismiss()
    }
    dialog.edit_btn_alert_show_ID.setOnClickListener {

        var check_User_information = Check_User_information();
        var user_ID = check_User_information.userID

        var date_child = event_details_PojoClass.date
        var date = event_details_PojoClass.date
        var day = event_details_PojoClass.day
        var event_details = event_details_PojoClass.event_details
        var event_title = event_details_PojoClass.event_title
        var event_time = event_details_PojoClass.event_time
        var event_month = event_details_PojoClass.month
        var event_year = event_details_PojoClass.year

        dialog.dismiss()
        showEditEvent_Alert(itemview.context, date_child, date, day,
                event_details,
                event_time, event_title, event_month, event_year, user_ID)


    }

    val window = dialog.getWindow()
    val wlp = window.getAttributes()
    wlp.gravity = Gravity.CENTER
    wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
    wlp.windowAnimations = R.style.DialogAnimation_right_TO_Center
    window.setAttributes(wlp)

    dialog.show()
}
*/

/*
fun showEditEvent_Alert(context: Context, date_child: String, date: String,
                        day: String, event_details: String,
                        event_time_st: String,
                        event_title_st: String, event_month:
                        String, event_year: String, user_ID: String) {

    val dialog = Dialog(context)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.custom_alert_dialog_event_add)
//set animation
    dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation_Left_TO_Center

    val add_Event_button = dialog.findViewById<Button>(R.id.button_ok_dialog)
    add_Event_button.text="Save it"
    val cross_btn = dialog.findViewById<Button>(R.id.cross_image_button_ID)
    val textView = dialog.findViewById<TextView>(R.id.event_date_TV_ID)
    val event_time_ET = dialog.findViewById<TextView>(R.id.event_Time_ET_ID)
    val title_event_ET = dialog.findViewById<EditText>(R.id.event_title_ET_ID)
    val details_event_ET = dialog.findViewById<EditText>(R.id.event_details_ET_ID)

    textView.text = date
    event_time_ET.text = event_time_st
    details_event_ET.text = Editable.Factory.getInstance().newEditable(event_details)
    title_event_ET.text = Editable.Factory.getInstance().newEditable(event_title_st)


    textView.text = "Event : " + date

    event_time_ET.setOnClickListener { v -> setTime(v, event_time_ET as EditText?) }

    cross_btn.setOnClickListener { dialog.dismiss() }

    add_Event_button.setOnClickListener { v ->
        val event_title = title_event_ET.text.toString()
        val event_details = details_event_ET.text.toString()
        val event_time_set = event_time_ET.text.toString()

        //check the alert input field is empty ?
        if (event_title.isEmpty() || event_details.isEmpty() || event_time_set.isEmpty()) {
            Toasty.info(context, "Fill up the input field").show()
            dialog.dismiss()
            return@setOnClickListener
        }

        val event_details_pojoClass = Event_details_PojoClass(date_child, day, event_month, event_year
                , event_title, event_details, event_time_set)

        if (!CheckInternet.isInternet(context)) {
            Toasty.error(context, "Internet Connection Error")
            return@setOnClickListener
        }
        dialog.dismiss()

        val kAlertDialog1 = KAlertDialog(context, KAlertDialog.PROGRESS_TYPE)
        kAlertDialog1.titleText = "Saving Data to Database"
        kAlertDialog1.show()

        var databaseReference = FirebaseDatabase.getInstance().getReference()

        databaseReference.child("Event_list").child(user_ID).child(date_child).setValue(event_details_pojoClass).addOnSuccessListener(OnSuccessListener<Void> {
            Toasty.success(context, "Event Saved").show()
            kAlertDialog1.changeAlertType(KAlertDialog.SUCCESS_TYPE)
            kAlertDialog1.titleText = "Done"
            kAlertDialog1.setConfirmClickListener { kAlertDialog1.dismissWithAnimation() }
        })
    }
    dialog.show()

}

fun setTime(v: View?, event_time: EditText?) {

    var CalendarHour: Int
    var CalendarMinute: Int
    var format: String
    var timepickerdialog: TimePickerDialog?


    var calendar = Calendar.getInstance()
    CalendarHour = calendar.get(Calendar.HOUR_OF_DAY)
    CalendarMinute = calendar.get(Calendar.MINUTE)


    timepickerdialog = TimePickerDialog(v!!.context,
            { _, hourOfDay, minute ->

                var hour_of_day = when (hourOfDay) {

                    13->"01"
                    14->"02"
                    15->"03"
                    16->"04"
                    17->"05"
                    18->"06"
                    19->"07"
                    20->"08"
                    21->"09"
                    22->"10"
                    23->"11"
                    else -> hourOfDay
                }

                format = if(hourOfDay<12){
                    "AM"
                }else{
                    "PM"
                }


                if (minute < 10) {
                    event_time!!.text = Editable.Factory.getInstance().newEditable("$hour_of_day : 0$minute $format");

                } else {
                    event_time!!.text = Editable.Factory.getInstance().newEditable("$hour_of_day : $minute $format")

                }

            }, CalendarHour, CalendarMinute, false)
    timepickerdialog.show()
}*/
