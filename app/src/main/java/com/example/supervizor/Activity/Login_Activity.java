package com.example.supervizor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
   private Button button_login;
   private TextView forget_pass_TV;
    private  TextView register_company;
    private EditText email_ET;
    private EditText password_ET;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private KAlertDialog kAlertDialog;

    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
//hide action bar
        getSupportActionBar().hide();
//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        button_login = findViewById(R.id.login_btn_ID);
        register_company = findViewById(R.id.register_company_TV_ID);

        button_login.setOnClickListener(this);
forget_pass_TV.setOnClickListener(this);

        register_company.setOnClickListener(this);

    }

    private void initialize() {
        email_ET = findViewById(R.id.email_login_ET_ID);
        forget_pass_TV=findViewById(R.id.forget_password_TV_ID);
        password_ET = findViewById(R.id.password_login_ET_ID);

// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    //set on click on the button_login Start
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_password_TV_ID:
                startActivity(new Intent(this,Forget_Password.class));
                break;

            case R.id.login_btn_ID:
                String email_st = email_ET.getText().toString();
                String pass_st = password_ET.getText().toString();

                if (email_st.isEmpty()) {
                    //animation
                    YoYo.with(Techniques.Tada)
                            .duration(100)
                            .repeat(1)
                            .playOn(findViewById(R.id.email_login_ET_ID));

                    email_ET.requestFocus();
                    email_ET.setError("Email ?");
                    return;
                }
                if (pass_st.isEmpty()) {
                    //animation
                    YoYo.with(Techniques.Tada)
                            .duration(100)
                            .repeat(1)
                            .playOn(findViewById(R.id.password_login_ET_ID));

                    password_ET.requestFocus();
                    password_ET.setError("Password ?");
                    return;
                }
                //check Internet Connection is ok
                if (!CheckInternet.isInternet(getApplicationContext())) {

 //vibrator Start
                     vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
                     vibrator.vibrate(1000);
//vibrator End
                    Toasty.error(getApplicationContext(), "Internet Error").show();
                    return;
                }
                kAlertDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
                kAlertDialog.setCancelable(false);
                kAlertDialog.setTitleText("Loading...");
                kAlertDialog.show();


                mAuth.signInWithEmailAndPassword(email_st, pass_st)
                        .addOnCompleteListener(this, task -> {

                            if (task.isSuccessful()) {

                                Toasty.success(getApplicationContext(), "Success").show();
                                kAlertDialog.setTitleText("Checking user Data");
                                FirebaseUser user = mAuth.getCurrentUser();
                                checkUserType(user.getUid(),pass_st);

                            } else {
                                //animation email input
                                YoYo.with(Techniques.Tada)
                                        .duration(100)
                                        .repeat(1)
                                        .playOn(findViewById(R.id.email_login_ET_ID));
                                //animation login input
                                YoYo.with(Techniques.Tada)
                                        .duration(100)
                                        .repeat(1)
                                        .playOn(findViewById(R.id.password_login_ET_ID));
  //animation on login button
                                YoYo.with(Techniques.Tada)
                                        .duration(100)
                                        .repeat(1)
                                        .playOn(findViewById(R.id.login_btn_ID));
//vibrator
                                vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);
//vibrator End

                                Toasty.error(Login_Activity.this, "Login failed.",
                                        Toast.LENGTH_SHORT).show();
                                kAlertDialog.dismiss();

                            }

                            // ...
                        });

                break;
            case R.id.register_company_TV_ID:
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        }
    }
//check usr Type

    private void checkUserType(String uid,String pass_st) {


        firebaseDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean company_true= dataSnapshot.child("company_list").hasChild(uid);

                if (company_true){
                    //set password in data base
                    firebaseDatabase.getReference().child("company_list").child(uid).child("company_password")
                            .setValue(pass_st);

                    startActivity(new Intent(getApplicationContext(), CompanyMainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    kAlertDialog.dismiss();

                    return;
                }

                boolean receptionist_true= dataSnapshot.child("receptionist_list").hasChild(uid);
                if (receptionist_true){

                    startActivity(new Intent(Login_Activity.this,ReceptionistMainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                    //set password in data base
                    firebaseDatabase.getReference().child("receptionist_list").child(uid)
                            .child("password_Receptionist").setValue(pass_st);

//                    Toasty.info(getApplicationContext(),"Receptionist").show();
                    return;

                }

                boolean employee_true= dataSnapshot.child("employee_list").hasChild(uid);
                if (employee_true){

                    //set password in data base
                    firebaseDatabase.getReference().child("employee_list").child(uid)
                            .child("employee_password").setValue(pass_st);

                    startActivity(new Intent(Login_Activity.this,EmployeeMainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));


//                    Toasty.info(getApplicationContext(),"Employee").show();
                    return;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //set on click on the button_login END

    //check the user is login or not
    @Override
    public void onStart() {
        super.onStart();

        kAlertDialog = new KAlertDialog(Login_Activity.this, KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setCancelable(false);
        kAlertDialog.setTitleText("Loading....");
//        kAlertDialog.show();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            kAlertDialog.dismiss();
        } else {
            kAlertDialog.setTitleText("Getting the user Data");
            //check user data when not login
            checkUserType(currentUser.getUid());
        }
//        updateUI(currentUser);
    }

//check user Type without Login
    private void checkUserType(String uid) {

        firebaseDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean company_true= dataSnapshot.child("company_list").hasChild(uid);
                if (company_true){
                    startActivity(new Intent(getApplicationContext(), CompanyMainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    kAlertDialog.dismiss();

                    return;
                }

                boolean receptionist_true= dataSnapshot.child("receptionist_list").hasChild(uid);

                if (receptionist_true){
                    startActivity(new Intent(Login_Activity.this,ReceptionistMainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));

//                    Toasty.info(getApplicationContext(),"Receptionist").show();
                    return;
                }
                boolean employee_true= dataSnapshot.child("employee_list").hasChild(uid);
                if (employee_true){
                    startActivity(new Intent(Login_Activity.this,EmployeeMainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));

//                    Toasty.info(getApplicationContext(),"Employee").show();
                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
