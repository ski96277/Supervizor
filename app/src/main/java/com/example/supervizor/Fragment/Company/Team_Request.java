package com.example.supervizor.Fragment.Company;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.AdapterClass.Team_Event_List_Adapter_View_by_Team_member;
import com.example.supervizor.AdapterClass.Team_Event_Request_List_Adapter_View_by_company;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Team_Request extends Fragment {

    private RecyclerView recyclerViewTeamRequestID;
    private Check_User_information check_user_information;
    private DatabaseReference databaseReference;

    List<String> team_name_list = new ArrayList<>();
    List<String> team_leader_User_ID = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);

        return inflater.inflate(R.layout.team_request_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewTeamRequestID.setLayoutManager(gridLayoutManager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("my_team_request_pending")
                        .child(check_user_information.getUserID())
                        .getChildren()) {

                    String team_leader_ID_key = snapshot.getKey();

                    for (DataSnapshot snapshot1 : dataSnapshot.child("my_team_request_pending")
                            .child(check_user_information.getUserID()).child(team_leader_ID_key)
                            .getChildren()) {

                        String team_name_key = snapshot1.getKey();

                        for (DataSnapshot snapshot2 : dataSnapshot.child("my_team_request_pending")
                                .child(check_user_information.getUserID())
                                .child(team_leader_ID_key).child(team_name_key).getChildren()) {


                            String status_key = snapshot2.getKey();

                            String status = dataSnapshot.child("my_team_request_pending")
                                    .child(check_user_information.getUserID())
                                    .child(team_leader_ID_key).child(team_name_key).child(status_key).getValue(String.class);
                            if (status.equals("0")) {
                                team_name_list.add(team_name_key);
                                team_leader_User_ID.add(team_leader_ID_key);
                            }

                        }
                    }
                }
                if (team_name_list.isEmpty()) {
                    KAlertDialog no_team_request_Alert = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                    no_team_request_Alert.setTitleText("No Team Request Yet");
                    no_team_request_Alert.show();
                    no_team_request_Alert.setConfirmClickListener(kAlertDialog -> {
                        no_team_request_Alert.dismissWithAnimation();

                    });
                }

                Team_Event_Request_List_Adapter_View_by_company team_event_request_list_adapter_view_by_company
                        = new Team_Event_Request_List_Adapter_View_by_company(team_name_list, team_leader_User_ID);
                recyclerViewTeamRequestID.setAdapter(team_event_request_list_adapter_view_by_company);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initView(View rootView) {
        recyclerViewTeamRequestID = rootView.findViewById(R.id.recycler_view_team_request_ID);
        check_user_information = new Check_User_information();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Team Request List");
    }
}
