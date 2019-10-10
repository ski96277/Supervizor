package com.example.supervizor.Activity.ReceptionistActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileViewReceptionistActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AddEmployee_PojoClass addEmployee_pojoClass;
    Check_User_information check_user_information;
    String user_ID;
    CircleImageView circleImageView_profile;
    TextView name_TV_head;
    TextView designation_TV_head;
    TextView name_TV_ID;
    TextView phone_TV_ID;
    TextView email_TV_ID;
    TextView calender_TV_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view_receptionist_f);

        //hide notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Profile View");

        initialize();

        if (!CheckInternet.isInternet(getApplicationContext())) {
            Toasty.info(this, "Check Internet Connection").show();
            return;
        }
        check_user_information = new Check_User_information();
        user_ID = check_user_information.getUserID();

        getProfile_Information(user_ID);

    }

    private void getProfile_Information(String user_ID) {
        //get profile data from firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_ID).getValue(AddEmployee_PojoClass.class);

                if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                    Picasso.get().load(addEmployee_pojoClass.getEmployee_profile_image_link())
                            .into(circleImageView_profile);
                }
                name_TV_head.setText(addEmployee_pojoClass.getEmployee_name());
                designation_TV_head.setText(addEmployee_pojoClass.getEmployee_designation());
                name_TV_ID.setText(addEmployee_pojoClass.getEmployee_name());
                if (addEmployee_pojoClass.getUser_phone_number() != null) {
                    phone_TV_ID.setText(addEmployee_pojoClass.getUser_phone_number());
                }
                email_TV_ID.setText(addEmployee_pojoClass.getEmployee_email());
                calender_TV_ID.setText(addEmployee_pojoClass.getEmployee_joinDate());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialize( ) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        circleImageView_profile = findViewById(R.id.receptionist_profile_image_user_profile);
        name_TV_head = findViewById(R.id.receptionist_name_tv_profile_view);
        designation_TV_head = findViewById(R.id.receptionist_designation_profile_view);
        name_TV_ID = findViewById(R.id.receptionist_name_tv_profile_view_page_1);
        phone_TV_ID = findViewById(R.id.receptionist_phone_number_tv_profile_view_page_1);
        email_TV_ID = findViewById(R.id.receptionist_email_tv_profile_view_page_1);
        calender_TV_ID = findViewById(R.id.receptionist_date_tv_profile_view_page_1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.receptionist_profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit_profile_receptionsit:
//                load_Edit_profile_Fragment();
                startActivity(new Intent(ProfileViewReceptionistActivity.this,EditReceptionistProfileActivity.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
