package com.example.supervizor.Fragment.Employee;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.supervizor.AdapterClass.All_Leave_Application_List_Adapter;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyLeaveApplication_F extends Fragment {
    protected RecyclerView leaveApplicationList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference_for_company_UI;
    private DatabaseReference databaseReference_for_leave_information;
    private AddEmployee_PojoClass addEmployee_pojoClass;
    private   LeaveApplication_PojoClass leaveApplication_pojoClass;
    private All_Leave_Application_List_Adapter all_leave_application_list_adapter;
    private Check_User_information check_user_information;
    String user_id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_leave_application_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        user_id=check_user_information.getUserID();
        databaseReference_for_leave_information.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LeaveApplication_PojoClass leaveApplication_pojoClass = dataSnapshot.child("leave_application")
                        .child("2xc8CYa24aVGhHEMAwcbyx0hweF3")
                        .getValue(LeaveApplication_PojoClass.class);
                Log.e("TAG - ", "onDataChange: "+leaveApplication_pojoClass.getLeave_applying_Date() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
//get user company ID
        databaseReference_for_company_UI.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_id).getValue(AddEmployee_PojoClass.class);

                String userID_company=addEmployee_pojoClass.getCompany_User_id();
                String image_link=addEmployee_pojoClass.getEmployee_profile_image_link();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
    }

    private void initView(View rootView) {
        leaveApplicationList = (RecyclerView) rootView.findViewById(R.id.leave_application_list);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference_for_company_UI=firebaseDatabase.getReference();
        databaseReference_for_leave_information=firebaseDatabase.getReference();

        check_user_information=new Check_User_information();
    }
}
