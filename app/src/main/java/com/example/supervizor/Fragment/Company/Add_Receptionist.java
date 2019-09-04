package com.example.supervizor.Fragment.Company;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.AddReceptionist_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.kinda.alert.KAlertDialog;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class Add_Receptionist extends Fragment {

    EditText name_receptionnist;
    EditText designation_receptionnist;
    EditText email_receptionnist;
    CardView joinDate_receptionnist_layout;
    EditText joinDate_receptionnist;
    EditText salary_receptionnist;
    EditText contact_period_number_receptionnist;
    Spinner spinner_receptionist;
    Button add_receptionist;

    int year;
    int month;
    int day;
    Calendar calendar;

    DatePickerDialog datePickerDialog;

    String password_Receptionist = "123456";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);

        return inflater.inflate(R.layout.add_receptionist, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        //call date Alert View
        joinDate_receptionnist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallDatePicker();
            }
        });
        //add receptionist
        add_receptionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReceptionist(v);
            }
        });
    }

    private void addReceptionist(View view) {

        String name_Reception = name_receptionnist.getText().toString();
        String designation_Receptionist = designation_receptionnist.getText().toString();
        String email_Receptionist = email_receptionnist.getText().toString();
        String join_Receptionist = joinDate_receptionnist.getText().toString();
        String salary_Receptionist = salary_receptionnist.getText().toString();
        String contact_period_Receptionist = contact_period_number_receptionnist.getText().toString();
        String spinner_month_Year = spinner_receptionist.getSelectedItem().toString();

        if (name_Reception.isEmpty()) {
            name_receptionnist.requestFocus();
            name_receptionnist.setError("name ?");
            return;
        }
        if (designation_Receptionist.isEmpty()) {
            designation_receptionnist.requestFocus();
            designation_receptionnist.setError("Designation ?");
            return;
        }
        if (email_Receptionist.isEmpty()) {
            email_receptionnist.requestFocus();
            email_receptionnist.setError("Email ?");
            return;
        }
        if (join_Receptionist.isEmpty()) {
            joinDate_receptionnist.requestFocus();
            joinDate_receptionnist.setError("Date ? ");
            return;
        }
        if (salary_Receptionist.isEmpty()) {
            salary_receptionnist.requestFocus();
            salary_receptionnist.setError("salary ?");
        }
        if (contact_period_Receptionist.isEmpty()) {
            contact_period_number_receptionnist.requestFocus();
            contact_period_number_receptionnist.setError("Contract Period ?");
            return;
        }
        if (!CheckInternet.isInternet(getContext())) {
            Toasty.info(getContext(), "Internet Error.....").show();
            return;
        }
        Check_User_information check_user_information = new Check_User_information();
        String email_company = check_user_information.getEmail();
        String uID_company = check_user_information.getUserID();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        //log out
        mAuth.signOut();

        KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Creating User");
        kAlertDialog.show();

        mAuth.createUserWithEmailAndPassword(email_Receptionist, password_Receptionist)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            kAlertDialog.setTitleText("User Created...");

                            FirebaseUser firebaseUser1 = FirebaseAuth.getInstance().getCurrentUser();
                            String user_ID_receptionist=firebaseUser1.getUid();


                            AddEmployee_PojoClass addEmployee_pojoClass=new AddEmployee_PojoClass(name_Reception,designation_Receptionist,email_Receptionist,
                                    join_Receptionist,salary_Receptionist,password_Receptionist,contact_period_Receptionist,contact_period_Receptionist,spinner_month_Year,check_user_information.getUserID(),user_ID_receptionist,"null","receptionist");

                            AddReceptionist_PojoClass addReceptionist_pojoClass = new AddReceptionist_PojoClass(
                                    name_Reception, designation_Receptionist, email_Receptionist,
                                    join_Receptionist, salary_Receptionist, contact_period_Receptionist,
                                    spinner_month_Year, "null", password_Receptionist,
                                    uID_company,user_ID_receptionist);

                            kAlertDialog.setTitleText("information Uploading...");

                            //information upload under company

                            myRef.child("employee_list_by_company").child(check_user_information.getUserID())
                                    .child(user_ID_receptionist).setValue(addEmployee_pojoClass);
                            //save information in employee section
                            myRef.child("employee_list")
                                    .child(user_ID_receptionist).setValue(addEmployee_pojoClass);

                            myRef.child("receptionist_list_by_company")
                                    .child(uID_company).child(user_ID_receptionist)
                                    .setValue(addReceptionist_pojoClass)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            kAlertDialog.setTitleText("information Uploaded..");
                                        }
                                    });
                            //information upload in receptionist
                            myRef.child("receptionist_list")
                                    .child(firebaseUser1.getUid())
                                    .setValue(addReceptionist_pojoClass)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mAuth.signOut();
                                            //re login by admin

                                            //get password from database
                                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String password_company = dataSnapshot.child("company_list").child(uID_company)
                                                            .child("company_password")
                                                            .getValue(String.class);
                                                    mAuth.signInWithEmailAndPassword(email_company, password_company)
                                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if (task.isSuccessful()) {
                                                                        kAlertDialog.dismiss();

                                                                        name_receptionnist.setText("");
//                                                                        designation_receptionnist.setText("");
                                                                        email_receptionnist.setText("");
                                                                        joinDate_receptionnist.setText("");
                                                                        salary_receptionnist.setText("");
                                                                        contact_period_number_receptionnist.setText("");

                                                                        Toasty.info(getContext(), "Receptionist Added").show();
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
                                    });
                        } else {

                            Toasty.error(getContext(), "Error creating user").show();
                        }
                    }
                });


    }

    private void initialize(View view) {
        name_receptionnist = view.findViewById(R.id.Receptionist_name_ET_ID);
        designation_receptionnist = view.findViewById(R.id.Receptionist_designation_ET_ID);
        email_receptionnist = view.findViewById(R.id.Receptionist_email_ET_ID);
        joinDate_receptionnist_layout = view.findViewById(R.id.Receptionist_join_Date_ET_ID_layout);
        joinDate_receptionnist = view.findViewById(R.id.Receptionist_join_Date_ET_ID);
        salary_receptionnist = view.findViewById(R.id.Receptionist_salary_ET_ID);
        contact_period_number_receptionnist = view.findViewById(R.id.Receptionist_contact_pried_number_ET_ID);
        spinner_receptionist = view.findViewById(R.id.Receptionist_spinner_year_month);
        add_receptionist = view.findViewById(R.id.add_Receptionist_button_ID);

    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Add Receptionist");
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
                joinDate_receptionnist.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
