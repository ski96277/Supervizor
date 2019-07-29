package com.example.supervizor.Fragment.Employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ProfileView_Employee_F extends Fragment {
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_view_employee_f, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        if (!CheckInternet.isInternet(getContext())) {
            Toasty.info(getContext(), "Check Internet Connection").show();
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

                if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")){

                    Picasso.get().load(addEmployee_pojoClass.getEmployee_profile_image_link())
                            .into(circleImageView_profile);
                }
                name_TV_head.setText(addEmployee_pojoClass.getEmployee_name());
                designation_TV_head.setText(addEmployee_pojoClass.getEmployee_designation());
                name_TV_ID.setText(addEmployee_pojoClass.getEmployee_name());
                phone_TV_ID.setText(addEmployee_pojoClass.getEmployee_joinDate());
                email_TV_ID.setText(addEmployee_pojoClass.getEmployee_email());
                calender_TV_ID.setText(addEmployee_pojoClass.getEmployee_joinDate());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialize(View view) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        circleImageView_profile=view.findViewById(R.id.employee_profile_image_user_profile);
        name_TV_head = view.findViewById(R.id.employee_name_tv_profile_view);
        designation_TV_head = view.findViewById(R.id.employee_designation_profile_view);
        name_TV_ID = view.findViewById(R.id.employee_name_tv_profile_view_page_1);
        phone_TV_ID = view.findViewById(R.id.employee_phone_number_tv_profile_view_page_1);
        email_TV_ID = view.findViewById(R.id.employee_email_tv_profile_view_page_1);
        calender_TV_ID = view.findViewById(R.id.employee_date_tv_profile_view_page_1);

    }
}