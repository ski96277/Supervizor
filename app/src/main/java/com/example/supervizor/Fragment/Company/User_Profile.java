package com.example.supervizor.Fragment.Company;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
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

public class User_Profile extends Fragment {

    TextView name_Tv;
    TextView phone_TV;
    TextView email_TV;
    TextView calender_TV;
    TextView name_profile_TV;
    TextView designation_profile_TV;
    CircleImageView circleImageView;

    String user_id;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AddEmployee_PojoClass addEmployee_pojoClass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        user_id = bundle.getString("user_id");
//        Toasty.info(getContext(), user_id, Toasty.LENGTH_LONG).show();

        return inflater.inflate(R.layout.user_profile_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        //get user information

        databaseReference.child("employee_list").child(user_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        addEmployee_pojoClass = dataSnapshot.getValue(AddEmployee_PojoClass.class);


                        if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                            Picasso.get().load(Uri.parse(addEmployee_pojoClass.getEmployee_profile_image_link())).into(circleImageView);
                        } else {
                            circleImageView.setImageResource(R.drawable.profile);
                        }

                        name_profile_TV.setText(addEmployee_pojoClass.getEmployee_name());
                        designation_profile_TV.setText(addEmployee_pojoClass.getEmployee_designation());

                        name_Tv.setText(addEmployee_pojoClass.getEmployee_name());
                        email_TV.setText(addEmployee_pojoClass.getEmployee_email());
                        phone_TV.setText(addEmployee_pojoClass.getEmployee_Contact_period_number());
                        calender_TV.setText(addEmployee_pojoClass.getEmployee_joinDate());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    private void initialize(View view) {
        name_Tv = view.findViewById(R.id.name_tv_profile_view_page_1);
        phone_TV = view.findViewById(R.id.phone_number_tv_profile_view_page_1);
        email_TV = view.findViewById(R.id.email_tv_profile_view_page_1);
        calender_TV = view.findViewById(R.id.date_tv_profile_view_page_1);
        name_profile_TV = view.findViewById(R.id.name_tv_profile_view);
        designation_profile_TV = view.findViewById(R.id.designation_profile_view);
        circleImageView = view.findViewById(R.id.profile_image_user_profile);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Profile");
    }
}
