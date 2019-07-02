package com.example.supervizor.Fragment.Company;

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
import android.widget.ImageView;

import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.R;
import com.kinda.alert.KAlertDialog;

import java.io.IOException;
import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class Registartion_page_1 extends Fragment {
    Button company_next_btn_1_Button;
    Fragment fragment = null;

    EditText company_name_ET;
    EditText company_location_ET;
    EditText company_contact_number_ET;
    EditText company_email_ET;
    EditText company_password_ET;
    ImageView company_profile_image;

    private Uri filePath;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registartion_page_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        company_profile_image.setOnClickListener(v -> {
                    Toasty.info(getContext(), "select logo", Toasty.LENGTH_LONG).show();
                    chooseImage();
                }
        );

        company_next_btn_1_Button.setOnClickListener(v -> {

            String company_name = company_name_ET.getText().toString();
            String company_location = company_location_ET.getText().toString();
            String company_contact_number = company_contact_number_ET.getText().toString();
            String company_email = company_email_ET.getText().toString();
            String company_password = company_password_ET.getText().toString();

            if (filePath==null){
                KAlertDialog kAlertDialog=new KAlertDialog(getContext(),KAlertDialog.ERROR_TYPE);
                kAlertDialog.setCancelable(false);
                kAlertDialog.setTitleText("Select company Logo");
                kAlertDialog.show();
                kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                            kAlertDialog1.dismiss();
                            chooseImage();
                        }
                );
                return;
            }

            if (company_name.isEmpty()) {
                company_name_ET.requestFocus();
                company_name_ET.setError("Company name");
                return;
            }
            if (company_location.isEmpty()) {
                company_location_ET.requestFocus();
                company_location_ET.setError("Location ?");
                return;
            }
            if (company_contact_number.isEmpty()) {
                company_contact_number_ET.requestFocus();
                company_contact_number_ET.setError("Contact Number ?");
                return;
            }
            if (company_email.isEmpty()) {
                company_email_ET.requestFocus();
                company_email_ET.setError("Email ?");
                return;
            }
            if (company_password.isEmpty()) {
                company_password_ET.requestFocus();
                company_password_ET.setError("Password ?");
                return;
            } else {

                SignUp_Pojo signUp_pojo = new SignUp_Pojo(company_name, company_location, company_contact_number, company_email, company_password);
                Bundle bundle = new Bundle();
                bundle.putString("filePath",filePath.toString());
                bundle.putSerializable("signUp_pojo_obj_1", signUp_pojo);

                fragment = new Registartion_page_2();
                if (fragment != null) {
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.registration_main_screen, fragment);
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }
            }


        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
    }

    private void initialize(View view) {
        company_profile_image = view.findViewById(R.id.company_profile_image_singup);
        company_password_ET = view.findViewById(R.id.company_password_ET_ID_singup);
        company_name_ET = view.findViewById(R.id.company_name_ET_ID_singup);
        company_location_ET = view.findViewById(R.id.company_location_ET_ID_singup);
        company_contact_number_ET = view.findViewById(R.id.company_contact_ET_ID_singup);
        company_email_ET = view.findViewById(R.id.company_email_ET_ID_singup);
        company_next_btn_1_Button = view.findViewById(R.id.company_next_btn_1_ID_singup);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                company_profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
