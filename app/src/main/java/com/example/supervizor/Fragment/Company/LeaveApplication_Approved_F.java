package com.example.supervizor.Fragment.Company;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.supervizor.Activity.CompanyActivity.CompanyMainActivity;
import com.example.supervizor.Activity.CompanyActivity.LeaveApplicationListActivity;
import com.example.supervizor.AdapterClass.Leave_Application_Accepted_Adapter_Company;
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.dmoral.toasty.Toasty;

public class LeaveApplication_Approved_F extends Fragment {

    protected RecyclerView leaveApplicationApprovedListID;
    private DatabaseReference databaseReference;
    private List<LeaveApplication_PojoClass> leaveApplication_pojoClasses_seen_list = new ArrayList<>();
    private Check_User_information check_user_information;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       /* CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.VISIBLE);
*/
        LeaveApplicationListActivity.pending_button_layout
                .setBackgroundColor(Color.parseColor("#000000"));
        LeaveApplicationListActivity.approved_button_layout
                .setBackgroundColor(Color.parseColor("#00CCCC"));
//check nav leave application buttton
        CompanyMainActivity.navigationView.setCheckedItem(R.id.nav_leave_application);

        return inflater.inflate(R.layout.leave_application_approved_f, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        leave_Application_Data();


    }


    private void leave_Application_Data() {
        KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.show();
        kAlertDialog.setCancelable(false);
        kAlertDialog.setTitleText("Getting Data.....");

        //get value from Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //get data for recycler view
                leaveApplication_pojoClasses_seen_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("leave_application")
                        .child(check_user_information.getUserID())
                        .getChildren()) {
                    String userID_Key = snapshot.getKey();

                    for (DataSnapshot snapshot1:dataSnapshot.child("leave_application")
                            .child(check_user_information.getUserID())
                            .child(userID_Key)
                            .getChildren()){
                        LeaveApplication_PojoClass leaveApplication_pojoClass = snapshot1.getValue(LeaveApplication_PojoClass.class);

                        if (leaveApplication_pojoClass.isLeave_seen()) {
                            leaveApplication_pojoClasses_seen_list.add(leaveApplication_pojoClass);

                        } else {

                        }
                    }


                }
                //set count update nav bar number

                //if recycler view is empty
                if (leaveApplication_pojoClasses_seen_list.isEmpty()) {
                    Toasty.info(getContext(), "No Data Found.....").show();
                }
//                CompanyMainActivity.leave_notification_nav.setText(String.valueOf(leaveApplication_pojoClasses_seen_list.size()));

                Leave_Application_Accepted_Adapter_Company leave_application_pending_adapterCompany = new Leave_Application_Accepted_Adapter_Company(leaveApplication_pojoClasses_seen_list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                leaveApplicationApprovedListID.setLayoutManager(linearLayoutManager);

                int resId = R.anim.layout_animation_fall_down;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(),resId);
                leaveApplicationApprovedListID.setLayoutAnimation(animation); /*(https://proandroiddev.com/enter-animation-using-recyclerview-and-layoutanimation-part-1-list-75a874a5d213)*/


                leaveApplicationApprovedListID.setAdapter(leave_application_pending_adapterCompany);
                kAlertDialog.dismissWithAnimation();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.info(getContext(), "try it later, some problem are append").show();
                kAlertDialog.dismissWithAnimation();

            }
        });

      /*  //get value from Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//get data for recycler view
                leaveApplication_pojoClasses_seen_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("leave_application")
                        .child(check_user_information.getUserID())
                        .getChildren()) {
                    String userID_Key = snapshot.getKey();

                    for (DataSnapshot snapshot1:dataSnapshot.child("leave_application")
                            .child(check_user_information.getUserID())
                            .child(userID_Key)
                            .getChildren()){
                        LeaveApplication_PojoClass leaveApplication_pojoClass = snapshot1.getValue(LeaveApplication_PojoClass.class);
                        if (leaveApplication_pojoClass.isLeave_seen()) {
                            leaveApplication_pojoClasses_seen_list.add(leaveApplication_pojoClass);

                        } else {

                        }
                    }
                }

                //if recycler view is empty
                if (leaveApplication_pojoClasses_seen_list.isEmpty()) {
                    Toasty.info(getActivity(), "No Data Found.....").show();

                }else {

                    Leave_Application_Accepted_Adapter_Company leave_application_accepted_adapter_company = new Leave_Application_Accepted_Adapter_Company(leaveApplication_pojoClasses_seen_list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    leaveApplicationApprovedListID.setLayoutManager(linearLayoutManager);
                    leaveApplicationApprovedListID.setAdapter(leave_application_accepted_adapter_company);
                    kAlertDialog.dismissWithAnimation();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.info(getContext(), "try it later, some problem are append").show();
                kAlertDialog.dismissWithAnimation();

            }
        });

    */}



    private void initView(View rootView) {

        check_user_information = new Check_User_information();
        leaveApplicationApprovedListID = rootView.findViewById(R.id.leave_application_Approved_list_ID);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
