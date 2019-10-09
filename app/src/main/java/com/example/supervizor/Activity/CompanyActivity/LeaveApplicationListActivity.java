package com.example.supervizor.Activity.CompanyActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.supervizor.Fragment.Company.LeaveApplication_Approved_F;
import com.example.supervizor.Fragment.Company.LeaveApplication_Pending_F;
import com.example.supervizor.R;

public class LeaveApplicationListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_pending, button_approved;
    public static LinearLayout pending_button_layout;
    public static LinearLayout approved_button_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_application_list_activity);
        //hide notificationbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Leave Application");

        button_pending = findViewById(R.id.pending_button);
        button_approved = findViewById(R.id.approved_button);
        pending_button_layout = findViewById(R.id.pending_button_layout);
        approved_button_layout = findViewById(R.id.approved_button_layout);


        leave_application_pending_fragment();

        button_approved.setOnClickListener(this);
        button_pending.setOnClickListener(this);


    }

    private void leave_application_approved_fragment() {

        Fragment fragment = new LeaveApplication_Approved_F();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.screenAreaForLeaveApplication_Layout, fragment);
            fragmentTransaction.commit();


        }
    }

    private void leave_application_pending_fragment() {

        Fragment fragment = new LeaveApplication_Pending_F();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.screenAreaForLeaveApplication_Layout, fragment);
            fragmentTransaction.commit();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pending_button:
                leave_application_pending_fragment();
                break;
            case R.id.approved_button:
                leave_application_approved_fragment();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LeaveApplicationListActivity.this, CompanyMainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onBackPressed();
    }
}
