package com.example.supervizor.Activity.CompanyActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.supervizor.AdapterClass.Team_Leader_List_Adapter;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamLeaderListActivity extends AppCompatActivity {


    RecyclerView recyclerView_team_leader_lsit;
    private DatabaseReference databaseReference;
    Check_User_information check_user_information;
    List<AddEmployee_PojoClass> addEmployee_pojoClasses_list = new ArrayList<>();

    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_leader_f);
//hide notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Team Leader List");

        initview();

        if (!CheckInternet.isInternet(this)){
            progressBar.setVisibility(View.GONE);
            Toasty.info(this,"Check Internet Connection").show();
            return;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_team_leader_lsit.setLayoutManager(linearLayoutManager);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("my_team_request_pending")
                        .child(check_user_information.getUserID()).getChildren()) {
                    String team_leader_ID = snapshot.getKey();
                    Log.e("TAG", "onDataChange: team_leader_ID = " + team_leader_ID);

                    AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(team_leader_ID).getValue(AddEmployee_PojoClass.class);

                    addEmployee_pojoClasses_list.add(addEmployee_pojoClass);
                }

                if (addEmployee_pojoClasses_list.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    Toasty.info(TeamLeaderListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.GONE);
                recyclerView_team_leader_lsit.setVisibility(View.VISIBLE);


                Team_Leader_List_Adapter team_leader_list_adapter = new Team_Leader_List_Adapter(addEmployee_pojoClasses_list);
                recyclerView_team_leader_lsit.setAdapter(team_leader_list_adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);

            }
        });

    }


    private void initview() {
        progressBar = findViewById(R.id.loading_team_leader);
        recyclerView_team_leader_lsit = findViewById(R.id.recycler_view_team_leader_list);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();

    }
}
