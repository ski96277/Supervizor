package com.example.supervizor.Fragment.Company;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
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
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kinda.alert.KAlertDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class Calender_F extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    MaterialCalendarView materialCalendarView;

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
        materialCalendarView.setBackgroundColor(Color.parseColor("#EFEFEF"));
//        Toasty.info(getContext(),date.toString()).show();
// highlight today date
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setSelectedDate(LocalDate.parse("2019-07-08"));
        Toasty.info(getContext(), LocalDate.now().toString()).show();


        materialCalendarView.setOnDateChangedListener((materialCalendarView, calendarDay, b) -> {

            if (materialCalendarView.getSelectedDate() != null) {

                String date_child = calendarDay.getDate().toString();
                String day_date = String.valueOf(calendarDay.getDay());
                String month = String.valueOf(calendarDay.getMonth());
                String year = String.valueOf(calendarDay.getYear());

                final Dialog dialog = new Dialog(getActivity());

                dialog.setCancelable(false);

                dialog.setContentView(R.layout.custom_alert_dialog_event_add);
                Button add_Event_button = dialog.findViewById(R.id.button_ok_dialog);
                Button cross_btn=dialog.findViewById(R.id.cross_image_button_ID);
                TextView textView = dialog.findViewById(R.id.event_date_TV_ID);
                EditText title_event_ET = dialog.findViewById(R.id.event_title_ET_ID);
                EditText details_event_ET = dialog.findViewById(R.id.event_details_ET_ID);

                textView.setText("Event : " + calendarDay.getDate());

                cross_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                add_Event_button.setOnClickListener(v -> {
                    String event_title = title_event_ET.getText().toString();
                    String event_details = details_event_ET.getText().toString();

                    Check_User_information check_user_information=new Check_User_information();
                    String user_ID=check_user_information.getUserID();

                    if (event_title.isEmpty() || event_details.isEmpty()){
                        Toasty.info(getActivity(),"Fill up the input field").show();
                        dialog.dismiss();
                        return;
                    }

                    Event_details_PojoClass event_details_pojoClass
                            = new Event_details_PojoClass(date_child,day_date,month,year,event_title,event_details);

                    if (!CheckInternet.isInternet(getActivity())) {
                        Toasty.error(getActivity(), "Internet Connection Error");
                        return;
                    }
                    dialog.dismiss();

                    KAlertDialog kAlertDialog = new KAlertDialog(getActivity(), KAlertDialog.PROGRESS_TYPE);
                    kAlertDialog.setTitleText("Saving Data to Database");
                    kAlertDialog.show();
                    databaseReference.child("Event_list").child(user_ID).child(date_child).setValue(event_details_pojoClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                       Toasty.success(getContext(),"Event Saved").show();
                       kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                       kAlertDialog.setTitleText("Done");
                       kAlertDialog.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(KAlertDialog kAlertDialog) {
                          kAlertDialog.dismissWithAnimation();
                           }
                       });
                        }
                    });
                });
                dialog.show();

            }
        });

    }

    private void initialize(View view) {
        materialCalendarView = view.findViewById(R.id.calendarView_ID);
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
