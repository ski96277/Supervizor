package com.example.supervizor.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class UpdateProfileByEmployeeActivity extends AppCompatActivity implements View.OnClickListener {


    private CircleImageView profile_image_ID;
    private EditText EmployeeNameETID;
    private EditText EmployeeDesignationETID;
    private EditText EmployeeEmailETID;
    private EditText EmployeePhoneETID;
    private Button updateBtnID;

    private DatabaseReference databaseReference;
    private Check_User_information check_user_information;

    private Uri filePath;

    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_by_employee);
        getSupportActionBar().setTitle("Update Profile");
//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(check_user_information.getUserID()).getValue(AddEmployee_PojoClass.class);


                if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                    Picasso.get().load(Uri.parse(addEmployee_pojoClass.getEmployee_profile_image_link()))
                            .into(profile_image_ID);
                } else {
                    profile_image_ID.setImageResource(R.drawable.profile);
                }
                EmployeeNameETID.setText(addEmployee_pojoClass.getEmployee_name());
                EmployeeDesignationETID.setText(addEmployee_pojoClass.getEmployee_designation());
                EmployeeEmailETID.setText(addEmployee_pojoClass.getEmployee_email());
                if (addEmployee_pojoClass.getUser_phone_number() != null) {
                    EmployeePhoneETID.setText(addEmployee_pojoClass.getUser_phone_number());
                }
                updateBtnID.setOnClickListener(v -> {

                    String name = EmployeeNameETID.getText().toString();
                    String designation = EmployeeDesignationETID.getText().toString();
                    String email = EmployeeEmailETID.getText().toString();
                    String phone = EmployeePhoneETID.getText().toString();

                    if (addEmployee_pojoClass.getEmployee_profile_image_link() == null && filePath == null) {
                        KAlertDialog kAlertDialog = new KAlertDialog(UpdateProfileByEmployeeActivity.this, KAlertDialog.ERROR_TYPE);
                        kAlertDialog.setCancelable(false);
                        kAlertDialog.setTitleText("Select user image");
                        kAlertDialog.show();
                        kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                                    kAlertDialog1.dismiss();
                                    chooseImage();
                                }
                        );
                        return;
                    }


                    if (name.isEmpty() || designation.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                        Toasty.info(UpdateProfileByEmployeeActivity.this, "fill the all input field").show();
                        return;
                    }

                    KAlertDialog kAlertDialog = new KAlertDialog(UpdateProfileByEmployeeActivity.this, KAlertDialog.PROGRESS_TYPE);
                    kAlertDialog.setTitleText("Uploading..");
                    kAlertDialog.show();

//                    Toast.makeText(getContext(), ""+filePath, Toast.LENGTH_SHORT).show();
                    if (filePath != null) {
                        storageReference.child("userImage")
                                .child(addEmployee_pojoClass.getEmployee_User_id())
                                .child(addEmployee_pojoClass.getEmployee_name())
                                .putFile(Uri.parse(String.valueOf(filePath)))
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String downloadLink = uri.toString();

                                                AddEmployee_PojoClass addEmployee_pojoClass1 = new AddEmployee_PojoClass(
                                                        name, designation, email,
                                                        addEmployee_pojoClass.getEmployee_joinDate(),
                                                        addEmployee_pojoClass.getEmployee_salary(),
                                                        addEmployee_pojoClass.getEmployee_password(),
                                                        addEmployee_pojoClass.getEmployee_status(),
                                                        addEmployee_pojoClass.getEmployee_Contact_period_number(),
                                                        addEmployee_pojoClass.getEmployee_Contact_period_year_OR_month(),
                                                        addEmployee_pojoClass.getCompany_User_id(),
                                                        addEmployee_pojoClass.getEmployee_User_id(),
                                                        downloadLink,
                                                        addEmployee_pojoClass.getUser_type(), phone);


                                                databaseReference.child("employee_list_by_company")
                                                        .child(addEmployee_pojoClass.getCompany_User_id())
                                                        .child(addEmployee_pojoClass.getEmployee_User_id())
                                                        .setValue(addEmployee_pojoClass1);

                                                databaseReference.child("employee_list")
                                                        .child(addEmployee_pojoClass.getEmployee_User_id())
                                                        .setValue(addEmployee_pojoClass1)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                startActivity(new Intent(UpdateProfileByEmployeeActivity.this, EmployeeMainActivity.class)
                                                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


                                                            }
                                                        });
                                            }
                                        });
                                    }
                                });
                    } else {

                        AddEmployee_PojoClass addEmployee_pojoClass1 = new AddEmployee_PojoClass(
                                name, designation, email,
                                addEmployee_pojoClass.getEmployee_joinDate(),
                                addEmployee_pojoClass.getEmployee_salary(),
                                addEmployee_pojoClass.getEmployee_password(),
                                addEmployee_pojoClass.getEmployee_status(),
                                addEmployee_pojoClass.getEmployee_Contact_period_number(),
                                addEmployee_pojoClass.getEmployee_Contact_period_year_OR_month(),
                                addEmployee_pojoClass.getCompany_User_id(),
                                addEmployee_pojoClass.getEmployee_User_id(),
                                addEmployee_pojoClass.getEmployee_profile_image_link(),
                                addEmployee_pojoClass.getUser_type(), phone);


                        databaseReference.child("employee_list_by_company")
                                .child(addEmployee_pojoClass.getCompany_User_id())
                                .child(addEmployee_pojoClass.getEmployee_User_id())
                                .setValue(addEmployee_pojoClass1);

                        databaseReference.child("employee_list")
                                .child(addEmployee_pojoClass.getEmployee_User_id())
                                .setValue(addEmployee_pojoClass1)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(UpdateProfileByEmployeeActivity.this, EmployeeMainActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile_image_ID) {

            chooseImage();

        }
    }

    private void initView() {
        profile_image_ID = (CircleImageView) findViewById(R.id.profile_image_ID);
        profile_image_ID.setOnClickListener(this);
        EmployeeNameETID = (EditText) findViewById(R.id.Employee_name_ET_ID);
        EmployeeDesignationETID = (EditText) findViewById(R.id.Employee_designation_ET_ID);
        EmployeeEmailETID = (EditText) findViewById(R.id.Employee_email_ET_ID);
        EmployeePhoneETID = (EditText) findViewById(R.id.Employee_phone_ET_ID);
        updateBtnID = (Button) findViewById(R.id.update_btn_ID);
        updateBtnID.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateProfileByEmployeeActivity.this.getContentResolver(), filePath);
                profile_image_ID.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
