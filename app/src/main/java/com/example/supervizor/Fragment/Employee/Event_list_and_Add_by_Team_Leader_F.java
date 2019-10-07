/*
package com.example.supervizor.Fragment.Employee;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.supervizor.Activity.EmployeeActivity.EmployeeMainActivity;
import com.example.supervizor.AdapterClass.Team_Event_List_Adapter_by_Team_Leader;
import com.example.supervizor.JavaPojoClass.Event_Details_Team_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class Event_list_and_Add_by_Team_Leader_F extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerViewIDTeamEventList;
    private FloatingActionButton addNewTeamEvent;

    int day_select;
    int month_select;
    int year_select;
    int CalendarHour, CalendarMinute;
    String format;
    Calendar calendar;
    TimePickerDialog timepickerdialog;

    private DatabaseReference databaseReference;
    Check_User_information check_user_information;
    Event_Details_Team_PojoClass event_details_team_pojoClass;

    List<Event_Details_Team_PojoClass> event_details_team_pojoClasses = new ArrayList<>();
    private String team_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_list_and_add_by_team_leader, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
//get team name
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        team_name = sharedPreferences.getString("team_name", "");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewIDTeamEventList.setLayoutManager(linearLayoutManager);

        databaseReference.child("event_list_by_Team")
                .child(check_user_information.getUserID())
                .child(team_name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            Event_Details_Team_PojoClass event_details_team_pojoClass = snapshot.getValue(Event_Details_Team_PojoClass.class);
                            Log.e("TAG", "onDataChange: Date = " + event_details_team_pojoClass.getDate());

//                            event_details_pojoClass = snapshot.getValue(Event_details_PojoClass.class);
                            event_details_team_pojoClasses.add(event_details_team_pojoClass);
                        }
                        Log.e("TAG", "onDataChange:list size =  " + event_details_team_pojoClasses.size());
                        if (event_details_team_pojoClasses.isEmpty()){
                            showCustomAlertDialog_setEvent_for_Team_mate();
                        }
                        Team_Event_List_Adapter_by_Team_Leader team_event_list_adapter_by_team_leader = new Team_Event_List_Adapter_by_Team_Leader(event_details_team_pojoClasses);
                        recyclerViewIDTeamEventList.setAdapter(team_event_list_adapter_by_team_leader);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toasty.error(getContext(), "Failed to load data ").show();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_New_team_Event) {
            showCustomAlertDialog_setEvent_for_Team_mate();
        }
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Team Event List ");
    }

    private void initView(View rootView) {
        recyclerViewIDTeamEventList = (RecyclerView) rootView.findViewById(R.id.recyclerView_ID_Team_Event_list);
        addNewTeamEvent = (FloatingActionButton) rootView.findViewById(R.id.add_New_team_Event);
        addNewTeamEvent.setOnClickListener(Event_list_and_Add_by_Team_Leader_F.this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();


    }


    //show alert for adding event
    private void showCustomAlertDialog_setEvent_for_Team_mate() {


        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_alert_dialog_event_add_by_team_leader);
        dialog.setCancelable(false);
//show alert dialog
        dialog.show();

        EditText title_ET = dialog.findViewById(R.id.event_title_ET_ID_team_leader);
        EditText details_ET = dialog.findViewById(R.id.event_details_ET_ID_team_leader);
        EditText date_ET = dialog.findViewById(R.id.event_Date_ET_ID_team_leader);
        EditText time_ET = dialog.findViewById(R.id.event_Time_ET_ID_team_leader);
        Button button_cancle = dialog.findViewById(R.id.cancel_button_ID_team_leader);
        Button button_submit = dialog.findViewById(R.id.add_button_dialog_team_leader);

        //show time picker dialog
        time_ET.setOnClickListener(v -> SelectTime(v, time_ET));
        date_ET.setOnClickListener(v -> {

            selectDate(v, date_ET);
        });

        //cancel alert dialog
        button_cancle.setOnClickListener(v -> dialog.dismiss());
        //save event alert dialog
        button_submit.setOnClickListener(v -> {

            Check_User_information check_user_information = new Check_User_information();

            String title = title_ET.getText().toString();
            String details = details_ET.getText().toString();
            String time = time_ET.getText().toString();
            String date = date_ET.getText().toString();

            String day = String.valueOf(day_select);
            String month = String.valueOf(month_select);
            String year = String.valueOf(year_select);
            //check data is empty or not ?
            if (title.isEmpty() || details.isEmpty() || time.isEmpty() || date.isEmpty()) {
                Toasty.info(getContext(), "Fill up the information").show();
                return;
            }
            //check internet connection
            if (!CheckInternet.isInternet(getContext())) {
                Toasty.error(getContext(), "Internet Connection Error").show();
                return;
            }
            KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
            kAlertDialog.show();
            kAlertDialog.setTitleText("Uploading Data....");


            Event_Details_Team_PojoClass event_details_team_pojoClass = new Event_Details_Team_PojoClass(date, day,
                    month, year, title, details, time);

            databaseReference.child("event_list_by_Team")
                    .child(check_user_information.getUserID())
                    .child(team_name)
                    .child(date_ET.getText().toString())
                    .setValue(event_details_team_pojoClass)
                    .addOnCompleteListener(task -> {
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setTitleText("Done");
                        dialog.dismiss();
                    });


        });

    }

    private void selectDate(View v, EditText date_ET) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                day_select = day;
                month_select = month + 1;
                year_select = year;

                if (month < 10) {
                    date_ET.setText(year + "-0" + (month + 1) + "-" + dayOfMonth);
                } else if (dayOfMonth < 10) {
                    date_ET.setText(year + "-" + (month + 1) + "-0" + dayOfMonth);
                } else {
                    date_ET.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
            }
        }, year, month, day);
        datePickerDialog.show();

    }

    private void SelectTime(View v, EditText time_ET) {


        calendar = Calendar.getInstance();
        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(getContext(),
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

                    if (minute < 10) {
                        time_ET.setText(hourOfDay + " : 0" + minute + " " + format);

                    } else {
                        time_ET.setText(hourOfDay + " : " + minute + " " + format);

                    }


                }, CalendarHour, CalendarMinute, false);
        timepickerdialog.show();

    }
}
*/
