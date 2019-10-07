package com.example.supervizor.Activity.CompanyActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

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

public class Team_RequestListActivity extends AppCompatActivity {


    private RecyclerView recyclerViewTeamRequestID;
    private Check_User_information check_user_information;
    private DatabaseReference databaseReference;

    List<String> team_name_list = new ArrayList<>();
    List<String> team_leader_User_ID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_request_f);

//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Team Request List");
        initView();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewTeamRequestID.setLayoutManager(gridLayoutManager);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                if (team_name_list.size() <= 0) {
                    KAlertDialog no_team_request_Alert = new KAlertDialog(Team_RequestListActivity.this, KAlertDialog.WARNING_TYPE);
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


    private void initView() {
        recyclerViewTeamRequestID = findViewById(R.id.recycler_view_team_request_ID);
        check_user_information = new Check_User_information();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CompanyMainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onBackPressed();
    }
}
