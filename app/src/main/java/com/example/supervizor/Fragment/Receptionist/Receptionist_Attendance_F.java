package com.example.supervizor.Fragment.Receptionist;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.supervizor.Activity.ReceptionistMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Receptionist_Attendance_F extends Fragment {

    protected View rootView;
    protected TextView nameTVAttendance;
    protected TextView designationTVIDAttandence;
    private CircleImageView profileImage;
    private Spinner monthSpinnerIDReceptionAttendance;
    private Spinner yearSpinnerIDReceptionAttendance;
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
    private TextView entryTimeDate4;
    private TextView exitTimeDate4;
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
    private TableRow tableRow29;
    private TextView dateTV30;
    private TextView entryTimeDate30;
    private TextView exitTimeDate30;
    private TableRow tableRow30;
    private TextView dateTV31;
    private TextView entryTimeDate31;
    private TextView exitTimeDate31;
    private TableRow tableRow31;
    private TableLayout table;

    private DatabaseReference databaseReference;
    Check_User_information check_user_information;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.receptionist_attendance_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        Spinner month_spinner = view.findViewById(R.id.month_spinner_ID_Reception_Attendance);
        Spinner year_spinner = view.findViewById(R.id.year_spinner_ID_Reception_Attendance);

        month_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);
        year_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);

        String[] month_array = {"January", "February", "March", "April", "May", "june", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter_month =
                new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, month_array);
        adapter_month.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(adapter_month);

        //SET current month
        Calendar cal = Calendar.getInstance();
        String current_month = month_array[cal.get(Calendar.MONTH)];
        int selectionPosition = adapter_month.getPosition(current_month);
        month_spinner.setSelection(selectionPosition);

        String[] year_array = {"2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        ArrayAdapter<String> adapter_year =
                new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, year_array);
        adapter_year.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(adapter_year);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(check_user_information.getUserID()).getValue(AddEmployee_PojoClass.class);

                String name = addEmployee_pojoClass.getEmployee_name();
                String designation = addEmployee_pojoClass.getEmployee_designation();
                String image_link = addEmployee_pojoClass.getEmployee_profile_image_link();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("company_user_ID",addEmployee_pojoClass.getCompany_User_id());
                editor.apply();

                nameTVAttendance.setText(name);
                designationTVIDAttandence.setText(designation);

                if (!image_link.equals("null")) {

                    Picasso.get().load(Uri.parse(image_link)).into(profileImage);
                } else {
                    profileImage.setImageResource(R.drawable.profile);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((ReceptionistMainActivity) getActivity())
                .setActionBarTitle("Attendance");
    }

    private void initView(View rootView) {
        profileImage = (CircleImageView) rootView.findViewById(R.id.profile_image);
        monthSpinnerIDReceptionAttendance = (Spinner) rootView.findViewById(R.id.month_spinner_ID_Reception_Attendance);
        yearSpinnerIDReceptionAttendance = (Spinner) rootView.findViewById(R.id.year_spinner_ID_Reception_Attendance);
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

        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        nameTVAttendance = (TextView) rootView.findViewById(R.id.name_TV_attendance);
        designationTVIDAttandence = (TextView) rootView.findViewById(R.id.designation_TV_ID_attandence);

    }


    private void getAttendenceValue(String month, String year) {

        entryTimeDate1.setText("..");
        entryTimeDate2.setText("..");
        entryTimeDate3.setText("..");
        entryTimeDate4.setText("..");
        entryTimeDate5.setText("..");
        entryTimeDate6.setText("..");
        entryTimeDate7.setText("..");
        entryTimeDate8.setText("..");
        entryTimeDate9.setText("..");
        entryTimeDate10.setText("..");
        entryTimeDate11.setText("..");
        entryTimeDate12.setText("..");
        entryTimeDate13.setText("..");
        entryTimeDate14.setText("..");
        entryTimeDate15.setText("..");
        entryTimeDate16.setText("..");
        entryTimeDate17.setText("..");
        entryTimeDate18.setText("..");
        entryTimeDate19.setText("..");
        entryTimeDate20.setText("..");
        entryTimeDate21.setText("..");
        entryTimeDate22.setText("..");
        entryTimeDate23.setText("..");
        entryTimeDate24.setText("..");
        entryTimeDate25.setText("..");
        entryTimeDate26.setText("..");
        entryTimeDate27.setText("..");
        entryTimeDate28.setText("..");
        entryTimeDate29.setText("..");
        entryTimeDate30.setText("..");
        entryTimeDate31.setText("..");


        exitTimeDate1.setText("..");
        exitTimeDate2.setText("..");
        exitTimeDate3.setText("..");
        exitTimeDate4.setText("..");
        exitTimeDate5.setText("..");
        exitTimeDate6.setText("..");
        exitTimeDate7.setText("..");
        exitTimeDate8.setText("..");
        exitTimeDate9.setText("..");
        exitTimeDate10.setText("..");
        exitTimeDate11.setText("..");
        exitTimeDate12.setText("..");
        exitTimeDate13.setText("..");
        exitTimeDate14.setText("..");
        exitTimeDate15.setText("..");
        exitTimeDate16.setText("..");
        exitTimeDate17.setText("..");
        exitTimeDate18.setText("..");
        exitTimeDate19.setText("..");
        exitTimeDate20.setText("..");
        exitTimeDate21.setText("..");
        exitTimeDate22.setText("..");
        exitTimeDate23.setText("..");
        exitTimeDate24.setText("..");
        exitTimeDate25.setText("..");
        exitTimeDate26.setText("..");
        exitTimeDate27.setText("..");
        exitTimeDate28.setText("..");
        exitTimeDate29.setText("..");
        exitTimeDate30.setText("..");
        exitTimeDate31.setText("..");

        int monthNumber;
        int i = 0;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String entry_time_company =preferences.getString("company_entry_time","");
        String company_exit_time =preferences.getString("company_exit_time","");
        String company_penalty_time =preferences.getString("company_penalty_time","");



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
        DatabaseReference databaseReference2 = firebaseDatabase.getReference();

        //first data (date) start here
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear the date_list for reduce the data replete

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String company_user_ID = preferences.getString("company_user_ID", "");

                for (DataSnapshot snapshot : dataSnapshot
                        .child("Attendance")
                        .child(company_user_ID)
                        .child(check_user_information.getUserID())
                        .child(year).child(String.valueOf(monthNumber))
                        .getChildren()) {

                    String date = snapshot.getKey();

                    databaseReference2.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                            String company_user_ID = preferences.getString("company_user_ID", "");

                            String entryTime = dataSnapshot
                                    .child("Attendance")
                                    .child(company_user_ID)
                                    .child(check_user_information.getUserID())
                                    .child(year)
                                    .child(String.valueOf(monthNumber))
                                    .child(date)
                                    .child("Entry")
                                    .child("entryTime")
                                    .getValue(String.class);
                            String exitTime = dataSnapshot
                                    .child("Attendance")
                                    .child(company_user_ID)
                                    .child(check_user_information.getUserID())
                                    .child(year)
                                    .child(String.valueOf(monthNumber))
                                    .child(date)
                                    .child("Exit")
                                    .child("exitTime")
                                    .getValue(String.class);


                            if (date.equals(String.valueOf(1))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate1.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate1.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate1.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate1.setTextColor(Color.parseColor("#FFFFFF"));
                                }

                                entryTimeDate1.setText(entryTime);
                                exitTimeDate1.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(2))) {
                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate2.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate2.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate2.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate2.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate2.setText(entryTime);
                                exitTimeDate2.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(3))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate3.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate3.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate3.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate3.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate3.setText(entryTime);
                                exitTimeDate3.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(4))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate4.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate4.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate4.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate4.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate4.setText(entryTime);
                                exitTimeDate4.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(5))) {


                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate5.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate5.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate5.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate5.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate5.setText(entryTime);
                                exitTimeDate5.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(6))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate6.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate6.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate6.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate6.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate6.setText(entryTime);
                                exitTimeDate6.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(7))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate7.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate7.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate7.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate7.setTextColor(Color.parseColor("#FFFFFF"));
                                }

                                entryTimeDate7.setText(entryTime);
                                exitTimeDate7.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(8))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate8.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate8.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate8.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate8.setTextColor(Color.parseColor("#FFFFFF"));
                                }

                                entryTimeDate8.setText(entryTime);
                                exitTimeDate8.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(9))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate9.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate9.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate9.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate9.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate9.setText(entryTime);
                                exitTimeDate9.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(10))) {


                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate10.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate10.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate10.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate10.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate10.setText(entryTime);
                                exitTimeDate10.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(11))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate11.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate11.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate11.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate11.setTextColor(Color.parseColor("#FFFFFF"));
                                }

                                entryTimeDate11.setText(entryTime);
                                exitTimeDate11.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(12))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate12.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate12.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate12.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate12.setTextColor(Color.parseColor("#FFFFFF"));
                                }

                                entryTimeDate12.setText(entryTime);
                                exitTimeDate12.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(13))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate13.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate13.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate13.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate13.setTextColor(Color.parseColor("#FFFFFF"));
                                }



                                entryTimeDate13.setText(entryTime);
                                exitTimeDate13.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(14))) {


                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate14.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate14.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate14.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate14.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate14.setText(entryTime);
                                exitTimeDate14.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(15))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate15.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate15.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate15.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate15.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate15.setText(entryTime);
                                exitTimeDate15.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(16))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate16.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate16.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate16.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate16.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate16.setText(entryTime);
                                exitTimeDate16.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(17))) {


                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate17.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate17.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate17.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate17.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate17.setText(entryTime);
                                exitTimeDate17.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(18))) {


                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate18.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate18.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate18.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate18.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate18.setText(entryTime);
                                exitTimeDate18.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(19))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate19.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate19.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate19.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate19.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate19.setText(entryTime);
                                entryTimeDate19.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(20))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate20.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate20.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate20.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate20.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate20.setText(entryTime);
                                exitTimeDate20.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(21))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate21.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate21.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate21.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate21.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate21.setText(entryTime);
                                exitTimeDate21.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(22))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate22.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate22.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate22.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate22.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate22.setText(entryTime);
                                exitTimeDate22.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(23))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate23.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate23.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate23.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate23.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate23.setText(entryTime);
                                exitTimeDate23.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(24))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate24.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate24.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate24.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate24.setTextColor(Color.parseColor("#FFFFFF"));
                                }



                                entryTimeDate24.setText(entryTime);
                                exitTimeDate24.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(25))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate25.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate25.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate25.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate25.setTextColor(Color.parseColor("#FFFFFF"));
                                }



                                entryTimeDate25.setText(entryTime);
                                exitTimeDate25.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(26))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate26.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate26.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate26.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate26.setTextColor(Color.parseColor("#FFFFFF"));
                                }



                                entryTimeDate26.setText(entryTime);
                                exitTimeDate26.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(27))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate27.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate27.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate27.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate27.setTextColor(Color.parseColor("#FFFFFF"));
                                }



                                entryTimeDate27.setText(entryTime);
                                exitTimeDate27.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(28))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate28.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate28.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate28.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate28.setTextColor(Color.parseColor("#FFFFFF"));
                                }


                                entryTimeDate28.setText(entryTime);
                                exitTimeDate28.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(29))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate29.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate29.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate29.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate29.setTextColor(Color.parseColor("#FFFFFF"));
                                }



                                entryTimeDate29.setText(entryTime);
                                exitTimeDate29.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(30))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    exitTimeDate30.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate30.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    exitTimeDate30.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    exitTimeDate30.setTextColor(Color.parseColor("#FFFFFF"));
                                }



                                entryTimeDate30.setText(entryTime);
                                exitTimeDate30.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(31))) {



                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                } else {
                                    entryTimeDate31.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate31.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                }

                                if (time1_exit.isBefore(time2_exit)){
                                    entryTimeDate31.setBackgroundColor(Color.parseColor("#E61A5F"));
                                    entryTimeDate31.setTextColor(Color.parseColor("#FFFFFF"));
                                }



                                entryTimeDate31.setText(entryTime);
                                exitTimeDate31.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toasty.error(getContext(), "Data Error", Toasty.LENGTH_SHORT).show();
                        }
                    });//second Data coming end
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getContext(), "date not Found", Toasty.LENGTH_SHORT).show();
            }
        });
//first data (date) END


    }


}
