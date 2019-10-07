package com.example.supervizor.Activity.EmployeeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.supervizor.AdapterClass.Team_Member_List_Adapter;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;

public class Team_Member_List_Activity extends AppCompatActivity {

    private RecyclerView recyclerViewTeamMemberList;
    String team_name;
    DatabaseReference databaseReference;
    Check_User_information check_user_information;

    ArrayList<AddEmployee_PojoClass> addEmployee_pojoClasses = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_member_list);
        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Team Member");

        initView();
        Intent intent = getIntent();
        team_name = intent.getStringExtra("team_name");
        Toast.makeText(this, "" + team_name, Toast.LENGTH_SHORT).show();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewTeamMemberList.setLayoutManager(linearLayoutManager);

        //Load member list from data base
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String company_user_ID = dataSnapshot.child("employee_list")
                        .child(check_user_information.getUserID())
                        .child("company_User_id").getValue(String.class);
                //check request status
                String status = dataSnapshot.child("my_team_request_pending")
                        .child(company_user_ID)
                        .child(check_user_information.getUserID())
                        .child(team_name)
                        .child("status")
                        .getValue(String.class);
                if (status != null) {
                    if (status.equals("0")) {


                        recyclerViewTeamMemberList.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        KAlertDialog kAlertDialog = new KAlertDialog(Team_Member_List_Activity.this, KAlertDialog.WARNING_TYPE);
                        kAlertDialog.setTitleText("You have no Permission");
                        kAlertDialog.showCancelButton(true);
                        kAlertDialog.setCancelText("Add Team Mate");
                        kAlertDialog.setCancelable(false);
                        kAlertDialog.setConfirmText("          OK          ");
                        kAlertDialog.show();

                        kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                            kAlertDialog.dismissWithAnimation();
                            startActivity(new Intent(getApplicationContext(), My_Team_List_Activity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        });
                        kAlertDialog.setCancelClickListener(kAlertDialog1 -> {
                            startActivity(new Intent(getApplicationContext(), AddNewTeamMateFrom_ListActivity.class)
                                    .putExtra("team_name", team_name)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                        });
                        return;
                    }
                }

//if the team has no member go to the employee list
                if (!dataSnapshot.child("my_team_request")
                        .child(check_user_information.getUserID()).hasChild(team_name)) {

                    //show dialog
                    if (Team_Member_List_Activity.this != null) {


                        recyclerViewTeamMemberList.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        KAlertDialog kAlertDialog = new KAlertDialog(Team_Member_List_Activity.this, KAlertDialog.WARNING_TYPE);
                        kAlertDialog.show();
                        kAlertDialog.setTitleText("No user Found");
                        kAlertDialog.setContentText(" Add New Team Mate");
                        kAlertDialog.setConfirmClickListener(kAlertDialog12 -> {

//                        loadNewteam_From_List();
                            kAlertDialog12.dismissWithAnimation();
                            startActivity(new Intent(
                                    getApplicationContext(),
                                    AddNewTeamMateFrom_ListActivity.class)
                                    .putExtra("team_name", team_name)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        });
                    }


                }

                addEmployee_pojoClasses.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("my_team_request")
                        .child(check_user_information.getUserID()).child(team_name).getChildren()) {
                    String user_id = snapshot.getKey();


                    //get user information
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_id).getValue(AddEmployee_PojoClass.class);
                            addEmployee_pojoClasses.add(addEmployee_pojoClass);

                            Team_Member_List_Adapter team_member_list_adapter = new Team_Member_List_Adapter(addEmployee_pojoClasses);

                            recyclerViewTeamMemberList.setAdapter(team_member_list_adapter);

                            recyclerViewTeamMemberList.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.team_leader_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_team_mate:
//                loadNewteam_From_List();
                startActivity(new Intent(this, AddNewTeamMateFrom_ListActivity.class)
                        .putExtra("team_name", team_name));
                break;
            case R.id.event_List_this_Group:

                startActivity(new Intent(this, Event_list_and_Add_by_Team_Leader_Activity.class)
                .putExtra("team_name",team_name));
//                loadEvent_Fragment_By_Team_Leader();
                break;
        }

        return false;
    }


    private void initView() {
        recyclerViewTeamMemberList = findViewById(R.id.recyclerView_Team_member_List);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        progressBar=findViewById(R.id.loading_wave_kit);

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, My_Team_List_Activity.class));

        super.onBackPressed();

    }
}
