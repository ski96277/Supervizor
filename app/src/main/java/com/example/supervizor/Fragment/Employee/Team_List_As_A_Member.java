package com.example.supervizor.Fragment.Employee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supervizor.Activity.EmployeeMainActivity;
import com.example.supervizor.AdapterClass.Team_Name_List_As_A_Member_Adapter;
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
import java.util.concurrent.Executors;

public class Team_List_As_A_Member extends Fragment {

    private DatabaseReference databaseReference;
    Check_User_information check_user_information;
    RecyclerView recycler_view_team_list_as_a_member;
    List<String> team_name_list = new ArrayList<>();
    List<String> team_leader_ID_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_list_as_a_member, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recycler_view_team_list_as_a_member.setLayoutManager(gridLayoutManager);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("my_team_request").getChildren()) {
                    String user_ID_Leader = snapshot.getKey();

                    for (DataSnapshot snapshot1 : dataSnapshot.child("my_team_request")
                            .child(user_ID_Leader).getChildren()) {
                        String team_name = snapshot1.getKey();

                        for (DataSnapshot snapshot2 : dataSnapshot.child("my_team_request")
                                .child(user_ID_Leader).child(team_name).getChildren()) {


                            String member_user_ID = snapshot2.getKey();
                            if (member_user_ID.equals(check_user_information.getUserID())) {
                                team_name_list.add(team_name);
                                team_leader_ID_list.add(user_ID_Leader);
                            }
                        }
                    }
                }
                if (team_name_list.isEmpty()) {
                    KAlertDialog teamListEmptyAlert = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                    teamListEmptyAlert.show();
                    teamListEmptyAlert.setTitleText("You are Not assign");
                    teamListEmptyAlert.setContentText("any team yet");
                    teamListEmptyAlert.setConfirmClickListener(kAlertDialog -> {
                        teamListEmptyAlert.dismissWithAnimation();
                    });
                }
                Log.e("TAG", "onDataChange: 3 = " + team_name_list.size());
                Team_Name_List_As_A_Member_Adapter team_name_list_as_a_member_adapter
                        = new Team_Name_List_As_A_Member_Adapter(team_name_list, team_leader_ID_list);
                recycler_view_team_list_as_a_member.setAdapter(team_name_list_as_a_member_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("My Team List");
    }

    private void initialize(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        recycler_view_team_list_as_a_member = view.findViewById(R.id.recycler_view_team_list_as_a_member);

    }
}
