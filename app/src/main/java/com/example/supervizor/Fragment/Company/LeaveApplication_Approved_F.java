package com.example.supervizor.Fragment.Company;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supervizor.Activity.CompanyMainActivity;
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
    long count_leave_Application;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.VISIBLE);

        CompanyMainActivity.pending_button_layout
                .setBackgroundColor(Color.parseColor("#000000"));
        CompanyMainActivity.approved_button_layout
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //get count update nav bar number

              /*  count_leave_Application = dataSnapshot.child("leave_application")
                        .child(check_user_information.getUserID())
                        .child("pending").getChildrenCount();
                CompanyMainActivity.leave_notification_nav.setText(String.valueOf(count_leave_Application));*/
//get data for recycler view
                leaveApplication_pojoClasses_seen_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("leave_application")
                        .child(check_user_information.getUserID())
                        .child("pending").getChildren()) {
                    LeaveApplication_PojoClass leaveApplication_pojoClass = snapshot.getValue(LeaveApplication_PojoClass.class);
                    if (leaveApplication_pojoClass.isLeave_seen()) {

                        leaveApplication_pojoClasses_seen_list.add(leaveApplication_pojoClass);

                    } else {
                    }
                }

                //set count update nav bar number

//                CompanyMainActivity.leave_notification_nav.setText(String.valueOf(leaveApplication_pojoClasses_seen_list.size()));


                Leave_Application_Accepted_Adapter_Company leave_application_accepted_adapter_company = new Leave_Application_Accepted_Adapter_Company(leaveApplication_pojoClasses_seen_list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                leaveApplicationApprovedListID.setLayoutManager(linearLayoutManager);
                leaveApplicationApprovedListID.setAdapter(leave_application_accepted_adapter_company);
                kAlertDialog.dismissWithAnimation();

                //if recycler view is empty
                if (leaveApplication_pojoClasses_seen_list.isEmpty()){

                    Toasty.info(getActivity(),"No Data Found.....").show();
                  /*  KAlertDialog kAlertDialog1=new KAlertDialog(getContext(),KAlertDialog.SUCCESS_TYPE);
                    kAlertDialog1.setTitleText("No Data Found....");
                    kAlertDialog1.show();
                    kAlertDialog1.setConfirmClickListener(kAlertDialog2 -> kAlertDialog1.dismissWithAnimation());
*/

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.info(getContext(), "try it later, some problem are append").show();
                kAlertDialog.dismissWithAnimation();

            }
        });

    }


    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Leave Application");
    }

    private void initView(View rootView) {

        check_user_information = new Check_User_information();
        leaveApplicationApprovedListID = rootView.findViewById(R.id.leave_application_Approved_list_ID);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }
}
