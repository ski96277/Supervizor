package com.example.supervizor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.supervizor.AdapterClass.SalaryPolicyListAdapter;
import com.example.supervizor.JavaPojoClass.SalaryPolicyPojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SalaryGenerateDashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    protected RecyclerView recyclerViewPolicy;
    protected TextView noPolicyAddedID;
    protected FloatingActionButton floatingActionButtonSalaryGenerate;
    private SpinKitView loading_ID;
    DatabaseReference databaseReference;
    private Check_User_information check_user_information;
    private List<SalaryPolicyPojoClass> salaryPolicyPojoClassList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_salary_generate_dash_board);
        getSupportActionBar().setTitle("Salary policy");
//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();

        check_user_information = new Check_User_information();
        noPolicyAddedID.setVisibility(View.GONE);


        loadedDataToRecyclerView();


    }

    private void loadedDataToRecyclerView() {
        loading_ID.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                salaryPolicyPojoClassList.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("Salary_Policy").child(check_user_information.getUserID())
                        .getChildren()) {
                    SalaryPolicyPojoClass salaryPolicyPojoClass = snapshot.getValue(SalaryPolicyPojoClass.class);

                    salaryPolicyPojoClassList.add(salaryPolicyPojoClass);

                }
                if (salaryPolicyPojoClassList.isEmpty()) {
                    noPolicyAddedID.setVisibility(View.VISIBLE);
                    recyclerViewPolicy.setVisibility(View.GONE);

                    loading_ID.setVisibility(View.GONE);
                } else {

                    loading_ID.setVisibility(View.GONE);
                    noPolicyAddedID.setVisibility(View.GONE);
                    recyclerViewPolicy.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewPolicy.setLayoutManager(linearLayoutManager);

                    SalaryPolicyListAdapter salaryPolicyListAdapter = new SalaryPolicyListAdapter(salaryPolicyPojoClassList);
                    recyclerViewPolicy.setAdapter(salaryPolicyListAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading_ID.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.floatingActionButton_salary_generate) {

            startActivity(new Intent(getApplicationContext(), PolicyFormActivity.class));
        }
    }

    private void initView() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerViewPolicy = (RecyclerView) findViewById(R.id.recyclerView_policy);
        noPolicyAddedID = (TextView) findViewById(R.id.no_policy_added_ID);
        floatingActionButtonSalaryGenerate = (FloatingActionButton) findViewById(R.id.floatingActionButton_salary_generate);
        floatingActionButtonSalaryGenerate.setOnClickListener(SalaryGenerateDashBoardActivity.this);
        check_user_information = new Check_User_information();
        loading_ID = findViewById(R.id.loading_spin_kit);
    }
}
