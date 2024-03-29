package com.example.supervizor.Activity.EmployeeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.supervizor.AdapterClass.All_Leave_Application_List_Employee_Adapter;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
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

public class MyLeaveApplicationListActivity extends AppCompatActivity {

    private RecyclerView leaveApplicationList;
    private DatabaseReference databaseReference_for_company_UI;
    private AddEmployee_PojoClass addEmployee_pojoClass;
    private LeaveApplication_PojoClass leaveApplication_pojoClass;
    private List<LeaveApplication_PojoClass> leaveApplication_pojoClasses_list = new ArrayList<>();

    private Check_User_information check_user_information;
    private String user_id;

    private KAlertDialog kAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_leave_application_f);

        initView();

        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Attendance");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        leaveApplicationList.setLayoutManager(linearLayoutManager);

        user_id = check_user_information.getUserID();

        kAlertDialog.setTitleText("Getting Data.....");
        kAlertDialog.show();
        if (!CheckInternet.isInternet(getApplicationContext())) {
            Toasty.error(getApplicationContext(), "Check internet Connection").show();
            kAlertDialog.dismissWithAnimation();
        }
        /* get user company ID */
        databaseReference_for_company_UI.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_id).getValue(AddEmployee_PojoClass.class);

                assert addEmployee_pojoClass != null;
                String userID_company = addEmployee_pojoClass.getCompany_User_id();
                String image_link = addEmployee_pojoClass.getEmployee_profile_image_link();

                databaseReference_for_company_UI.child("leave_application")
                        .child(userID_company)
                        .child(check_user_information.getUserID())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    leaveApplication_pojoClass = snapshot.getValue(LeaveApplication_PojoClass.class);

//                            if (check_user_information.getUserID().equals(leaveApplication_pojoClass.getUser_ID_Employee())) {
                                    leaveApplication_pojoClasses_list.add(leaveApplication_pojoClass);
//                            }
                                }
                                if (leaveApplication_pojoClasses_list.size()==0){
                                    kAlertDialog.dismissWithAnimation();
                                    Toasty.info(getApplicationContext(),"No data found").show();
                                    return;
                                }

                                Log.e("TAG", "onDataChange: size = " + leaveApplication_pojoClasses_list.size());
                                Log.e("TAG", "onDataChange: date = " + leaveApplication_pojoClass.getLeave_applying_Date());
                                Log.e("TAG", "onDataChange: title = " + leaveApplication_pojoClass.getLeave_Title());
                                All_Leave_Application_List_Employee_Adapter all_leave_application_list_Employee_adapter = new All_Leave_Application_List_Employee_Adapter(
                                        leaveApplication_pojoClasses_list,
                                        image_link);
//set adapter
                                leaveApplicationList.setAdapter(all_leave_application_list_Employee_adapter);
                                kAlertDialog.dismissWithAnimation();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toasty.info(getApplicationContext(), "have some problem try it later").show();
                            }
                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.info(getApplicationContext(), "have some problem try it later").show();

            }
        });
    }


    private void initView() {
        leaveApplicationList = findViewById(R.id.leave_application_list);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference_for_company_UI = firebaseDatabase.getReference();

        check_user_information = new Check_User_information();
        kAlertDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
    }
}
