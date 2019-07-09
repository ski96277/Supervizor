package com.example.supervizor.Fragment.Company;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.R;

import java.util.Calendar;

public class Registartion_page_2 extends Fragment implements View.OnClickListener {

    TextView entry_time_TV;
    TextView exit_time_TV;
    TextView penalty_time_TV;
    Button company_next_btn_2_ID;

    LinearLayout entry_time_layout;
    LinearLayout exit_time_layout;
    LinearLayout penalty_time_layout;

    Fragment fragment;

    String company_name;
    String company_location;
    String company_contact_number;
    String company_email;
    String company_password;

    String filePath;

    private int CalendarHour, CalendarMinute;
    String format;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    Vibrator vibrator;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registartion_page_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        entry_time_layout.setOnClickListener(this);
        exit_time_layout.setOnClickListener(this);
        penalty_time_layout.setOnClickListener(this);
        company_next_btn_2_ID.setOnClickListener(this);

        Bundle bundle = getArguments();
        filePath=bundle.getString("filePath");
        SignUp_Pojo signUp_pojo = (SignUp_Pojo) bundle.getSerializable("signUp_pojo_obj_1");

        company_name = signUp_pojo.getCompany_name();
        company_location = signUp_pojo.getCompany_location();
        company_contact_number = signUp_pojo.getCompany_contact_number();
        company_email = signUp_pojo.getCompany_email();
        company_password = signUp_pojo.getCompany_password();

    }

    private void initialize(View view) {

        company_next_btn_2_ID = view.findViewById(R.id.company_next_btn_2_ID_singup);
        entry_time_layout = view.findViewById(R.id.entry_time_layout);
        exit_time_layout = view.findViewById(R.id.exit_time_layout);
        penalty_time_layout = view.findViewById(R.id.penalty_time_layout);

        entry_time_TV = view.findViewById(R.id.entry_time_TV_ID);
        exit_time_TV = view.findViewById(R.id.exit_time_TV_ID);
        penalty_time_TV = view.findViewById(R.id.penalty_time_TV_ID);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entry_time_layout:

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
                                entry_time_TV.setText(hourOfDay + " : 0" + minute + " " + format);

                            } else {
                                entry_time_TV.setText(hourOfDay + " : " + minute + " " + format);

                            }


                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

                break;
            case R.id.exit_time_layout:

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
                                exit_time_TV.setText(hourOfDay + " : 0" + minute + " " + format);

                            } else {
                                exit_time_TV.setText(hourOfDay + " : " + minute + " " + format);

                            }

                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();


                break;
            case R.id.penalty_time_layout:

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

                            if (minute<10){
                                penalty_time_TV.setText(hourOfDay + " : 0" + minute + " " + format);

                            }else {
                                penalty_time_TV.setText(hourOfDay + " : " + minute + " " + format);

                            }

                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

                break;
            case R.id.company_next_btn_2_ID_singup:

                String entry_Time = entry_time_TV.getText().toString();
                String exit_Time = exit_time_TV.getText().toString();
                String penalty_time = penalty_time_TV.getText().toString();

                if (entry_Time.isEmpty()) {

 //vibrator Start
                    vibrator= (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);
//vibrator End

                    entry_time_TV.requestFocus();
                    entry_time_TV.setError("Set Entry Time");
                    return;
                }
                if (exit_Time.isEmpty()) {

                    //vibrator Start
                    vibrator= (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);
//vibrator End

                    exit_time_TV.requestFocus();
                    exit_time_TV.setError("set exit time");
                    return;
                }
                if (penalty_time.isEmpty()) {

                    //vibrator Start
                    vibrator= (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);
//vibrator End

                    penalty_time_TV.requestFocus();
                    penalty_time_TV.setError("Set Penalty Time");
                    return;
                } else {

                    SignUp_Pojo signUp_pojo = new SignUp_Pojo(company_name, company_location, company_contact_number, company_email, company_password, entry_Time, exit_Time, penalty_time);

                    Bundle bundle = new Bundle();
                    bundle.putString("filePath",filePath);
                    bundle.putSerializable("signUp_pojo_obj_2", signUp_pojo);

                    fragment = new Registartion_page_3();
                    if (fragment != null) {
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.registration_main_screen, fragment);
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }

                }

                break;
        }
    }

}
