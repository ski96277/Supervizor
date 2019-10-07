package com.example.supervizor.Activity.CompanyActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kinda.alert.KAlertDialog;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Update_Company_Profile extends AppCompatActivity implements View.OnClickListener {

    protected TextView emailSaveInUpdateProfile;
    protected LinearLayout entryTimeLayout;
    protected LinearLayout exitTimeLayout;
    protected LinearLayout penaltyTimeLayout;
    protected TextView imageDownloadLinkUpdateProfile;
    protected TextView passwordUpdateProfile;
    protected TextView userIDUpdateProfile;
    protected TextView userTypeUpdateProfile;
    private CircleImageView companyProfileImageUpdate;
    private EditText companyNameETIDUpdate;
    private EditText companyLocationETIDUpdate;
    private EditText companyContactETIDUpdate;
    private TextView entryTimeTVID;
    private TextView exitTimeTVID;
    private TextView penaltyTimeTVID;
    private EditText workingDaysETIDUpdate;
    private EditText delayCountETIDUpdate;
    private Button update_btn_company;

    private Check_User_information check_user_information;
    private DatabaseReference databaseReference;


    FirebaseStorage storage;
    StorageReference storageReference;


    private int CalendarHour, CalendarMinute;
    String format;
    Uri filePath;

    Calendar calendar;
    TimePickerDialog timepickerdialog;
    KAlertDialog loading_data_alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_update__company__profile);
//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();

        if (!CheckInternet.isInternet(getApplicationContext())) {
            Toasty.info(getApplicationContext(), "check Internet Connection").show();
            return;
        }

        loading_data_alert = new KAlertDialog(Update_Company_Profile.this, KAlertDialog.PROGRESS_TYPE);

        loading_data_alert.setTitleText("Loading...");
        loading_data_alert.show();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                SignUp_Pojo signUp_pojo = dataSnapshot.child("company_list").child(check_user_information.getUserID())
                        .getValue(SignUp_Pojo.class);

                if (signUp_pojo != null) {

                    Picasso.get().load(signUp_pojo.getLogo_download_url())
                            .into(companyProfileImageUpdate);
                    companyNameETIDUpdate.setText(signUp_pojo.getCompany_name());
                    companyLocationETIDUpdate.setText(signUp_pojo.getCompany_location());
                    companyContactETIDUpdate.setText(signUp_pojo.getCompany_contact_number());
                    entryTimeTVID.setText(signUp_pojo.getCompany_entry_time());
                    exitTimeTVID.setText(signUp_pojo.getCompany_exit_time());
                    penaltyTimeTVID.setText(signUp_pojo.getCompany_penalty_time());
                    workingDaysETIDUpdate.setText(signUp_pojo.getCompany_working_day());
                    delayCountETIDUpdate.setText(signUp_pojo.getCompany_daley_count());
                    emailSaveInUpdateProfile.setText(signUp_pojo.getCompany_email());

//                    signUp_pojo.getLogo_download_url();
                    imageDownloadLinkUpdateProfile.setText(signUp_pojo.getLogo_download_url());
//                    signUp_pojo.getCompany_password();
                    passwordUpdateProfile.setText(signUp_pojo.getCompany_password());
//                    signUp_pojo.getCompany_user_id();
                    userIDUpdateProfile.setText(signUp_pojo.getCompany_user_id());
//                    signUp_pojo.getUser_type();
                    userTypeUpdateProfile.setText(signUp_pojo.getUser_type());

                    loading_data_alert.dismissWithAnimation();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                loading_data_alert.dismissWithAnimation();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                companyProfileImageUpdate.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.company_profile_image_update) {

            chooseImage();

        }
        if (view.getId() == R.id.update_btn_company) {

            update_company_profile();
        }
        if (view.getId() == R.id.entry_time_layout) {

            calendar = Calendar.getInstance();
            CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
            CalendarMinute = calendar.get(Calendar.MINUTE);

            timepickerdialog = new TimePickerDialog(this,
                    (View, hourOfDay, minute) -> {

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
                            entryTimeTVID.setText(hourOfDay + ":0" + minute + " " + format);

                        } else {
                            entryTimeTVID.setText(hourOfDay + ":" + minute + " " + format);

                        }


                    }, CalendarHour, CalendarMinute, false);
            timepickerdialog.show();

        }
        if (view.getId() == R.id.exit_time_layout) {


            calendar = Calendar.getInstance();
            CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
            CalendarMinute = calendar.get(Calendar.MINUTE);

            timepickerdialog = new TimePickerDialog(this,
                    (View, hourOfDay, minute) -> {

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
                            exitTimeTVID.setText(hourOfDay + ":0" + minute + " " + format);

                        } else {
                            exitTimeTVID.setText(hourOfDay + ":" + minute + " " + format);

                        }

                    }, CalendarHour, CalendarMinute, false);
            timepickerdialog.show();


        }
        if (view.getId() == R.id.penalty_time_layout) {

            calendar = Calendar.getInstance();
            CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
            CalendarMinute = calendar.get(Calendar.MINUTE);

            timepickerdialog = new TimePickerDialog(this,
                    (View, hourOfDay, minute) -> {

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
                            penaltyTimeTVID.setText(hourOfDay + ":0" + minute + " " + format);

                        } else {
                            penaltyTimeTVID.setText(hourOfDay + ":" + minute + " " + format);

                        }

                    }, CalendarHour, CalendarMinute, false);
            timepickerdialog.show();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
    }

    private void update_company_profile() {

        String company_name = companyNameETIDUpdate.getText().toString();
        String company_location = companyLocationETIDUpdate.getText().toString();
        String company_phone = companyContactETIDUpdate.getText().toString();
        String company_entry_time = entryTimeTVID.getText().toString();
        String company_exit_time = exitTimeTVID.getText().toString();
        String company_penalty_time = penaltyTimeTVID.getText().toString();
        String company_working_days = workingDaysETIDUpdate.getText().toString();
        String company_delay_count = delayCountETIDUpdate.getText().toString();

        if (company_name.isEmpty() || company_location.isEmpty()
                || company_phone.isEmpty() || company_entry_time.isEmpty()
                || company_exit_time.isEmpty() || company_penalty_time.isEmpty()
                || company_working_days.isEmpty() || company_delay_count.isEmpty()) {
            Toasty.info(getApplicationContext(), "Fill up the All input field").show();
            return;
        }

        KAlertDialog update_information_alert = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        update_information_alert.setTitleText("Uploading...");
        update_information_alert.show();


        if (filePath == null) {

            SignUp_Pojo signUp_pojo = new SignUp_Pojo(company_name, company_location, company_phone,
                    emailSaveInUpdateProfile.getText().toString(),
                    passwordUpdateProfile.getText().toString(),
                    company_entry_time, company_exit_time,
                    company_penalty_time, company_working_days, company_delay_count,
                    userTypeUpdateProfile.getText().toString(),
                    userIDUpdateProfile.getText().toString(),
                    imageDownloadLinkUpdateProfile.getText().toString());

            databaseReference.child("company_list").child(check_user_information.getUserID())
                    .setValue(signUp_pojo).addOnSuccessListener(aVoid -> {
                update_information_alert.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                update_information_alert.setTitleText("Done..");
                update_information_alert.setConfirmClickListener(kAlertDialog -> startActivity(new Intent(getApplicationContext(), CompanyMainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)));
            });

        }
        //if filepath is empty
        else {

            //Upload Image to server Start
            storageReference.child("images").child(check_user_information.getUserID()).child(company_name)
                    .putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String logo_downloadLink = uri.toString();


                            SignUp_Pojo signUp_pojo = new SignUp_Pojo(company_name, company_location, company_phone,
                                    emailSaveInUpdateProfile.getText().toString(),
                                    passwordUpdateProfile.getText().toString(),
                                    company_entry_time, company_exit_time,
                                    company_penalty_time, company_working_days, company_delay_count,
                                    userTypeUpdateProfile.getText().toString(),
                                    userIDUpdateProfile.getText().toString(),
                                    logo_downloadLink);

                            databaseReference.child("company_list").child(check_user_information.getUserID())
                                    .setValue(signUp_pojo).addOnSuccessListener(aVoid -> {
                                update_information_alert.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                update_information_alert.setTitleText("Done..");
                                update_information_alert.setConfirmClickListener(kAlertDialog -> startActivity(new Intent(getApplicationContext(), CompanyMainActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)));
                            });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    update_information_alert.dismissWithAnimation();
                    Toasty.error(getApplicationContext(),"Upload Failed").show();
                }
            });


        }


    }

    private void initView() {
        companyProfileImageUpdate = (CircleImageView) findViewById(R.id.company_profile_image_update);
        companyNameETIDUpdate = (EditText) findViewById(R.id.company_name_ET_ID_update);
        companyLocationETIDUpdate = (EditText) findViewById(R.id.company_location_ET_ID_update);
        companyContactETIDUpdate = (EditText) findViewById(R.id.company_contact_ET_ID_update);
        entryTimeTVID = (TextView) findViewById(R.id.entry_time_TV_ID);
        exitTimeTVID = (TextView) findViewById(R.id.exit_time_TV_ID);
        penaltyTimeTVID = (TextView) findViewById(R.id.penalty_time_TV_ID);
        workingDaysETIDUpdate = (EditText) findViewById(R.id.working_days_ET_ID_update);
        delayCountETIDUpdate = (EditText) findViewById(R.id.delay_count_ET_ID_update);
        update_btn_company = (Button) findViewById(R.id.update_btn_company);
        update_btn_company.setOnClickListener(Update_Company_Profile.this);
        check_user_information = new Check_User_information();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        emailSaveInUpdateProfile = (TextView) findViewById(R.id.email_save_In_update_profile);
        entryTimeLayout = (LinearLayout) findViewById(R.id.entry_time_layout);
        exitTimeLayout = (LinearLayout) findViewById(R.id.exit_time_layout);
        penaltyTimeLayout = (LinearLayout) findViewById(R.id.penalty_time_layout);
        entryTimeLayout.setOnClickListener(this);
        exitTimeLayout.setOnClickListener(this);
        penaltyTimeLayout.setOnClickListener(this::onClick);
        companyProfileImageUpdate.setOnClickListener(this::onClick);
        imageDownloadLinkUpdateProfile = (TextView) findViewById(R.id.image_download_link_update_profile);
        passwordUpdateProfile = (TextView) findViewById(R.id.password_update_profile);
        userIDUpdateProfile = (TextView) findViewById(R.id.user_ID_update_profile);
        userTypeUpdateProfile = (TextView) findViewById(R.id.user_Type_update_profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }
}
