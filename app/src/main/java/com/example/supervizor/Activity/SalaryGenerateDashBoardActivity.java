package com.example.supervizor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.supervizor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SalaryGenerateDashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    protected RecyclerView recyclerViewPolicy;
    protected TextView noPolicyAddedID;
    protected FloatingActionButton floatingActionButtonSalaryGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_salary_generate_dash_board);
        getSupportActionBar().setTitle("Salary policy");
//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.floatingActionButton_salary_generate) {

            startActivity(new Intent(getApplicationContext(),PolicyFormActivity.class));
        }
    }

    private void initView() {
        recyclerViewPolicy = (RecyclerView) findViewById(R.id.recyclerView_policy);
        noPolicyAddedID = (TextView) findViewById(R.id.no_policy_added_ID);
        floatingActionButtonSalaryGenerate = (FloatingActionButton) findViewById(R.id.floatingActionButton_salary_generate);
        floatingActionButtonSalaryGenerate.setOnClickListener(SalaryGenerateDashBoardActivity.this);
    }
}
