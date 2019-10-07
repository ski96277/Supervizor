package com.example.supervizor.Activity.EmployeeActivity;

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

public class Team_Event_View_As_A_MemberActivity extends AppCompatActivity {

    private RecyclerView recyclerView_Team_event_view_by_member;
    String team_leader_id;
    String team_name;
    private DatabaseReference databaseReference;
    private Event_Details_Team_PojoClass event_details_team_pojoClass;
    private List<Event_Details_Team_PojoClass> event_details_team_pojoClasses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_event_show_as_a_member);

        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Team Event");
        initView();

        Intent intent=getIntent();
        team_name = intent.getStringExtra("team_name");
        team_leader_id = intent.getStringExtra("team_leader_id");


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_Team_event_view_by_member.setLayoutManager(linearLayoutManager);

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
                            KAlertDialog emptyEventAlert = new KAlertDialog(Team_Event_View_As_A_MemberActivity.this, KAlertDialog.WARNING_TYPE);
                            emptyEventAlert.setTitleText("No Event Found");
                            emptyEventAlert.show();
                        }
                        Team_Event_List_Adapter_View_by_Team_member team_event_list_adapter_view_by_team_member = new Team_Event_List_Adapter_View_by_Team_member(event_details_team_pojoClasses);
                        recyclerView_Team_event_view_by_member.setAdapter(team_event_list_adapter_view_by_team_member);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toasty.error(getApplicationContext(), "Failed to load data ").show();
                    }
                });
    }


    private void initView() {
        recyclerView_Team_event_view_by_member = findViewById(R.id.team_event_show_by_member);
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }
}
