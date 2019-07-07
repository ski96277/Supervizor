package com.example.supervizor.Fragment.Company;

import android.content.Context;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.R;
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

        return inflater.inflate(R.layout.calender_f,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        materialCalendarView=view.findViewById(R.id.calendarView_ID);

        CalendarDay date  = materialCalendarView.getCurrentDate();
        materialCalendarView.setBackgroundColor(Color.parseColor("#EFEFEF"));
//        Toasty.info(getContext(),date.toString()).show();
// highlight today date
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setSelectedDate(LocalDate.parse("2019-07-08"));
        Toasty.info(getContext(),LocalDate.now().toString()).show();


        materialCalendarView.setOnDateChangedListener((materialCalendarView, calendarDay, b) -> {
            if (materialCalendarView.getSelectedDate()!=null){

                Toasty.info(getContext(),materialCalendarView.getSelectedDate().toString()).show();
                materialCalendarView.setSelectionColor(Color.parseColor("#926C24"));
            }
        });

    }
    //set title
    public void onResume(){
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Calender");
    }

}
