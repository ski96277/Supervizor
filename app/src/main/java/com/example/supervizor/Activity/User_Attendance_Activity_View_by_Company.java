package com.example.supervizor.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.squareup.picasso.Picasso;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class User_Attendance_Activity_View_by_Company extends AppCompatActivity {
    ScrollView linearLayout_attendance;
    Bitmap bitmap;
    Spinner month_spinner;
    Spinner year_spinner;
    TextView name_TV;
    TextView designation_TV;
    TableLayout tableLayout;
    CircleImageView profileImage;
    Check_User_information check_user_information;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    String user_id;


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
    private TableRow tableRow29;
    private TextView dateTV30;
    private TextView entryTimeDate30;
    private TextView exitTimeDate30;
    private TableRow tableRow30;
    private TextView dateTV31;
    private TextView entryTimeDate31;
    private TextView exitTimeDate31;
    private TableRow tableRow31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__attendance___view_by__company);
        getSupportActionBar().setTitle("Attendance");
        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();

        if (!CheckInternet.isInternet(User_Attendance_Activity_View_by_Company.this)) {
            Toasty.info(User_Attendance_Activity_View_by_Company.this, "Check Internet Connection").show();
            return;
        }

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");


        month_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);
        year_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);

        String[] month_array = {"January", "February", "March", "April", "May", "june", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter_month =
                new ArrayAdapter<>(User_Attendance_Activity_View_by_Company.this, R.layout.simple_spinner_item, month_array);
        adapter_month.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(adapter_month);

        String[] year_array = {"2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        ArrayAdapter<String> adapter_year =
                new ArrayAdapter<>(User_Attendance_Activity_View_by_Company.this, R.layout.simple_spinner_item, year_array);
        adapter_year.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(adapter_year);

//SET current month
        Calendar cal = Calendar.getInstance();
        String current_month = month_array[cal.get(Calendar.MONTH)];
        int selectionPosition = adapter_month.getPosition(current_month);
        month_spinner.setSelection(selectionPosition);
//SET current month End

        check_user_information = new Check_User_information();
//SET current month End
        if (!CheckInternet.isInternet(User_Attendance_Activity_View_by_Company.this)) {
            Toasty.info(User_Attendance_Activity_View_by_Company.this, "Check Internet connection").show();
            return;
        }
        //get user Information

        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                user_id = getArguments().getString("user_id");

                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list")
                        .child(user_id)
                        .getValue(AddEmployee_PojoClass.class);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(User_Attendance_Activity_View_by_Company.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("company_user_ID", addEmployee_pojoClass.getCompany_User_id());
                editor.apply();

                if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                    Picasso.get().load(Uri.parse(addEmployee_pojoClass.getEmployee_profile_image_link()))
                            .into(profileImage);
                } else {
                    profileImage.setImageResource(R.drawable.profile_white);
                }
                name_TV.setText(addEmployee_pojoClass.getEmployee_name());
                designation_TV.setText(addEmployee_pojoClass.getEmployee_designation());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//get user Information END


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

        exitTimeDate1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate1.setTextColor(Color.parseColor("#000000"));
        entryTimeDate1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate1.setTextColor(Color.parseColor("#000000"));

        entryTimeDate2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate2.setTextColor(Color.parseColor("#000000"));
        exitTimeDate2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate2.setTextColor(Color.parseColor("#000000"));

        entryTimeDate3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate3.setTextColor(Color.parseColor("#000000"));
        exitTimeDate3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate3.setTextColor(Color.parseColor("#000000"));

        entryTimeDate4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate4.setTextColor(Color.parseColor("#000000"));
        exitTimeDate4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate4.setTextColor(Color.parseColor("#000000"));

        entryTimeDate5.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate5.setTextColor(Color.parseColor("#000000"));
        exitTimeDate5.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate5.setTextColor(Color.parseColor("#000000"));

        entryTimeDate6.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate6.setTextColor(Color.parseColor("#000000"));
        exitTimeDate6.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate6.setTextColor(Color.parseColor("#000000"));

        entryTimeDate7.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate7.setTextColor(Color.parseColor("#000000"));
        exitTimeDate7.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate7.setTextColor(Color.parseColor("#000000"));

        entryTimeDate8.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate8.setTextColor(Color.parseColor("#000000"));
        exitTimeDate8.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate8.setTextColor(Color.parseColor("#000000"));

        entryTimeDate9.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate9.setTextColor(Color.parseColor("#000000"));
        exitTimeDate9.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate9.setTextColor(Color.parseColor("#000000"));

        entryTimeDate10.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate10.setTextColor(Color.parseColor("#000000"));
        exitTimeDate10.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate10.setTextColor(Color.parseColor("#000000"));

        entryTimeDate11.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate11.setTextColor(Color.parseColor("#000000"));
        exitTimeDate11.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate11.setTextColor(Color.parseColor("#000000"));

        entryTimeDate12.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate12.setTextColor(Color.parseColor("#000000"));
        exitTimeDate12.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate12.setTextColor(Color.parseColor("#000000"));

        entryTimeDate13.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate13.setTextColor(Color.parseColor("#000000"));
        exitTimeDate13.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate13.setTextColor(Color.parseColor("#000000"));

        entryTimeDate14.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate14.setTextColor(Color.parseColor("#000000"));
        exitTimeDate14.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate14.setTextColor(Color.parseColor("#000000"));

        entryTimeDate15.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate15.setTextColor(Color.parseColor("#000000"));
        exitTimeDate15.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate15.setTextColor(Color.parseColor("#000000"));

        entryTimeDate16.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate16.setTextColor(Color.parseColor("#000000"));
        exitTimeDate16.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate16.setTextColor(Color.parseColor("#000000"));

        entryTimeDate17.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate17.setTextColor(Color.parseColor("#000000"));
        exitTimeDate17.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate17.setTextColor(Color.parseColor("#000000"));

        entryTimeDate18.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate18.setTextColor(Color.parseColor("#000000"));
        exitTimeDate18.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate18.setTextColor(Color.parseColor("#000000"));

        entryTimeDate19.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate19.setTextColor(Color.parseColor("#000000"));
        exitTimeDate19.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate19.setTextColor(Color.parseColor("#000000"));

        entryTimeDate20.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate20.setTextColor(Color.parseColor("#000000"));
        exitTimeDate20.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate20.setTextColor(Color.parseColor("#000000"));

        entryTimeDate21.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate21.setTextColor(Color.parseColor("#000000"));
        exitTimeDate21.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate21.setTextColor(Color.parseColor("#000000"));

        entryTimeDate22.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate22.setTextColor(Color.parseColor("#000000"));
        exitTimeDate22.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate22.setTextColor(Color.parseColor("#000000"));

        entryTimeDate23.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate23.setTextColor(Color.parseColor("#000000"));
        exitTimeDate23.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate23.setTextColor(Color.parseColor("#000000"));

        entryTimeDate24.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate24.setTextColor(Color.parseColor("#000000"));
        exitTimeDate24.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate24.setTextColor(Color.parseColor("#000000"));

        entryTimeDate25.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate25.setTextColor(Color.parseColor("#000000"));
        exitTimeDate25.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate25.setTextColor(Color.parseColor("#000000"));

        entryTimeDate26.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate26.setTextColor(Color.parseColor("#000000"));
        exitTimeDate26.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate26.setTextColor(Color.parseColor("#000000"));

        entryTimeDate27.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate27.setTextColor(Color.parseColor("#000000"));
        exitTimeDate27.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate27.setTextColor(Color.parseColor("#000000"));

        entryTimeDate28.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate28.setTextColor(Color.parseColor("#000000"));
        exitTimeDate28.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate28.setTextColor(Color.parseColor("#000000"));

        entryTimeDate29.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate29.setTextColor(Color.parseColor("#000000"));
        exitTimeDate29.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate29.setTextColor(Color.parseColor("#000000"));

        entryTimeDate30.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate30.setTextColor(Color.parseColor("#000000"));
        exitTimeDate30.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate30.setTextColor(Color.parseColor("#000000"));

        entryTimeDate31.setBackgroundColor(Color.parseColor("#FFFFFF"));
        entryTimeDate31.setTextColor(Color.parseColor("#000000"));
        exitTimeDate31.setBackgroundColor(Color.parseColor("#FFFFFF"));
        exitTimeDate31.setTextColor(Color.parseColor("#000000"));


        monthNumber = i;
        //first data (date) start here
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear the date_list for reduce the data replete

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(User_Attendance_Activity_View_by_Company.this);
                String company_user_ID = preferences.getString("company_user_ID", "");

                String entry_time_company = preferences.getString("company_entry_time", "");
                String company_exit_time = preferences.getString("company_exit_time", "");
                String company_penalty_time = preferences.getString("company_penalty_time", "");

//                user_id = getArguments().getString("user_id");


                for (DataSnapshot snapshot : dataSnapshot
                        .child("Attendance")
                        .child(company_user_ID)
                        .child(user_id)
                        .child(year).child(String.valueOf(monthNumber))
                        .getChildren()) {

                    String date = snapshot.getKey();

                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {

                        @TargetApi(Build.VERSION_CODES.O)
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(User_Attendance_Activity_View_by_Company.this);
                            String company_user_ID = preferences.getString("company_user_ID", "");

//                            user_id = getArguments().getString("user_id");

                            String entryTime = dataSnapshot
                                    .child("Attendance")
                                    .child(company_user_ID)
                                    .child(user_id)
                                    .child(year)
                                    .child(String.valueOf(monthNumber))
                                    .child(date)
                                    .child("Entry")
                                    .child("entryTime")
                                    .getValue(String.class);
                            String exitTime = dataSnapshot
                                    .child("Attendance")
                                    .child(company_user_ID)
                                    .child(user_id)
                                    .child(year)
                                    .child(String.valueOf(monthNumber))
                                    .child(date)
                                    .child("Exit")
                                    .child("exitTime")
                                    .getValue(String.class);


                            if (date.equals(String.valueOf(1))) {


                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate1.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate1.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }


                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate1.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate1.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }
                                entryTimeDate1.setText(entryTime);
                                exitTimeDate1.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(2))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate2.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate2.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate2.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate2.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate2.setText(entryTime);
                                exitTimeDate2.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(3))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate3.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate3.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate3.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate3.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate3.setText(entryTime);
                                exitTimeDate3.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(4))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate4.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate4.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (entryTime != null) {


                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate4.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate4.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate4.setText(entryTime);
                                exitTimeDate4.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(5))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate5.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate5.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate5.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate5.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate5.setText(entryTime);
                                exitTimeDate5.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(6))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate6.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate6.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {

                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate6.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate6.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate6.setText(entryTime);
                                exitTimeDate6.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(7))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate7.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate7.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate7.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate7.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }
                                entryTimeDate7.setText(entryTime);
                                exitTimeDate7.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(8))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate8.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate8.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate8.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate8.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }


                                entryTimeDate8.setText(entryTime);
                                exitTimeDate8.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(9))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate9.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate9.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate9.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate9.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate9.setText(entryTime);
                                exitTimeDate9.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(10))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate10.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate10.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate10.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate10.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }
                                entryTimeDate10.setText(entryTime);
                                exitTimeDate10.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(11))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate11.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate11.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {

                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate11.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate11.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate11.setText(entryTime);
                                exitTimeDate11.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(12))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate12.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate12.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {

                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate12.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate12.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate12.setText(entryTime);
                                exitTimeDate12.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(13))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate13.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate13.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate13.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate13.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate13.setText(entryTime);
                                exitTimeDate13.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(14))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate14.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate14.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate14.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate14.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }
                                entryTimeDate14.setText(entryTime);
                                exitTimeDate14.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(15))) {


                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate15.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate15.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate15.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate15.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate15.setText(entryTime);
                                exitTimeDate15.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(16))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate16.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate16.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {

                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate16.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate16.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate16.setText(entryTime);
                                exitTimeDate16.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(17))) {


                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate17.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate17.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate17.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate17.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate17.setText(entryTime);
                                exitTimeDate17.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(18))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate18.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate18.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate18.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate18.setTextColor(Color.parseColor("#FFFFFF"));
                                    }

                                }


                                entryTimeDate18.setText(entryTime);
                                exitTimeDate18.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(19))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate19.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate19.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {


                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate19.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate19.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate19.setText(entryTime);
                                exitTimeDate19.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(20))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate20.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate20.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate20.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate20.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }
                                entryTimeDate20.setText(entryTime);
                                exitTimeDate20.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(21))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate21.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate21.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate21.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate21.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate21.setText(entryTime);
                                exitTimeDate21.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(22))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);


                                if (entryTime != null) {

                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate22.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate22.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {

                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate22.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate22.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }


                                entryTimeDate22.setText(entryTime);
                                exitTimeDate22.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(23))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate23.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate23.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate23.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate23.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate23.setText(entryTime);
                                exitTimeDate23.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(24))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate24.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate24.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {


                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate24.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate24.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }


                                entryTimeDate24.setText(entryTime);
                                exitTimeDate24.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(25))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);


                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate25.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate25.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);
                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate25.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate25.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }

                                entryTimeDate25.setText(entryTime);
                                exitTimeDate25.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(26))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate26.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate26.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate26.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate26.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }


                                entryTimeDate26.setText(entryTime);
                                exitTimeDate26.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(27))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate27.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate27.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate27.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate27.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }


                                entryTimeDate27.setText(entryTime);
                                exitTimeDate27.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(28))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate28.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate28.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }


                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate28.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate28.setTextColor(Color.parseColor("#FFFFFF"));

                                    } else {

                                    }
                                }
                                entryTimeDate28.setText(entryTime);
                                exitTimeDate28.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(29))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate29.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate29.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate29.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate29.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }


                                entryTimeDate29.setText(entryTime);
                                exitTimeDate29.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(30))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime != null) {
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);

                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate30.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate30.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (exitTime != null) {
                                    LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                    LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);

                                    if (time1_exit.isBefore(time2_exit)) {
                                        exitTimeDate30.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        exitTimeDate30.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                                }
                                entryTimeDate30.setText(entryTime);
                                exitTimeDate30.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(31))) {

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                                if (entryTime!=null){
                                    LocalTime time1_entry = LocalTime.parse(entryTime, timeFormatter);
                                    LocalTime time2_entry = LocalTime.parse(company_penalty_time, timeFormatter);
                                    if (time1_entry.isBefore(time2_entry)) {
//                                    Toast.makeText(getContext(), "time1 < time2", Toast.LENGTH_SHORT).show();
                                    } else {
                                        entryTimeDate31.setBackgroundColor(Color.parseColor("#E61A5F"));
                                        entryTimeDate31.setTextColor(Color.parseColor("#FFFFFF"));
//                                    Toast.makeText(getContext(), "time1 > time2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                              if (exitTime!=null){
                                  LocalTime time1_exit = LocalTime.parse(exitTime, timeFormatter);
                                  LocalTime time2_exit = LocalTime.parse(company_exit_time, timeFormatter);


                                  if (time1_exit.isBefore(time2_exit)) {
                                      exitTimeDate31.setBackgroundColor(Color.parseColor("#E61A5F"));
                                      exitTimeDate31.setTextColor(Color.parseColor("#FFFFFF"));
                                  }

                              }

                                entryTimeDate31.setText(entryTime);
                                exitTimeDate31.setText(exitTime);
                                Log.e("TAG", "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toasty.error(User_Attendance_Activity_View_by_Company.this, "Data Error", Toasty.LENGTH_SHORT).show();
                        }
                    });//second Data coming end
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(User_Attendance_Activity_View_by_Company.this, "date not Found", Toasty.LENGTH_SHORT).show();
            }
        });
//first data (date) END
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight;
        int convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // create a File object for the parent directory
        File wallpaperDirectory = new File("/sdcard/OfficeManagement_APP/");
// have the object build the directory structure, if needed.
        wallpaperDirectory.mkdirs();

        // write the document content
        String targetPdf = "/sdcard/OfficeManagement_APP/" + designation_TV.getText().toString() + "_" + year_spinner.getSelectedItem().toString() + "_" + month_spinner.getSelectedItem().toString() + ".pdf";

        File filePath = new File(targetPdf);

        try {
            document.writeTo(new FileOutputStream(filePath));
            // close the document
            document.close();
            Toasty.success(User_Attendance_Activity_View_by_Company.this, "PDF is created!!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(User_Attendance_Activity_View_by_Company.this, targetPdf, Toast.LENGTH_SHORT).show();

            openGeneratedPDF();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG - ", "createPdf: " + e.toString());
            Toasty.error(User_Attendance_Activity_View_by_Company.this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }


    }

    private void openGeneratedPDF() {
        File file = new File("/sdcard/OfficeManagement_APP/" + designation_TV.getText().toString() + "_" + year_spinner.getSelectedItem().toString() + "_" + month_spinner.getSelectedItem().toString() + ".pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(User_Attendance_Activity_View_by_Company.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void initView() {
        dateTV1 = (TextView) findViewById(R.id.date_TV_1);
        entryTimeDate1 = (TextView) findViewById(R.id.entry_time_date_1);
        exitTimeDate1 = (TextView) findViewById(R.id.exit_time_date_1);
        dateTV2 = (TextView) findViewById(R.id.date_TV_2);
        entryTimeDate2 = (TextView) findViewById(R.id.entry_time_date_2);
        exitTimeDate2 = (TextView) findViewById(R.id.exit_time_date_2);
        dateTV3 = (TextView) findViewById(R.id.date_TV_3);
        entryTimeDate3 = (TextView) findViewById(R.id.entry_time_date_3);
        exitTimeDate3 = (TextView) findViewById(R.id.exit_time_date_3);
        dateTV4 = (TextView) findViewById(R.id.date_TV_4);
        entryTimeDate4 = (TextView) findViewById(R.id.entry_time_date_4);
        exitTimeDate4 = (TextView) findViewById(R.id.exit_time_date_4);
        dateTV5 = (TextView) findViewById(R.id.date_TV_5);
        entryTimeDate5 = (TextView) findViewById(R.id.entry_time_date_5);
        exitTimeDate5 = (TextView) findViewById(R.id.exit_time_date_5);
        dateTV6 = (TextView) findViewById(R.id.date_TV_6);
        entryTimeDate6 = (TextView) findViewById(R.id.entry_time_date_6);
        exitTimeDate6 = (TextView) findViewById(R.id.exit_time_date_6);
        dateTV7 = (TextView) findViewById(R.id.date_TV_7);
        entryTimeDate7 = (TextView) findViewById(R.id.entry_time_date_7);
        exitTimeDate7 = (TextView) findViewById(R.id.exit_time_date_7);
        dateTV8 = (TextView) findViewById(R.id.date_TV_8);
        entryTimeDate8 = (TextView) findViewById(R.id.entry_time_date_8);
        exitTimeDate8 = (TextView) findViewById(R.id.exit_time_date_8);
        dateTV9 = (TextView) findViewById(R.id.date_TV_9);
        entryTimeDate9 = (TextView) findViewById(R.id.entry_time_date_9);
        exitTimeDate9 = (TextView) findViewById(R.id.exit_time_date_9);
        dateTV10 = (TextView) findViewById(R.id.date_TV_10);
        entryTimeDate10 = (TextView) findViewById(R.id.entry_time_date_10);
        exitTimeDate10 = (TextView) findViewById(R.id.exit_time_date_10);
        dateTV11 = (TextView) findViewById(R.id.date_TV_11);
        entryTimeDate11 = (TextView) findViewById(R.id.entry_time_date_11);
        exitTimeDate11 = (TextView) findViewById(R.id.exit_time_date_11);
        dateTV12 = (TextView) findViewById(R.id.date_TV_12);
        entryTimeDate12 = (TextView) findViewById(R.id.entry_time_date_12);
        exitTimeDate12 = (TextView) findViewById(R.id.exit_time_date_12);
        dateTV13 = (TextView) findViewById(R.id.date_TV_13);
        entryTimeDate13 = (TextView) findViewById(R.id.entry_time_date_13);
        exitTimeDate13 = (TextView) findViewById(R.id.exit_time_date_13);
        dateTV14 = (TextView) findViewById(R.id.date_TV_14);
        entryTimeDate14 = (TextView) findViewById(R.id.entry_time_date_14);
        exitTimeDate14 = (TextView) findViewById(R.id.exit_time_date_14);
        dateTV15 = (TextView) findViewById(R.id.date_TV_15);
        entryTimeDate15 = (TextView) findViewById(R.id.entry_time_date_15);
        exitTimeDate15 = (TextView) findViewById(R.id.exit_time_date_15);
        dateTV16 = (TextView) findViewById(R.id.date_TV_16);
        entryTimeDate16 = (TextView) findViewById(R.id.entry_time_date_16);
        exitTimeDate16 = (TextView) findViewById(R.id.exit_time_date_16);
        dateTV17 = (TextView) findViewById(R.id.date_TV_17);
        entryTimeDate17 = (TextView) findViewById(R.id.entry_time_date_17);
        exitTimeDate17 = (TextView) findViewById(R.id.exit_time_date_17);
        dateTV18 = (TextView) findViewById(R.id.date_TV_18);
        entryTimeDate18 = (TextView) findViewById(R.id.entry_time_date_18);
        exitTimeDate18 = (TextView) findViewById(R.id.exit_time_date_18);
        dateTV19 = (TextView) findViewById(R.id.date_TV_19);
        entryTimeDate19 = (TextView) findViewById(R.id.entry_time_date_19);
        exitTimeDate19 = (TextView) findViewById(R.id.exit_time_date_19);
        dateTV20 = (TextView) findViewById(R.id.date_TV_20);
        entryTimeDate20 = (TextView) findViewById(R.id.entry_time_date_20);
        exitTimeDate20 = (TextView) findViewById(R.id.exit_time_date_20);
        dateTV21 = (TextView) findViewById(R.id.date_TV_21);
        entryTimeDate21 = (TextView) findViewById(R.id.entry_time_date_21);
        exitTimeDate21 = (TextView) findViewById(R.id.exit_time_date_21);
        dateTV22 = (TextView) findViewById(R.id.date_TV_22);
        entryTimeDate22 = (TextView) findViewById(R.id.entry_time_date_22);
        exitTimeDate22 = (TextView) findViewById(R.id.exit_time_date_22);
        dateTV23 = (TextView) findViewById(R.id.date_TV_23);
        entryTimeDate23 = (TextView) findViewById(R.id.entry_time_date_23);
        exitTimeDate23 = (TextView) findViewById(R.id.exit_time_date_23);
        dateTV24 = (TextView) findViewById(R.id.date_TV_24);
        entryTimeDate24 = (TextView) findViewById(R.id.entry_time_date_24);
        exitTimeDate24 = (TextView) findViewById(R.id.exit_time_date_24);
        dateTV25 = (TextView) findViewById(R.id.date_TV_25);
        entryTimeDate25 = (TextView) findViewById(R.id.entry_time_date_25);
        exitTimeDate25 = (TextView) findViewById(R.id.exit_time_date_25);
        dateTV26 = (TextView) findViewById(R.id.date_TV_26);
        entryTimeDate26 = (TextView) findViewById(R.id.entry_time_date_26);
        exitTimeDate26 = (TextView) findViewById(R.id.exit_time_date_26);
        dateTV27 = (TextView) findViewById(R.id.date_TV_27);
        entryTimeDate27 = (TextView) findViewById(R.id.entry_time_date_27);
        exitTimeDate27 = (TextView) findViewById(R.id.exit_time_date_27);
        dateTV28 = (TextView) findViewById(R.id.date_TV_28);
        entryTimeDate28 = (TextView) findViewById(R.id.entry_time_date_28);
        exitTimeDate28 = (TextView) findViewById(R.id.exit_time_date_28);
        tableRow28 = (TableRow) findViewById(R.id.table_row_28);
        dateTV29 = (TextView) findViewById(R.id.date_TV_29);
        entryTimeDate29 = (TextView) findViewById(R.id.entry_time_date_29);
        exitTimeDate29 = (TextView) findViewById(R.id.exit_time_date_29);
        tableRow29 = (TableRow) findViewById(R.id.table_row_29);
        dateTV30 = (TextView) findViewById(R.id.date_TV_30);
        entryTimeDate30 = (TextView) findViewById(R.id.entry_time_date_30);
        exitTimeDate30 = (TextView) findViewById(R.id.exit_time_date_30);
        tableRow30 = (TableRow) findViewById(R.id.table_row_30);
        dateTV31 = (TextView) findViewById(R.id.date_TV_31);
        entryTimeDate31 = (TextView) findViewById(R.id.entry_time_date_31);
        exitTimeDate31 = (TextView) findViewById(R.id.exit_time_date_31);
        tableRow31 = (TableRow) findViewById(R.id.table_row_31);

        month_spinner = findViewById(R.id.month_spinner_ID_user__attendance_company);
        year_spinner = findViewById(R.id.year_spinner_ID_user__attendance_company);
        name_TV = findViewById(R.id.name_attendance_company);
        designation_TV = findViewById(R.id.designation_attendance_company);
        profileImage = findViewById(R.id.profile_image_attendance_company);

        tableLayout = findViewById(R.id.table);
        linearLayout_attendance = findViewById(R.id.attendance_view_layout);
        databaseReference3 = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference2 = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_user_attendance_activity_view_by_company, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pdf_generate_to_this_employee:
                KAlertDialog kAlertDialog = new KAlertDialog(User_Attendance_Activity_View_by_Company.this);
                kAlertDialog.setTitleText("Alert !!");

                kAlertDialog.setContentText("Do You want to create pdf ?");
                kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {

                    Log.d("size", " " + tableLayout.getWidth() + "  " + tableLayout.getWidth());
                    bitmap = loadBitmapFromView(tableLayout, tableLayout.getWidth(), tableLayout.getHeight());
                    createPdf();
                    kAlertDialog1.dismiss();
                });
                kAlertDialog.setCancelClickListener(kAlertDialog12 -> kAlertDialog.dismiss());
                kAlertDialog.show();


                break;
        }
        return false;
    }
}
