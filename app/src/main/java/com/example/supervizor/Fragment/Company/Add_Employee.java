package com.example.supervizor.Fragment.Company;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.kinda.alert.KAlertDialog;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class Add_Employee extends Fragment implements View.OnClickListener {
    Button parmanent_btn, probational_btn;
    Button add_employee_btn;
    EditText name_ET;
    EditText designation_ET;
    EditText email_ET;
    EditText joindate_ET;
    EditText salary_ET;
    EditText contact_perid_number;
    Spinner spinner_year_month;
    String job_status = "Permanent";
    String userID_company;
    String email_company;
    String password_company;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;


    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    int year;
    int month;
    int day;
    Calendar calendar;

    DatePickerDialog datePickerDialog;

    String employee_password="123456";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);


        return inflater.inflate(R.layout.add_employee_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);


        parmanent_btn.setOnClickListener(this);
        probational_btn.setOnClickListener(this);
        add_employee_btn.setOnClickListener(this);
        joindate_ET.setOnClickListener(this);

//get password from database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               password_company = dataSnapshot.child("company_list").child(userID_company)
                        .child("company_password")
                        .getValue(String.class);
               Toasty.info(getContext(),password_company,Toasty.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//get password from database End

    }

    private void initialize(View view) {
        parmanent_btn = view.findViewById(R.id.parmanent_btn_ID);
        probational_btn = view.findViewById(R.id.probationary_btn_ID);

        name_ET = view.findViewById(R.id.Employee_name_ET_ID);
        designation_ET = view.findViewById(R.id.Employee_designation_ET_ID);
        email_ET = view.findViewById(R.id.Employee_email_ET_ID);
        joindate_ET = view.findViewById(R.id.Employee_join_Date_ET_ID);
        salary_ET = view.findViewById(R.id.Employee_salary_ET_ID);
        contact_perid_number = view.findViewById(R.id.Employee_contact_pried_number_ET_ID);
        spinner_year_month = view.findViewById(R.id.Employee_spinner_year_month);
        add_employee_btn = view.findViewById(R.id.add_Employee_button_ID);

        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser();
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID_company = user.getUid();
        email_company = user.getEmail();
        Toasty.info(getContext(), userID_company).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parmanent_btn_ID:
                job_status = parmanent_btn.getText().toString();
                parmanent_btn.setBackground(getResources().getDrawable(R.drawable.button_background_employee_type_dark));
                probational_btn.setBackground(getResources().getDrawable(R.drawable.button_background_employee_type));
                break;
            case R.id.probationary_btn_ID:
                job_status = probational_btn.getText().toString();
                probational_btn.setBackground(getResources().getDrawable(R.drawable.button_background_employee_type_dark));
                parmanent_btn.setBackground(getResources().getDrawable(R.drawable.button_background_employee_type));
                break;
            case R.id.Employee_join_Date_ET_ID:
                CallDatePicker();
                break;
            case R.id.add_Employee_button_ID:

                if (!CheckInternet.isInternet(getContext())){
                    Toasty.info(getContext(),"Internet Error").show();
                    return;
                }

                String employee_name = name_ET.getText().toString();
                String employee_designation = designation_ET.getText().toString();
                String employee_email = email_ET.getText().toString();
                String employee_join_Date = joindate_ET.getText().toString();
                String employee_salary = salary_ET.getText().toString();
                String employee_contact_priodNumber = contact_perid_number.getText().toString();
                String employee_month_year = spinner_year_month.getSelectedItem().toString();

                if (employee_name.isEmpty()) {
                    name_ET.requestFocus();
                    name_ET.setError("Employee Name?");
                    return;
                }
                if (employee_designation.isEmpty()) {
                    designation_ET.requestFocus();
                    designation_ET.setError("Employee Designation?");
                    return;
                }
                if (employee_email.isEmpty()) {
                    email_ET.requestFocus();
                    email_ET.setError("Employee Email?");
                    return;
                }
                if (employee_join_Date.isEmpty()) {
                    joindate_ET.requestFocus();
                    joindate_ET.setError("Join Date ?");
                    return;
                }
                if (employee_salary.isEmpty()) {
                    salary_ET.requestFocus();
                    salary_ET.setError("salary ?");
                    return;
                }
                if (employee_contact_priodNumber.isEmpty()) {
                    contact_perid_number.requestFocus();
                    contact_perid_number.setError("contact duration?");
                    return;
                }

                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
                kAlertDialog.setTitleText("Loading......");
                kAlertDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(employee_email, employee_password)
                        .addOnCompleteListener(getActivity(), task -> {
                            kAlertDialog.setTitleText("User Creating......");
                            if (task.isSuccessful()) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String userid = user.getUid();
                                if (userid != null) {
                                    kAlertDialog.setTitleText("User Created......");

                                    FirebaseAuth.getInstance().signOut();
                     //re login by company

             //get password from database
                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            password_company = dataSnapshot.child("company_list").child(userID_company)
                                                    .child("company_password")
                                                    .getValue(String.class);
                                            firebaseAuth.signInWithEmailAndPassword(email_company,password_company)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                // Sign in success, update UI with the signed-in user's information
                                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                                userID_company=user.getUid();
                                                            }
                                                        }
                                                    });
                                            //re login by company END
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                                kAlertDialog.setTitleText("Uploading information......");

                                AddEmployee_PojoClass addEmployee_pojoClass
                                        = new AddEmployee_PojoClass(employee_name, employee_designation,
                                        employee_email, employee_join_Date, employee_salary,
                                        employee_password, job_status,
                                        employee_contact_priodNumber, employee_month_year,
                                        userID_company, userid,
                                        "null", "Employee");
                             //save value under company user id
                                databaseReference.child("employee_list_by_company").child(userID_company)
                                        .child(userid).setValue(addEmployee_pojoClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                                //save in employee section
                                databaseReference.child("employee_list")
                                        .child(userid).setValue(addEmployee_pojoClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        kAlertDialog.dismiss();
                                        Toasty.info(getContext(),"User Added").show();
                                        name_ET.setText("");
                                        designation_ET.setText("");
                                        email_ET.setText("");
                                        joindate_ET.setText("");
                                        salary_ET.setText("");
                                        contact_perid_number.setText("");
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(getContext(), e.getMessage()).show();
                    }
                });

                break;
            default:
        }
    }

    //    Showing Date picker popup
    private void CallDatePicker() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                joindate_ET.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }


    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Add Employee");
    }
}
