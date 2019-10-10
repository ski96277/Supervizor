package com.example.supervizor.Activity.ReceptionistActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.supervizor.AdapterClass.Team_Name_List_As_A_Member_Reception_Adapter;
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

public class TeamListReceptionActivity extends AppCompatActivity {


    private DatabaseReference databaseReference;
    Check_User_information check_user_information;
    RecyclerView recycler_view_team_list_as_a_member_reception;
    List<String> team_name_list = new ArrayList<>();
    List<String> team_leader_ID_list = new ArrayList<>();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list_reception);
        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Team Name");

        initialize();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recycler_view_team_list_as_a_member_reception.setLayoutManager(gridLayoutManager);

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
                    progressBar.setVisibility(View.GONE);
                    recycler_view_team_list_as_a_member_reception.setVisibility(View.VISIBLE);
                    KAlertDialog teamListEmptyAlert = new KAlertDialog(TeamListReceptionActivity.this, KAlertDialog.WARNING_TYPE);
                    teamListEmptyAlert.show();
                    teamListEmptyAlert.setTitleText("You are Not assign");
                    teamListEmptyAlert.setContentText("any team yet");
                    teamListEmptyAlert.setConfirmClickListener(kAlertDialog -> {
                        teamListEmptyAlert.dismissWithAnimation();
                        startActivity(new Intent(TeamListReceptionActivity.this,ReceptionistMainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    });
                }else {

                    progressBar.setVisibility(View.GONE);
                    recycler_view_team_list_as_a_member_reception.setVisibility(View.VISIBLE);
                    Log.e("TAG", "onDataChange: 3 = " + team_name_list.size());

                    Team_Name_List_As_A_Member_Reception_Adapter team_name_list_as_a_member_adapter
                            = new Team_Name_List_As_A_Member_Reception_Adapter(team_name_list, team_leader_ID_list);
                    recycler_view_team_list_as_a_member_reception.setAdapter(team_name_list_as_a_member_adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void initialize() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        recycler_view_team_list_as_a_member_reception = findViewById(R.id.recycler_view_team_list_as_a_member_reception);

        progressBar=findViewById(R.id.team_name_loading);

    }
}
