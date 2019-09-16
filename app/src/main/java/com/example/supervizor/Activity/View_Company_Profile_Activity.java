package com.example.supervizor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.squareup.picasso.Picasso;

public class View_Company_Profile_Activity extends AppCompatActivity {

    SignUp_Pojo signUp_pojo;

    CircleImageView circleImageView;
    TextView company_name;
    TextView location_TV;
    TextView contact_TV;
    TextView email_TV;
    Button edit_btn;
    DatabaseReference databaseReference;
    Check_User_information check_user_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__company__profile);
        getSupportActionBar().setTitle("Company profile");
//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initialize();

        /*Bundle bundle = getArguments();
              signUp_pojo = bundle.getParcelable("singup_information");
         */
//        Intent intent = getIntent();
//        signUp_pojo = (SignUp_Pojo) intent.getSerializableExtra("signUp_pojo");


//check Internet Connection
        if (!CheckInternet.isInternet(this)) {
            KAlertDialog kAlertDialog = new KAlertDialog(View_Company_Profile_Activity.this, KAlertDialog.ERROR_TYPE);
            kAlertDialog.setTitleText("Internet Error !");
            kAlertDialog.setContentText("Check internet Connection");
            kAlertDialog.show();
            return;
        }
        //check Internet Connection END

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                signUp_pojo = dataSnapshot.child("company_list")
                        .child(check_user_information.getUserID())
                        .getValue(SignUp_Pojo.class);

                Picasso.get().load(signUp_pojo.getLogo_download_url()).into(circleImageView);
                company_name.setText(signUp_pojo.getCompany_name());
                location_TV.setText(signUp_pojo.getCompany_location());
                contact_TV.setText(signUp_pojo.getCompany_contact_number());
                email_TV.setText(signUp_pojo.getCompany_email());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Company_Profile_Activity.this, Update_Company_Profile.class));


            }
        });


    }

    private void initialize() {
        circleImageView = findViewById(R.id.company_profile_circle_image);
        company_name = findViewById(R.id.company_profile_name);
        location_TV = findViewById(R.id.company_profile_location);
        contact_TV = findViewById(R.id.company_profile_contact);
        email_TV = findViewById(R.id.company_profile_email_ID);
        edit_btn = findViewById(R.id.edit_profile_company);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();

    }


}
