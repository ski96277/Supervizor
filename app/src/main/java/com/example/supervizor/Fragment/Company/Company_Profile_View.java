package com.example.supervizor.Fragment.Company;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.Activity.Update_Company_Profile;
import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.R;
import com.kinda.alert.KAlertDialog;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Company_Profile_View extends Fragment {
    SignUp_Pojo signUp_pojo;

    CircleImageView circleImageView;
    TextView company_name;
    TextView location_TV;
    TextView contact_TV;
    TextView email_TV;
    Button edit_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        signUp_pojo = bundle.getParcelable("singup_information");

        return inflater.inflate(R.layout.company_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
//check Internet Connection
        if (!CheckInternet.isInternet(getContext())) {
            KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.ERROR_TYPE);
            kAlertDialog.setTitleText("Internet Error !");
            kAlertDialog.setContentText("Check internet Connection");
            kAlertDialog.show();
            return;
        }
        //check Internet Connection END


        Picasso.get().load(signUp_pojo.getLogo_download_url()).into(circleImageView);
        company_name.setText(signUp_pojo.getCompany_name());
        location_TV.setText(signUp_pojo.getCompany_location());
        contact_TV.setText(signUp_pojo.getCompany_contact_number());
        email_TV.setText(signUp_pojo.getCompany_email());

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Update_Company_Profile.class));


            }
        });


    }

    private void initialize(View view) {
        circleImageView = view.findViewById(R.id.company_profile_circle_image);
        company_name = view.findViewById(R.id.company_profile_name);
        location_TV = view.findViewById(R.id.company_profile_location);
        contact_TV = view.findViewById(R.id.company_profile_contact);
        email_TV = view.findViewById(R.id.company_profile_email_ID);
        edit_btn = view.findViewById(R.id.edit_profile_company);

    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Company profile");
    }
}
