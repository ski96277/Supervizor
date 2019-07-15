package com.example.supervizor.Fragment.Company;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.AdapterClass.Event_List_Adapter;
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class Calender_F extends Fragment {

    private static TimePickerDialog timepickerdialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    MaterialCalendarView materialCalendarView;

    String user_ID;
    List<Event_details_PojoClass> event_date_list = new ArrayList<>();
    Check_User_information check_user_information;

    RecyclerView recyclerView_ID;
    KAlertDialog kAlertDialog;


    static int CalendarHour;
    static int CalendarMinute;
    static String format;

    @Override
    public void onStart() {
        super.onStart();
        //hide key pad start
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
//Hide key pad End
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //set Button background color
        CompanyMainActivity.employee_button_layout.setBackgroundColor(Color.parseColor("#000000"));
        CompanyMainActivity.calender_button_layout.setBackgroundColor(Color.parseColor("#00CCCC"));

        return inflater.inflate(R.layout.calender_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);


        CalendarDay date = materialCalendarView.getCurrentDate();

        materialCalendarView.setBackgroundColor(Color.parseColor("#FFF7F7"));

// highlight today date
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);


//get event date list

        //get user ID
        check_user_information = new Check_User_information();
        user_ID = check_user_information.getUserID();

        if (!CheckInternet.isInternet(getActivity())){
            Toasty.info(getActivity(),"Check Internet Connection").show();
            return;
        }
         kAlertDialog=new KAlertDialog(getContext(),KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Loading.....");


        kAlertDialog.show();
//get the event list from firebase END

        databaseReference.child("Event_list")
                .child(user_ID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        event_date_list.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Event_details_PojoClass event_details_pojoClass = snapshot.getValue(Event_details_PojoClass.class);
                            event_date_list.add(event_details_pojoClass);
                        }

                        for (Event_details_PojoClass event_details_pojoClass : event_date_list) {
                            // highlight today date
                            materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
                            materialCalendarView.setSelectedDate(LocalDate.parse(event_details_pojoClass.getDate()));

                            Event_List_Adapter event_list_adapter=new Event_List_Adapter(getContext(),event_date_list);

                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            recyclerView_ID.setLayoutManager(linearLayoutManager);
                            recyclerView_ID.setAdapter(event_list_adapter);

                            kAlertDialog.dismissWithAnimation();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        kAlertDialog.dismissWithAnimation();
                    }
                });
        //get the event list from firebase END

        materialCalendarView.setOnDateChangedListener((materialCalendarView, calendarDay, b) -> {

            if (materialCalendarView.getSelectedDate() != null) {


                String date_child = calendarDay.getDate().toString();
                String day_date = String.valueOf(calendarDay.getDay());
                String month = String.valueOf(calendarDay.getMonth());
                String year = String.valueOf(calendarDay.getYear());

                showAddEvent_Alert(getActivity(),date_child,day_date,month,year,
                        calendarDay,check_user_information,databaseReference);


            }
        });

    }

    private static void showAddEvent_Alert(FragmentActivity activity, String date_child, String day_date,
                                           String month, String year,
                                           CalendarDay calendarDay, Check_User_information check_user_information, DatabaseReference databaseReference) {


        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_dialog_event_add);
//set animation
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_Left_TO_Center;
        Button add_Event_button = dialog.findViewById(R.id.button_ok_dialog);
        Button cross_btn = dialog.findViewById(R.id.cross_image_button_ID);
        TextView textView = dialog.findViewById(R.id.event_date_TV_ID);
        TextView event_time=dialog.findViewById(R.id.event_Time_ET_ID);
        EditText title_event_ET = dialog.findViewById(R.id.event_title_ET_ID);
        EditText details_event_ET = dialog.findViewById(R.id.event_details_ET_ID);

        textView.setText("Event : " + calendarDay.getDate());

        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(v,event_time);
            }
        });

        cross_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        add_Event_button.setOnClickListener(v -> {
            String event_title = title_event_ET.getText().toString();
            String event_details = details_event_ET.getText().toString();
            String event_time_set=event_time.getText().toString();

            String user_ID = check_user_information.getUserID();

            if (event_title.isEmpty() || event_details.isEmpty() || event_time_set.isEmpty()) {
                Toasty.info(activity, "Fill up the input field").show();
                dialog.dismiss();
                return;
            }

            Event_details_PojoClass event_details_pojoClass
                    = new Event_details_PojoClass(date_child, day_date, month, year, event_title, event_details,event_time_set);

            if (!CheckInternet.isInternet(activity)) {
                Toasty.error(activity, "Internet Connection Error");
                return;
            }
            dialog.dismiss();

            KAlertDialog kAlertDialog1 = new KAlertDialog(activity, KAlertDialog.PROGRESS_TYPE);
            kAlertDialog1.setTitleText("Saving Data to Database");
            kAlertDialog1.show();
            databaseReference.child("Event_list").child(user_ID).child(date_child).setValue(event_details_pojoClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toasty.success(activity, "Event Saved").show();
                    kAlertDialog1.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                    kAlertDialog1.setTitleText("Done");
                    kAlertDialog1.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(KAlertDialog kAlertDialog) {
                            kAlertDialog1.dismissWithAnimation();
                        }
                    });
                }
            });
        });
        dialog.show();

    }

    private static void setTime(View v, TextView event_time) {

        Calendar calendar = java.util.Calendar.getInstance();
        CalendarHour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        CalendarMinute = calendar.get(Calendar.MINUTE);


        timepickerdialog = new TimePickerDialog(v.getContext(),
                (view, hourOfDay, minute) -> {

                    if (hourOfDay == 0) {

                        hourOfDay += 12;

                        format = "AM";
                    } else if (hourOfDay == 12) {

                        format = "PM";

                    } else if (hourOfDay > 12) {

                        hourOfDay -= 12;

                        format = "PM";

                    } else {

                        format = "AM";
                    }

                    if (minute<10){
                        event_time.setText(hourOfDay + " : 0" + minute + " " + format);

                    }else {
                        event_time.setText(hourOfDay + " : " + minute + " " + format);

                    }

                }, CalendarHour, CalendarMinute, false);
        timepickerdialog.show();

    }

    private void initialize(View view) {
        materialCalendarView = view.findViewById(R.id.calendarView_ID);
        recyclerView_ID=view.findViewById(R.id.event_list_recycler_ID);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Calender");
    }

}
