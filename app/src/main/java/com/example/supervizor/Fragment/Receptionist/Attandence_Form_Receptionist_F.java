package com.example.supervizor.Fragment.Receptionist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.Activity.ReceptionistMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class Attandence_Form_Receptionist_F extends Fragment {

    EditText email_ET;
    EditText password_ET;
    Button button_login;

    RadioGroup radioGroup_ID;
    String radio_St = "";
    String email_st_reception;
    String pass_st_reception;
    String user_ID_reception;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    AddEmployee_PojoClass addEmployee_pojoClass;

    private KAlertDialog kAlertDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.attandence_form_receptionist_f, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        Check_User_information check_user_information=new Check_User_information();
       user_ID_reception =check_user_information.getUserID();

        //get selected radio button text
        radioGroup_ID.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.entry_time_RD:
                    radio_St = "Entry Time";
                    break;
                case R.id.exit_time_RD:
                    radio_St = "Exit Time";
                    break;
            }
        });

        //click on login button
        button_login.setOnClickListener(v -> loginFrom_Receptionist(v));


    }

    private void initialize(View view) {

        email_ET = view.findViewById(R.id.email_reception_form_login_ET_ID);
        password_ET = view.findViewById(R.id.password_reception_form_login_ET_ID);
        button_login = view.findViewById(R.id.login_btn_receptionist_form_ID);

        radioGroup_ID = view.findViewById(R.id.radio_group_ID);

        mAuth = FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference();

    }

    public void loginFrom_Receptionist(View v) {

        String email = email_ET.getText().toString();
        String password = password_ET.getText().toString();


        if (radio_St.isEmpty()) {
            Toasty.error(getContext(), "select radio button", Toasty.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            email_ET.requestFocus();
            email_ET.setError("Email ?");
            return;
        }
        if (password.isEmpty()) {
            password_ET.requestFocus();
            password_ET.setError("password ?");
            return;
        }
//check internet connection
        if (!CheckInternet.isInternet(getContext())){
            Toasty.info(getContext(),"Check Internet Error");
            return;
        }
//call login method for attendance
        login(email, password);


    }

    private void login(String email, String password) {
//check the is set automatically in android settings
        if (isTimeAutomatic(getContext())){

            kAlertDialog=new KAlertDialog(getContext(),KAlertDialog.PROGRESS_TYPE);
            kAlertDialog.setTitleText("Loading.....");
            kAlertDialog.show();

            //login for attendance
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Check_User_information check_user_information = new Check_User_information();
                        String user_ID = check_user_information.getUserID();


                        databaseReference.child("employee_list")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        addEmployee_pojoClass = dataSnapshot.child(user_ID).getValue(AddEmployee_PojoClass.class);


                                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                                        int year = calendar.get(Calendar.YEAR);
                                        int month = calendar.get(Calendar.MONTH);
                                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                                        if (radio_St.equals("Entry Time")){

                                            DateFormat df = new SimpleDateFormat("h:mm a");
                                            String time = df.format(Calendar.getInstance().getTime());
                                            databaseReference.child("Attendance").child(addEmployee_pojoClass.getCompany_User_id()).child(user_ID)
                                                    .child(String.valueOf(year)).child(String.valueOf(month + 1))
                                                    .child(String.valueOf(day)).child("Entry").child("entryTime").setValue(time)
                                            .addOnCompleteListener(task1 -> {

                                                Toasty.success(getContext(), "submitted Entry Time", Toast.LENGTH_SHORT).show();
                                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                                kAlertDialog.setTitleText("Done..");
                                                kAlertDialog.setConfirmClickListener(kAlertDialog -> {

                                                    kAlertDialog.dismissWithAnimation();
                                                    //sign out new login
                                                    mAuth.signOut();

                                                    //get data from receptionist54ex
                                                    addEmployee_pojoClass = dataSnapshot.child(user_ID_reception).getValue(AddEmployee_PojoClass.class);
                                                    email_st_reception = addEmployee_pojoClass.getEmployee_email();
                                                    pass_st_reception=addEmployee_pojoClass.getEmployee_password();
//login again
                                                    mAuth.signInWithEmailAndPassword(email_st_reception,pass_st_reception).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                                            if (task.isComplete()){
                                                                Log.e("TAG", "onComplete:---- Login again " );
//                                                                Toasty.info(getContext(),"Login again").show();
                                                            }

                                                        }
                                                    });


                                                });
                                            });
                                        }
                                        if (radio_St.equals("Exit Time")){

                                            DateFormat df = new SimpleDateFormat("h:mm a");
                                            String time = df.format(Calendar.getInstance().getTime());
                                            databaseReference.child("Attendance").child(addEmployee_pojoClass.getCompany_User_id())
                                                    .child(user_ID).child(String.valueOf(year)).child(String.valueOf(month + 1))
                                                    .child(String.valueOf(day)).child("Exit").child("exitTime").setValue(time)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {


                                                    Toasty.success(getContext() ,"submitted Exit Time", Toast.LENGTH_SHORT).show();

                                                    kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                                    kAlertDialog.setTitleText("Done..");   kAlertDialog.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(KAlertDialog kAlertDialog) {

                                                            kAlertDialog.dismissWithAnimation();

                                                            //sign out new login
                                                            mAuth.signOut();

                                                            //get data from receptionist54ex
                                                            addEmployee_pojoClass = dataSnapshot.child(user_ID_reception).getValue(AddEmployee_PojoClass.class);
                                                            email_st_reception = addEmployee_pojoClass.getEmployee_email();
                                                            pass_st_reception=addEmployee_pojoClass.getEmployee_password();
//login again
                                                            mAuth.signInWithEmailAndPassword(email_st_reception,pass_st_reception).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {

                                                                    if (task.isComplete()){
                                                                        Log.e("TAG", "onComplete:---- Login again " );
//                                                                Toasty.info(getContext(),"Login again").show();
                                                                    }

                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }else {
                        Toasty.info(getContext(),"Login Error").show();
                        kAlertDialog.dismissWithAnimation();
                    }
                }
            });

        }else {
            Toasty.info(getContext(),"Set Time Auto").show();
//open auto time settings
            startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
        }

       }

    //check the time & Date is set auto
    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((ReceptionistMainActivity) getActivity())
                .setActionBarTitle("Attendance Form");
    }
}
