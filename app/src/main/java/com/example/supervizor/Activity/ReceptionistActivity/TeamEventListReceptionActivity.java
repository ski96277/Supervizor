package com.example.supervizor.Activity.ReceptionistActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.supervizor.Activity.EmployeeActivity.Team_Event_View_As_A_MemberActivity;
import com.example.supervizor.AdapterClass.Team_Event_List_Adapter_View_by_Receptionist;
import com.example.supervizor.AdapterClass.Team_Event_List_Adapter_View_by_Team_member;
import com.example.supervizor.JavaPojoClass.Event_Details_Team_PojoClass;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;
import java.util.List;

public class TeamEventListReceptionActivity extends AppCompatActivity {


    private RecyclerView team_event_show_by_reception;
    String team_leader_id;
    String team_name;
    private DatabaseReference databaseReference;
    private Event_Details_Team_PojoClass event_details_team_pojoClass;
    private List<Event_Details_Team_PojoClass> event_details_team_pojoClasses = new ArrayList<>();

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_event_list);


        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Team Event");
        initView();

        Intent intent = getIntent();
        team_name = intent.getStringExtra("team_name");
        team_leader_id = intent.getStringExtra("team_leader_id");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        team_event_show_by_reception.setLayoutManager(linearLayoutManager);

        databaseReference.child("event_list_by_Team")
                .child(team_leader_id)
                .child(team_name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            event_details_team_pojoClass = snapshot.getValue(Event_Details_Team_PojoClass.class);
                            Log.e("TAG", "onDataChange: Date = " + event_details_team_pojoClass.getDate());

                            event_details_team_pojoClasses.add(event_details_team_pojoClass);
                        }
                        Log.e("TAG", "onDataChange:list size =  " + event_details_team_pojoClasses.size());
                        if (event_details_team_pojoClasses.isEmpty()) {
                            progressBar.setVisibility(View.GONE);
                            team_event_show_by_reception.setVisibility(View.VISIBLE);
                            KAlertDialog emptyEventAlert = new KAlertDialog(TeamEventListReceptionActivity.this, KAlertDialog.WARNING_TYPE);
                            emptyEventAlert.setTitleText("No Event Found");
                            emptyEventAlert.show();

                            emptyEventAlert.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {

                                   startActivity(new Intent(TeamEventListReceptionActivity.this,TeamListReceptionActivity.class)
                                   .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            });
                        } else {

                            progressBar.setVisibility(View.GONE);
                            team_event_show_by_reception.setVisibility(View.VISIBLE);

                            Team_Event_List_Adapter_View_by_Receptionist team_event_list_adapter_view_by_team_member = new Team_Event_List_Adapter_View_by_Receptionist(event_details_team_pojoClasses);
                            team_event_show_by_reception.setAdapter(team_event_list_adapter_view_by_team_member);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toasty.error(getApplicationContext(), "Failed to load data ").show();

                        progressBar.setVisibility(View.GONE);
                        team_event_show_by_reception.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void initView() {
        team_event_show_by_reception = findViewById(R.id.team_event_show_by_reception);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressBar = (ProgressBar) findViewById(R.id.team_event_loading);

    }
}
