package com.example.supervizor.Activity.EmployeeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.supervizor.AdapterClass.Team_Name_List_Adapter;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

public class My_Team_List_Activity extends AppCompatActivity implements View.OnClickListener {

    protected RecyclerView recyclerViewIDTeamList;
    private FloatingActionButton addNewTeamMate;

    DatabaseReference databaseReference;
    Check_User_information check_user_information;
    Team_Name_List_Adapter team_name_list_adapter;

    List<String> team_name_list = new ArrayList<>();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_team_f);
        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Team List");


        initView();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewIDTeamList.setLayoutManager(gridLayoutManager);

//get tam list from database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String company_user_ID = dataSnapshot.child("employee_list")
                        .child(check_user_information.getUserID())
                        .child("company_User_id")
                        .getValue(String.class);

                //get team name
                for (DataSnapshot snapshot : dataSnapshot
                        .child("my_team_request_pending")
                        .child(company_user_ID)
                        .child(check_user_information.getUserID())
                        .getChildren()) {
                    String team_name = snapshot.getKey();
                    team_name_list.add(team_name);
                }
                //if have no team
                if (team_name_list.isEmpty()) {

//                    recyclerViewIDTeamList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    KAlertDialog kAlertDialog = new KAlertDialog(My_Team_List_Activity.this, KAlertDialog.WARNING_TYPE);
                    kAlertDialog.setContentText("please click on the + icon \n for making your team");
                    kAlertDialog.show();
                    kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                        kAlertDialog.dismissWithAnimation();
                    });
                }

                progressBar.setVisibility(View.GONE);
                recyclerViewIDTeamList.setVisibility(View.VISIBLE);
                team_name_list_adapter = new Team_Name_List_Adapter(team_name_list);
                recyclerViewIDTeamList.setAdapter(team_name_list_adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addNew_team_fab_button) {

            addTeamNameToDatabase();

        }
    }

    private void addTeamNameToDatabase() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_input_team_title_view);
        dialog.show();

        EditText editText = dialog.findViewById(R.id.teamTitleET_ID);
        Button button_cancel = dialog.findViewById(R.id.button_cancel);
        Button button_add = dialog.findViewById(R.id.button_add);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team_name = editText.getText().toString();

                if (team_name.isEmpty()) {
                    return;
                }
//check the the team has exit
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String company_user_ID = dataSnapshot.child("employee_list")
                                .child(check_user_information.getUserID())
                                .child("company_User_id")
                                .getValue(String.class);

                        boolean yes = dataSnapshot.child("my_team_request_pending")
                                .child(company_user_ID)
                                .child(check_user_information.getUserID())
                                .hasChild(team_name);

                        if (yes) {
                            Toasty.info(getApplicationContext(), "You can't set Same Team name \n Change the team name").show();
                        } else {
                            team_name_list.add(team_name);
                            if (team_name_list_adapter != null) {
                                team_name_list_adapter.notifyDataSetChanged();

                            }
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_loading);
        addNewTeamMate = (FloatingActionButton) findViewById(R.id.addNew_team_fab_button);
        addNewTeamMate.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        recyclerViewIDTeamList = (RecyclerView) findViewById(R.id.recyclerView_ID_team_list);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, EmployeeMainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onBackPressed();
    }
}
