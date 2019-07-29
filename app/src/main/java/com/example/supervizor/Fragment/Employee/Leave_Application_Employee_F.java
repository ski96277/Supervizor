package com.example.supervizor.Fragment.Employee;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.supervizor.Activity.EmployeeMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class Leave_Application_Employee_F extends Fragment implements View.OnClickListener {

    protected EditText leaveTitleETID;
    protected EditText leaveDescriptionETID;
    protected TextView leaveStartTimeTVID;
    protected TextView leaveENDTimeTVID;
    protected Button LeaveSubmitBtnID;
    private AddEmployee_PojoClass addEmployee_pojoClass;

    int year;
    int month;
    int day;
    Calendar calendar;

    DatePickerDialog datePickerDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leave_application_employee_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.leave_start_Time_TV_ID) {
            callDate_Start_Method();

        } else if (view.getId() == R.id.leave_END_Time_TV_ID) {
            callDate_END_Method();


        } else if (view.getId() == R.id.submit_leave_btn_ID) {
//save value to Database
            saveValueToDatabase(view);
        }
    }

    private void saveValueToDatabase(View view) {

        if (!CheckInternet.isInternet(getContext())) {
            Toasty.info(getContext(), "check Internet Connection").show();
            return;
        }
//show loading
        KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Sending Information");
        kAlertDialog.show();


        String leave_Title = leaveTitleETID.getText().toString();
        String description = leaveDescriptionETID.getText().toString();
        String startDate = leaveStartTimeTVID.getText().toString();
        String endDate = leaveENDTimeTVID.getText().toString();

        if (leave_Title.isEmpty()) {
            leaveTitleETID.requestFocus();
            leaveTitleETID.setError("Title ?");
            return;
        }
        if (description.isEmpty()) {
            leaveDescriptionETID.requestFocus();
            leaveDescriptionETID.setError("Description ?");
            return;
        }
        if (startDate.isEmpty()) {
            leaveStartTimeTVID.requestFocus();
            return;
        }
        if (endDate.isEmpty()) {
            leaveENDTimeTVID.requestFocus();
            return;
        }

        Check_User_information check_user_information = new Check_User_information();
        String user_ID_employee = check_user_information.getUserID();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get value from Database
                addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_ID_employee).getValue(AddEmployee_PojoClass.class);


                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate current_Date = LocalDate.now();
//save value to Database
                LeaveApplication_PojoClass leaveApplication_pojoClass = new LeaveApplication_PojoClass(user_ID_employee,
                        addEmployee_pojoClass.getCompany_User_id(),
                        leave_Title, description, startDate, endDate,current_Date.toString() ,false);

                databaseReference.child("leave_application").child(addEmployee_pojoClass.getCompany_User_id())
                        .child(addEmployee_pojoClass.getEmployee_User_id()).setValue(leaveApplication_pojoClass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.setTitleText("Uploaded");
                                kAlertDialog.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(KAlertDialog kAlertDialog) {
                                        kAlertDialog.dismissWithAnimation();
                                    }
                                });
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void callDate_Start_Method() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                leaveStartTimeTVID.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void callDate_END_Method() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                leaveENDTimeTVID.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void initView(View rootView) {
        leaveTitleETID = (EditText) rootView.findViewById(R.id.leave_title_ET_ID);
        leaveDescriptionETID = (EditText) rootView.findViewById(R.id.leave_description_ET_ID);
        leaveStartTimeTVID = (TextView) rootView.findViewById(R.id.leave_start_Time_TV_ID);
        leaveStartTimeTVID.setOnClickListener(Leave_Application_Employee_F.this);
        leaveENDTimeTVID = (TextView) rootView.findViewById(R.id.leave_END_Time_TV_ID);
        leaveENDTimeTVID.setOnClickListener(Leave_Application_Employee_F.this);
        LeaveSubmitBtnID = (Button) rootView.findViewById(R.id.submit_leave_btn_ID);
        LeaveSubmitBtnID.setOnClickListener(Leave_Application_Employee_F.this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Leave application");
    }
}
