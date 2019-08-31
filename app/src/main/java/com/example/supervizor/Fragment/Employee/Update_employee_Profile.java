package com.example.supervizor.Fragment.Employee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.supervizor.Activity.EmployeeMainActivity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class Update_employee_Profile extends Fragment implements View.OnClickListener {

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.uodate_profile_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

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

                    if (filePath == null) {
                        KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.ERROR_TYPE);
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
                        Toasty.info(getContext(), "fill the all input field").show();
                        return;
                    }

                    KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
                    kAlertDialog.setTitleText("Uploading..");
                    kAlertDialog.show();

                    Toast.makeText(getContext(), ""+filePath, Toast.LENGTH_SHORT).show();
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
                                                            startActivity(new Intent(getContext(), EmployeeMainActivity.class)
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile_image_ID) {

            chooseImage();

        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
    }

    private void initView(View rootView) {
        profile_image_ID = (CircleImageView) rootView.findViewById(R.id.profile_image_ID);
        profile_image_ID.setOnClickListener(Update_employee_Profile.this);
        EmployeeNameETID = (EditText) rootView.findViewById(R.id.Employee_name_ET_ID);
        EmployeeDesignationETID = (EditText) rootView.findViewById(R.id.Employee_designation_ET_ID);
        EmployeeEmailETID = (EditText) rootView.findViewById(R.id.Employee_email_ET_ID);
        EmployeePhoneETID = (EditText) rootView.findViewById(R.id.Employee_phone_ET_ID);
        updateBtnID = (Button) rootView.findViewById(R.id.update_btn_ID);
        updateBtnID.setOnClickListener(Update_employee_Profile.this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Edit Profile");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profile_image_ID.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
