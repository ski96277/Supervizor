package com.example.supervizor.Fragment.Employee;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.supervizor.Activity.EmployeeMainActivity;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Employee_Attendance_F extends Fragment {

    private View rootView;
    private CircleImageView profileImage;
    private TextView dateTV1;
    private TextView entryTimeDate1;
    private TextView exitTimeDate1;
    private TextView dateTV2;
    private TextView entryTimeDate2;
    private TextView exitTimeDate2;
    private TextView dateTV3;
    private TextView entryTimeDate3;
    private TextView exitTimeDate3;
    private TextView dateTV4;
    private TextView exitTimeDate4;
    private TextView entryTimeDate4;
    private TextView dateTV5;
    private TextView entryTimeDate5;
    private TextView exitTimeDate5;
    private TextView dateTV6;
    private TextView entryTimeDate6;
    private TextView exitTimeDate6;
    private TextView dateTV7;
    private TextView entryTimeDate7;
    private TextView exitTimeDate7;
    private TextView dateTV8;
    private TextView entryTimeDate8;
    private TextView exitTimeDate8;
    private TextView dateTV9;
    private TextView entryTimeDate9;
    private TextView exitTimeDate9;
    private TextView dateTV10;
    private TextView entryTimeDate10;
    private TextView exitTimeDate10;
    private TextView dateTV11;
    private TextView entryTimeDate11;
    private TextView exitTimeDate11;
    private TextView dateTV12;
    private TextView entryTimeDate12;
    private TextView exitTimeDate12;
    private TextView dateTV13;
    private TextView entryTimeDate13;
    private TextView exitTimeDate13;
    private TextView dateTV14;
    private TextView entryTimeDate14;
    private TextView exitTimeDate14;
    private TextView dateTV15;
    private TextView entryTimeDate15;
    private TextView exitTimeDate15;
    private TextView dateTV16;
    private TextView entryTimeDate16;
    private TextView exitTimeDate16;
    private TextView dateTV17;
    private TextView entryTimeDate17;
    private TextView exitTimeDate17;
    private TextView dateTV18;
    private TextView entryTimeDate18;
    private TextView exitTimeDate18;
    private TextView dateTV19;
    private TextView entryTimeDate19;
    private TextView exitTimeDate19;
    private TextView dateTV20;
    private TextView entryTimeDate20;
    private TextView exitTimeDate20;
    private TextView dateTV21;
    private TextView entryTimeDate21;
    private TextView exitTimeDate21;
    private TextView dateTV22;
    private TextView entryTimeDate22;
    private TextView exitTimeDate22;
    private TextView dateTV23;
    private TextView entryTimeDate23;
    private TextView exitTimeDate23;
    private TextView dateTV24;
    private TextView entryTimeDate24;
    private TextView exitTimeDate24;
    private TextView dateTV25;
    private TextView entryTimeDate25;
    private TextView exitTimeDate25;
    private TextView dateTV26;
    private TextView entryTimeDate26;
    private TextView exitTimeDate26;
    private TextView dateTV27;
    private TextView entryTimeDate27;
    private TextView exitTimeDate27;
    private TextView dateTV28;
    private TextView entryTimeDate28;
    private TextView exitTimeDate28;
    private TableRow tableRow28;
    private TextView dateTV29;
    private TextView entryTimeDate29;
    private TextView exitTimeDate29;
    protected TableRow tableRow29;
    protected TextView dateTV30;
    protected TextView entryTimeDate30;
    protected TextView exitTimeDate30;
    protected TableRow tableRow30;
    protected TextView dateTV31;
    protected TextView entryTimeDate31;
    protected TextView exitTimeDate31;
    protected TableRow tableRow31;
    protected TableLayout table;

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_attendance_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initView(rootView);

        Spinner month_spinner = view.findViewById(R.id.month_spinner_ID_employee_Attendance);
        Spinner year_spinner = view.findViewById(R.id.year_spinner_ID_employee_Attendance);

        month_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);
        year_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);
//set custom spinner
        String[] month_array = {"January", "February", "March", "April", "May", "june", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter_month =
                new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, month_array);
        adapter_month.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(adapter_month);
//set custom spinner
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

        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String year = year_spinner.getSelectedItem().toString();
                String month = month_spinner.getSelectedItem().toString();
                getAttendenceValue(month, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String year = year_spinner.getSelectedItem().toString();
                String month = month_spinner.getSelectedItem().toString();
                getAttendenceValue(month, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getAttendenceValue(String month, String year) {
        Check_User_information check_user_information = new Check_User_information();
        String user_ID = check_user_information.getUserID();


        int monthNumber;
        int i = 0;

        if (month.equals("January")) {
            i = 1;
            tableRow31.setVisibility(View.VISIBLE);
            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("February")) {

            tableRow31.setVisibility(View.GONE);
            tableRow30.setVisibility(View.GONE);
            tableRow29.setVisibility(View.GONE);

            boolean flag = false;
            int year_int = Integer.valueOf(year);
            if (year_int % 400 == 0) {
                flag = true;
            } else if (year_int % 100 == 0) {
                flag = false;
            } else if (year_int % 4 == 0) {
                flag = true;
            } else {
                flag = false;
            }
            if (flag) {
                tableRow29.setVisibility(View.VISIBLE);
            }

            i = 2;
        }
        if (month.equals("March")) {
            i = 3;

            tableRow31.setVisibility(View.VISIBLE);
            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("April")) {
            tableRow31.setVisibility(View.GONE);
            i = 4;

            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("May")) {
            i = 5;

            tableRow31.setVisibility(View.VISIBLE);
            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("June")) {
            tableRow31.setVisibility(View.GONE);
            i = 6;

            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("July")) {

            i = 7;

            tableRow31.setVisibility(View.VISIBLE);
            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("August")) {
            i = 8;

            tableRow31.setVisibility(View.VISIBLE);
            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("September")) {
            tableRow31.setVisibility(View.GONE);
            i = 9;

            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("October")) {
            i = 10;

            tableRow31.setVisibility(View.VISIBLE);
            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("November")) {
            tableRow31.setVisibility(View.GONE);
            i = 11;
            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }
        if (month.equals("December")) {
            i = 12;

            tableRow31.setVisibility(View.VISIBLE);
            tableRow30.setVisibility(View.VISIBLE);
            tableRow29.setVisibility(View.VISIBLE);
        }

        monthNumber = i;
       FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Attendance");
    }

    private void initView(View rootView) {
        profileImage = (CircleImageView) rootView.findViewById(R.id.profile_image);
        dateTV1 = (TextView) rootView.findViewById(R.id.date_TV_1);
        entryTimeDate1 = (TextView) rootView.findViewById(R.id.entry_time_date_1);
        exitTimeDate1 = (TextView) rootView.findViewById(R.id.exit_time_date_1);
        dateTV2 = (TextView) rootView.findViewById(R.id.date_TV_2);
        entryTimeDate2 = (TextView) rootView.findViewById(R.id.entry_time_date_2);
        exitTimeDate2 = (TextView) rootView.findViewById(R.id.exit_time_date_2);
        dateTV3 = (TextView) rootView.findViewById(R.id.date_TV_3);
        entryTimeDate3 = (TextView) rootView.findViewById(R.id.entry_time_date_3);
        exitTimeDate3 = (TextView) rootView.findViewById(R.id.exit_time_date_3);
        dateTV4 = (TextView) rootView.findViewById(R.id.date_TV_4);
        entryTimeDate4 = (TextView) rootView.findViewById(R.id.entry_time_date_4);
        exitTimeDate4 = (TextView) rootView.findViewById(R.id.exit_time_date_4);
        dateTV5 = (TextView) rootView.findViewById(R.id.date_TV_5);
        entryTimeDate5 = (TextView) rootView.findViewById(R.id.entry_time_date_5);
        exitTimeDate5 = (TextView) rootView.findViewById(R.id.exit_time_date_5);
        dateTV6 = (TextView) rootView.findViewById(R.id.date_TV_6);
        entryTimeDate6 = (TextView) rootView.findViewById(R.id.entry_time_date_6);
        exitTimeDate6 = (TextView) rootView.findViewById(R.id.exit_time_date_6);
        dateTV7 = (TextView) rootView.findViewById(R.id.date_TV_7);
        entryTimeDate7 = (TextView) rootView.findViewById(R.id.entry_time_date_7);
        exitTimeDate7 = (TextView) rootView.findViewById(R.id.exit_time_date_7);
        dateTV8 = (TextView) rootView.findViewById(R.id.date_TV_8);
        entryTimeDate8 = (TextView) rootView.findViewById(R.id.entry_time_date_8);
        exitTimeDate8 = (TextView) rootView.findViewById(R.id.exit_time_date_8);
        dateTV9 = (TextView) rootView.findViewById(R.id.date_TV_9);
        entryTimeDate9 = (TextView) rootView.findViewById(R.id.entry_time_date_9);
        exitTimeDate9 = (TextView) rootView.findViewById(R.id.exit_time_date_9);
        dateTV10 = (TextView) rootView.findViewById(R.id.date_TV_10);
        entryTimeDate10 = (TextView) rootView.findViewById(R.id.entry_time_date_10);
        exitTimeDate10 = (TextView) rootView.findViewById(R.id.exit_time_date_10);
        dateTV11 = (TextView) rootView.findViewById(R.id.date_TV_11);
        entryTimeDate11 = (TextView) rootView.findViewById(R.id.entry_time_date_11);
        exitTimeDate11 = (TextView) rootView.findViewById(R.id.exit_time_date_11);
        dateTV12 = (TextView) rootView.findViewById(R.id.date_TV_12);
        entryTimeDate12 = (TextView) rootView.findViewById(R.id.entry_time_date_12);
        exitTimeDate12 = (TextView) rootView.findViewById(R.id.exit_time_date_12);
        dateTV13 = (TextView) rootView.findViewById(R.id.date_TV_13);
        entryTimeDate13 = (TextView) rootView.findViewById(R.id.entry_time_date_13);
        exitTimeDate13 = (TextView) rootView.findViewById(R.id.exit_time_date_13);
        dateTV14 = (TextView) rootView.findViewById(R.id.date_TV_14);
        entryTimeDate14 = (TextView) rootView.findViewById(R.id.entry_time_date_14);
        exitTimeDate14 = (TextView) rootView.findViewById(R.id.exit_time_date_14);
        dateTV15 = (TextView) rootView.findViewById(R.id.date_TV_15);
        entryTimeDate15 = (TextView) rootView.findViewById(R.id.entry_time_date_15);
        exitTimeDate15 = (TextView) rootView.findViewById(R.id.exit_time_date_15);
        dateTV16 = (TextView) rootView.findViewById(R.id.date_TV_16);
        entryTimeDate16 = (TextView) rootView.findViewById(R.id.entry_time_date_16);
        exitTimeDate16 = (TextView) rootView.findViewById(R.id.exit_time_date_16);
        dateTV17 = (TextView) rootView.findViewById(R.id.date_TV_17);
        entryTimeDate17 = (TextView) rootView.findViewById(R.id.entry_time_date_17);
        exitTimeDate17 = (TextView) rootView.findViewById(R.id.exit_time_date_17);
        dateTV18 = (TextView) rootView.findViewById(R.id.date_TV_18);
        entryTimeDate18 = (TextView) rootView.findViewById(R.id.entry_time_date_18);
        exitTimeDate18 = (TextView) rootView.findViewById(R.id.exit_time_date_18);
        dateTV19 = (TextView) rootView.findViewById(R.id.date_TV_19);
        entryTimeDate19 = (TextView) rootView.findViewById(R.id.entry_time_date_19);
        exitTimeDate19 = (TextView) rootView.findViewById(R.id.exit_time_date_19);
        dateTV20 = (TextView) rootView.findViewById(R.id.date_TV_20);
        entryTimeDate20 = (TextView) rootView.findViewById(R.id.entry_time_date_20);
        exitTimeDate20 = (TextView) rootView.findViewById(R.id.exit_time_date_20);
        dateTV21 = (TextView) rootView.findViewById(R.id.date_TV_21);
        entryTimeDate21 = (TextView) rootView.findViewById(R.id.entry_time_date_21);
        exitTimeDate21 = (TextView) rootView.findViewById(R.id.exit_time_date_21);
        dateTV22 = (TextView) rootView.findViewById(R.id.date_TV_22);
        entryTimeDate22 = (TextView) rootView.findViewById(R.id.entry_time_date_22);
        exitTimeDate22 = (TextView) rootView.findViewById(R.id.exit_time_date_22);
        dateTV23 = (TextView) rootView.findViewById(R.id.date_TV_23);
        entryTimeDate23 = (TextView) rootView.findViewById(R.id.entry_time_date_23);
        exitTimeDate23 = (TextView) rootView.findViewById(R.id.exit_time_date_23);
        dateTV24 = (TextView) rootView.findViewById(R.id.date_TV_24);
        entryTimeDate24 = (TextView) rootView.findViewById(R.id.entry_time_date_24);
        exitTimeDate24 = (TextView) rootView.findViewById(R.id.exit_time_date_24);
        dateTV25 = (TextView) rootView.findViewById(R.id.date_TV_25);
        entryTimeDate25 = (TextView) rootView.findViewById(R.id.entry_time_date_25);
        exitTimeDate25 = (TextView) rootView.findViewById(R.id.exit_time_date_25);
        dateTV26 = (TextView) rootView.findViewById(R.id.date_TV_26);
        entryTimeDate26 = (TextView) rootView.findViewById(R.id.entry_time_date_26);
        exitTimeDate26 = (TextView) rootView.findViewById(R.id.exit_time_date_26);
        dateTV27 = (TextView) rootView.findViewById(R.id.date_TV_27);
        entryTimeDate27 = (TextView) rootView.findViewById(R.id.entry_time_date_27);
        exitTimeDate27 = (TextView) rootView.findViewById(R.id.exit_time_date_27);
        dateTV28 = (TextView) rootView.findViewById(R.id.date_TV_28);
        entryTimeDate28 = (TextView) rootView.findViewById(R.id.entry_time_date_28);
        exitTimeDate28 = (TextView) rootView.findViewById(R.id.exit_time_date_28);
        tableRow28 = (TableRow) rootView.findViewById(R.id.table_row_28);
        dateTV29 = (TextView) rootView.findViewById(R.id.date_TV_29);
        entryTimeDate29 = (TextView) rootView.findViewById(R.id.entry_time_date_29);
        exitTimeDate29 = (TextView) rootView.findViewById(R.id.exit_time_date_29);
        tableRow29 = (TableRow) rootView.findViewById(R.id.table_row_29);
        dateTV30 = (TextView) rootView.findViewById(R.id.date_TV_30);
        entryTimeDate30 = (TextView) rootView.findViewById(R.id.entry_time_date_30);
        exitTimeDate30 = (TextView) rootView.findViewById(R.id.exit_time_date_30);
        tableRow30 = (TableRow) rootView.findViewById(R.id.table_row_30);
        dateTV31 = (TextView) rootView.findViewById(R.id.date_TV_31);
        entryTimeDate31 = (TextView) rootView.findViewById(R.id.entry_time_date_31);
        exitTimeDate31 = (TextView) rootView.findViewById(R.id.exit_time_date_31);
        tableRow31 = (TableRow) rootView.findViewById(R.id.table_row_31);
        table = (TableLayout) rootView.findViewById(R.id.table);
    }
}
