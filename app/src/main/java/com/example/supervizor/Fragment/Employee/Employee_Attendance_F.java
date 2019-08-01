package com.example.supervizor.Fragment.Employee;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.supervizor.Activity.EmployeeMainActivity;
import com.example.supervizor.Activity.ReceptionistMainActivity;
import com.example.supervizor.R;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Employee_Attendance_F extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_attendance_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner month_spinner = view.findViewById(R.id.month_spinner_ID_employee_Attendance);
        Spinner year_spinner = view.findViewById(R.id.year_spinner_ID_employee_Attendance);

        month_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);
        year_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);

        String[] month_array = {"January", "February", "March", "April", "May", "june", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter_month =
                new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, month_array);
        adapter_month.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(adapter_month);

        String[] year_array = {"2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        ArrayAdapter<String> adapter_year =
                new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, year_array);
        adapter_year.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(adapter_year);

//SET current month
        Calendar cal = Calendar.getInstance();
        String current_month = month_array[cal.get(Calendar.MONTH)];
        int selectionPosition = adapter_month.getPosition(current_month);
        month_spinner.setSelection(selectionPosition);
//SET current month End

    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Attendance");
    }
}
