package com.example.supervizor.Fragment.Company;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kinda.alert.KAlertDialog;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Registartion_page_3 extends Fragment {
    Button information_signup_delay_button;
    Button information_signup_working_button_ID;
    Button signup_btn;
    EditText workingDays_ET;
    EditText delayCount_ET;

    String company_name;
    String company_location;
    String company_contact_number;
    String company_email;
    String company_password;
    String company_entry_time;
    String company_exit_time;
    String company_penalty_time;

    String filePath;
    String logo_download_url=null;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registartion_page_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);

        Bundle bundle = getArguments();
        filePath = bundle.getString("filePath");

        SignUp_Pojo signUp_pojo = (SignUp_Pojo) bundle.getSerializable("signUp_pojo_obj_2");

        company_name = signUp_pojo.getCompany_name();
        company_location = signUp_pojo.getCompany_location();
        company_contact_number = signUp_pojo.getCompany_contact_number();
        company_email = signUp_pojo.getCompany_email();
        company_password = signUp_pojo.getCompany_password();
        company_entry_time = signUp_pojo.getCompany_entry_time();
        company_exit_time = signUp_pojo.getCompany_exit_time();
        company_penalty_time = signUp_pojo.getCompany_penalty_time();

//working days info start
        information_signup_working_button_ID.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Info")
                    .setMessage("Enter how many days in a month will be counted for generating salary")
                    .setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });
//working days info End

//delay days info
        information_signup_delay_button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Info")
                    .setMessage("Enter how many delays will be counted as one day penalty")
                    .setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });
        //delay days info End

        signup_btn.setOnClickListener(v -> {


            String working_days = workingDays_ET.getText().toString();
            String delay_count = delayCount_ET.getText().toString();

            if (working_days.isEmpty()) {
                workingDays_ET.requestFocus();
                workingDays_ET.setError("Set Working Days");
                return;
            }
            if (delay_count.isEmpty()) {
                delayCount_ET.requestFocus();
                delayCount_ET.setError("Set delay count");
                return;
            }

      /*      SignUp_Pojo signUp_pojo1 = new SignUp_Pojo(company_name, company_location, company_contact_number
                    , company_email, company_password, company_entry_time, company_exit_time,
                    company_penalty_time, working_days, delay_count, "Company");

            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_name());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_location());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_contact_number());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_email());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_password());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_entry_time());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_exit_time());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_penalty_time());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_working_day());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getCompany_daley_count());
            Log.e("Registration page 3", "onClick: " + signUp_pojo1.getUser_type());
*/

            if (!CheckInternet.isInternet(getContext())) {
                Toasty.error(getContext(), "Internet Error").show();
                return;

            }
            if (CheckInternet.isInternet(getContext())) {
                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
                kAlertDialog.setTitleText("Creating Account...");
                kAlertDialog.show();

                // Write a message to the database
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("company_list");
                mAuth = FirebaseAuth.getInstance();

                mAuth.createUserWithEmailAndPassword(company_email, company_password)
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toasty.success(getContext(), "Account Created").show();
                                kAlertDialog.setTitleText("Wait some Moment");

                                user = mAuth.getCurrentUser();
//Upload Image to server Start
                                storageReference.child("images").child(user.getUid()).child(company_name)
                                .putFile(Uri.parse(filePath)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String downloadLink = uri.toString();
                                                logo_download_url = downloadLink;

                                                kAlertDialog.setContentText("information uploading");

                                                SignUp_Pojo signUp_pojo1 = new SignUp_Pojo(company_name, company_location, company_contact_number
                                                        , company_email, company_password, company_entry_time, company_exit_time,
                                                        company_penalty_time, working_days, delay_count, "Company", user.getUid(),logo_download_url);
                                                myRef.child(user.getUid()).setValue(signUp_pojo1).addOnSuccessListener(aVoid -> {
                                                    kAlertDialog.dismiss();
                                                    startActivity(new Intent(getContext(), CompanyMainActivity.class)
                                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                                                });

                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toasty.error(getContext(), "logo is not uploaded").show();
                                            }
                                        });

//Upload Image to server END
//
/*
                                kAlertDialog.setContentText("information uploading");

                                SignUp_Pojo signUp_pojo1 = new SignUp_Pojo(company_name, company_location, company_contact_number
                                        , company_email, company_password, company_entry_time, company_exit_time,
                                        company_penalty_time, working_days, delay_count, "Company", user.getUid(),logo_download_url);
                                myRef.child(user.getUid()).setValue(signUp_pojo1).addOnSuccessListener(aVoid -> {
                                    kAlertDialog.dismiss();
                                    startActivity(new Intent(getContext(), CompanyMainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                                });*/
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getActivity(), "Authentication failed."+task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                kAlertDialog.dismiss();
//                                updateUI(null);
                            }

                            // ...
                        });
            }

        });


    }

    private void initialize(View view) {
        information_signup_working_button_ID = view.findViewById(R.id.information_signup_working_days_button_ID);

        information_signup_delay_button = view.findViewById(R.id.information_signup_delay_count_button_ID);
        signup_btn = view.findViewById(R.id.sign_up_btn);

        workingDays_ET = view.findViewById(R.id.working_days_ET_ID);
        delayCount_ET = view.findViewById(R.id.delay_count_ET_ID);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

}
