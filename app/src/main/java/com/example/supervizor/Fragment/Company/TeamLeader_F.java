package com.example.supervizor.Fragment.Company;

import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.AdapterClass.Team_Leader_List_Adapter;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class TeamLeader_F extends Fragment {

    RecyclerView recyclerView_team_leader_lsit;
    private DatabaseReference databaseReference;
    Check_User_information check_user_information;
    List<AddEmployee_PojoClass>addEmployee_pojoClasses_list=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);

        return inflater.inflate(R.layout.team_leader_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_team_leader_lsit.setLayoutManager(linearLayoutManager);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.child("my_team_request_pending")
                        .child(check_user_information.getUserID()).getChildren()){
                    String team_leader_ID = snapshot.getKey();
                    Log.e("TAG", "onDataChange: team_leader_ID = " + team_leader_ID);

                       AddEmployee_PojoClass addEmployee_pojoClass= dataSnapshot.child("employee_list").child(team_leader_ID).getValue(AddEmployee_PojoClass.class);

                       addEmployee_pojoClasses_list.add(addEmployee_pojoClass);
                }

                if (addEmployee_pojoClasses_list.isEmpty()){
                    Toasty.info(view.getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    return;
                }

                Team_Leader_List_Adapter team_leader_list_adapter=new Team_Leader_List_Adapter(addEmployee_pojoClasses_list);
                recyclerView_team_leader_lsit.setAdapter(team_leader_list_adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initview(View view) {
        recyclerView_team_leader_lsit = view.findViewById(R.id.recycler_view_team_leader_list);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        check_user_information=new Check_User_information();

    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Team Leader");
    }
}
