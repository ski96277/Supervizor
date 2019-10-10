package com.example.supervizor.Activity.ReceptionistActivity;

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
import com.example.supervizor.JavaPojoClass.AddReceptionist_PojoClass;
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

public class EditReceptionistProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView profileImageReceptionID;
    private EditText receptionNameETID;
    private EditText receptionDesignationETID;
    private EditText receptionEmailETID;
    private EditText receptionPhoneETID;
    private Button updateBtnReceptionID;

    private DatabaseReference databaseReference;
    private Check_User_information check_user_information;

    private Uri filePath;

    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_receptionist_profile);
        //hide notification
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Edit Profile");

        initView();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(check_user_information.getUserID()).getValue(AddEmployee_PojoClass.class);


                if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                    Picasso.get().load(Uri.parse(addEmployee_pojoClass.getEmployee_profile_image_link()))
                            .into(profileImageReceptionID);
                } else {
                    profileImageReceptionID.setImageResource(R.drawable.profile);
                }
                receptionNameETID.setText(addEmployee_pojoClass.getEmployee_name());
                receptionDesignationETID.setText(addEmployee_pojoClass.getEmployee_designation());
                receptionEmailETID.setText(addEmployee_pojoClass.getEmployee_email());
                if (addEmployee_pojoClass.getUser_phone_number() != null) {
                    receptionPhoneETID.setText(addEmployee_pojoClass.getUser_phone_number());
                }
                updateBtnReceptionID.setOnClickListener(v -> {

                    String name = receptionNameETID.getText().toString();
                    String designation = receptionDesignationETID.getText().toString();
                    String email = receptionEmailETID.getText().toString();
                    String phone = receptionPhoneETID.getText().toString();

                    if (filePath == null) {
                        KAlertDialog kAlertDialog = new KAlertDialog(EditReceptionistProfileActivity.this, KAlertDialog.ERROR_TYPE);
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
                        Toasty.info(EditReceptionistProfileActivity.this, "fill the all input field").show();
                        return;
                    }

                    KAlertDialog kAlertDialog = new KAlertDialog(EditReceptionistProfileActivity.this, KAlertDialog.PROGRESS_TYPE);
                    kAlertDialog.setTitleText("Uploading..");
                    kAlertDialog.show();

//                    Toast.makeText(getContext(), "" + filePath, Toast.LENGTH_SHORT).show();
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

                                            AddReceptionist_PojoClass addReceptionist_pojoClass = new AddReceptionist_PojoClass(
                                                    name,
                                                    designation,
                                                    email, addEmployee_pojoClass.getEmployee_joinDate(),
                                                    addEmployee_pojoClass.getEmployee_salary(),
                                                    addEmployee_pojoClass.getEmployee_Contact_period_number(),
                                                    addEmployee_pojoClass.getEmployee_Contact_period_year_OR_month(),
                                                    downloadLink,
                                                    addEmployee_pojoClass.getEmployee_password(),
                                                    addEmployee_pojoClass.getCompany_User_id(),
                                                    addEmployee_pojoClass.getEmployee_User_id(),
                                                    phone
                                            );


                                            databaseReference.child("receptionist_list")
                                                    .child(addEmployee_pojoClass.getEmployee_User_id())
                                                    .setValue(addReceptionist_pojoClass);

                                            databaseReference.child("receptionist_list_by_company")
                                                    .child(addEmployee_pojoClass.getCompany_User_id())
                                                    .child(addEmployee_pojoClass.getEmployee_User_id())
                                                    .setValue(addReceptionist_pojoClass);

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

                                                            startActivity(new Intent(EditReceptionistProfileActivity.this, ProfileViewReceptionistActivity.class)
                                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


                                                        }
                                                    });


                                        }
                                    });
                                }
                            });

                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile_image_reception_ID) {
            chooseImage();
        }
    }

    private void initView( ) {
        profileImageReceptionID = (CircleImageView) findViewById(R.id.profile_image_reception_ID);
        profileImageReceptionID.setOnClickListener(this);
        receptionNameETID = (EditText) findViewById(R.id.reception_name_ET_ID);
        receptionDesignationETID = (EditText) findViewById(R.id.reception_designation_ET_ID);
        receptionEmailETID = (EditText) findViewById(R.id.reception_email_ET_ID);
        receptionPhoneETID = (EditText) findViewById(R.id.reception_phone_ET_ID);
        updateBtnReceptionID = (Button) findViewById(R.id.update_btn_reception_ID);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        storageReference = FirebaseStorage.getInstance().getReference();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                profileImageReceptionID.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
